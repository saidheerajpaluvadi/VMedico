package com.example.vmedico.controller;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.example.vmedico.dao.TestLabAppointmentRepo;
import com.example.vmedico.dao.TestLabRepo;

import com.example.vmedico.model.TestingLab;
import com.example.vmedico.model.TestingLabAppointment;

@Controller
public class BookLabController {
	TestingLabAppointment tla;
	
	String testname;
	
	
	@Autowired
	TestLabRepo tlrepo;
	
	@Autowired
	TestLabAppointmentRepo tlarepo;
	
	/*
	 * To display the various testing laboratories available according to the given 
	 * Test ( @param testname)
	 */	
	@RequestMapping("testlaboratorydetails")
	public ModelAndView bookatestlabappointment(String testname) {
		System.out.println("LAB DETAILS");
		this.testname = testname;	
		ModelAndView mv = new ModelAndView(); 
		ArrayList<TestingLab> ali = (ArrayList<TestingLab>) tlrepo.findAll();
		
		Comparator<TestingLab> cmp = (TestingLab o1, TestingLab o2)-> {
					if(o1.getYearofestablishment()>o2.getYearofestablishment())
					return 1;
					else if(o1.getYearofestablishment()==o2.getYearofestablishment())
						return 0;
					else
						return -1;
				};
		ali.sort(cmp);
		mv.addObject("testlist",ali);
		mv.setViewName("testlabdetails.jsp");
		return mv;
	}
	
	/*
	 *  To get the email ID of the Test lab chosen by Patient 
	 */
	@RequestMapping("/booktestlab")
	public String book(String testlabmail,HttpServletRequest request) {
		HttpSession session = request.getSession();
		session.setAttribute("testlabmail", testlabmail);
		System.out.println(testlabmail);
		System.out.println(session.getAttribute("patientmail"));
		return "booktestonadate.jsp";
	}
	
	/*
	 *  To get the appointment date and appointment time of a patient and 
	 *  created a TestingLabAppointment Object which is used later.
	 */
	@RequestMapping("/booktestondate")
	public ModelAndView bookonDate(String time,HttpServletRequest request) {
		tla = new TestingLabAppointment();
		HttpSession session = request.getSession();
		String str[] = time.split("-");
		String day = str[0];
		String timeselected = str[1];
		String appdate="";
		if(day.equals("today")) {
			Date date;
			  Format formatter;
			  Calendar calendar = Calendar.getInstance();

			  date = calendar.getTime();
			  formatter = new SimpleDateFormat("dd/MMM/yyyy");
			  appdate = formatter.format(date);
		}
		else if(day.equals("tomorrow")) {
			Date date;
			  Format formatter;
			  Calendar calendar = Calendar.getInstance();
			  calendar.add(Calendar.DATE, 1);
			  date = calendar.getTime();
			  formatter = new SimpleDateFormat("dd/MMM/yyyy");
			  appdate = formatter.format(date);			
		}
		
		tla.setTestinglabmail((session.getAttribute("testlabmail").toString()));
		tla.setPatientmail(session.getAttribute("patientmail").toString());
		tla.setTestname(testname);
		tla.setAppointmentdate(appdate);
		tla.setAppointmenttime(timeselected);
		tla.setStatus("waitingforconformation");
		TestingLab tl= tlrepo.findById(session.getAttribute("testlabmail").toString()).orElse(null) ;
		if(tlarepo.checkExists(session.getAttribute("patientmail").toString(),tla.getAppointmentdate(),tla.getAppointmenttime(),tla.getStatus(),"booked").size()==0) {
		//tlarepo.save(tla);
		
		ModelAndView mv = new ModelAndView();
		mv.addObject("testinglabappointmentobj",tla);
		mv.addObject("testinglab",tl);
		mv.setViewName("bookingconformationfortestlab.jsp");
		
		return mv;
		}
		else {
			ModelAndView mv = new ModelAndView();
			//mv.addObject("bookingerrormsg","You have an appointment already at this time");
			mv.addObject("testlabbookingerrormsg","You have an appointment already at this time");
			mv.setViewName("booktestonadate.jsp");
			return mv;
		}
		
	}
	
	/*
	 *  Confirmation from the patient
	 */
	@RequestMapping("/placearequestfortestlab")
	public ModelAndView placearequest(String reason,String result) {
		ModelAndView mv = new ModelAndView();
		
		if(result.equals("confirm")) {
			tlarepo.save(tla);
		mv.addObject("testlabbookingmsg","Your request has been placed... Click On status to view your application status");
		mv.addObject("testinglabappointmentobj",null);
		mv.addObject("testinglab",null);
		mv.setViewName("bookingconformationfortestlab.jsp");
		return mv;
		}
		else {
			mv.setViewName("booktestlab.jsp");
			return mv;
		}
	}


}
