package com.example.contactsapp;

public class Contact {
	
	private int id;
	private String last;
	private String first;
	private String address;
	private String email;
	private String phone;

	
	public Contact(){}
	
	public Contact(String last, String first, String address, String email, String phone) {
		super();
		this.last = last;
		this.first = first;
		this.address = address;
		this.email = email;
		this.phone = phone;
	}
	
	//getters and setters
	
	@Override
	public String toString() {
		return "Contact [id=" + id + ": " + first + " " + last + " " + address + " " + email + " " + phone + "]";
	}

	public int getId() {
		return id;
	}
	public String getLast() {
		return last;
	}
	public String getFirst() {
		return first;
	}
	public String getAddress() {
		return address;
	}
	public String getEmail() {
		return email;
	}
	public String getPhone() {
		return phone;
	}
	public void setId(int id) {
		this.id = id;
	}
	public void setLast(String last) {
		this.last = last;
	}
	public void setFirst(String first) {
		this.first = first;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}


}
