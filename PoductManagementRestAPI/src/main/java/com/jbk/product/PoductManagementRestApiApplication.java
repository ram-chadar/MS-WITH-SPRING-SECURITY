package com.jbk.product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;


@SpringBootApplication
//@EnableEurekaClient
public class PoductManagementRestApiApplication {

	//@Autowired 
	//PasswordEncoder encoder;
	
	
	public static void main(String[] args){
		SpringApplication.run(PoductManagementRestApiApplication.class, args);
	
	}
	
	@Bean
	public CommonsMultipartResolver commonsMultipartResolver() {
		return new CommonsMultipartResolver();
	}
	
	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}

	
}
