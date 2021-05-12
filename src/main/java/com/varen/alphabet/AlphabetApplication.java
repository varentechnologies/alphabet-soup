package com.varen.alphabet;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class AlphabetApplication {

	@Autowired
	Setup setup;
	
	
	public static void main(String[] args) {
		SpringApplication.run(AlphabetApplication.class, args);
	}
	
	@Bean
	public CommandLineRunner commandLineRunner(ApplicationContext ctx) {
		return args -> {

			System.out.println(args[0]);
			List<String> fileContents = setup.readFile(args[0]);
			char[][] d = setup.createMatrix(fileContents); 
			
	        //print answers:
			
	        for (Map.Entry<String, String> entry : setup.answerMap.entrySet()) {
	        	System.out.println(entry.getKey() + " " + entry.getValue());
	        }
		};
	}

}
