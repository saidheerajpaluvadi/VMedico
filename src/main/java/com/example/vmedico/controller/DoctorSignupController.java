package com.example.vmedico.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.example.vmedico.dao.DoctorRepo;
import com.example.vmedico.model.Doctor;

@Controller
public class DoctorSignupController {
	
	@Autowired
	DoctorRepo drepo;
	
	/*
	 * New doctor account creation
	 */
	@RequestMapping("newdoctor")
	public ModelAndView doctorSignup(Doctor d) {
		System.out.println(d);
		if(d.getYearsofexperience()==null) {
			d.setYearsofexperience(0);
		}
		 if(d.getFirstname()==""){
				ModelAndView mv = new ModelAndView();
				mv.addObject("errormsg","Please enter your first name");
				mv.setViewName("doctorsignup");
				return mv;
			}
		 
		 else if(d.getSpecialization()==null) {
			 ModelAndView mv = new ModelAndView();
				mv.addObject("errormsg","Please enter your specialization");
				mv.setViewName("doctorsignup");
				return mv;	 
		 }
		 else if(d.getHospitalname()=="") {
			 ModelAndView mv = new ModelAndView();
				mv.addObject("errormsg","Please enter your hospital name");
				mv.setViewName("doctorsignup");
				return mv;	 
		 }
		 else if(d.getYearsofexperience()<0) {
			 ModelAndView mv = new ModelAndView();
				mv.addObject("errormsg","Years of experience cannot be negative");
				mv.setViewName("doctorsignup");
				return mv;	 
		 }
			
				
		 else if(d.getEmail()==""){
			ModelAndView mv = new ModelAndView();
			mv.addObject("errormsg","Please enter your mail ID");
			mv.setViewName("doctorsignup");
			return mv;
		}
		 
		 else if(d.getPwd()=="") {
			 ModelAndView mv = new ModelAndView();
				mv.addObject("errormsg","Please enter your password");
				mv.setViewName("doctorsignup");
				return mv;			 
		 }
		 else if(d.getCpwd()=="") {
			 ModelAndView mv = new ModelAndView();
				mv.addObject("errormsg","Please enter your confirm password");
				mv.setViewName("doctorsignup");
				return mv;			 
		 }
		
		else if(!d.getPwd().equals(d.getCpwd())) {
			ModelAndView mv = new ModelAndView();
			mv.addObject("errormsg","Your password and confirm password are not same");
			mv.setViewName("doctorsignup");
			return mv;
		}
		else {	
			ModelAndView mv = new ModelAndView();
			
			if(drepo.findById(d.getEmail()).isPresent()) {
				mv.addObject("errormsg","EmailID already exists");
				mv.setViewName("doctorsignup");
				return mv;
			}
			else {
			drepo.save(d);
	
			mv.setViewName("home");
			return mv;
			}
			
		}

		
	}

}
