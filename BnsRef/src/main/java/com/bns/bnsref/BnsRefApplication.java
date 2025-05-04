package com.bns.bnsref;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BnsRefApplication {

	public static void main(String[] args) {
		SpringApplication.run(BnsRefApplication.class, args);
		System.out.println("BnsRef Service is up and running");
	}

}
