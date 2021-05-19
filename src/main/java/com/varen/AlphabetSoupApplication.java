package com.varen;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public class AlphabetSoupApplication {
	private static String fileUrl = "C:\\Users\\buonomo\\Desktop\\alphabet-soup.input";

	public static void main(String[] args) throws IOException {
		System.out.println("Begin Alphabet Soup:");

		FileInputStream inputFileReader = new FileInputStream(new File(fileUrl));
		BufferedReader bufferedFileReader = new BufferedReader(new InputStreamReader(inputFileReader, "Cp1252"));

		List<String> inputLines = new ArrayList<String>();

		bufferedFileReader.lines().forEach((String line) -> {
			inputLines.add(line);
		});

		String gridDimensions = inputLines.get(0);
		Integer numberOfRows = Integer.valueOf(gridDimensions.charAt(0) + "");
		Integer numberOfCols = Integer.valueOf(gridDimensions.charAt(2) + "");
		
		// NOTE our word scramble grid begins at index (line number) 1
		IntStream.range(1, numberOfRows).forEach( (i -> {
			String row = inputLines.get(i);
		}));
	}

}
