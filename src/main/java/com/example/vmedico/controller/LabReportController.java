package com.example.vmedico.controller;


import java.text.Format;
import java.text.ParseException;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.example.vmedico.dao.PatientRepo;
import com.example.vmedico.dao.TestLabAppointmentRepo;
import com.example.vmedico.model.Patient;
import com.example.vmedico.model.TestingLabAppointment;

@Controller
public class LabReportController {
	
	@Autowired
	TestLabAppointmentRepo darepo;
	
	@Autowired
	PatientRepo prepo;
	

	private TestingLabAppointment  da = new TestingLabAppointment() ;

	ArrayList<TestingLabAppointment> al;
	
	@RequestMapping("/newtestlabreports")
	public ModelAndView listOfPatientstoBeGivenReports(HttpServletRequest req) throws ParseException {
		HttpSession session = req.getSession();
		ArrayList<TestingLabAppointment> dal = darepo.findAllAppointmentsOfTestingLab(session.getAttribute("testinglabmail").toString(),"booked");
		 al = new ArrayList<TestingLabAppointment>();
		
		for(TestingLabAppointment da:dal) {
			
				Date date1;
				Format formatter;
				Calendar calendar = Calendar.getInstance();

			  date1 = calendar.getTime();
			  formatter = new SimpleDateFormat("dd/MMM/yyyy hh:mm:ss aa");
			  String todaydate = formatter.format(date1);

			  	SimpleDateFormat myFormat = new SimpleDateFormat("dd/MMM/yyyy hh:mm:ss aa");
		       Date dateBefore = myFormat.parse(todaydate);
		       Date dateAfter = myFormat.parse(da.getAppointmentdate()+" "+da.getAppointmenttime());
		       //System.out.print( dateAfter.getTime() +" - "+ dateBefore.getTime());
		       long difference = dateAfter.getTime() - dateBefore.getTime();
		       long daysBetween =  (difference );
		       if(daysBetween < 0 && da.getReport()==null)
		    	   al.add(da);
			}

		Comparator<TestingLabAppointment> cmp = (TestingLabAppointment da1,TestingLabAppointment da2)->{
			int diff = da1.getAppointmentdate().compareTo(da2.getAppointmentdate());
			if(diff==0)
			{
				SimpleDateFormat format = new SimpleDateFormat("hh:mm:ss aa");
				try {
					Date date1 = format.parse(da1.getAppointmenttime());
					Date date2 = format.parse(da2.getAppointmenttime());
					return (int)(date1.getTime() - date2.getTime())/1000 ;
				} catch (ParseException e) {
					e.printStackTrace();
					return 0;
				}
			}
			else
				return diff;
		};
				
		al.sort(cmp);
		
		ArrayList<Patient> pal = new ArrayList<Patient>();
		
		for(TestingLabAppointment da:al) {
			Patient p = prepo.findById(da.getPatientmail()).orElse(null);
			pal.add(p);
		}
		ModelAndView mv = new ModelAndView();
		mv.addObject("testinglabappointmentstogivereportnew",al);
		mv.addObject("patientsforreportnew",pal);
		mv.setViewName("testlabreportnew.jsp");
		return mv;
	}
	
	@RequestMapping("/oldtestlabreports")
	public ModelAndView listOfPatientsAlreadyGivenReports(HttpServletRequest req) throws ParseException {
		HttpSession session = req.getSession();
		ArrayList<TestingLabAppointment> dal = darepo.findAllAppointmentsOfTestingLab(session.getAttribute("testinglabmail").toString(),"booked");
		 al = new ArrayList<TestingLabAppointment>();
		
		for(TestingLabAppointment da:dal) {
			
				Date date1;
				Format formatter;
				Calendar calendar = Calendar.getInstance();

			  date1 = calendar.getTime();
			  formatter = new SimpleDateFormat("dd/MMM/yyyy hh:mm:ss aa");
			  String todaydate = formatter.format(date1);

			  	SimpleDateFormat myFormat = new SimpleDateFormat("dd/MMM/yyyy hh:mm:ss aa");
		       Date dateBefore = myFormat.parse(todaydate);
		       Date dateAfter = myFormat.parse(da.getAppointmentdate()+" "+da.getAppointmenttime());
		       //System.out.print( dateAfter.getTime() +" - "+ dateBefore.getTime());
		       long difference = dateAfter.getTime() - dateBefore.getTime();
		       long daysBetween =  (difference );
		       if(daysBetween < 0 && da.getReport()!=null)
		    	   al.add(da);
			}

		Comparator<TestingLabAppointment> cmp = (TestingLabAppointment da1,TestingLabAppointment da2)->{
			int diff = da2.getAppointmentdate().compareTo(da1.getAppointmentdate());
			if(diff==0)
			{
				SimpleDateFormat format = new SimpleDateFormat("hh:mm:ss aa");
				try {
					Date date1 = format.parse(da1.getAppointmenttime());
					Date date2 = format.parse(da2.getAppointmenttime());
					return (int)(date1.getTime() - date2.getTime())/1000 ;
				} catch (ParseException e) {
					e.printStackTrace();
					return 0;
				}
			}
			else
				return diff;
		};
				
		al.sort(cmp);
		
		ArrayList<Patient> pal = new ArrayList<Patient>();
		
		for(TestingLabAppointment da:al) {
			Patient p = prepo.findById(da.getPatientmail()).orElse(null);
			pal.add(p);
		}
		ModelAndView mv = new ModelAndView();
		mv.addObject("testinglabappointmentstogivereportold",al);
		mv.addObject("patientsforreportold",pal);
		mv.setViewName("testlabreportold.jsp");
		return mv;
	}
	
	@RequestMapping("/addreport")
	public String addReport(String appdetails) {
		
		
		for(TestingLabAppointment da: al) {
			if(da.toString().equals(appdetails)) {
				this.da = da; break;
			}
		}
		
		System.out.println(this.da.getTestinglabmail()+" "+this.da.getPatientmail());
		return "labreportupload.jsp";	
	}
	
	
	@RequestMapping("/uploadreport")
	public ModelAndView uploadReport(@RequestParam("reportfile") MultipartFile  reportfile)   {
		
		try {
		ModelAndView mv = new ModelAndView();
			System.out.println(reportfile.getOriginalFilename());
			System.out.println(this.da.getPatientmail()+" "+this.da.getTestinglabmail()+" "+this.da.getAppointmentdate()+" "+ this.da.getAppointmenttime());
			darepo.storeFile(reportfile.getOriginalFilename(), reportfile.getContentType(),reportfile.getBytes(), this.da.getPatientmail(), this.da.getTestinglabmail(), this.da.getAppointmentdate(), this.da.getAppointmenttime());		
			
			String fileDownloaduri = ServletUriComponentsBuilder.fromCurrentContextPath()
									 .path("/downloadReport/")
									 .path(reportfile.getOriginalFilename())
									 .toUriString();
			System.out.print(fileDownloaduri);
			
			mv.addObject("resultofuploadinglabreport","Uploaded sucessfully");
			mv.setViewName("labreportupload.jsp");
			return mv;
		}
		catch(Exception ex) {
			ModelAndView mv = new ModelAndView();
			mv.addObject("erroroccuredinuploading","File size is big... cannot be uploaded");
			mv.setViewName("labreportupload.jsp");
			return mv;
			
		}
				
	}
	
	   
}
