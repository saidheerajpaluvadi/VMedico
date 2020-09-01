package com.example.vmedico.controller;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.example.vmedico.dao.DoctorAppointmentRepo;
import com.example.vmedico.dao.DoctorRepo;
import com.example.vmedico.model.Doctor;
import com.example.vmedico.model.DoctorAppointment;


@Controller
public class BookAnAppointmentController {
	
	HttpServletRequest req;
	
	@Autowired
	DoctorRepo drepo;	

	DoctorAppointment docapp;
	
	@Autowired
	DoctorAppointmentRepo docapprepo;
	
	/*
	 * To display the various doctors available according to the given 
	 * Specialization ( @param spec)
	 */	
	@RequestMapping("/doctordetails")
	public ModelAndView bookanappointment(String spec) {
		ModelAndView mv = new ModelAndView();
		ArrayList<Doctor> rli = new ArrayList<Doctor>(); 
		ArrayList<Doctor> ali = (ArrayList<Doctor>) drepo.findAll();
		
		for(Doctor doc:ali) {
			if(doc.getSpecialization().equals(spec)) 
				rli.add(doc);
		}
		Comparator<Doctor> cmp = (Doctor o1, Doctor o2)-> {
					if(o1.getYearsofexperience()<o2.getYearsofexperience())
					return 1;
					else if(o1.getYearsofexperience()==o2.getYearsofexperience())
						return 0;
					else
						return -1;
				};
		rli.sort(cmp);
		mv.addObject("doclist",rli);
		mv.setViewName("doctordetails.jsp");
		return mv;
	}
	
	/*
	 *  To get the email ID of the doctor chosen by Patient 
	 */
	@RequestMapping("/book")
	public String book(String doctormail,HttpServletRequest request) {
		HttpSession session = request.getSession();
		session.setAttribute("doctormail", doctormail);
		//System.out.println(doctormail);
		//System.out.println(session.getAttribute("patientmail"));
		return "bookonadate.jsp";
	}
	
	/*
	 *  To get the appointment date and appointment time of a patient and 
	 *  created a DoctorAppointment Object which is used later.
	 */
	@RequestMapping("/bookondate")
	public ModelAndView bookonDate(String time,HttpServletRequest request,HttpServletResponse response) {
		docapp = new DoctorAppointment();
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
		
		docapp.setDoctormail(session.getAttribute("doctormail").toString());
		docapp.setPatientmail(session.getAttribute("patientmail").toString());
		docapp.setAppointmentdate(appdate);
		docapp.setAppointmenttime(timeselected);
		docapp.setStatus("waitingforconformation");
		Doctor doctor = drepo.findById(session.getAttribute("doctormail").toString()).orElse(null);
		if(docapprepo.checkExists(session.getAttribute("patientmail").toString(),docapp.getAppointmentdate(),docapp.getAppointmenttime(),docapp.getStatus(),"booked").size()==0) {
		
		ModelAndView mv = new ModelAndView();
		mv.addObject("doctorappointmentobj",docapp);
		mv.addObject("doctor",doctor);
		mv.setViewName("bookingreason.jsp");
			
		return mv;
		}
		else {
			ModelAndView mv = new ModelAndView();
			mv.addObject("doctorbookingerrormsg","You have an appointment already at this time");
			mv.setViewName("bookonadate.jsp");
			return mv;
		}
		
	}
	
	/*
	 *  Confirmation from the patient
	 */
	@RequestMapping("/placearequest")
	public ModelAndView placearequest(String reason,String result) {
		ModelAndView mv = new ModelAndView();
		docapp.setReason(reason);
		if(result.equals("confirm")) {
			docapprepo.save(docapp);
		mv.addObject("doctorbookingmsg","Your request has been placed... Click On status to view your application status");
		mv.addObject("doctorappointmentobj",null);
		mv.addObject("doctor",null);
		mv.setViewName("bookingreason.jsp");
		return mv;
		}
		else {
			mv.setViewName("bookanappointment.jsp");
			return mv;
		}
	}

}
