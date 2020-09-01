package com.example.vmedico.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.example.vmedico.dao.PatientRepo;
import com.example.vmedico.model.Patient;

@Controller
public class PatientSignupController {
	
	@Autowired
	PatientRepo prepo;
	
	@RequestMapping("newpatient")
	public ModelAndView patientsignup(Patient p,HttpServletRequest req) {
		HttpSession session = req.getSession();
		System.out.print(p);
		 if(p.getFirstname()==""){
				ModelAndView mv = new ModelAndView();
				mv.addObject("errormsg","Please enter your first name");
				mv.setViewName("patientsignup");
				return mv;
			}
				
		 else if(p.getEmail()==""){
			ModelAndView mv = new ModelAndView();
			mv.addObject("errormsg","Please enter your mail ID");
			mv.setViewName("patientsignup");
			return mv;
		}
		 
		 else if(p.getPwd()=="") {
			 ModelAndView mv = new ModelAndView();
				mv.addObject("errormsg","Please enter your password");
				mv.setViewName("patientsignup");
				return mv;			 
		 }
		 else if(p.getCpwd()=="") {
			 ModelAndView mv = new ModelAndView();
				mv.addObject("errormsg","Please enter your confirm password");
				mv.setViewName("patientsignup");
				return mv;			 
		 }
		
		else if(!p.getPwd().equals(p.getCpwd())) {
			ModelAndView mv = new ModelAndView();
			mv.addObject("errormsg","Your password and confirm password are not same");
			mv.setViewName("patientsignup");
			return mv;
		}
		else {	
			ModelAndView mv = new ModelAndView();
			
			if(prepo.findById(p.getEmail()).isPresent()) {
				mv.addObject("errormsg","EmailID already exists");
				mv.setViewName("patientsignup");
				return mv;
			}
			else {
			prepo.save(p);
			session.setAttribute("patientmail", p.getEmail());
			mv.setViewName("patientstab.jsp");
			return mv;
			}
			
		}
		
	}

}
