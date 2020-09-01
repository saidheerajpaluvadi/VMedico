package com.example.vmedico.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;


import com.example.vmedico.model.TestingLabAppointment;

public interface TestLabAppointmentRepo extends CrudRepository<TestingLabAppointment,Long> {
	
	@Query("from TestingLabAppointment u where u.testinglabmail= ?1 and u.appointmentdate= ?2 and u.appointmenttime=?3")
	List<TestingLabAppointment> find(String doctormail,String appointmentdate,String appointmenttime) ;
	
	
	@Query("from TestingLabAppointment u where u.patientmail = ?1 and u.appointmentdate= ?2 and u.appointmenttime= ?3 and (u.status=?4 or u.status= ?5)")
	ArrayList<TestingLabAppointment> checkExists(String patientmail,String appointmentdate, String appointmenttime, String status1 ,String status2);

	@Query("from TestingLabAppointment u where u.patientmail= ?1 and u.status=?2")
	ArrayList<TestingLabAppointment> findAllAppointmentsOfPatients(String patientmail, String string);
	
	@Query("from TestingLabAppointment u where u.testinglabmail= ?1 and u.status=?2")
	ArrayList<TestingLabAppointment> findAllAppointmentsOfTestingLab(String testinglabmail,String status);
	
	@Transactional
	@Modifying
	@Query("update TestingLabAppointment u set u.status =?1 where u.id= ?2 ")
	void changeThePatientStatus(String status, Long ID);

	@Query(value = " select count(patientmail) from TestingLabAppointment  u where u.patientmail =?1 ")
	Integer getCountOfTestLabBookings(String patientmail);

	@Transactional
	@Modifying
	@Query("update TestingLabAppointment u set u.reportname=?1, u.reporttype = ?2, u.report =?3 where u.patientmail= ?4 and u.testinglabmail= ?5  and u.appointmentdate= ?6 and u.appointmenttime=?7")
	void storeFile(String originalFilename, String contentType, byte[] bytes,String patientmail, String testinglabmail, String appointmentdate, String appointmenttime);

	
}
