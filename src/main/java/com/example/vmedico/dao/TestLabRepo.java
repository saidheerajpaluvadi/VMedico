package com.example.vmedico.dao;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import com.example.vmedico.model.TestingLab;

public interface TestLabRepo extends CrudRepository<TestingLab,String>{

	@Transactional
	@Modifying
	@Query("update TestingLab u set u.address = address")
	void updateDetails(String string, String address);

}
