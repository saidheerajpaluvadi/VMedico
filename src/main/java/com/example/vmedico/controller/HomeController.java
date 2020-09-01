package com.example.vmedico.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {
	@RequestMapping("home")
	public String home() {
		System.out.println("Hi Home controller");
		return "home.jsp";
	}
	
	@RequestMapping("patientsignup")
	public String patientsignup() {
		System.out.println("Hi patientsignup controller");
		return "patientsignup.jsp";
	}
	
	@RequestMapping("doctorsignup")
	public String doctorsignup() {
		System.out.println("Hi doctorsignup controller");
		return "doctorsignup.jsp";
	}
	
	@RequestMapping("testinglabsignup")
	public String testinglabsignup() {
		System.out.println("Hi testinglabsignup controller");
		return "testinglabsignup.jsp";
	}

}
