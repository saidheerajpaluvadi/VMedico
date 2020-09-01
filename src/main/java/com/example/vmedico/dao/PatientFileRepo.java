package com.example.vmedico.dao;

import java.util.ArrayList;


import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;


import com.example.vmedico.model.PatientFiles;



public interface PatientFileRepo extends CrudRepository<PatientFiles, Long> {


	@Query("from PatientFiles where patientmail=?1")
	ArrayList<PatientFiles> getFiles(String patientmail);


}
