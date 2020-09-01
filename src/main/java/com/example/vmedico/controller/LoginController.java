package com.example.vmedico.controller;




import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.example.vmedico.dao.DoctorAppointmentRepo;
import com.example.vmedico.dao.DoctorRepo;
import com.example.vmedico.dao.PatientRepo;
import com.example.vmedico.dao.TestLabRepo;
import com.example.vmedico.model.Doctor;

import com.example.vmedico.model.Patient;
import com.example.vmedico.model.TestingLab;

@Controller
public class LoginController  {
			
	@Autowired
	PatientRepo prepo;
	
	@Autowired
	DoctorRepo drepo;
	
	@Autowired
	TestLabRepo tlrepo;
	
	@Autowired
	DoctorAppointmentRepo docapprepo;

	String patientmail;
	
	@RequestMapping("loginpatient")
	public ModelAndView login(String email,String password,HttpServletRequest request) {
		HttpSession session = request.getSession();
		System.out.print(email + password);
		ModelAndView mv = new ModelAndView();
		
		Patient p =prepo.findById(email).orElse(null); 
		int flag=0;
		
			if(p!=null && p.getPwd().equals(password)) {
				patientmail = email;
				mv.addObject("patientname",p.getFirstname());
				session.setAttribute("patientname",p.getFirstname());
				session.setAttribute("patientmail",p.getEmail());
				mv.addObject("patientmail",p.getEmail());
				mv.setViewName("patientstab.jsp");
				
				System.out.println("Im here");
				flag = 1;
			}		
		
		if(flag==0) {
			mv.addObject("errormsgpatient","Invalid username or password");
			mv.setViewName("home");
		}
		return mv;
	}

	

	@RequestMapping("logindoctor")
	public ModelAndView logindoctor(String email,String password,HttpServletRequest request) {
		HttpSession session = request.getSession();
		System.out.print(email + password);
		ModelAndView mv = new ModelAndView();
		
		Doctor d =drepo.findById(email).orElse(null); 
	
		
			int flag=0;
			
				if(d != null && d.getPwd().equals(password)) {
					session.setAttribute("doctormail",d.getEmail());
					mv.addObject("name",d.getFirstname());
					mv.setViewName("doctorstab.jsp");
					System.out.println("Im here");
					flag = 1;
				}
				
		if(flag==0) {
			mv.addObject("errormsgdoctor","Invalid username or password");
			mv.setViewName("home");
		}
		return mv;
	}
	@RequestMapping("logintestlab")
	public ModelAndView logintestlab(String email,String password,HttpServletRequest request) {
		HttpSession session = request.getSession();
		System.out.print(email + password);
		ModelAndView mv = new ModelAndView();
		
		TestingLab tl =tlrepo.findById(email).orElse(null); 
			int flag=0;
			
				if(tl!=null && tl.getPwd().equals(password)) {
					session.setAttribute("testinglabmail", email);
					mv.addObject("name",tl.getNameoflab());
					mv.setViewName("testlabtab.jsp");
					System.out.println("Im here");
					flag = 1;
				}
				
	
		if(flag==0) {
			mv.addObject("errormsgtestlab","Invalid username or password");
			mv.setViewName("home");
		}
		return mv;
	}
	
			
}
