package com.varen.alphabet;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;


@Service 
public class WorkSearchService {

	Logger log = LogManager.getLogger(WorkSearchService.class);

	
//	public Map<String, String> answerMap = new HashMap<>();
	
	/**
	 * Given a path to a word search file (given: valid input) solve the puzzle
	 * Steps:
	 * 1. read file
	 * 2. validate
	 * @param filePath
	 */
	public void solvePuzzle(String filePath) {
		
		log.info("filepath provided: {}", filePath);
		List<String> fileContents = readFile(filePath);
//		if (fileContents == null)
//			return;
//		if (!validateInputFile(fileContents))
//			return;
		int inputRowStart = determineStartRowWordList(fileContents.get(0));
		
		List<char[]> wordAsLettersList = gatherWordList(fileContents, inputRowStart);
		
		char[][] wordGrid = createMatrix(fileContents); 
		
//		Map<String, String> answerMap = new HashMap<>();
		
		for (char[] word : wordAsLettersList) {
			String ans = findWordInMatrix(word, wordGrid);
//			answerMap.put(new String(word), ans);
			log.info("{} {}", new String(word), ans);
		}
		
//		for(char[] word : wordAsLettersList) {
//			log.info("{} {}", new String(word), ans);
//		}
		
        //print answers:
		
//        for (Map.Entry<String, String> entry : this.answerMap.entrySet()) {
//        	System.out.println(entry.getKey() + " " + entry.getValue());
//        }
	}
    
    public List<String> readFile(String filePath) {
    	
        Path path = Paths.get(filePath); //"src/test/resources/sample.txt");

        try {
        	List<String> lines = Files.readAllLines(path);
        	for (String line : lines) {
        		System.out.println(line);
        	}
        	return lines;
        }
        catch (IOException e) {
        	log.error("Could not read a file at the path provided: {}.\nTry another file path.", path);
        	return null;
        }
    }
    
    public int determineStartRowWordList(String firstLine) {
    	String[] arrayDimensions = firstLine.split("x");
    	int numRows = Integer.parseInt(arrayDimensions[0]);
    	return numRows + 1;
    }
    
    public List<char[]> gatherWordList(List<String> lines, int inputRowStart) {
        List<char[]> wordAsLettersList = new ArrayList<>();
    	while(inputRowStart < lines.size()) {
        	String word = lines.get(inputRowStart++);
        	word = word.replaceAll("\\s+", "");
        	wordAsLettersList.add(word.toCharArray());
        }
    	return wordAsLettersList;
    }
    	   
    
    /**
     * Basic validation. Check input file matches expected. 
     * @param lines
     * @return
     */
    public boolean validateInputFile(List<String> lines) {
    	
    	int rows = 0;
    	int cols = 0;
    	try {
    		String[] firstLine = lines.get(0).split("x");
    		
    		rows = Integer.parseInt(firstLine[0]);
    		cols = Integer.parseInt(firstLine[1]);
    	}
    	catch(NumberFormatException e) {
    		log.error("The first line of the input file does not adhere to the specification. "
    				+ "Looking for <#rows>x<#cols> where rows and cols are integers. {}", e);
    		return false;
    	}
    	
    	Deque<Character> charList; 
    	
    	try {
	    	charList = new LinkedList<>();
	    			
	    	for(int i = 1; i < 1 + rows; i++) {
	    		 String[] charString = lines.get(i).split(" ");
	    		 for (String s : charString) {
	    			 char c = s.charAt(0);
	    			 charList.add(c);
	    		 }
	    	}
	    	
	    	char[][] matrix = new char[rows][cols];
	    	
	        for (int i = 0; i < matrix.length; i++) {
	            for (int j = 0; j < matrix[i].length; j++) {
	            	matrix[i][j] = charList.remove();
	            }
	        }
	        
	        for (int i = 0; i < matrix.length; i++) {
	            for (int j = 0; j < matrix[i].length; j++) {
	            	log.info(matrix[i][j] + " ");
	            }
	            log.info("");
	        }
    	}
    	catch (IndexOutOfBoundsException e) {
    		log.error("The matrix provided did not match the parameters given in the first line input."
    				+ "Expected {}x{} but the input matrix did not support creating that matrix. {}", e);
    		return false;
    	}
    	

    	int index = 1 + rows; 
    	List<String> wordsToFind = new ArrayList<>();
    	
    	try {
	        while(index < lines.size()) {
	        	String word = lines.get(index++);
	        	wordsToFind.add(word);
	        	System.out.println(word);
	        	char[] eachLetter = word.toCharArray();	
	//        	findWordInMatrix(eachLetter, matrix);
	        }
	        if (wordsToFind.size() == 0) {
	        	log.error("There were no words to search in the input file. Please add words to your input file.");
	        	return false;
	        }
    	}
    	catch(IndexOutOfBoundsException e) {
    		log.error("Could not read words to search.");
    		return false;
    	}
    	
    	return true;
    	
    }
    
    public char[][] createMatrix(List<String> lines) {
    	
    	int index = 0;
    	
    	String[] firstLine = lines.get(index++).split("x");
    	
    	int rows = Integer.parseInt(firstLine[0]);
    	int cols = Integer.parseInt(firstLine[1]);
    	
    	Deque<Character> charList = new LinkedList<>();
    			
    	for(int i = 1; i < 1 + rows; i++) {
    		 String[] charString = lines.get(i).split(" ");
    		 for (String s : charString) {
    			 char c = s.charAt(0);
    			 charList.add(c);
    		 }
    		index++;
    	}
    	
    	char[][] matrix = new char[rows][cols];
    	
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
            	matrix[i][j] = charList.remove();
            }
        }
        
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
            	System.out.print(matrix[i][j]);
            }
            System.out.println();
        }
        
        System.out.println();
        
        while(index < lines.size()) {
        	String word = lines.get(index++);
//        	System.out.println(word);
        	char[] eachLetter = word.toCharArray();	
        	findWordInMatrix(eachLetter, matrix);
        }
    	
        return matrix; 
    }
    
    /** 3 steps:
     * 1. find first letter.
     * 2. find potential direction(s).
     * 3. match rest of word (or continue)
     * @param word
     * @param matrix
     */
    private String findWordInMatrix(char[] word, char[][] matrix) {
    
    	
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
            	// 3 steps:
            	// 1. find first letter.
            	// 2. find direction.
            	// 3. match rest of word (or continue)
            	if (word[0] == matrix[i][j]) {
//                	System.out.println("found potential start at x:" + i + " y: " + j); 
            		int[] direction = findPossibleDirectionByCirclingStart(word, matrix, i, j);
//            		if (new String(word).equalsIgnoreCase("PARADE"))
//            			log.info("found direction: {} from start i: {}, j: {}", direction, i, j);
            		boolean isMatch = doesWordMatch(word, matrix, i, j, direction);
            		if (new String(word).equalsIgnoreCase("PARADE") && isMatch)
            			log.info("found direction: {} from start i: {}, j: {}", direction, i, j);
            		if (isMatch) {
            			//find end:
            			int x = i;
            			int y = j;
            			for (int n = 0; n < word.length; n++) {
	                		if (n < word.length-1) {
	                			x += direction[0];
	                			y += direction[1];
	                		}
            			}
                		
                		String ans = String.format("%s:%s %s:%s", i, j, x, y);
                		return ans;
//                		StringBuilder sb = new StringBuilder(100);
////                		originX + ":" + originY + " " + x + ":" + y );
//                		sb.append(i).append(isMatch)
//                		sb.append(":");
//                		sb.append(j);
//                		sb.append(" ");
//                		sb.append(x);
//                		sb.
            		}
            			
//            		System.out.println("found match? " + isMatch);
            	}
            }
        }
        
        log.warn("Word {} was NOT found in the grid. Is that expected?", word);
        return ""; 
        
//        //print answers:
//        for (Map.Entry<String, String> entry : answerMap.entrySet()) {
//        	System.out.println(entry.getKey() + " " + entry.getValue());
//        }
    }
    
    private int[] findPossibleDirectionByCirclingStart(char[] word, char[][] matrix, int x, int y) {
    	
    	int[] ans = new int[2];
    	
    	int i = Math.max(x-1, 0);
    	for ( ; i < matrix.length && i <= x + 1; i++) {
    		
    		int j = Math.max(y-1, 0);
    		for ( ; j >= 0 && j < matrix[0].length && j <= y + 1; j++) {
    			//skip element itself
    			if ( i == x && j == y) {
    				continue;
    			}
//    			System.out.println("search for " + word[1] +  " looking around " + matrix[x][y] + " . circles:" + matrix[i][j]);
    			if (word[1] == (matrix[i][j])) {
//    				System.out.println("found next: " + matrix[i][j]); 
    				ans = new int[2];
    				ans[0] = i - x;
    				ans[1] = j - y; 
    				// put in the word match function here because multiple paths could start valid but be wrong. 
//    				boolean eachAns = doesWordMatch(word, matrix, x, y, ans);
//    				System.out.println("tried path - match? " + eachAns);
    			}
    		}
    	}
    	
//    	System.out.println("direction - x:" + ans[0] + " y: " + ans[1]); 
    	return ans;
    }
    
    private boolean doesWordMatch(char[] word, char[][] matrix, int x, int y, int[] direction) {
    	
//		if (new String(word).equalsIgnoreCase("PARADE") && direction[0] == 0 && direction[1] == 1)
//			log.info("looking for: PARADE with direction {} from start i: {}, j: {}", direction, x, y);
//		
    	int originX = x;
    	int originY = y;
    	
    	for (int i = 0; i < word.length; i++) {
//    		System.out.println("character in word: " + word[i] + " char from matrix: " + matrix[x][y]);
//    		System.out.println("x: " + x + " y: " + y);
    		if (x < 0 || x > matrix.length -1 || y < 0 || y > matrix[0].length -1) {
//    			if (new String(word).equalsIgnoreCase("PARADE") && direction[0] == 0 && direction[1] == 1)
//    				log.info("looking for: PARADE with direction {} from start i: {}, j: {}", direction, x, y);
    		
    			return false;
    		}
    		if (word[i] != matrix[x][y]) {
//    			if (new String(word).equalsIgnoreCase("PARADE") && direction[0] == 0 && direction[1] == 1)
//    				log.info("looking for: PARADE with direction {} from start i: {}, j: {}", direction, x, y);
//    		
    			return false;
    		}
    		//don't increment the last value once we've found a match.
//    		if (i < word.length-1) {
    			x += direction[0];
    			y += direction[1];
//    		}
    	}
    	
		if (new String(word).equalsIgnoreCase("PARADE") && direction[0] == 0 && direction[1] == 1)
			log.info("looking for: PARADE with direction {} from start i: {}, j: {}", direction, x, y);
	
    	
//    	answerMap.put(new String(word), originX + ":" + originY + " " + x + ":" + y );
//    	System.out.println("direction - x:" + x + " y: " + y); 
    	return true;
    }

}
