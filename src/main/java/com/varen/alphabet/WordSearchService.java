package com.varen.alphabet;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;


@Service 
public class WordSearchService {

	Logger log = LogManager.getLogger(WordSearchService.class);

	/**
	 * Given a path to a word search file (given: valid input) solve the puzzle
	 * Steps:
	 * 1. read file
	 * 2. gather the word list
	 * 3. create the word grid, or search matrix.
	 * 4. find each word in the matrix. 
	 * @param filePath
	 */
	public void solvePuzzle(String filePath) {
		
		log.debug("filepath provided: {}", filePath);
		List<String> fileContents = readFile(filePath);

		int inputRowStart = determineStartRowWordList(fileContents.get(0));
		
		List<String> wordList = gatherWordList(fileContents, inputRowStart);
		
		char[][] wordGrid = createMatrix(fileContents); 
			
		for (String word : wordList) {
			String ans = findWordInMatrix(word, wordGrid);
			System.out.println(word + " " + ans);
		}
	}
    
	/**
	 * Helper function to read input file as a list of Strings. 
	 * @param filePath
	 * @return
	 */
    private List<String> readFile(String filePath) {
    	
    	Path path = Paths.get(filePath);
    	
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
    
    /**
     * Helper function to determine where word search input begins in the input file. 
     * @param firstLine
     * @return
     */
    private int determineStartRowWordList(String firstLine) {
    	String[] arrayDimensions = firstLine.split("x");
    	int numRows = Integer.parseInt(arrayDimensions[0]);
    	return numRows + 1;
    }
    
    /**
     * Helper function to consolidate word list and strip spaces from word inputs. 
     * @param lines
     * @param inputRowStart
     * @return
     */
    private List<String> gatherWordList(List<String> lines, int inputRowStart) {
    	List<String> wordList = new ArrayList<>();
    	while(inputRowStart < lines.size()) {
        	String word = lines.get(inputRowStart++);
        	word = word.replaceAll("\\s+", "");
        	wordList.add(word);
        }
    	return wordList;
    }
    	   
    
    /**
     * Create the matrix that represents the word search grid. 
     * @param lines
     * @return
     */
    private char[][] createMatrix(List<String> lines) {
    	
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
            	log.debug(matrix[i][j]);
            }
            log.debug("");
        }
        
        log.debug("");
    	
        return matrix; 
    }
    
    /** 3 steps to find a word in the grid - corresponds to a typical human (manual) search:
     * 1. find first letter.
     * 2. find potential direction(s).
     * 3. match rest of word (or continue with another direction)
     * @param word
     * @param matrix
     */
    private String findWordInMatrix(String wordStr, char[][] matrix) {

    	log.debug("search for word: {}", wordStr);
    	char[] word = wordStr.toCharArray();
    	
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
            	if (word[0] == matrix[i][j]) {
            		List<int[]> directionList = findPossibleDirectionsByCirclingStart(word, matrix, i, j);
            		for (int[] direction : directionList) {
	            		boolean isMatch = doesWordMatch(word, matrix, i, j, direction);
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
	            		}
            		}
            	}
            }
        }
        
        log.warn("Word {} was NOT found in the grid. Does the word exist?", word);
        return "NOT FOUND"; 
    }
    
    /**
     * Find all the directions a word could go by circling the starting letter. 
     * Ignore the starting letter itself.
     * Find all directions because more than one potential direction could exist from any starting point.  
     * @param word
     * @param matrix
     * @param x
     * @param y
     * @return
     */
    private List<int[]> findPossibleDirectionsByCirclingStart(char[] word, char[][] matrix, int x, int y) {
    	
    	List<int[]> directionList = new ArrayList<>();
    	int[] ans;
    	
    	int i = Math.max(x-1, 0);
    	for ( ; i < matrix.length && i <= x + 1; i++) {
    		
    		int j = Math.max(y-1, 0);
    		for ( ; j >= 0 && j < matrix[0].length && j <= y + 1; j++) {
    			//skip element itself
    			if ( i == x && j == y) {
    				continue;
    			}
    			if (word[1] == (matrix[i][j])) {
    				ans = new int[2];
    				ans[0] = i - x;
    				ans[1] = j - y; 
    				directionList.add(ans);
    			}
    		}
    	}
    	
    	return directionList;
    }
    
    /**
     * Given a word, a starting point, and a direction, trace the direction and check whether the word matches.
     * Stop if the word goes off the grid. Stop on the first non-matching character. 
     * @param word
     * @param matrix
     * @param x
     * @param y
     * @param direction
     * @return
     */
    private boolean doesWordMatch(char[] word, char[][] matrix, int x, int y, int[] direction) {
    	   	
    	for (int i = 0; i < word.length; i++) {
    		if (x < 0 || x > matrix.length -1 || y < 0 || y > matrix[0].length -1) {		
    			return false;
    		}
    		if (word[i] != matrix[x][y]) {
    			return false;
    		}
    		
			x += direction[0];
			y += direction[1];
    	}
    	
    	return true;
    }

}
