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

import com.example.vmedico.dao.TestLabAppointmentRepo;
import com.example.vmedico.dao.TestLabRepo;
import com.example.vmedico.model.TestingLab;
import com.example.vmedico.model.TestingLabAppointment;

@Controller
public class CancelLabController {
	
	@Autowired
	TestLabAppointmentRepo tlarepo;

	@Autowired
	TestLabRepo tlrepo;
	
	
	/*
	 * To display the list of previously booked appointments
	 */
	@RequestMapping("/cancetestlab")
	public ModelAndView cancelAnAppointMent(HttpServletRequest request) throws ParseException {
		HttpSession session = request.getSession();
		ModelAndView mv = new ModelAndView();
		ArrayList<TestingLabAppointment> li = (ArrayList<TestingLabAppointment>) tlarepo.findAll();
		ArrayList<TestingLabAppointment> patientappointmentslist = new ArrayList<TestingLabAppointment>();
		
		ArrayList<TestingLab> dli = (ArrayList<TestingLab>) tlrepo.findAll();
		ArrayList<TestingLab>  resdli = new ArrayList<TestingLab>();
		
		
		for(TestingLabAppointment da:li) {
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
		Comparator<TestingLabAppointment> cmp = (TestingLabAppointment da1,TestingLabAppointment da2)->{
			
			String s1 = da1.getAppointmentdate().trim()+" "+da1.getAppointmenttime().trim();
			String s2 = da2.getAppointmentdate().trim()+" "+da2.getAppointmenttime().trim();
			 try {
	                return f.parse(s1).compareTo(f.parse(s2));
	            } catch (ParseException e) {
	                throw new IllegalArgumentException(e);
	            }
		};
		patientappointmentslist.sort(cmp);
		
		
		
		for(TestingLabAppointment da:patientappointmentslist) {
			System.out.print(da.getTestinglabmail()+" ");
			for(int i=0;i<dli.size();i++) {
				if(dli.get(i).getEmail().equals(da.getTestinglabmail())) {
					resdli.add(dli.get(i));
					//System.out.println(dli.get(i).getFirstname());
					break;
				}
			}
			
		}
		mv.addObject("testlabappointmentlist",patientappointmentslist);
		mv.addObject("Bookedtestlablist",resdli);
		mv.setViewName("canceltestlab.jsp");
		return mv;
	}
	
	/*
	 * To cancel any appointment
	 */
	@RequestMapping("canceltestlab")
	public ModelAndView cancel(String testlabmailamdappointmnetdetails,HttpServletRequest request) throws ParseException {
		HttpSession session = request.getSession();
		ModelAndView mv = new ModelAndView();
		String str[] = testlabmailamdappointmnetdetails.split("-");
		String docmail = str[0];
		String date = str[1];
		String time = str[2];
		
		
		ArrayList<TestingLabAppointment> li = (ArrayList<TestingLabAppointment>) tlarepo.findAll();
		ArrayList<TestingLabAppointment> patientappointmentslist = new ArrayList<TestingLabAppointment>(); // patientappointmentslist -> All appointments of a login patient
		ArrayList<TestingLab> dli = (ArrayList<TestingLab>) tlrepo.findAll();
		
		ArrayList<TestingLab>  resdli = new ArrayList<TestingLab>();
		for(TestingLabAppointment da:li) {
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
			if(patientappointmentslist.get(i).getTestinglabmail().equals(docmail) &&
					patientappointmentslist.get(i).getAppointmentdate().equals(date) &&
					patientappointmentslist.get(i).getAppointmenttime().equals(time)) {
			
				//List lidarepo.find(da.getTestingLabmail(),da.getAppointmentdate() ,da.getAppointmenttime());
						tlarepo.changeThePatientStatus("Cancelled",patientappointmentslist.get(i).getId());
						patientappointmentslist.remove(i);
			}
		}
		
		DateFormat f = new SimpleDateFormat("dd/MMM/yyyy hh:mm:ss aa");
		Comparator<TestingLabAppointment> cmp = (TestingLabAppointment da1,TestingLabAppointment da2)->{
			
			String s1 = da1.getAppointmentdate().trim()+" "+da1.getAppointmenttime().trim();
			String s2 = da2.getAppointmentdate().trim()+" "+da2.getAppointmenttime().trim();
			 try {
	                return f.parse(s1).compareTo(f.parse(s2));
	            } catch (ParseException e) {
	                throw new IllegalArgumentException(e);
	            }
		};
		patientappointmentslist.sort(cmp);
		
		
		
		
		for(TestingLabAppointment da:patientappointmentslist) {
			for(int i=0;i<dli.size();i++) {
				if(dli.get(i).getEmail().equals(da.getTestinglabmail())) {
					resdli.add(dli.get(i));
					
					break;
				}
			}
			
		}
		mv.addObject("testlabappointmentlist",patientappointmentslist);
		mv.addObject("Bookedtestlablist",resdli);
		mv.setViewName("canceltestlab.jsp");
		return mv;
			
	}

	

}
