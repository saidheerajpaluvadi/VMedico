package com.example.vmedico.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.example.vmedico.model.PrescriptionRemainder;

public interface PrescriptionRemainderRepo extends CrudRepository<PrescriptionRemainder,Long>{

	@Query("from PrescriptionRemainder u where u.patientmail= ?1")
	List<PrescriptionRemainder> findByPatientmail(String patientmail);

}
