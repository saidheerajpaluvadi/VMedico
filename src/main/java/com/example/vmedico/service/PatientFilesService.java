package com.example.vmedico.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.vmedico.dao.PatientFileRepo;
import com.example.vmedico.model.PatientFiles;

@Service
public class PatientFilesService {
	
	@Autowired
	PatientFileRepo pfrepo;
	

	public void storeFile(String originalFilename, String contentType, byte[] bytes, String string) {
		PatientFiles pf = new PatientFiles(string, bytes,contentType,originalFilename) ;
		pfrepo.save(pf);
	}
	
	


}
