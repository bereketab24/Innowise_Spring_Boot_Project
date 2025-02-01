package com.example.SimpleCRUDAPI;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
// 
@EntityScan(basePackages = "com.example.SimpleCRUDAPI.entity")
public class SimpleCrudapiApplication {

	public static void main(String[] args) {
		SpringApplication.run(SimpleCrudapiApplication.class, args);
	}

}
