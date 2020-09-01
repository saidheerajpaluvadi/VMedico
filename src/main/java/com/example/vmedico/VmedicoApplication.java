package com.example.vmedico;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class VmedicoApplication {

	public static void main(String[] args) {
		SpringApplication.run(VmedicoApplication.class, args);
	}

}
