package com.varen.alphabet;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest(classes = WordSearchService.class)
@ActiveProfiles("test")
class AlphabetApplicationTests {

	Logger log = LogManager.getLogger(AlphabetApplicationTests.class);
	
	@Autowired
	private WordSearchService wordSearchService;
	
	@Test
	void runSampleProvided() {
		wordSearchService.solvePuzzle("src/test/resources/sample.txt");
	}
	
	@Test
	void runSampleLarger() {
		wordSearchService.solvePuzzle("src/test/resources/sample2.txt");
	}
	
	@Test
	void runSampleMoreRows() {
		wordSearchService.solvePuzzle("src/test/resources/sample3.txt");
	}
	
	@Test
	void runSampleMoreColumns() {
		wordSearchService.solvePuzzle("src/test/resources/sample4.txt");
	}

}
