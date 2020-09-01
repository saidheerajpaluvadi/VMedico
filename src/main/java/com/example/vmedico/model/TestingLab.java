package com.example.vmedico.model;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class TestingLab {
	
	@Id
	private String email;
	private String nameoflab;
	private Integer yearofestablishment;
	private String pwd;
	private String cpwd;
	private String address;
	
	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getNameoflab() {
		return nameoflab;
	}
	public void setNameoflab(String nameoflab) {
		this.nameoflab = nameoflab;
	}
	public Integer getYearofestablishment() {
		return yearofestablishment;
	}
	public void setYearofestablishment(Integer yearofestablishment) {
		this.yearofestablishment = yearofestablishment;
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
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	@Override
	public String toString() {
		return "TestingLab [email=" + email + ", nameoflab=" + nameoflab + ", yearofestablishment="
				+ yearofestablishment + ", pwd=" + pwd + ", cpwd=" + cpwd + "]";
	}
	
	

}
