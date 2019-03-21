package com.varen.alphabetsoup;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class AlphabetSoupKey {
	
	Map<String, Integer> dimensions;
	List<List<Character>> grid;
	Set<String> words;
	Map<String, Map<String, Integer>> locations;

	public AlphabetSoupKey() {
		this.dimensions = new HashMap<>();
		this.grid = new ArrayList<>();
		this.words = new HashSet<>();
		this.locations = new HashMap<>();
	}
	
	// Driving method to search grid
	public boolean exist(char[][] grid, String word) {
		
		boolean result = false;
		
		for (int i = 0; i < grid.length; i++) {
			for (int j = 0; j < grid[0].length; j++) {
				if (search(grid, word, i, j, 0)) {
					result = true;
				}
			}
		}

		return result;
	}

	// Check neighbors for next character
	public boolean search(char[][] grid, String word, int i, int j, int k) {
		
		// Exit on invalid coordinates
		if (i < 0 || j < 0 || i >= grid.length || j >= grid[0].length) {
			return false;
		}
		
		// Check cood value matches k index of word
		if (grid[i][j] == word.charAt(k)) {
			
			char temp = grid[i][j];
			//grid[i][j] = '#';
			
			// Exit at end of word
			if (k == word.length() - 1) {				
				this.locations.get(word).put("lastRow", Integer.valueOf(i));
				this.locations.get(word).put("lastCol", Integer.valueOf(j));
				return true;
			} else if (
				// Recursively check all 8 surrounding locations for next letter
				search(grid, word, i, j, k + 1) 		|| // Center
				search(grid, word, i + 1, j, k + 1) 	|| // North
				search(grid, word, i + 1, j + 1, k + 1) || // NorthEast
				search(grid, word, i, j + 1, k + 1) 	|| // East
				search(grid, word, i - 1, j + 1, k + 1)	|| // SouthEast
				search(grid, word, i - 1, j, k + 1) 	|| // South
				search(grid, word, i - 1, j - 1, k + 1)	|| // SouthWest
				search(grid, word, i, j - 1, k + 1) 	|| // West
				search(grid, word, i + 1, j - 1, k + 1)) { // NorthWest
				
				if (k == 0) {
					this.locations.get(word).put("firstRow", Integer.valueOf(i));				
					this.locations.get(word).put("firstCol", Integer.valueOf(j));
				}
				
				return true;
			}
			// Incomplete full word match - continue to next grid coordinate
			grid[i][j] = temp;
		}

		return false;
	}

	public Map<String, Integer> getDimensions() {
		return dimensions;
	}

	public void setDimensions(Map<String, Integer> dimensions) {
		this.dimensions = dimensions;
	}
	
	public List<List<Character>> getGrid() {
		return this.grid;
	}
	
	public void setGridRow(List<Character> row) {
		this.grid.add(row);				
	}
	
	public Set<String> getWords() {
		return words;
	}

	public void setWords(Set<String> words) {
		this.words = words;
	}	
	
	public Map<String, Map<String, Integer>> getLocations() {
		return locations;
	}

	public void setLocations(String word, Map<String, Integer> locations) {
		this.locations.put(word, locations);
	}

}
