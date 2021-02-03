package model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class MasterCourseBag
{
	private Course[] masterCourseArray;
	private int nElems;
	private Course[] courseBag = new Course[0];
	
	public void insert(Course course){
		Course[] temp = new Course[courseBag.length + 1];
		for(int i = 0; i<courseBag.length; i++){
			temp[i] = courseBag[i];
		}
		temp[temp.length-1] = course;
		courseBag = temp;
	}
	
	public void display() 
	{
		for(int i = 0; i < nElems; i++)
		{
			System.out.println(masterCourseArray[i]);
		}
		System.out.println();
	}
	
	public void list() {
		for(Course c: courseBag){
			System.out.println(c.getCourseTitle());
		}
		
	}
	
	public Course find(String courseNumber) 
	{
		for(int i = 0; i < nElems; i++) 
		{
			if(masterCourseArray[i].getCourseNumber().equals(courseNumber))
			{
				return masterCourseArray[i];
			}
		}
		return null;
	}
	public void delete(Course course){
		ArrayList<Course> arraylist = new ArrayList<Course>();
		for(Course c: courseBag){
			arraylist.add(c);
		}
		if(arraylist.contains(course)){
			arraylist.remove(course);
		courseBag = arraylist.toArray(new Course[arraylist.size()]);
		System.out.println("Removed: \t" +  course.getCourseTitle() + " " +  course.getCourseNumber());
		}
		else{
			System.out.println("Person not in courseBag..");
		}
		
		
	}
	public Course searchByCourseNumber(String courseNumber){
		for(Course c: courseBag){
			if(c.getCourseNumber().equals(courseNumber)){
				return c;
			}
		}
		return null;
		
	}
	
	public void importCourseBag(String path){

		FileReader fr = null;
	
		try {
			fr = new FileReader(path);
		} catch (FileNotFoundException e) {
			System.out.println("Couldnt locate file..");
			return;
		}
		BufferedReader br = new BufferedReader(fr);
		
		String read = null;
		
		try {
			while((read = br.readLine()) != null){
				String[] splited = read.split("#");
				for(String t: splited){
					String[] course = t.split("/");
//					public Course(String courseNumber, String courseTitle, String courseDescription, Double courseCredits,
//							ArrayList<String> textbooks) {
					
					String coursenumber = course[0];
					String coursetitle = course[1];
					String coursedescription = course[2];
					Double coursecredits = Double.parseDouble(course[3]);	
					
					String[] a = course[4].substring(1, course[4].length()).split(",");
					ArrayList<String> Textbooks = new ArrayList<String>();
					for(String textbook: a){
						Textbooks.add(textbook);
					}
					Course c = new Course(coursetitle, coursenumber, coursecredits, coursedescription, Textbooks);
					if(searchByCourseNumber(coursenumber) == null){
						Demo.mastercoursebag.insert(c);
						System.out.println("Added course: " + c.getCourseNumber());
					}
					else{
						System.out.println("Couldn't add Course:" + c.getCourseNumber() + "... already have course with same course number");
						
					}
				}
			}
		} catch (IOException e) {
			System.out.println("Problem with importing BodyBag" + e);
		}
		finally {
			try {
				br.close();
			} catch (IOException e) {
			}
		}
		
		
	}
	
	public void exportCourseBag(String path){
		File file = new File(path);
		try {
			file.createNewFile();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		PrintWriter pw = null;
		try {
			pw = new PrintWriter(file);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		for(Course c: courseBag){	
			pw.write("#\n"+c.getCourseNumber() + "/" + c.getCourseTitle()+ "/" + c.getCourseDescription() +  "/"  + c.getCredits() + "/"  + c.getTextbook().toString());
			pw.close();
		}
	}
	
	public ObservableList<String> getCourses(){
		ObservableList<String> h = FXCollections.observableArrayList();
		for(Course c: courseBag){
			h.add(c.getCourseTitle());
		}
		
		return h;
	}
	
	public void load(){
		ObjectInputStream OIS = null;
		System.out.println("Trying to load CourseBag.");
		try{
			try {
				OIS = new ObjectInputStream(new FileInputStream("CourseBag.dat"));
			} catch (IOException e) {
				System.out.println("Couldnt find coursebag: " + e);
			}
			try {
				this.courseBag= ((Course[])OIS.readObject());
			} catch (NullPointerException | ClassNotFoundException | IOException e) {
				System.out.println("Array couldn't be read from BodyBag: " + e);
			}
		}finally{
			System.out.println("CourseBag Successfully Loaded.");
			for(Course c: this.courseBag){
				System.out.println(c.getCourseNumber());
			}
		}
		
	}
	
	public void update() {
		FileOutputStream FOS = null;
		ObjectOutputStream OOS = null;
		try {
			FOS = new FileOutputStream("CourseBag.dat");
		} catch (FileNotFoundException e) {
			// IF FILE ISNT FOUND
		}
		
		try {
			OOS = new ObjectOutputStream(FOS);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			OOS.writeObject(this.courseBag);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			OOS.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("CourseBag successfully updated.");
	}
	
	
}
