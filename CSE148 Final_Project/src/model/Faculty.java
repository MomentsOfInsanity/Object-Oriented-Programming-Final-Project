package model;

import java.util.ArrayList;

public class Faculty extends Person{
	private String rank;
	private double salary;
	private Address Address;
	private ArrayList<String> coursesTeaching = new ArrayList<String>();

	public Address getAddress() {
		return Address;
	}


	public void setCoursesTeaching(ArrayList<String> coursesTeaching) {
		this.coursesTeaching = coursesTeaching;
	}


	public void setAddress(Address address) {
		Address = address;
	}


	
	
	
	public void setRank(String rank) {
		this.rank = rank;
	}


	public void setSalary(double salary) {
		this.salary = salary;
	}


	public String getRank() {
		return rank;
	}


	public double getSalary() {
		return salary;
	}


	public ArrayList<String> getCoursesTeaching() {
		return coursesTeaching;
	}


	public Faculty(String firstName, String lastName) {
		super(firstName, lastName);
	}

	

}
