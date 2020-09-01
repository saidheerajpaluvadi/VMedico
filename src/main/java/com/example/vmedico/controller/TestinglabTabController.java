package com.example.vmedico.controller;

import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.stream.Collectors;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;


import com.example.vmedico.dao.PatientRepo;
import com.example.vmedico.dao.TestLabAppointmentRepo;
import com.example.vmedico.dao.TestLabRepo;
import com.example.vmedico.model.EmailToPatient;
import com.example.vmedico.model.Patient;
import com.example.vmedico.model.TestingLab;
import com.example.vmedico.model.TestingLabAppointment;

@Controller
public class TestinglabTabController {
	
	@Autowired
	TestLabAppointmentRepo darepo;
	
	@Autowired
	PatientRepo prepo;
	
	@Autowired
	TestLabRepo drepo;
	
	@Autowired
	JavaMailSender jms;
	
	@RequestMapping("alltestlabappointments")
	public ModelAndView allappointments(HttpServletRequest req) {
		HttpSession session = req.getSession();
		ArrayList<TestingLabAppointment> al = darepo.findAllAppointmentsOfTestingLab(session.getAttribute("testinglabmail").toString(),"waitingforconformation");
		
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
		mv.addObject("alltestinglabappointments",al);
		mv.addObject("patientslistoftestinglab",pal);
		mv.setViewName("patientsdetailsfortestinglab.jsp");
		return mv;
	}
	
	@RequestMapping("/acceptorrejectbytestinglab")
	public ModelAndView acceptorrejectbydoctor(String pateintmailandresult,HttpServletRequest req) throws MessagingException {
		
		HttpSession session = req.getSession();
		TestingLab d = drepo.findById(session.getAttribute("testinglabmail").toString()).orElse(null);
		String str[] = pateintmailandresult.split("-");
		String result = str[0];
		Long ID = Long.parseLong(str[1]);
		TestingLabAppointment selectedta = darepo.findById(ID).orElse(null);
		//SimpleMailMessage smm = new SimpleMailMessage();
		MimeMessage message = jms.createMimeMessage();
		MimeMessageHelper msghelper = new MimeMessageHelper(message);
		EmailToPatient etp; 
		//smm.setTo(patientmail);
		
		if(result.equals("accept")) {
			darepo.changeThePatientStatus("booked",selectedta.getId());
			etp = new EmailToPatient(selectedta.getPatientmail(),d,selectedta.getAppointmentdate(),selectedta.getAppointmenttime(),"booked");
			msghelper.setTo(etp.getEmailIDTo());
			msghelper.setSubject("Appointment is booked");
	
			msghelper.setText(etp.getEmailBodyinHtmlFormatForTestinglab(),true);
			//smm.setSubject("Appointment is booked");
			//smm.setText("Your appointment in "+d.getNameoflab()+" on "+appdate+" at "+apptime+" is booked...");
		}
		else if(result.equals("reject")) {
			darepo.changeThePatientStatus("rejected",selectedta.getId());
			etp = new EmailToPatient(selectedta.getPatientmail(),d,selectedta.getAppointmentdate(),selectedta.getAppointmenttime(),"booked");
			msghelper.setTo(etp.getEmailIDTo());
			msghelper.setSubject("Appointment is rejected");
			msghelper.setText(etp.getEmailBodyinHtmlFormatForTestinglab(),true);
			//smm.setSubject("Appointment is Cancelled");
			//smm.setText("Sorry,Your appointment in "+d.getNameoflab()+" on "+appdate+" at "+apptime+" is rejected");
		}
		
		jms.send(message);
		
		ArrayList<TestingLabAppointment> al = darepo.findAllAppointmentsOfTestingLab(session.getAttribute("testinglabmail").toString(),"waitingforconformation");
		
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
		mv.addObject("alltestinglabappointments",al);
		mv.addObject("patientslistoftestinglab",pal);
		mv.setViewName("patientsdetailsfortestinglab.jsp");
		return mv;
		
		
	}
	
	@RequestMapping("/allappointmentsacceptedbytestlab")
	public ModelAndView appointmentsaccepted(HttpServletRequest req) {
		HttpSession session = req.getSession();
		ArrayList<TestingLabAppointment> al = darepo.findAllAppointmentsOfTestingLab(session.getAttribute("testinglabmail").toString(),"booked");
		
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
			System.out.println(da.getPatientmail());
			Patient p = prepo.findById(da.getPatientmail()).orElse(null);
			pal.add(p);
		}
		ModelAndView mv = new ModelAndView();
		mv.addObject("testinglabappointmentsaccepted",al);
		mv.addObject("acceptedpatientslistfortestinglab",pal);
		mv.setViewName("appointmentsacceptedbytestlab.jsp");
		return mv;
	}
	
	@RequestMapping("/todaytestlabappointments")
	public ModelAndView todayappointments(HttpServletRequest req) {
		HttpSession session = req.getSession();
		ArrayList<TestingLabAppointment> al = darepo.findAllAppointmentsOfTestingLab(session.getAttribute("testinglabmail").toString(),"booked");
		Date date;
		Format formatter;
		Calendar calendar = Calendar.getInstance();

		date = calendar.getTime();
		formatter = new SimpleDateFormat("dd/MMM/yyyy");
		String appdate = formatter.format(date);
		al = (ArrayList<TestingLabAppointment>) al.stream().filter(da -> da.getAppointmentdate().equals(appdate)).collect(Collectors.toList());
		

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
			mv.addObject("testinglabappointmentsacceptedtoday",al);
			mv.addObject("acceptedpatientslistfortestinglabtoday",pal);
			mv.setViewName("appointmentsacceptedbytestlabtoday.jsp");
		return mv;
	}
	
	@RequestMapping("/upcomingtestlabappointments")
	public ModelAndView upcomingappointments(HttpServletRequest req) throws ParseException {
		HttpSession session = req.getSession();
	ArrayList<TestingLabAppointment> dal = darepo.findAllAppointmentsOfTestingLab(session.getAttribute("testinglabmail").toString(),"booked");
	ArrayList<TestingLabAppointment> al = new ArrayList<TestingLabAppointment>();
	
	for(TestingLabAppointment da:dal) {
		
		Date date1;
		Format formatter1;
		Calendar calendar1 = Calendar.getInstance();

	  date1 = calendar1.getTime();
	  formatter1 = new SimpleDateFormat("dd/MMM/yyyy");
	  String todaydate = formatter1.format(date1);

	  	SimpleDateFormat myFormat = new SimpleDateFormat("dd/MMM/yyyy");
       Date dateBefore = myFormat.parse(todaydate);
       Date dateAfter = myFormat.parse(da.getAppointmentdate());
      // System.out.print( dateAfter.getTime() +" - "+ dateBefore.getTime());
       long difference = dateAfter.getTime() - dateBefore.getTime();
       long daysBetween =  (difference );
       if(daysBetween > 0 )
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
		mv.addObject("testinglabappointmentsacceptedupcoming",al);
		mv.addObject("acceptedpatientslistfortestinglabupcoming",pal);
		mv.setViewName("appointmentsacceptedbytestlabupcoming.jsp");
		return mv;
	}
	
	@RequestMapping("/previoustestlabappointments")
	public ModelAndView previousappointments(HttpServletRequest req) throws ParseException {
		HttpSession session = req.getSession();
	ArrayList<TestingLabAppointment> dal = darepo.findAllAppointmentsOfTestingLab(session.getAttribute("testinglabmail").toString(),"booked");
	ArrayList<TestingLabAppointment> al = new ArrayList<TestingLabAppointment>();	
	for(TestingLabAppointment da:dal) {
		
		Date date1;
		Format formatter1;
		Calendar calendar1 = Calendar.getInstance();

	  date1 = calendar1.getTime();
	  formatter1 = new SimpleDateFormat("dd/MMM/yyyy");
	  String todaydate = formatter1.format(date1);

	  	SimpleDateFormat myFormat = new SimpleDateFormat("dd/MMM/yyyy");
       Date dateBefore = myFormat.parse(todaydate);
       Date dateAfter = myFormat.parse(da.getAppointmentdate());
      // System.out.print( dateAfter.getTime() +" - "+ dateBefore.getTime());
       long difference = dateAfter.getTime() - dateBefore.getTime();
       long daysBetween =  (difference );
       if(daysBetween < 0 )
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
		mv.addObject("testinglabappointmentsacceptedprevious",al);
		mv.addObject("acceptedpatientslistfortestinglabprevious",pal);
		mv.setViewName("appointmentsacceptedbytestlabprevious.jsp");
		
		return mv;
	}



	
	@RequestMapping("/testlabprofile")
	public ModelAndView profile(HttpServletRequest req) {
		HttpSession session = req.getSession();
		String testinglabmail = (String) session.getAttribute("testinglabmail");
		TestingLab p = drepo.findById(testinglabmail).orElse(null);
		//System.out.print("testinglab profilr");
		ModelAndView mv = new ModelAndView();
		mv.addObject("testinglabprofileobj",p);
		mv.setViewName("testinglabprofile.jsp");	
		return mv;
	}
	
	@RequestMapping("/testlablogout")
	public String doctorlogout(HttpServletRequest req) {
		req.getSession().invalidate();
		//return "home.jsp";
		return "redirect:home";
	}

	@RequestMapping("/edittestlabprofile")
	public String edittestlabprofile(String address,HttpServletRequest req) {
		HttpSession session = req.getSession();
		drepo.updateDetails(session.getAttribute("testinglabmail").toString(),address);
		return "redirect:testlabprofile";
	}

}
