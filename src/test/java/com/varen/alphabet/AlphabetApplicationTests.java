package com.varen.alphabet;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest(classes = Setup.class)
//@SpringBootTest(args = "src/test/resources/sample.txt")
@ActiveProfiles("test")
//@SpringBootTest
//@RunWith(SpringJUnit4ClassRunner.class)
//@SpringBootConfiguration(AlphabetApplication.class)
//@IntegrationTest(value = "input:expectedOutput")
//@EnableAutoConfiguration
//@Configuration
//@ComponentScan
class AlphabetApplicationTests {

	@Autowired
	private Setup setup;
//	
//	@Autowired
//    private CommandLineRunner clr;
	
//	@Autowired
//	private ApplicationRunner appRunner;
//	ApplicationRunner
//	@Autowired
//	private AlphabetApplication app;
//	
//    @Autowired
//    ApplicationContext ctx;
	
//	@Autowired Environment env
	@Test
	void contextLoads() throws Exception {
		
		setup.solvePuzzle("src/test/resources/sample.txt");
		
//	    HELLO 0:0 4:4
//	    GOOD 4:0 4:3
//	    BYE 1:3 1:1


		assertEquals("0:0 4:4", this.setup.answerMap.get("HELLO"));
		assertEquals("4:0 4:3", this.setup.answerMap.get("GOOD"));
		assertEquals("1:3 1:1", this.setup.answerMap.get("BYE"));
//		appRunner.run(new ApplicationArguments);
		
//		clr.run("src/test/resources/sample.txt");
		
//		AlphabetApplication runner = ctx.getBean(AlphabetApplication.class);
//		runner.setup.readFile("src/test/resources/sample.txt");
		
//		CommandLineRunner runner = app.commandLineRunner(ctx);
//		runner.run(new String [] { "src/test/resources/sample.txt" } );
		
		
//        AlphabetApplication.main(new String[] { "src/test/resources/sample.txt" } );
//        commandLineRunner.run("-i", "src/test/resources/sample.txt");
		
//		app.main(new String[] { "src/test/resources/sample.txt" });
//		clr.run("src/test/resources/sample.txt");
	}
	
	@Test
	void runSampleProvided() {
		setup.solvePuzzle("src/test/resources/sample.txt");
	}
		
//		List<String> fileContents = setup.readFile("src/test/resources/sample.txt");
//		char[][] d = setup.createMatrix(fileContents); 
//		
//        //print answers:
//		
//        for (Map.Entry<String, String> entry : setup.answerMap.entrySet()) {
//        	System.out.println(entry.getKey() + " " + entry.getValue());
//        }
//	}
	
	@Test
	void runSampleLarger() {
		setup.solvePuzzle("src/test/resources/sample2.txt");
	}
	
	@Test
	void runSampleMoreRows() {
		setup.solvePuzzle("src/test/resources/sample3.txt");
	}
	
	@Test
	void runSampleMoreColumns() {
		setup.solvePuzzle("src/test/resources/sample4.txt");
	}

}
