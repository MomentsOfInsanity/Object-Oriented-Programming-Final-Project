package model;

import java.io.Serializable;
import java.util.ArrayList;

public class Course implements Serializable
{
	private String coursetitle;
	private ArrayList<String> textbooks;
	private String courseDescription;
	private String courseNumber;
	private double credits;
	
	private static int courseCounter = 101;
	
	Course(String coursetitle, String courseNumber, double credits, String courseDescription, ArrayList<String> textbooks)
	{
		super();
		this.coursetitle = coursetitle;
		this.textbooks = textbooks;
		this.courseNumber = String.valueOf(courseCounter++);
		this.credits = credits;
		this.courseDescription = courseDescription;
	}

	public String getCourseTitle()
	{
		return coursetitle;
	}

	public void setCourseTitle(String coursetitle)
	{
		this.coursetitle = coursetitle;
	}

	public ArrayList<String> getTextbook()
	{
		return textbooks;
	}

	public void setTextbook(ArrayList<String> textbooks) 
	{
		this.textbooks = textbooks;
	}

	public String getCourseNumber()
	{
		return courseNumber;
	}

	public void setCourseNumber(String courseNumber)
	{
		this.courseNumber = courseNumber;
	}

	public double getCredits()
	{
		return credits;
	}

	public void setCredits(double credits)
	{
		this.credits = credits;
	}
	
	public String getCourseDescription() {
		return courseDescription;
	}

}
