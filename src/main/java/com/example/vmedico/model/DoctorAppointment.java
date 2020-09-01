package com.example.vmedico.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.TableGenerator;





@TableGenerator(name="tab", initialValue=0, allocationSize=50)
@Entity
public class DoctorAppointment {
	
	 @GeneratedValue(strategy=GenerationType.TABLE, generator="tab")
	 @Id
	 Long id;
	 
	 private String patientmail;
	 private String doctormail;
	 private String appointmentdate;
	 private String appointmenttime;
	 private String status;
	private String reason;
	 
	 @Lob
	 private byte[] data;
	 
	 private String doctype;
	 private String docname;
	 
	 private boolean isPrescriptionnotneed;
	 
	 
	 
	public boolean isPrescriptionnotneed() {
		return isPrescriptionnotneed;
	}
	public void setPrescriptionnotneed(boolean isPrescriptionnotneed) {
		this.isPrescriptionnotneed = isPrescriptionnotneed;
	}
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	public String getDocname() {
		return docname;
	}
	public void setDocname(String docname) {
		this.docname = docname;
	}
	public String getDoctype() {
		return doctype;
	}
	public void setDoctype(String doctype) {
		this.doctype = doctype;
	}
	public byte[] getData() {
		return data;
	}
	public void setData(byte[] data) {
		this.data = data;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getPatientmail() {
		return patientmail;
	}
	public void setPatientmail(String patientmail) {
		this.patientmail = patientmail;
	}
	public String getDoctormail() {
		return doctormail;
	}
	public void setDoctormail(String doctormail) {
		this.doctormail = doctormail;
	}
	public String getAppointmentdate() {
		return appointmentdate;
	}
	public void setAppointmentdate(String appointmentdate) {
		this.appointmentdate = appointmentdate;
	}
	public String getAppointmenttime() {
		return appointmenttime;
	}
	public void setAppointmenttime(String appointmenttime) {
		this.appointmenttime = appointmenttime;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	
	 
	 
	
}
