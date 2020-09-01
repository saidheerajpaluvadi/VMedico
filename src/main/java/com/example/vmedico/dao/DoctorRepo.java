package com.example.vmedico.dao;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import com.example.vmedico.model.Doctor;

public interface DoctorRepo extends CrudRepository<Doctor, String> {

	@Transactional
	@Modifying
	@Query("update Doctor u set u.lastname =?2 , u.highestqualification= ?3 ,u.yearsofexperience=?4,u.hospitalname=?5,u.location=?6 where u.email=?1")
	void updateDetails(String string, String lastname, String highestqualification, Integer yearsofexperience,String hospitalname, String location);

}
