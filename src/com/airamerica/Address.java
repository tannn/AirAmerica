package com.airamerica;

public class Address {
	
	private String street;
	private String city;
	private String state;
	private int zip;
	private String country;

	public Address(String street, String city, String state, String zip, String country) {
		this.street = street;
		this.city = city.replaceAll("\\s","");
		this.state = state.replaceAll("\\s","");
		this.zip = Integer.parseInt(zip.replaceAll("\\s",""));
		this.country = country.replaceAll("\\s","");
		}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}
	
	public String getState() {
		return state;
	}
	
	public void setState(String state) {
		this.state = state;
	}
	
	public int getZip() {
		return zip;
	}
	
	public void setZip(int zip) {
		this.zip = zip;
	}
	
	public String getCountry() {
		return country;
	}
	
	public void setCountry(String country) {
		this.country = country;
	}
	
}