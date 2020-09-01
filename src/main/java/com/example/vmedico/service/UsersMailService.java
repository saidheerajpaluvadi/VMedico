package com.example.vmedico.service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.example.vmedico.dao.DoctorAppointmentRepo;
import com.example.vmedico.dao.PatientRepo;
import com.example.vmedico.dao.PrescriptionRemainderRepo;
import com.example.vmedico.model.DoctorAppointment;
import com.example.vmedico.model.Patient;
import com.example.vmedico.model.PrescriptionRemainder;

@Service
public class UsersMailService {

	@Autowired
	PatientRepo prepo;
	
	@Autowired
	PrescriptionRemainderRepo premrepo;
	
	@Autowired
	DoctorAppointmentRepo darepo;
	
	@Autowired
	JavaMailSender jms;
	
	@Scheduled(cron ="0 0 6 * * *")
	public void sendMailstoPatient() {
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MMM/yyyy");  
	    Date date = new Date();  
	    String todaysdate = formatter.format(date);
	    
	    ArrayList<DoctorAppointment> al = darepo.findAppointmentsOnDate(todaysdate);
	    System.out.println(al.size());
	    for(DoctorAppointment da:al) {
	    	if(da.getStatus().equals("booked")) {
	    		SimpleMailMessage smm = new SimpleMailMessage();
				smm.setTo(da.getPatientmail());
				smm.setSubject("Appointment Remainder");
				smm.setText("You have an appointment today with "+da.getDoctormail());	
				System.out.println(da.getPatientmail());
				jms.send(smm);
	    	}
	    	    	
	    }
	    
	    HashMap<String,Integer> mymap = new HashMap<String,Integer>();
	    for(DoctorAppointment da:al) {
	    	if(da.getStatus().equals("booked")) {
	    		if(mymap.containsKey(da.getDoctormail()))
		    		mymap.put(da.getDoctormail(), mymap.get(da.getDoctormail()) + 1);
		    	else
		    		mymap.put(da.getDoctormail(), 1);
	    	}
	    	
	    }
	    
	    for(String docmail : mymap.keySet()) {
	    	SimpleMailMessage smm = new SimpleMailMessage();
			smm.setTo(docmail);
			smm.setSubject("Appointment Remainder");
			smm.setText("Hello doctor,\n Today you have "+ mymap.get(docmail)+"appointments");	
			System.out.println(docmail);
			jms.send(smm);
	    }
	    	
		
		//
		System.out.println(new Date().toString());
		
	}
	
	@Scheduled(cron ="0 0/1 * * * *")
	public void sendMedicationRemainder() {
		System.out.print("hi");
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MMM/yyyy");  
	    Date date = new Date();  
	    DateFormat df = new SimpleDateFormat("HH:mm");
	    String presenttime = df.format(date);
	    String todaysdate = formatter.format(date);
	    
	    ArrayList<Patient> pal = (ArrayList<Patient>) prepo.findAll();
	    SimpleMailMessage smm = new SimpleMailMessage();
		
	    for(Patient p:pal) {
	    	
	    	List<PrescriptionRemainder> li = premrepo.findByPatientmail(p.getEmail());
	    	//System.out.println(li);
	    	for(PrescriptionRemainder pr:li) {
	    		int noofdays = pr.getNoofdays();
	    		try {
	    		Date today = formatter.parse(todaysdate);
	    		Date start = formatter.parse(pr.getStartdate());
	    		long difference = today.getTime() - start.getTime();
	    		float daysBetween = (difference / (1000*60*60*24));
	    		//System.out.println(daysBetween);
	    		if(daysBetween < noofdays && pr.getRemaindertime().equals(presenttime)) {
	    			smm.setTo(p.getEmail());
	    			smm.setSubject("Medication Remainder");
	    			smm.setText("Hello, please take your medicine");	
	    			System.out.println(p.getEmail());
	    			//jms.send(smm);
	    		}
	    		}
	    		catch(Exception e) {
	    			e.printStackTrace();
	    		}
	    	}
	    }
		
	}
}
