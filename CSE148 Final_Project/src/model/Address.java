package model;

import java.io.Serializable;

public class Address implements Serializable{
	private String street;
	private String town;
	private String state;
	private String zip;
	private String streetnum;



	public Address(String streetnum, String street, String town, String state, String zip) {
		super();
		this.streetnum = street;
		this.street = street;
		this.town = town;
		this.state = state;
		this.zip = zip;
	}


	public String getStreet() {
		return street;
	}
	public void setStreet(String street) {
		this.street = street;
	}
	public String getTown() {
		return town;
	}
	public void setTown(String town) {
		this.town = town;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getZip() {
		return zip;
	}
	public void setZip(String zip) {
		this.zip = zip;
	}
	public String getStreetnum() {
		return streetnum;
	}
	
	
	public void setStreetnum(String streetnum) {
		this.streetnum = streetnum;
	}
	
	public String toString(){
		String s = streetnum + "," + street + "," + town + "," + state + "," + zip;
		return s;
	}
}
