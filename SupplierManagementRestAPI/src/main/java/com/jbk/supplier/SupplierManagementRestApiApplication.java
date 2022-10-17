package com.jbk.supplier;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class SupplierManagementRestApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(SupplierManagementRestApiApplication.class, args);
	}

}
