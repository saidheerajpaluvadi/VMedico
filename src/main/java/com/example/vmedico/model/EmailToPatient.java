package com.example.vmedico.model;

public class EmailToPatient {
	
	private String emailIDTo;
	private String emailBody;
	private Doctor doc;
	private String appdate;
	private String apptime;
	private String status;
	private TestingLab testlab;
		
	
	public EmailToPatient(String emailIDTo, Doctor doc, String appdate, String apptime,String status) {
		super();
		this.emailIDTo = emailIDTo;
		this.doc = doc;
		this.appdate = appdate;
		this.apptime = apptime;
		this.status = status;
	}
	
	public EmailToPatient(String emailIDTo, TestingLab testlab, String appdate, String apptime,String status) {
		super();
		this.emailIDTo = emailIDTo;
		this.testlab = testlab;
		this.appdate = appdate;
		this.apptime = apptime;
		this.status = status;
	}
	
	
	public TestingLab getTestlab() {
		return testlab;
	}


	public void setTestlab(TestingLab testlab) {
		this.testlab = testlab;
	}


	public String getEmailIDTo() {
		return emailIDTo;
	}
	public void setEmailIDTo(String emailIDTo) {
		this.emailIDTo = emailIDTo;
	}
	
	public String getEmailBody() {
		return emailBody;
	}
	public void setEmailBody(String emailBody) {
		this.emailBody = emailBody;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Doctor getDoc() {
		return doc;
	}
	public void setDoc(Doctor doc) {
		this.doc = doc;
	}
	public String getAppdate() {
		return appdate;
	}
	public void setAppdate(String appdate) {
		this.appdate = appdate;
	}
	public String getApptime() {
		return apptime;
	}
	public void setApptime(String apptime) {
		this.apptime = apptime;
	}
	
	
	public String getEmailBodyinHtmlFormat() {
		String res = "<html>" + 
				"<head>"
				+"</head>"
				+ "<body style=\"margin:10px\">"
				+ "<label style = \"background-color: #cc0000;padding:14px;color:white;float: left;width:100%;font-size:22px;\"> Vmedico </label>"
				+"<h2 style=\"padding-Top:70px\">Hello,</h2>"
				+"<p>Your Appointment is "+this.status+" with "+doc.getFirstname()+" in "+doc.getHospitalname()+" on "+this.appdate+" at "+this.apptime
				+"</p>"
				+ "</body>"
				+"</html>";
		
		return res;
	}
	
	public String getEmailBodyinHtmlFormatForTestinglab() {
		String res = "<html>" + 
				"<head>"
				+"</head>"
				+ "<body style=\"margin:10px\">"
				+ "<label style = \"background-color: #cc0000;padding:14px;color:white;float: left;width:100%;font-size:22px;\"> Vmedico </label>"
				+"<h2 style=\"padding-Top:70px\">Hello,</h2>"
				+"<p>Your Appointment is "+this.status+" in "+ testlab.getNameoflab()+" on "+this.appdate+" at "+this.apptime
				+"</p>"
				+ "</body>"
				+"</html>";
		
		return res;
	}
	
	

}
