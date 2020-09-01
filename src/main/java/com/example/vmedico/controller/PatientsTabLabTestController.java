package com.example.vmedico.controller;


import java.io.IOException;
import java.text.DateFormat;
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

import com.example.vmedico.dao.DoctorAppointmentRepo;
import com.example.vmedico.dao.PatientFileRepo;
import com.example.vmedico.dao.PatientRepo;
import com.example.vmedico.dao.TestLabAppointmentRepo;
import com.example.vmedico.dao.TestLabRepo;
import com.example.vmedico.model.Patient;
import com.example.vmedico.model.PatientFiles;
import com.example.vmedico.model.TestingLab;
import com.example.vmedico.model.TestingLabAppointment;
import com.example.vmedico.service.PatientFilesService;

@Controller
public class PatientsTabLabTestController {
	
	@Autowired
	TestLabRepo tlrepo;
	
	@Autowired
	TestLabAppointmentRepo tlarepo;
	
	@Autowired
	PatientFilesService pfilesservice;
	
	@Autowired
	PatientRepo prepo;

	@Autowired
	DoctorAppointmentRepo docapprepo;
	
	@Autowired
	PatientFilesService pfilesserivce;
	
	@Autowired
	PatientFileRepo pfilesrepo;
	@RequestMapping("statustestlab")
	public ModelAndView statustestlab(HttpServletRequest request) {
			HttpSession session = request.getSession();
			checkRejections(session.getAttribute("patientmail").toString());
			
			ModelAndView mv = new ModelAndView();
			ArrayList<TestingLabAppointment> li = (ArrayList<TestingLabAppointment>) tlarepo.findAll();
			ArrayList<TestingLabAppointment> rli = new ArrayList<TestingLabAppointment>();
			
			ArrayList<TestingLab> dli = (ArrayList<TestingLab>) tlrepo.findAll();
			ArrayList<TestingLab> resdli = new ArrayList<TestingLab>();
			
			for(TestingLabAppointment da:li) {
				if(da.getPatientmail().equals(session.getAttribute("patientmail")))
					rli.add(da);
			}
			DateFormat f = new SimpleDateFormat("dd/MMM/yyyy hh:mm:ss aa");
			Comparator<TestingLabAppointment> cmp = (TestingLabAppointment da1,TestingLabAppointment da2)->{
				
				String s1 = da1.getAppointmentdate().trim()+" "+da1.getAppointmenttime().trim();
				String s2 = da2.getAppointmentdate().trim()+" "+da2.getAppointmenttime().trim();
				 try {
		                return f.parse(s2).compareTo(f.parse(s1));
		            } catch (ParseException e) {
		                throw new IllegalArgumentException(e);
		            }
			};
			rli.sort(cmp);
			
			for(TestingLabAppointment da:rli) {
				for(int i=0;i<dli.size();i++) {
					if(dli.get(i).getEmail().equals(da.getTestinglabmail())) {
						resdli.add(dli.get(i));
						
						break;
					}
				}
				
			}
			mv.addObject("statustestlabappointmentlist",rli);
			mv.addObject("statustestlabdetails",resdli);
			
			
			mv.setViewName("statustestlab.jsp");	
			return mv;		
	}
	private void checkRejections(String patientmail) {
		System.out.print("checking");
		ArrayList<TestingLabAppointment> al = tlarepo.findAllAppointmentsOfPatients(patientmail,"waitingforconformation");
		System.out.print(al.size());
		Date date;
		  Format formatter;
		  Calendar calendar = Calendar.getInstance();

		  date = calendar.getTime();
		  formatter = new SimpleDateFormat("dd/MMM/yyyy hh:mm:ss aa");
		  String todaydate = formatter.format(date);
		  
		  for(int i=0;i<al.size();i++) {
			  String appdate = al.get(i).getAppointmentdate()+" "+al.get(i).getAppointmenttime();
			  if(difference(todaydate,appdate) < 0) {
				  tlarepo.changeThePatientStatus("rejected", al.get(i).getId());		  
			  }
		  }
		
	}



	private int difference(String todaydate, String appdate) {
		System.out.println("difference");
		try {
			 SimpleDateFormat myFormat = new SimpleDateFormat("dd/MMM/yyyy hh:mm:ss aa");
		       Date dateBefore = myFormat.parse(todaydate);
		       Date dateAfter = myFormat.parse(appdate);
		       System.out.print( dateAfter.getTime() +" - "+ dateBefore.getTime());
		       long difference = dateAfter.getTime() - dateBefore.getTime();
		       int daysBetween = (int) (difference );
	            
		       System.out.println("Number of Days between dates: "+daysBetween);
		       return daysBetween;
		 } catch (Exception e) {
		       e.printStackTrace();
		       return 0;
		 }
		
	}
	
	@RequestMapping("/patientprofilefortestlab")
	public ModelAndView profile(HttpServletRequest req) {
		HttpSession session = req.getSession();
		String patientmail = (String) session.getAttribute("patientmail");
		ModelAndView mv = new ModelAndView();
		
		Patient p = prepo.findById(patientmail).orElse(null);
		Integer countDoctorappointments = docapprepo.getCountOfDoctorsBookings(patientmail);
		Integer countTestlabAppointments = tlarepo.getCountOfTestLabBookings(patientmail);
		ArrayList<PatientFiles> files = pfilesrepo.getFiles(patientmail);
		//System.out.println("No of files "+files.size());
		mv.addObject("patientprofileobj",p);
		mv.addObject("noofbookingswithdoctor",countDoctorappointments);
		mv.addObject("noofbookingswithtestlab",countTestlabAppointments);
		mv.addObject("patientfiles",files);
		mv.setViewName("patientprofilefortestlab.jsp");
		

		
		return mv;
	}
	
	@RequestMapping("/uploadpatientfilesfromtestlab")
	public ModelAndView uploadprescription(@RequestParam("patientfiles") MultipartFile  patientfiles,HttpServletRequest request) throws IOException {
		HttpSession session = request.getSession();
		
			ModelAndView mv = new ModelAndView();
			MultipartFile mpf= patientfiles;
			System.out.println(mpf.getOriginalFilename());
			//System.out.println(this.da.getPatientmail()+" "+this.da.getDoctormail()+" "+this.da.getAppointmentdate()+" "+ this.da.getAppointmenttime());
			
			pfilesservice.storeFile(mpf.getOriginalFilename(), mpf.getContentType(),mpf.getBytes(),session.getAttribute("patientmail").toString());		
			
			String fileDownloaduri = ServletUriComponentsBuilder.fromCurrentContextPath()
									 .path("/downloadPatientFiles/")
									 .path(mpf.getOriginalFilename())
									 .toUriString();
			System.out.print(fileDownloaduri);
			
			
			
			mv.addObject("resultofuploadingpatientfiles","Uploaded sucessfully");
			mv.setViewName("patientfilesuploadfromtestlab.jsp");
			return mv;
		
			
	}

	@RequestMapping("deletefilefromtestlab")
	public String  deletePatientfile(String fileID){ 
		PatientFiles required = pfilesrepo.findById(Long.parseLong(fileID)).orElse(null);
		//pfilesrepo.deleteFile(required.getPatientfilename(), required.getPatilentfiletype(), required.getPatientfiles(),session.getAttribute("patientmail").toString());
		pfilesrepo.delete(required);
		System.out.println("deleted");
		return "redirect:patientprofilefortestlab";
	}

	@RequestMapping("/editprofilefromtestlab")
	public String editprofile(String lastname,String medicalhistory,HttpServletRequest request) {
		HttpSession session = request.getSession();
		prepo.updateChanges(lastname,medicalhistory,session.getAttribute("patientmail").toString());
		return "redirect:patientprofilefortestlab";
	}
	
}
