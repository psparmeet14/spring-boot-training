package com.parmeet.springboot.springboottraining;

import com.parmeet.springboot.springboottraining.survey.repository.SurveyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringBootTrainingApplication implements CommandLineRunner {

	@Autowired
	private SurveyRepository repo;

	public static void main(String[] args) {
		SpringApplication.run(SpringBootTrainingApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		System.out.println("CHECK: " + repo.retrieveAllSurveys());
	}

}
