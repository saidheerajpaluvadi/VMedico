package com.example.vmedico.model;


import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Doctor  {
	
	@Id
	private String email;
	private String firstname;
	private String lastname;
	private String hospitalname;
	private String highestqualification;
	private String specialization;
	private String location;
	private Integer yearsofexperience;
	private String pwd;
	private String cpwd;
	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getFirstname() {
		return firstname;
	}
	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}
	public String getLastname() {
		return lastname;
	}
	public void setLastname(String lastname) {
		this.lastname = lastname;
	}
	public String getHospitalname() {
		return hospitalname;
	}
	public void setHospitalname(String hospitalname) {
		this.hospitalname = hospitalname;
	}
	public String getSpecialization() {
		return specialization;
	}
	public void setSpecialization(String specialization) {
		this.specialization = specialization;
	}
	public Integer getYearsofexperience() {
		return yearsofexperience;
	}
	public void setYearsofexperience(Integer yearsofexperience) {
		this.yearsofexperience = yearsofexperience;
	}
	public String getPwd() {
		return pwd;
	}
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}
	public String getCpwd() {
		return cpwd;
	}
	public void setCpwd(String cpwd) {
		this.cpwd = cpwd;
	}
	public String getHighestqualification() {
		return highestqualification;
	}
	public void setHighestqualification(String highestqualification) {
		this.highestqualification = highestqualification;
	}
	
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	@Override
	public String toString() {
		return "Doctor [email=" + email + ", firstname=" + firstname + ", lastname=" + lastname + ", hospitalname="
				+ hospitalname + ", specialization=" + specialization + ", yearsofexperience=" + yearsofexperience
				+ ", pwd=" + pwd + ", cpwd=" + cpwd + "]";
	}
	

}
