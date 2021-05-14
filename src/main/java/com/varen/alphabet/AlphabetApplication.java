package com.varen.alphabet;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;

@Profile("!test")
@SpringBootApplication
public class AlphabetApplication {

	@Autowired
	WorkSearchService wordSearch;
	
//    @Override
//    public void run(String...args) throws Exception {
//        System.out.println("Application started with command-line arguments: {} . \n To kill this application, press Ctrl + C." + Arrays.toString(args));
//    }
	
	public static void main(String[] args) {
		SpringApplication.run(AlphabetApplication.class, args);
		
//		call(args);
		
	}
	
//    @Override
//    public void run(ApplicationArguments args) throws Exception {
//		
//    	System.out.println(args.getSourceArgs()[0]);
//		
//		System.out.println(args.getSourceArgs()[0]);
//		List<String> fileContents = setup.readFile(args.getSourceArgs()[0]);
//		char[][] d = setup.createMatrix(fileContents); 
////		
////        //print answers:
////		
//		for (Map.Entry<String, String> entry : setup.answerMap.entrySet()) {
//			System.out.println(entry.getKey() + " " + entry.getValue());
//		}
//    }
	
	
//	private void call(String[] args) {
//		System.out.println(args[0]);
//		
//		System.out.println(args[0]);
//		List<String> fileContents = setup.readFile(args[0]);
//		char[][] d = setup.createMatrix(fileContents); 
////		
////        //print answers:
////		
//		for (Map.Entry<String, String> entry : setup.answerMap.entrySet()) {
//			System.out.println(entry.getKey() + " " + entry.getValue());
//		}
//	}
	
	@Bean
	public CommandLineRunner commandLineRunner(ApplicationContext ctx) {
		return args -> {

			wordSearch.solvePuzzle(args[0]);
//			System.out.println(args[0]);
//			List<String> fileContents = wordSearch.readFile(args[0]);
//			char[][] d = wordSearch.createMatrix(fileContents); 
//			
//	        //print answers:
//			
//	        for (Map.Entry<String, String> entry : wordSearch.answerMap.entrySet()) {
//	        	System.out.println(entry.getKey() + " " + entry.getValue());
//	        }
		};
	}

}
