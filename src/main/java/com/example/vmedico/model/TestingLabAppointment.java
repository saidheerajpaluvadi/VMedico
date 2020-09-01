package com.example.vmedico.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.TableGenerator;

@TableGenerator(name="tab", initialValue=0, allocationSize=50)
@Entity
public class TestingLabAppointment {

	
	 @GeneratedValue(strategy=GenerationType.TABLE, generator="tab")
	 @Id
	 Long id;	 
	 private String patientmail;
	 private String testinglabmail;
	 private String appointmentdate;
	 private String appointmenttime;
	 private String testname;
	 private String status;
	 
	 @Lob
	 private byte[] report;
	 
	 private String reporttype;
	 private String reportname;
	 
	 
	public byte[] getReport() {
		return report;
	}
	public void setReport(byte[] report) {
		this.report = report;
	}
	public String getReporttype() {
		return reporttype;
	}
	public void setReporttype(String reporttype) {
		this.reporttype = reporttype;
	}
	public String getReportname() {
		return reportname;
	}
	public void setReportname(String reportname) {
		this.reportname = reportname;
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
	public String getTestinglabmail() {
		return testinglabmail;
	}
	public void setTestinglabmail(String testinglabmail) {
		this.testinglabmail = testinglabmail;
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
	public String getTestname() {
		return testname;
	}
	public void setTestname(String testname) {
		this.testname = testname;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	 
}