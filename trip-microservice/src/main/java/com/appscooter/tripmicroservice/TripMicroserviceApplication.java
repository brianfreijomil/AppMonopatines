package com.appscooter.tripmicroservice;

import com.appscooter.tripmicroservice.services.MainTest;
import com.appscooter.tripmicroservice.services.loadData.CsvReader;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

import java.io.IOException;
import java.sql.SQLException;

@SpringBootApplication
@EnableDiscoveryClient
public class TripMicroserviceApplication {

	@Autowired
	private CsvReader loadDb;

	@Autowired
	private MainTest tests;

	public static void main(String[] args) {
		SpringApplication.run(TripMicroserviceApplication.class, args);
	}

	@PostConstruct
	public void init() throws SQLException, IOException {
		//this.loadDb.load();
		//this.tests.loadTest();
	}
}
