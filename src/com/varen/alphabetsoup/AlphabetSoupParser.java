package com.varen.alphabetsoup;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Stream;

public class AlphabetSoupParser {
	
	// Read file input & build key
	public AlphabetSoupKey parse() {
		AlphabetSoupKey key = new AlphabetSoupKey();
		
		Path fileName = Paths.get("./src/com/varen/alphabetsoup/resources/sampleData.txt").toAbsolutePath().normalize();
		String dimensionPattern = "\\d[x]\\d";		// Ex: (3x3)
		String gridPattern = "(\\w )+\\w";			// Ex: (A B C D)
		
		try (Stream<String> stream = Files.lines(fileName)) {
			
			stream.forEach(line -> {
												
				/* Part 1 - Rows x Columns */
				if (Pattern.matches(dimensionPattern, line)) {
					String[] dimension = line.split("x");
					key.getDimensions().put("row", Integer.parseInt(dimension[0]));
					key.getDimensions().put("col", Integer.parseInt(dimension[1]));					
				}
				
				/* Part 2 - Grid contents w/ whitespace separator */
				else if (Pattern.matches(gridPattern, line)) {
					String[] gridRow = line.split(" ");
					
					List<Character> temp = new ArrayList<>();					
					for(int i = 0; i < gridRow.length; i++) {
						temp.add(gridRow[i].charAt(0));
					}
					key.setGridRow(temp);
				}
				
				/* Part 3 - Search terms */
				else {
					key.getWords().add(line);
					key.getLocations().put(line, new HashMap<String, Integer>());
				}
			});
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		return key;
	}	
}
