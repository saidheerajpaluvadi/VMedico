package com.example.vmedico.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import com.example.vmedico.model.DoctorAppointment;

public interface DoctorAppointmentRepo extends CrudRepository<DoctorAppointment,Long>  {
	
	
	@Query("from DoctorAppointment u where u.doctormail= ?1 and u.appointmentdate= ?2 and u.appointmenttime=?3")
	List<DoctorAppointment> find(String doctormail,String appointmentdate,String appointmenttime) ;
	
	
	@Query("from DoctorAppointment u where u.patientmail = ?1 and u.appointmentdate= ?2 and u.appointmenttime= ?3 and (u.status=?4 or u.status= ?5)")
	ArrayList<DoctorAppointment> checkExists(String patientmail,String appointmentdate, String appointmenttime, String status1 ,String status2);
	
	@Query(value = " select count(patientmail) from DoctorAppointment u where u.patientmail =?1 ")
	Integer getCountOfDoctorsBookings(String patientmail); 
	
	
	@Query("from DoctorAppointment u where u.doctormail= ?1 and u.status=?2")
	ArrayList<DoctorAppointment> findAllAppointmentsOfDoctor(String doctormail,String status);

	@Query("from DoctorAppointment u where u.patientmail= ?1 and u.status=?2")
	ArrayList<DoctorAppointment> findAllAppointmentsOfPatients(String patientmail, String string);

	@Transactional
	@Modifying
	@Query("update DoctorAppointment u set u.status =?1 where  id=?2")
	void changeThePatientStatus(String status, Long Id);

	@Transactional
	@Modifying
	@Query("update DoctorAppointment u set u.docname=?1, u.doctype = ?2, u.data =?3 where u.patientmail= ?4 and u.doctormail= ?5  and u.appointmentdate= ?6 and u.appointmenttime=?7")
	void storeFile(String originalFilename, String contentType, byte[] bytes,String patientmail, String doctormail, String appointmentdate, String appointmenttime);
	
	@Transactional
	@Modifying
	@Query("update DoctorAppointment u set u.isPrescriptionnotneed =?1 where u.patientmail= ?2 and u.doctormail= ?3  and u.appointmentdate= ?4 and u.appointmenttime=?5")
	void changeThePrescriptionStatus(boolean isPrescriptionnotneed, String patientmail, String doctormail, String appointmentdate, String appointmenttime);

	@Query("from DoctorAppointment u where u.appointmentdate= ?1")
	ArrayList<DoctorAppointment> findAppointmentsOnDate(String todaysdate);

}
