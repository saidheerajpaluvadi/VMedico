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
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.example.vmedico.dao.DoctorAppointmentRepo;
import com.example.vmedico.dao.DoctorRepo;
import com.example.vmedico.dao.PatientFileRepo;
import com.example.vmedico.dao.PatientRepo;
import com.example.vmedico.dao.PrescriptionRemainderRepo;
import com.example.vmedico.dao.TestLabAppointmentRepo;
import com.example.vmedico.dao.TestLabRepo;
import com.example.vmedico.model.Doctor;
import com.example.vmedico.model.DoctorAppointment;
import com.example.vmedico.model.Patient;
import com.example.vmedico.model.PatientFiles;
import com.example.vmedico.model.PrescriptionRemainder;
import com.example.vmedico.model.TestingLab;
import com.example.vmedico.model.TestingLabAppointment;
import com.example.vmedico.service.PatientFilesService;

@Controller
public class PatientsTabController {
	
	@Autowired
	DoctorAppointmentRepo docapprepo;
	
	@Autowired
	DoctorRepo drepo;
	
	@Autowired
	PatientRepo prepo;
	
	@Autowired
	PatientFileRepo pfilesrepo;
	
	@Autowired
	PatientFilesService pfilesservice;
	
	@Autowired
	TestLabAppointmentRepo tlarepo;
	
	@Autowired
	PrescriptionRemainderRepo premrepo;
	

	ArrayList<DoctorAppointment> downloadal;

	private ArrayList<TestingLabAppointment> downloadalreports;

	@Autowired
	TestLabRepo tlrepo;
	
	/*
	 * To display the status of various doctor appointments... 
	 */
	@RequestMapping("/status")
	public ModelAndView status(HttpServletRequest request) {
		HttpSession session = request.getSession();
		checkRejections(session.getAttribute("patientmail").toString());
		ModelAndView mv = new ModelAndView();
		ArrayList<DoctorAppointment> li = (ArrayList<DoctorAppointment>) docapprepo.findAll();
		ArrayList<DoctorAppointment> rli = new ArrayList<DoctorAppointment>();
		
		ArrayList<Doctor> dli = (ArrayList<Doctor>) drepo.findAll();
		ArrayList<Doctor> resdli = new ArrayList<Doctor>();
		
		for(DoctorAppointment da:li) {
			if(da.getPatientmail().equals(session.getAttribute("patientmail")))
				rli.add(da);
		}
		 DateFormat f = new SimpleDateFormat("dd/MMM/yyyy hh:mm:ss aa");
		Comparator<DoctorAppointment> cmp = (DoctorAppointment da1,DoctorAppointment da2)->{
			
			String s1 = da1.getAppointmentdate().trim()+" "+da1.getAppointmenttime().trim();
			String s2 = da2.getAppointmentdate().trim()+" "+da2.getAppointmenttime().trim();
			 try {
	                return f.parse(s2).compareTo(f.parse(s1));
	            } catch (ParseException e) {
	                throw new IllegalArgumentException(e);
	            }
		};
		rli.sort(cmp);
		
		for(DoctorAppointment da:rli) {
			for(int i=0;i<dli.size();i++) {
				if(dli.get(i).getEmail().equals(da.getDoctormail())) {
					resdli.add(dli.get(i));
					
					break;
				}
			}
			
		}
		mv.addObject("statusappointmentlist",rli);
		mv.addObject("statusdoctordetails",resdli);
		mv.setViewName("status.jsp");
		return mv;
	
	}
	
	@RequestMapping("/patientprofile")
	public ModelAndView profile(HttpServletRequest req) {
		HttpSession session = req.getSession();
		String patientmail = (String) session.getAttribute("patientmail");
		
		Patient p = prepo.findById(patientmail).orElse(null);
		Integer countDoctorappointments = docapprepo.getCountOfDoctorsBookings(patientmail);
		Integer countTestlabAppointments = tlarepo.getCountOfTestLabBookings(patientmail);
		ArrayList<PatientFiles> files = pfilesrepo.getFiles(patientmail);
		//System.out.println("No of files "+files.size());
		ModelAndView mv = new ModelAndView();
		mv.addObject("patientprofileobj",p);
		mv.addObject("noofbookingswithdoctor",countDoctorappointments);
		mv.addObject("noofbookingswithtestlab",countTestlabAppointments);
		mv.addObject("patientfiles",files);
		mv.setViewName("patientprofile.jsp");
		
		return mv;
	}
	
	@RequestMapping("/patientlogout")
	public String patientLogout(HttpServletRequest req) {
		HttpSession session = req.getSession();
		session.invalidate();
		//return "home.jsp";
		return "redirect:home";
	}
	
	/*
	 * To check any appointments to be cancelled
	 */
	private void checkRejections(String patientmail) {
		//System.out.print("checking");
		ArrayList<DoctorAppointment> al = docapprepo.findAllAppointmentsOfPatients(patientmail,"waitingforconformation");
		//System.out.print(al.size());
		Date date;
		  Format formatter;
		  Calendar calendar = Calendar.getInstance();

		  date = calendar.getTime();
		  formatter = new SimpleDateFormat("dd/MMM/yyyy hh:mm:ss aa");
		  String todaydate = formatter.format(date);
		  
		  for(int i=0;i<al.size();i++) {
			  String appdate = al.get(i).getAppointmentdate()+" "+al.get(i).getAppointmenttime();
			  if(difference(todaydate,appdate) < 0) {
				  docapprepo.changeThePatientStatus("rejected", al.get(i).getId());		  
			  }
		  }
		
	}

	/*
	 * To find the difference between two dates
	 */
	private int difference(String todaydate, String appdate) {
		System.out.println("difference");
		try {
			 SimpleDateFormat myFormat = new SimpleDateFormat("dd/MMM/yyyy hh:mm:ss aa");
		       Date dateBefore = myFormat.parse(todaydate);
		       Date dateAfter = myFormat.parse(appdate);
		       System.out.print( dateAfter.getTime() +" - "+ dateBefore.getTime());
		       long difference = dateAfter.getTime() - dateBefore.getTime();
		       int daysBetween = (int) (difference );
	            
		     //  System.out.println("Number of Days between dates: "+daysBetween);
		       return daysBetween;
		 } catch (Exception e) {
		       e.printStackTrace();
		       return 0;
		 }
		
	}
	
	
	@RequestMapping("/prescriptiongivenbydoctor")
	public ModelAndView prescriptiongivenbydoctor(HttpServletRequest req) throws ParseException {
		HttpSession session = req.getSession();
		ArrayList<DoctorAppointment> dal = docapprepo.findAllAppointmentsOfPatients(session.getAttribute("patientmail").toString(),"booked");
		downloadal = new ArrayList<DoctorAppointment>();
		
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
		      // System.out.print( dateAfter.getTime() +" - "+ dateBefore.getTime());
		       long difference = dateAfter.getTime() - dateBefore.getTime();
		       long daysBetween =  (difference );
		       if(daysBetween < 0 && da.getData()!=null)
		    	   downloadal.add(da);
			}

		 DateFormat f = new SimpleDateFormat("dd/MMM/yyyy hh:mm:ss aa");
			Comparator<DoctorAppointment> cmp = (DoctorAppointment da1,DoctorAppointment da2)->{
				
				String s1 = da1.getAppointmentdate().trim()+" "+da1.getAppointmenttime().trim();
				String s2 = da2.getAppointmentdate().trim()+" "+da2.getAppointmenttime().trim();
				 try {
		                return f.parse(s2).compareTo(f.parse(s1));
		            } catch (ParseException e) {
		                throw new IllegalArgumentException(e);
		            }
			};
			downloadal.sort(cmp);
		
		ArrayList<Doctor> pal = new ArrayList<Doctor>();
		
		for(DoctorAppointment da:downloadal) {
			Doctor p = drepo.findById(da.getDoctormail()).orElse(null);
			pal.add(p);
		}
		ArrayList<PrescriptionRemainder> prlist = (ArrayList<PrescriptionRemainder>) premrepo.findAll();
		prlist = (ArrayList<PrescriptionRemainder>) prlist.stream().filter((i)-> i.getPatientmail().equals(session.getAttribute("patientmail").toString())).collect(Collectors.toList());
		ModelAndView mv = new ModelAndView();
		mv.addObject("doctorappointmentstotakeprescription",downloadal);
		mv.addObject("doctorsgivenprescription",pal);
		mv.addObject("prescriptionremainder",prlist);
		mv.setViewName("prescription.jsp");
		return mv;

	}
	
	@RequestMapping("/downloadFile/{fileId}")
	public ResponseEntity<ByteArrayResource>  downloadprescription(@PathVariable String fileId) {
		DoctorAppointment required = docapprepo.findById(Long.parseLong(fileId)).orElse(null);
		
		
		return ResponseEntity.ok()
				.contentType(MediaType.parseMediaType(required.getDoctype()))
				.header(HttpHeaders.CONTENT_DISPOSITION,"attachment:filename=\""+required.getDocname()+"\"")
				.body(new ByteArrayResource(required.getData()));
		
		
	}
	
	
	//reports
	@RequestMapping("/labreportgivenbytestlab")
	public ModelAndView labreportgivenbydoctor(HttpServletRequest req) throws ParseException {
		HttpSession session = req.getSession();
		ArrayList<TestingLabAppointment> dal = tlarepo.findAllAppointmentsOfPatients(session.getAttribute("patientmail").toString(),"booked");
		downloadalreports = new ArrayList<TestingLabAppointment>();
		
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
		      // System.out.print( dateAfter.getTime() +" - "+ dateBefore.getTime());
		       long difference = dateAfter.getTime() - dateBefore.getTime();
		       long daysBetween =  (difference );
		       if(daysBetween < 0 && da.getReport()!=null)
		    	   downloadalreports.add(da);
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
		downloadalreports.sort(cmp);
		
		ArrayList<TestingLab> pal = new ArrayList<TestingLab>();
		
		for(TestingLabAppointment da:downloadalreports) {
			TestingLab p = tlrepo.findById(da.getTestinglabmail()).orElse(null);
			pal.add(p);
		}
		ModelAndView mv = new ModelAndView();
		mv.addObject("doctorappointmentstotakereport",downloadalreports);
		mv.addObject("doctorsgivenreport",pal);
		mv.setViewName("labreport.jsp");
		return mv;

	}
	
	@RequestMapping("/downloadReport/{fileId}")
	public ResponseEntity<ByteArrayResource>  downloadReport(@PathVariable String fileId) {
		TestingLabAppointment required = tlarepo.findById(Long.parseLong(fileId)).orElse(null);
		
		
		return ResponseEntity.ok()
				.contentType(MediaType.parseMediaType(required.getReporttype()))
				.header(HttpHeaders.CONTENT_DISPOSITION,"attachment:filename=\""+required.getReportname()+"\"")
				.body(new ByteArrayResource(required.getReport()));		
		
	}
	
	
	@RequestMapping("/uploadpatientfiles")
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
			mv.setViewName("patientfilesupload.jsp");
			return mv;
		
			
	}
	
	@RequestMapping("/downloadPatientFiles/{fileId}")
	public ResponseEntity<ByteArrayResource>  downloadPatientFiles(@PathVariable String fileId){ 
		PatientFiles required = pfilesrepo.findById(Long.parseLong(fileId)).orElse(null);
	
		
		return ResponseEntity.ok()
				.contentType(MediaType.parseMediaType(required.getPatilentfiletype()))
				.header(HttpHeaders.CONTENT_DISPOSITION,"attachment:filename=\""+required.getPatientfilename()+"\"")
				.body(new ByteArrayResource(required.getPatientfiles()));		
		
	}
	
	@RequestMapping("deletefile")
	public String  deletePatientfile(String fileID){ 
		PatientFiles required = pfilesrepo.findById(Long.parseLong(fileID)).orElse(null);
		//pfilesrepo.deleteFile(required.getPatientfilename(), required.getPatilentfiletype(), required.getPatientfiles(),session.getAttribute("patientmail").toString());
		pfilesrepo.delete(required);
		System.out.println("deleted");
		return "redirect:patientprofile";
	}

	
	@RequestMapping("/editprofile")
	public String editprofile(String lastname,String medicalhistory,HttpServletRequest request) {
		HttpSession session = request.getSession();
		prepo.updateChanges(lastname,medicalhistory,session.getAttribute("patientmail").toString());
		return "redirect:patientprofile";
	}
	
	@RequestMapping("/addaremainder")
	public String addaremainder(String remaindertime,Integer noofdays,HttpServletRequest request) {
		System.out.println(remaindertime+" "+noofdays);
		HttpSession session = request.getSession();
		String patientmail = session.getAttribute("patientmail").toString();
		Date date1;
		Format formatter;
		Calendar calendar = Calendar.getInstance();

		date1 = calendar.getTime();
		formatter = new SimpleDateFormat("dd/MMM/yyyy");
		String todaydate = formatter.format(date1);
		
		PrescriptionRemainder premainder = new PrescriptionRemainder();
		premainder.setNoofdays(noofdays);
		premainder.setPatientmail(patientmail );
		premainder.setRemaindertime(remaindertime);
		premainder.setStartdate(todaydate);
		
		
		premrepo.save(premainder);

		return "redirect:prescriptiongivenbydoctor";
	}
	
	

	@RequestMapping("/deletearemainder")
	public String deletearemainder(@RequestParam("premobj")Long premobjindex,HttpServletRequest request) {
		
		PrescriptionRemainder pr = premrepo.findById(premobjindex).orElse(null);
		premrepo.delete(pr);
		
		
		return "redirect:prescriptiongivenbydoctor";
	}
	
	

}
