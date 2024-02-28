package com.agency.amazon;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class })
public class AgencyAmazonApplication {

	public static void main(String[] args) {
		SpringApplication.run(AgencyAmazonApplication.class, args);
	}
}
