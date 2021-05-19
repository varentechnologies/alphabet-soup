package com.varen;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.IntStream;

public class AlphabetSoupApplication {
	private static String fileUrl = "C:\\Users\\buonomo\\Desktop\\alphabet-soup.input";

	public static void main(String[] args) throws IOException {
		System.out.println("Begin Alphabet Soup:");

		FileInputStream inputFileReader = new FileInputStream(new File(fileUrl));
		BufferedReader bufferedFileReader = new BufferedReader(new InputStreamReader(inputFileReader, "Cp1252"));

		List<String> inputLines = new ArrayList<String>();
		HashMap< Integer, List<String> > hiddenWordsByWordLength = new HashMap< Integer, List<String> >();
		
		bufferedFileReader.lines().forEach((String line) -> {
			inputLines.add(line);
		});

		String gridDimensions = inputLines.get(0);
		Integer numberOfRows = Integer.valueOf(gridDimensions.charAt(0) + "");
		Integer numberOfCols = Integer.valueOf(gridDimensions.charAt(2) + "");
		
		// Our set of hidden words begins at index (line number) equal to numberOfRows + 1
		IntStream.range(numberOfRows + 1, inputLines.size()).forEach( (i -> {
			String word = inputLines.get(i);
			
			if(hiddenWordsByWordLength.containsKey(word.length())) {
				hiddenWordsByWordLength.get(word.length()).add(word);
			}
			else {
				List<String> newListForLength = new ArrayList<String>();
				newListForLength.add(word);
				hiddenWordsByWordLength.put(word.length(), newListForLength);
			}
		}));
		
		// Our word scramble grid begins at index (line number) 1
		IntStream.range(1, numberOfRows + 1).forEach( (i -> {
			String row = inputLines.get(i);
		}));
		
		hiddenWordsByWordLength.keySet().forEach( (i) -> System.out.println("Word size: "+i+" / Count = "+hiddenWordsByWordLength.get(i).size()));
	}

}
