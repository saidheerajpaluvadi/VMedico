package com.example.vmedico.model;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.TableGenerator;


@TableGenerator(name="tab", initialValue=0, allocationSize=50)
@Entity 
public class PrescriptionRemainder {
	
	 @GeneratedValue(strategy=GenerationType.TABLE, generator="tab")
	 @Id
	 Long id;
	String patientmail;
	
	String startdate;
	String remaindertime;
	Integer noofdays;
	

	public PrescriptionRemainder() {
		super();
	}

	
	public String getPatientmail() {
		return patientmail;
	}
	public void setPatientmail(String patientmail) {
		this.patientmail = patientmail;
	}
	public Long getId() {
		return id;
	}

	public void setRemaindertime(String remaindertime) {
		this.remaindertime = remaindertime;
	}
	public void setNoofdays(Integer noofdays) {
		this.noofdays = noofdays;
	}
	public String getStartdate() {
		return startdate;
	}

	public void setStartdate(String startdate) {
		this.startdate = startdate;
	}

	public String getRemaindertime() {
		return remaindertime;
	}
	
	public Integer getNoofdays() {
		return noofdays;
	}
	

}
