package com.example.vmedico.controller;

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
import org.springframework.web.servlet.ModelAndView;

import com.example.vmedico.dao.DoctorAppointmentRepo;
import com.example.vmedico.dao.DoctorRepo;
import com.example.vmedico.model.Doctor;
import com.example.vmedico.model.DoctorAppointment;

@Controller
public class CancelAnAppointmentController {
	
	@Autowired
	DoctorAppointmentRepo darepo;
	
	@Autowired
	DoctorRepo drepo;
	
	/*
	 * To display the list of previously booked appointments...
	 */
	@RequestMapping("/cancelanappointment")
	public ModelAndView cancelAnAppointMent(HttpServletRequest request) throws ParseException {
		HttpSession session = request.getSession();
		ModelAndView mv = new ModelAndView();
		ArrayList<DoctorAppointment> li = (ArrayList<DoctorAppointment>) darepo.findAll();
		ArrayList<DoctorAppointment> patientappointmentslist = new ArrayList<DoctorAppointment>();
		
		ArrayList<Doctor> dli = (ArrayList<Doctor>) drepo.findAll();
		ArrayList<Doctor>  resdli = new ArrayList<Doctor>();
		
		
		for(DoctorAppointment da:li) {
			if(da.getPatientmail().equals(session.getAttribute("patientmail")) && (da.getStatus().equals("waitingforconformation")||(da.getStatus().equals("booked")) ))
				{
					Date date;
					Format formatter;
					Calendar calendar = Calendar.getInstance();

				  date = calendar.getTime();
				  formatter = new SimpleDateFormat("dd/MMM/yyyy hh:mm:ss aa");
				  String todaydate = formatter.format(date);

				  	SimpleDateFormat myFormat = new SimpleDateFormat("dd/MMM/yyyy hh:mm:ss aa");
			       Date dateBefore = myFormat.parse(todaydate);
			       Date dateAfter = myFormat.parse(da.getAppointmentdate()+" "+da.getAppointmenttime());
			       System.out.print( dateAfter.getTime() +" - "+ dateBefore.getTime());
			       long difference = dateAfter.getTime() - dateBefore.getTime();
			       long daysBetween =  (difference );
			       if(daysBetween > 0)
			       patientappointmentslist.add(da);
				}
		}
		DateFormat f = new SimpleDateFormat("dd/MMM/yyyy hh:mm:ss aa");
		Comparator<DoctorAppointment> cmp = (DoctorAppointment da1,DoctorAppointment da2)->{
			
			String s1 = da1.getAppointmentdate().trim()+" "+da1.getAppointmenttime().trim();
			String s2 = da2.getAppointmentdate().trim()+" "+da2.getAppointmenttime().trim();
			 try {
	                return f.parse(s1).compareTo(f.parse(s2));
	            } catch (ParseException e) {
	                throw new IllegalArgumentException(e);
	            }
		};
		patientappointmentslist.sort(cmp);
		
		
		
		for(DoctorAppointment da:patientappointmentslist) {
			System.out.print(da.getDoctormail()+" ");
			for(int i=0;i<dli.size();i++) {
				if(dli.get(i).getEmail().equals(da.getDoctormail())) {
					resdli.add(dli.get(i));
					//System.out.println(dli.get(i).getFirstname());
					break;
				}
			}
			
		}
		mv.addObject("Doctorappointmentlist",patientappointmentslist);
		mv.addObject("BookedDoctorlist",resdli);
		mv.setViewName("cancelanappointment.jsp");
		return mv;
	}
	
	/*
	 * To cancel any appointment...
	 */
	@RequestMapping("cancel")
	public ModelAndView cancel(String doctormailamdappointmnetdetails,HttpServletRequest request) throws ParseException {
		HttpSession session = request.getSession();
		ModelAndView mv = new ModelAndView();
		String str[] = doctormailamdappointmnetdetails.split("-");
		String docmail = str[0];
		String date = str[1];
		String time = str[2];
		
		
		ArrayList<DoctorAppointment> li = (ArrayList<DoctorAppointment>) darepo.findAll();
		ArrayList<DoctorAppointment> patientappointmentslist = new ArrayList<DoctorAppointment>(); // patientappointmentslist -> All appointments of a login patient
		ArrayList<Doctor> dli = (ArrayList<Doctor>) drepo.findAll();
		
		ArrayList<Doctor>  resdli = new ArrayList<Doctor>();
		for(DoctorAppointment da:li) {
			if(da.getPatientmail().equals(session.getAttribute("patientmail")) && (da.getStatus().equals("waitingforconformation")||(da.getStatus().equals("booked")) ))
			{
				Date date1;
				Format formatter;
				Calendar calendar = Calendar.getInstance();

			  date1 = calendar.getTime();
			  formatter = new SimpleDateFormat("dd/MMM/yyyy hh:mm:ss aa");
			  String todaydate = formatter.format(date1);

			  	SimpleDateFormat myFormat = new SimpleDateFormat("dd/MMM/yyyy hh:mm:ss aa");
		       Date dateBefore = myFormat.parse(todaydate);
		       Date dateAfter = myFormat.parse(da.getAppointmentdate()+" "+da.getAppointmenttime());
		       System.out.print( dateAfter.getTime() +" - "+ dateBefore.getTime());
		       long difference = dateAfter.getTime() - dateBefore.getTime();
		       long daysBetween =  (difference );
		       if(daysBetween > 0)
		       patientappointmentslist.add(da);
			}

		}
		
		
		for(int i=0;i<patientappointmentslist.size();i++) {
			if(patientappointmentslist.get(i).getDoctormail().equals(docmail) &&
					patientappointmentslist.get(i).getAppointmentdate().equals(date) &&
					patientappointmentslist.get(i).getAppointmenttime().equals(time)) {

						darepo.changeThePatientStatus("Cancelled",patientappointmentslist.get(i).getId());
						patientappointmentslist.remove(i);
			}
		}
		
		DateFormat f = new SimpleDateFormat("dd/MMM/yyyy hh:mm:ss aa");
		Comparator<DoctorAppointment> cmp = (DoctorAppointment da1,DoctorAppointment da2)->{
			
			String s1 = da1.getAppointmentdate().trim()+" "+da1.getAppointmenttime().trim();
			String s2 = da2.getAppointmentdate().trim()+" "+da2.getAppointmenttime().trim();
			 try {
	                return f.parse(s1).compareTo(f.parse(s2));
	            } catch (ParseException e) {
	                throw new IllegalArgumentException(e);
	            }
		};
		
		patientappointmentslist.sort(cmp);
				
		for(DoctorAppointment da:patientappointmentslist) {
			for(int i=0;i<dli.size();i++) {
				if(dli.get(i).getEmail().equals(da.getDoctormail())) {
					resdli.add(dli.get(i));
					
					break;
				}
			}
			
		}
		mv.clear();
		mv.addObject("Doctorappointmentlist",patientappointmentslist);
		mv.addObject("BookedDoctorlist",resdli);
		mv.setViewName("cancelanappointment.jsp");
		return mv;
	
		
	}

}
