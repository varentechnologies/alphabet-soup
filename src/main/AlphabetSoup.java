package main;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import classes.CONSTANTS;
import classes.Word;

public class AlphabetSoup {
	//NOTE: Please enter a file location here. 
	private static String file = "C:\\Users\\domin\\Projects\\alphabet-soup-master\\src\\resources\\wordSearch.txt";
	private static String[][] wordSearch;
	private static List<Word> words;
	private static int wordSearchRows = -1;
	private static int wordSearchColumns = -1;
	private static int endRow = -1; 
	private static int endColumn = -1; 
	
	public static void main (String[] args) {
		//temp list to avoid concurrency issues
		getFileData();
		
		//Rather than searching word by word, go through letter by letter of the word search matching letters to the words. 
		//Potentially can go either way though however this ensures we only fully traverse through the word search once. 
		completed:
		for (int row = 0; row < wordSearchRows; row++) {
			for (int column = 0; column < wordSearchColumns; column++) {
				for (int wordIndex = 0; wordIndex < words.size(); wordIndex++) {
					if (words.get(wordIndex).getWord().startsWith(wordSearch[row][column])) {
						//if the word is found at this point, set the coordinates of its location
						if (findWordAllDirections(words.get(wordIndex), row, column) && !words.get(wordIndex).isFound()) {
							words.get(wordIndex).setStartColumn(column);
							words.get(wordIndex).setStartRow(row);
							words.get(wordIndex).setEndColumn(endColumn);
							words.get(wordIndex).setEndRow(endRow);
							words.get(wordIndex).setFound(true);
							
							//reset the endColumn and endRow
							endColumn = endRow = -1; 
							
							//if all words are found, stop searching.
							boolean allFound = true;
							for (Word word : words) {
								if (!word.isFound()) {
									allFound = false;
								}
							}
							
							if (allFound) {
								break completed;
							}
						}
					}
				}
			}
		}
		
		//Print the results
		printWords();
	}
	
	/**
	 * Check all ways a word can be found if the letter is the beginning of any word.
	 * @param word The word we are looking for
	 * @param currentRow the row being reviewed
	 * @param currentColumn the column being reviewed
	 * @return whether the word is found or not
	 */
	public static boolean findWordAllDirections(Word word, int currentRow, int currentColumn) {
		if (findWord(word, 0, currentRow, currentColumn, CONSTANTS.TOP)) {
			return true;
		} else if (findWord(word, 0, currentRow, currentColumn, CONSTANTS.TOP_RIGHT)) {
			return true;
		} else if (findWord(word, 0, currentRow, currentColumn, CONSTANTS.RIGHT)) {
			return true;
		} else if (findWord(word, 0, currentRow, currentColumn, CONSTANTS.BOTTOM_RIGHT)) {
			return true;
		} else if (findWord(word, 0, currentRow, currentColumn, CONSTANTS.BOTTOM)) {
			return true;
		} else if (findWord(word, 0, currentRow, currentColumn, CONSTANTS.BOTTOM_LEFT)) {
			return true;
		} else if (findWord(word, 0, currentRow, currentColumn, CONSTANTS.LEFT)) {
			return true;
		} else if (findWord(word, 0, currentRow, currentColumn, CONSTANTS.TOP_LEFT)) {
			return true;
		}
		
		return false; 
	}
	
	/**
	 * Recursive method that looks for the word in multiple directions
	 * @param word the word looked for
	 * @param letterIndex the letter being reviewed
	 * @param currentRow the row being reviewed
	 * @param currentColumn the column being reviewed
	 * @param direction the direct being reviewed
	 * @return whether the letter was found or not
	 */
	public static boolean findWord(Word word, int letterIndex, int currentRow, int currentColumn, String direction) {
		//check if the current point is within the board
		if (currentRow < 0 || currentColumn < 0 || currentRow > wordSearchRows - 1 || currentColumn > wordSearchColumns - 1) {
			return false;
		}
		
		//get the letter at the current position
		String currentLetter = wordSearch[currentRow][currentColumn];
		
		//check if the word is continued
		if (currentLetter.equals(String.valueOf(word.getWord().charAt(letterIndex)))) {
			if (letterIndex == word.getWord().length() - 1) {
				endColumn = currentColumn;
				endRow = currentRow;
				return true;
			} else {
				switch (direction) {
					case CONSTANTS.TOP:
						return findWord(word, letterIndex+1, currentRow + CONSTANTS.TOP_ROW, currentColumn + CONSTANTS.TOP_COLUMN, CONSTANTS.TOP);
					case CONSTANTS.TOP_RIGHT: 
						return findWord(word, letterIndex+1, currentRow + CONSTANTS.TOP_RIGHT_ROW, currentColumn + CONSTANTS.TOP_RIGHT_COLUMN, CONSTANTS.TOP_RIGHT);
					case CONSTANTS.RIGHT: 
						return findWord(word, letterIndex+1, currentRow + CONSTANTS.RIGHT_ROW, currentColumn + CONSTANTS.RIGHT_COLUMN, CONSTANTS.RIGHT);
					case CONSTANTS.BOTTOM_RIGHT: 
						return findWord(word, letterIndex+1, currentRow + CONSTANTS.BOTTOM_RIGHT_ROW, currentColumn + CONSTANTS.BOTTOM_RIGHT_COLUMN, CONSTANTS.BOTTOM_RIGHT);
					case CONSTANTS.BOTTOM: 
						return findWord(word, letterIndex+1, currentRow + CONSTANTS.BOTTOM_ROW, currentColumn + CONSTANTS.BOTTOM_COLUMN, CONSTANTS.BOTTOM);
					case CONSTANTS.BOTTOM_LEFT: 
						return findWord(word, letterIndex+1, currentRow + CONSTANTS.BOTTOM_LEFT_ROW, currentColumn + CONSTANTS.BOTTOM_LEFT_COLUMN, CONSTANTS.BOTTOM_LEFT);
					case CONSTANTS.LEFT: 
						return findWord(word, letterIndex+1, currentRow + CONSTANTS.LEFT_ROW, currentColumn + CONSTANTS.LEFT_COLUMN, CONSTANTS.LEFT);
					case CONSTANTS.TOP_LEFT: 
						return findWord(word, letterIndex+1, currentRow + CONSTANTS.TOP_LEFT_ROW, currentColumn + CONSTANTS.TOP_LEFT_COLUMN, CONSTANTS.TOP_LEFT);
					default: 
						return false;
				}
			}
		}
		return false;
	}
	
	/**
	 * Retrieve the word search data including the table and words to look for
	 */
	public static void getFileData() {
		//TODO could be a scanner and allow multiple chances at entering a file
		//TODO verify the file exists without throwing the error.
		//TODO verify the file is not empty.
		//TODO verify that the file is formatted correctly. 
		BufferedReader reader;
		String line = "";
		try {
			reader = new BufferedReader(new FileReader(file));
			
			//Get the row X column from the file
			line = reader.readLine();
			String[] searchSize = line.split("x");
			wordSearchRows = Integer.valueOf(searchSize[0]);
			wordSearchColumns = Integer.valueOf(searchSize[1]);
			
			//Get the word search
			wordSearch = new String[wordSearchRows][wordSearchColumns];
			for (int i = 0; i < wordSearchRows; i++) {
				line = reader.readLine().replaceAll(" ", "");
				for (int j = 0; j < wordSearchColumns; j++) {
					wordSearch[i][j] = String.valueOf(line.charAt(j));
				}
			}
			
			//Get a list of words
			words = new ArrayList<Word>();
			line = reader.readLine();
			while (line != null) {
				//removes any spaces in words BAT MAN -> BATMAN
				//per the README.md
				Word word = new Word(line.replace(" ", "")); 
				words.add(word);
				line = reader.readLine();
			}
			
		} catch (IOException e) {
			System.out.println("The file was not found");
		}
	}
	
	/**
	 * Prints the list of words and their coordinates from the Word.toString() method
	 */
	public static void printWords() {
		for (Word word : words) { 
			word.print();
			System.out.println();
		}
	}
	
	/**
	 * For testing purposes
	 * Prints the word search 
	 */
	public static void printWordSearch() {
		System.out.println("Results");
		for (int i = 0; i < wordSearchRows; i++) { 
			for (int j = 0; j < wordSearchColumns; j++) { 
				System.out.print(wordSearch[i][j] + " ");
			}
			System.out.println();
		}
	}
}
