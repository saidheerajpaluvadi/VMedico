package com.example.vmedico.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.example.vmedico.dao.TestLabRepo;
import com.example.vmedico.model.TestingLab;

@Controller
public class TestingLabSignupController {
	
	@Autowired
	TestLabRepo tlrepo;
	
	@RequestMapping("newtestinglab")
	public ModelAndView newTestingLab(TestingLab tl) {
		System.out.print(tl);
		
		if(tl.getNameoflab()=="") {
			ModelAndView mv = new ModelAndView();
			mv.addObject("errormsg","Please enter your laboratory name");
			mv.setViewName("testinglabsignup");
			return mv;		
		}
	
		else if(tl.getEmail()==""){
			ModelAndView mv = new ModelAndView();
			mv.addObject("errormsg","Please enter your mail ID");
			mv.setViewName("testinglabsignup");
			return mv;
		}
		 
		 else if(tl.getPwd()=="") {
			 ModelAndView mv = new ModelAndView();
				mv.addObject("errormsg","Please enter your password");
				mv.setViewName("testinglabsignup");
				return mv;			 
		 }
		 else if(tl.getCpwd()=="") {
			 ModelAndView mv = new ModelAndView();
				mv.addObject("errormsg","Please enter your confirm password");
				mv.setViewName("testinglabsignup");
				return mv;			 
		 }
		 else if(tl.getAddress()==null || tl.getAddress()=="") {
			 ModelAndView mv = new ModelAndView();
				mv.addObject("errormsg","Please enter the testing laboratory address");
				mv.setViewName("testinglabsignup");
				return mv;			 
		 }
		
		else if(!tl.getPwd().equals(tl.getCpwd())) {
			ModelAndView mv = new ModelAndView();
			mv.addObject("errormsg","Your password and confirm password are not same");
			mv.setViewName("testinglabsignup");
			return mv;
		}
		else {	
			ModelAndView mv = new ModelAndView();
			
			if(tlrepo.findById(tl.getEmail()).isPresent()) {
				mv.addObject("errormsg","EmailID already exists");
				mv.setViewName("testinglabsignup");
				return mv;
			}
			else {
			tlrepo.save(tl);
			mv.setViewName("home");
			return mv;
			}
			
		}
		

		
	}

}
