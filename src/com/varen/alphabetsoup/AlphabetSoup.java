package com.varen.alphabetsoup;

public class AlphabetSoup {

	public static void main(String[] args) {
		AlphabetSoupParser parser = new AlphabetSoupParser();
		AlphabetSoupKey key = parser.parse();
		char[][] grid = new char[key.getDimensions().get("row").intValue()][key.getDimensions().get("col").intValue()];
		
		for(int i = 0; i < grid[0].length; i++){
			for(int j = 0; j < grid.length; j++) {
				grid[i][j] = key.grid.get(i).get(j).charValue();
			}
		}		
		
		key.getWords().forEach(word -> {
			//System.out.println("Starting word: " + word);
			
			if (key.exist(grid, word)) {
				//System.out.println("EXISTS: " + word);
				System.out.println(
					word + 
					" " + 
					key.getLocations().get(word).get("firstRow") + ":" + key.getLocations().get(word).get("firstCol") +
					" " +
					key.getLocations().get(word).get("lastRow") + ":" + key.getLocations().get(word).get("lastCol")
				);
			}
			
		});
		
	}

}
