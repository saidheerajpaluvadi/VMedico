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

import com.example.vmedico.dao.DoctorAppointmentRepo;
import com.example.vmedico.dao.PatientRepo;
import com.example.vmedico.model.DoctorAppointment;
import com.example.vmedico.model.Patient;

@Controller
public class PrescriptionController {
	
	@Autowired
	DoctorAppointmentRepo darepo;
	
	@Autowired
	PatientRepo prepo;
	

	private DoctorAppointment da = new DoctorAppointment() ;

	ArrayList<DoctorAppointment> al;
	
	@RequestMapping("/newdoctorprescription")
	public ModelAndView listOfPatientstoBeGivenPrescription(HttpServletRequest req) throws ParseException {
		HttpSession session = req.getSession();
		ArrayList<DoctorAppointment> dal = darepo.findAllAppointmentsOfDoctor(session.getAttribute("doctormail").toString(),"booked");
		 al = new ArrayList<DoctorAppointment>();
		
		for(DoctorAppointment da:dal) {
			
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
		       if(daysBetween < 0 && da.getData()==null && !da.isPrescriptionnotneed())
		    	   al.add(da);
			}

		Comparator<DoctorAppointment> cmp = (DoctorAppointment da1,DoctorAppointment da2)->{
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
		
		for(DoctorAppointment da:al) {
			Patient p = prepo.findById(da.getPatientmail()).orElse(null);
			pal.add(p);
		}
		ModelAndView mv = new ModelAndView();
		mv.addObject("doctorappointmentstogiveprescriptionnew",al);
		mv.addObject("patientsforprescriptionnew",pal);
		mv.setViewName("doctorprescriptionnew.jsp");
		return mv;
	}
	
	@RequestMapping("/olddoctorprescription")
	public ModelAndView listOfPatientsAlreadyGivenPrescription(HttpServletRequest req) throws ParseException {
		HttpSession session = req.getSession();
		ArrayList<DoctorAppointment> dal = darepo.findAllAppointmentsOfDoctor(session.getAttribute("doctormail").toString(),"booked");
		 al = new ArrayList<DoctorAppointment>();
		
		for(DoctorAppointment da:dal) {
			
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
		       if(daysBetween < 0 && da.getData()!=null)
		    	   al.add(da);
			}

		Comparator<DoctorAppointment> cmp = (DoctorAppointment da1,DoctorAppointment da2)->{
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
		
		for(DoctorAppointment da:al) {
			Patient p = prepo.findById(da.getPatientmail()).orElse(null);
			pal.add(p);
		}
		ModelAndView mv = new ModelAndView();
		mv.addObject("doctorappointmentstogiveprescriptionold",al);
		mv.addObject("patientsforprescriptionold",pal);
		mv.setViewName("doctorprescriptionold.jsp");
		return mv;
	}
	
	@RequestMapping("/addprescription")
	public String addprescription(String appdetails) {
		//al.get(i).getPatientmail()+"-"+al.get(i).getDoctormail()+"-"+al.get(i).getAppointmentdate()+"-"+al.get(i).getAppointmenttime()
		/*String str[] = appdetails.split("-");
		System.out.println(appdetails);
		this.da.setPatientmail(str[0]);
		this.da.setDoctormail(str[1]);
		this.da.setAppointmentdate(str[2]);
		this.da.setAppointmenttime(str[3]);
		*/
		
		for(DoctorAppointment da: al) {
			if(da.toString().equals(appdetails)) {
				this.da = da; break;
			}
		}
		
		System.out.println(this.da.getDoctormail()+" "+this.da.getPatientmail());
		return "prescriptionupload.jsp";	
	}
	
	@RequestMapping("/noneedprescription")
	public String noneedprescription(String appdetails) {
		DoctorAppointment myda = null;
		for(DoctorAppointment da: al) {
			if(da.toString().equals(appdetails)) {
				myda = da; break;
			}
		}
		darepo.changeThePrescriptionStatus(true, myda.getPatientmail(), myda.getDoctormail(), myda.getAppointmentdate(), myda.getAppointmenttime());
		System.out.print("done true");
		return "redirect:newdoctorprescription";
	}
	
	
	@RequestMapping("/uploadprescription")
	public ModelAndView uploadprescription(@RequestParam("prescriptionfile") MultipartFile  prescriptionfile)   {
		
		try {
		ModelAndView mv = new ModelAndView();
			System.out.println(prescriptionfile.getOriginalFilename());
			System.out.println(this.da.getPatientmail()+" "+this.da.getDoctormail()+" "+this.da.getAppointmentdate()+" "+ this.da.getAppointmenttime());
			darepo.storeFile(prescriptionfile.getOriginalFilename(), prescriptionfile.getContentType(),prescriptionfile.getBytes(), this.da.getPatientmail(), this.da.getDoctormail(), this.da.getAppointmentdate(), this.da.getAppointmenttime());		
			
			String fileDownloaduri = ServletUriComponentsBuilder.fromCurrentContextPath()
									 .path("/downloadFile/")
									 .path(prescriptionfile.getOriginalFilename())
									 .toUriString();
			System.out.print(fileDownloaduri);
			
			mv.addObject("resultofloadingprescription","Uploaded sucessfully");
			mv.setViewName("prescriptionupload.jsp");
			return mv;
		}
		catch(Exception ex) {
			ModelAndView mv = new ModelAndView();
			mv.addObject("erroroccuredinuploading","File size is big... cannot be uploaded");
			mv.setViewName("prescriptionupload.jsp");
			return mv;
			
		}
		
		
		
	}
	
	   
}
