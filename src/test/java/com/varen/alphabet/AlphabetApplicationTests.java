package com.varen.alphabet;

import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
//@RunWith(SpringJUnit4ClassRunner.class)
//@SpringBootConfiguration(AlphabetApplication.class)
//@IntegrationTest(value = "input:expectedOutput")
class AlphabetApplicationTests {

	@Autowired
	private Setup setup;
	
	@Autowired
    private CommandLineRunner clr;
	
	@Autowired
	private AlphabetApplication app;
	
	@Test
	void contextLoads() throws Exception {
//		app.main(new String[] { "src/test/resources/sample.txt" });
//		clr.run("src/test/resources/sample.txt");
	}
	
	@Test
	void runSampleProvided() {
		
//		List<String> fileContents = setup.readFile("src/test/resources/sample.txt");
//		char[][] d = setup.createMatrix(fileContents); 
//		
//        //print answers:
//		
//        for (Map.Entry<String, String> entry : setup.answerMap.entrySet()) {
//        	System.out.println(entry.getKey() + " " + entry.getValue());
//        }
	}
	
	@Test
	void runSampleLarger() {
	}

}
