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
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.example.vmedico.dao.DoctorAppointmentRepo;
import com.example.vmedico.dao.DoctorRepo;
import com.example.vmedico.dao.PatientFileRepo;
import com.example.vmedico.dao.PatientRepo;
import com.example.vmedico.dao.TestLabAppointmentRepo;
import com.example.vmedico.model.Doctor;
import com.example.vmedico.model.DoctorAppointment;
import com.example.vmedico.model.EmailToPatient;
import com.example.vmedico.model.Patient;
import com.example.vmedico.model.TestingLabAppointment;

@Controller
public class DoctorsTabController {
	
	@Autowired
	DoctorAppointmentRepo darepo;
	
	@Autowired
	PatientRepo prepo;
	
	@Autowired
	DoctorRepo drepo;
	
	@Autowired
	JavaMailSender jms;
	
	@Autowired
	PatientFileRepo pfilesrepo;
	
	@Autowired
	TestLabAppointmentRepo tlarepo;
	
	/*
	 * To display all the appointments requests received form the patients
	 */
	@RequestMapping("alldoctorappointments")
	public ModelAndView allappointments(HttpServletRequest req) {
		HttpSession session = req.getSession();
		ArrayList<DoctorAppointment> al = darepo.findAllAppointmentsOfDoctor(session.getAttribute("doctormail").toString(),"waitingforconformation");
		
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
		mv.addObject("alldoctorappointments",al);
		mv.addObject("patientslist",pal);
		mv.setViewName("patientsdetailsfordoctor.jsp");
		return mv;
	}
	
	/*
	 *  Doctor can accept or reject any appointment
	 */
	@RequestMapping("/acceptorrejectbydoctor")
	public ModelAndView acceptorrejectbydoctor(String pateintmailandresult,HttpServletRequest req) throws MessagingException {
		
		HttpSession session = req.getSession();
		Doctor d = drepo.findById(session.getAttribute("doctormail").toString()).orElse(null);
	
		String str[] = pateintmailandresult.split("-");
		String result = str[0];
		Long Id = Long.parseLong(str[1]);
		DoctorAppointment selectedda = darepo.findById(Id).orElse(null);
		//SimpleMailMessage smm = new SimpleMailMessage();
		MimeMessage message = jms.createMimeMessage();
		MimeMessageHelper msghelper = new MimeMessageHelper(message);
		
		EmailToPatient etp; 
		//smm.setTo(patientmail);
		
		if(result.equals("accept")) {
			darepo.changeThePatientStatus("booked",Id);
			etp = new EmailToPatient(selectedda.getPatientmail(),d, selectedda.getAppointmentdate(),selectedda.getAppointmenttime(),"booked");
			msghelper.setTo(etp.getEmailIDTo());
			msghelper.setSubject("Appointment is booked");
	
			msghelper.setText(etp.getEmailBodyinHtmlFormat(),true);
			
			//smm.setSubject("Appointment is booked");
			//smm.setText("Your appointment with "+d.getFirstname()+" in "+d.getHospitalname()+" on "+appdate+" at "+apptime+" is booked...");
		}
		else if(result.equals("reject")) {
			darepo.changeThePatientStatus("rejected",Id);
			etp = new EmailToPatient(selectedda.getPatientmail(),d, selectedda.getAppointmentdate(),selectedda.getAppointmenttime(),"rejected");
			msghelper.setTo(etp.getEmailIDTo());
			msghelper.setSubject("Appointment is rejected");
			msghelper.setText(etp.getEmailBodyinHtmlFormat(),true);
			
			//smm.setSubject("Appointment is Cancelled");
			//smm.setText("Sorry,Your appointment with "+d.getFirstname()+" in "+d.getHospitalname()+" on "+appdate+" at "+apptime+" is rejected");
		}
		
		jms.send(message);
		
		ArrayList<DoctorAppointment> al = darepo.findAllAppointmentsOfDoctor(session.getAttribute("doctormail").toString(),"waitingforconformation");
		
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
		mv.addObject("alldoctorappointments",al);
		mv.addObject("patientslist",pal);
		mv.setViewName("patientsdetailsfordoctor.jsp");
		return mv;
		
		
	}
	
	/*
	 * To display the list of all appointments accepted
	 */
	@RequestMapping("/allappointmentsaccepted")
	public ModelAndView appointmentsaccepted(HttpServletRequest req) {
		HttpSession session = req.getSession();
		ArrayList<DoctorAppointment> al = darepo.findAllAppointmentsOfDoctor(session.getAttribute("doctormail").toString(),"booked");
		
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
		mv.addObject("doctorappointmentsaccepted",al);
		mv.addObject("acceptedpatientslist",pal);
		mv.setViewName("appointmentsacceptedbydoctor.jsp");
		return mv;
	}
	
	/*
	 * To display the list of today's appointments  
	 */
	@RequestMapping("/todayappointments")
	public ModelAndView todayappointments(HttpServletRequest req) {
		HttpSession session = req.getSession();
		ArrayList<DoctorAppointment> al = darepo.findAllAppointmentsOfDoctor(session.getAttribute("doctormail").toString(),"booked");
		
		Date date;
		Format formatter;
		Calendar calendar = Calendar.getInstance();

		date = calendar.getTime();
		formatter = new SimpleDateFormat("dd/MMM/yyyy");
		String appdate = formatter.format(date);
		
		al = (ArrayList<DoctorAppointment>) al.stream().filter(da -> da.getAppointmentdate().equals(appdate)).collect(Collectors.toList());
		
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
		mv.addObject("doctorappointmentsacceptedtoday",al);
		mv.addObject("patientfilesrepo",pfilesrepo);
		mv.addObject("testinglabappointmentrepo",tlarepo);
		mv.addObject("acceptedpatientslisttoday",pal);
		mv.setViewName("appointmentsacceptedbydoctortoday.jsp");
		return mv;
	}
	
	/*
	 * To display the list of upcoming appointments accepted
	 */
	@RequestMapping("/upcomingappointments")
	public ModelAndView upcomingappointments(HttpServletRequest req) throws ParseException {
		HttpSession session = req.getSession();
		ArrayList<DoctorAppointment> dal = darepo.findAllAppointmentsOfDoctor(session.getAttribute("doctormail").toString(),"booked");
		
		ArrayList<DoctorAppointment> al = new ArrayList<DoctorAppointment>();
		
		for(DoctorAppointment da:dal) {
			
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
		mv.addObject("doctorappointmentsacceptedupcoming",al);
		mv.addObject("acceptedpatientslistupcoming",pal);
		mv.setViewName("appointmentsacceptedbydoctorupcoming.jsp");
		return mv;
	}
	
	/*
	 * To display the list of previous appointments accepted
	 */
	@RequestMapping("/previousappointments")
	public ModelAndView previousappointments(HttpServletRequest req) throws ParseException {
		HttpSession session = req.getSession();
		ArrayList<DoctorAppointment> dal = darepo.findAllAppointmentsOfDoctor(session.getAttribute("doctormail").toString(),"booked");
		
		ArrayList<DoctorAppointment> al = new ArrayList<DoctorAppointment>();
		
		for(DoctorAppointment da:dal) {
			
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
		mv.addObject("doctorappointmentsacceptedprevious",al);
		mv.addObject("acceptedpatientslistprevious",pal);
		mv.setViewName("appointmentacceptedbydoctorprevious.jsp");
		return mv;
	}


	/*
	 * To display the doctor profile
	 */
	@RequestMapping("/doctorprofile")
	public ModelAndView profile(HttpServletRequest req) {
		HttpSession session = req.getSession();
		String doctormail = (String) session.getAttribute("doctormail");
		Doctor p = drepo.findById(doctormail).orElse(null);
		//System.out.print("doc profilr");
		ModelAndView mv = new ModelAndView();
		mv.addObject("doctorprofileobj",p);
		mv.setViewName("doctorprofile.jsp");	
		return mv;
	}
	
	@RequestMapping("/doctorlogout")
	public String doctorlogout(HttpServletRequest req) {
		req.getSession().invalidate();
		//return "home.jsp";
		return "redirect:home";
		
	}
	
	@RequestMapping("/editdoctorprofile")
	public String editdoctorprofile(String lastname,String highestqualification,Integer yearsofexperience,String hospitalname,String location,HttpServletRequest req){
		HttpSession session =req.getSession();
		drepo.updateDetails(session.getAttribute("doctormail").toString(),lastname,highestqualification,yearsofexperience,hospitalname,location);
		return "redirect:doctorprofile";
	}

	/*
	 *  to download the patients report
	 */
	@RequestMapping("downloadPatientReports/{fileId}")
	public ResponseEntity<ByteArrayResource>  downloadPatientreports(@PathVariable String fileId){ 
		TestingLabAppointment required = tlarepo.findById(Long.parseLong(fileId)).orElse(null);
	
		
		return ResponseEntity.ok()
				.contentType(MediaType.parseMediaType(required.getReporttype()))
				.header(HttpHeaders.CONTENT_DISPOSITION,"attachment:filename=\""+required.getReportname()+"\"")
				.body(new ByteArrayResource(required.getReport()));		
		
	}
}
