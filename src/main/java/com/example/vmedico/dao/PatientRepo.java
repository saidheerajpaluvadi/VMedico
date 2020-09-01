package com.example.vmedico.dao;




import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import com.example.vmedico.model.Patient;


public interface PatientRepo extends CrudRepository<Patient, String> {
	
	@Transactional
	@Modifying
	@Query("update Patient u set u.lastname =?1 , u.medicalhistory= ?2 where u.email=?3")
	void updateChanges( String lastname, String medicalhistory,String patientmail);

	
	
	
}
