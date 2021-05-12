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

import org.springframework.stereotype.Service;

@Service 
public class Setup {

	
	public Map<String, String> answerMap = new HashMap<>();
    
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
        	System.out.println("Could not read a file at the path provided. Try another file path.");
        	return null;
        }
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
    
    private void findWordInMatrix(char[] word, char[][] matrix) {
    	
    	for (char w : word) {
    		
    	}
    	
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
            	// 3 steps:
            	// 1. find first letter.
            	// 2. find direction.
            	// 3. match rest of word (or continue)
            	if (word[0] == matrix[i][j]) {
//                	System.out.println("found potential start at x:" + i + " y: " + j); 
            		int[] direction = findDirectionByCirclingStart(word, matrix, i, j);
            		boolean isMatch = doesWordMatch(word, matrix, i, j, direction);
//            		System.out.println("found match? " + isMatch);
            	}
            }
        }
        
//        //print answers:
//        for (Map.Entry<String, String> entry : answerMap.entrySet()) {
//        	System.out.println(entry.getKey() + " " + entry.getValue());
//        }
    }
    
    private int[] findDirectionByCirclingStart(char[] word, char[][] matrix, int x, int y) {
    	
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
    				boolean eachAns = doesWordMatch(word, matrix, x, y, ans);
//    				System.out.println("tried path - match? " + eachAns);
    			}
    		}
    	}
    	
//    	System.out.println("direction - x:" + ans[0] + " y: " + ans[1]); 
    	return ans;
    }
    
    private boolean doesWordMatch(char[] word, char[][] matrix, int x, int y, int[] direction) {
    	
    	int originX = x;
    	int originY = y;
    	
    	for (int i = 0; i < word.length; i++) {
//    		System.out.println("character in word: " + word[i] + " char from matrix: " + matrix[x][y]);
//    		System.out.println("x: " + x + " y: " + y);
    		if (x < 0 || x > matrix.length -1 || y < 0 || y > matrix[0].length -1) {
    			return false;
    		}
    		if (word[i] != matrix[x][y]) {
    			return false;
    		}
    		//don't increment the last value once we've found a match.
    		if (i < word.length-1) {
    			x += direction[0];
    			y += direction[1];
    		}
    	}
    	
    	answerMap.put(new String(word), originX + ":" + originY + " " + x + ":" + y );
//    	System.out.println("direction - x:" + x + " y: " + y); 
    	return true;
    }

}
