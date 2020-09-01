package com.example.vmedico.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.TableGenerator;

@TableGenerator(name="tab", initialValue=0, allocationSize=50)
@Entity
public class PatientFiles {
	
	
	private String patientmail;
	
	 @GeneratedValue(strategy=GenerationType.TABLE, generator="tab")
	 @Id
	 Long id;

	 @Lob
	 private byte[] patientfiles;
	 private String patilentfiletype;
	 private String patientfilename;
	 
	 
	 public PatientFiles() {
		 
	 }
	 
	 
	 
	 public PatientFiles(String patientmail, byte[] patientfiles, String patilentfiletype, String patientfilename) {
		super();
		this.patientmail = patientmail;
		this.patientfiles = patientfiles;
		this.patilentfiletype = patilentfiletype;
		this.patientfilename = patientfilename;
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
	public void setId(Long id) {
		this.id = id;
	}
	
	 
	public byte[] getPatientfiles() {
		return patientfiles;
	}
	public void setPatientfiles(byte[] patientfiles) {
		this.patientfiles = patientfiles;
	}
	public String getPatilentfiletype() {
		return patilentfiletype;
	}
	public void setPatilentfiletype(String patilentfiletype) {
		this.patilentfiletype = patilentfiletype;
	}
	public String getPatientfilename() {
		return patientfilename;
	}
	public void setPatientfilename(String patientfilename) {
		this.patientfilename = patientfilename;
	}
	
	 
	 
}
