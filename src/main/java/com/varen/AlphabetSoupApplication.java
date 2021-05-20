package com.varen;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.stream.IntStream;

public class AlphabetSoupApplication {
	private static String fileUrl = "C:\\Users\\buonomo\\Desktop\\alphabet-soup.input";
	private static String fileCharset = "Cp1252";

	public static void main(String[] args) throws IOException {

		try (BufferedReader bufferedFileReader = new BufferedReader(
				new InputStreamReader(new FileInputStream(new File(fileUrl)), fileCharset))) {

			List<String> inputLines = new ArrayList<String>();
			List<String> foundResults = new ArrayList<String>();

			// Map is keyed by length of words contained in the collection held by the value
			HashMap<Integer, List<String>> hiddenWordsMap = new HashMap<Integer, List<String>>();

			bufferedFileReader.lines().forEach((String line) -> {
				inputLines.add(line.replaceAll("[^A-Za-z0-9]", "").toUpperCase());
			});

			String gridDimensions = inputLines.get(0);

			int indexOfFirstNonNumeric = gridDimensions.indexOf(
					gridDimensions.chars().filter((int i) -> !Character.isDigit((char) i)).findFirst().getAsInt());

			Integer numberOfRows = Integer.valueOf(gridDimensions.substring(0, indexOfFirstNonNumeric));
			Integer numberOfCols = Integer
					.valueOf(gridDimensions.substring(indexOfFirstNonNumeric + 1, gridDimensions.length()));

			// Our set of hidden words begins at index (line number) equal to numberOfRows +
			// 1
			IntStream.range(numberOfRows + 1, inputLines.size()).forEach((i -> {
				String word = inputLines.get(i);
				foundResults.add(word);

				if (hiddenWordsMap.containsKey(word.length())) {
					hiddenWordsMap.get(word.length()).add(word);
				} else {
					List<String> newListForLength = new ArrayList<String>();
					newListForLength.add(word);
					hiddenWordsMap.put(word.length(), newListForLength);
				}
			}));

			// Our word scramble grid begins at index (line number) 1
			IntStream.range(1, numberOfRows + 1).forEach((rowIndex -> {
				String row = inputLines.get(rowIndex);

				hiddenWordsMap.keySet().stream().filter((Integer wordLength) -> {
					return wordLength.intValue() <= row.length();
				}).forEach((Integer wordLength) -> AlphabetSoupApplication.findWordsInRow(wordLength, hiddenWordsMap,
						row, foundResults, rowIndex - 1));

				// When processing the first row, we will check all columns and the majority of
				// diagonals
				if (rowIndex == 1) {
					IntStream.range(0, numberOfCols).forEach((colIndex) -> {

						System.out.println(colIndex + " of " + numberOfCols);

						StringBuilder colBuilder = new StringBuilder();
						StringBuilder diagDownRightBuilder = new StringBuilder();
						StringBuilder diagDownLeftBuilder = new StringBuilder();

						IntStream.range(1, numberOfRows + 1).forEach((colStringIndex) -> {
							String downRow = inputLines.get(colStringIndex);
							int diagonalOffset = colStringIndex - 1;

							colBuilder.append(downRow.charAt(colIndex));
							diagDownRightBuilder.append(colIndex + diagonalOffset >= numberOfCols ? ""
									: downRow.charAt(colIndex + diagonalOffset));
							diagDownLeftBuilder.append(
									colIndex - diagonalOffset < 0 ? "" : downRow.charAt(colIndex - diagonalOffset));
						});
						String col = colBuilder.toString();

						System.out.println("uhhhh???? " + col);

						hiddenWordsMap.keySet().stream().filter((Integer wordLength) -> {
							return wordLength.intValue() <= col.length();
						}).forEach((Integer wordLength) -> AlphabetSoupApplication.findWordsInCol(wordLength,
								hiddenWordsMap, col, foundResults, colIndex));

						String diagDownRight = diagDownRightBuilder.toString();
						String diagDownLeft = diagDownLeftBuilder.toString();

						hiddenWordsMap.keySet().stream().filter((Integer wordLength) -> {
							return wordLength.intValue() <= diagDownRight.length();
						}).forEach((Integer wordLength) -> AlphabetSoupApplication.findWordsInDiagonalDownRight(
								wordLength, hiddenWordsMap, diagDownRight, foundResults, colIndex, rowIndex - 1));

						hiddenWordsMap.keySet().stream().filter((Integer wordLength) -> {
							return wordLength.intValue() <= diagDownLeft.length();
						}).forEach((Integer wordLength) -> AlphabetSoupApplication.findWordsInDiagonalDownLeft(
								wordLength, hiddenWordsMap, diagDownLeft, foundResults, colIndex, rowIndex - 1));
					});
				}
				// On All subsequent rows, we must check the leftmost and rightmost diagonals.
				// May be able to abstract this and the above code to a single function
				else {
					StringBuilder diagDownRightBuilder = new StringBuilder();
					StringBuilder diagDownLeftBuilder = new StringBuilder();

					IntStream.range(rowIndex, numberOfRows + 1).forEach((colStringIndex) -> {
						String downRow = inputLines.get(colStringIndex);
						int diagonalOffset = colStringIndex - rowIndex;

						diagDownRightBuilder
								.append(diagonalOffset >= numberOfCols ? "" : downRow.charAt(diagonalOffset));
						diagDownLeftBuilder.append(numberOfCols - 1 - diagonalOffset < 0 ? ""
								: downRow.charAt(numberOfCols - 1 - diagonalOffset));
					});

					String diagDownRight = diagDownRightBuilder.toString();
					String diagDownLeft = diagDownLeftBuilder.toString();

					hiddenWordsMap.keySet().stream().filter((Integer wordLength) -> {
						return wordLength.intValue() <= diagDownRight.length();
					}).forEach((Integer wordLength) -> AlphabetSoupApplication.findWordsInDiagonalDownRight(wordLength,
							hiddenWordsMap, diagDownRight, foundResults, 0, rowIndex - 1));

					hiddenWordsMap.keySet().stream().filter((Integer wordLength) -> {
						return wordLength.intValue() <= diagDownLeft.length();
					}).forEach((Integer wordLength) -> AlphabetSoupApplication.findWordsInDiagonalDownLeft(wordLength,
							hiddenWordsMap, diagDownLeft, foundResults, numberOfCols - 1, rowIndex - 1));
				}

			}));

			foundResults.forEach((s) -> {
				System.out.println(s);
			});
		}
	}

	public static void findWordsInRow(Integer wordLength, HashMap<Integer, List<String>> hiddenWordsMap, String row,
			List<String> foundResults, Integer correctedRowIndex) {
		List<String> hiddenWords = hiddenWordsMap.get(wordLength);
		List<String> wordsFound = new ArrayList<String>();
		hiddenWords.forEach((String word) -> {
			int foundAt = row.indexOf(word);
			int foundAtBackwards = foundAt >= 0 ? -1 : row.indexOf(new StringBuilder(word).reverse().toString());

			if (foundAt >= 0) {
				wordsFound.add(word);
				foundResults.set(foundResults.indexOf(word), MessageFormat.format("{0} {1}:{2} {1}:{3}", word,
						correctedRowIndex, foundAt, foundAt + word.length() - 1));

			} else if (foundAtBackwards >= 0) {
				wordsFound.add(word);

				foundResults.set(foundResults.indexOf(word), MessageFormat.format("{0} {1}:{3} {1}:{2}", word,
						correctedRowIndex, foundAtBackwards, foundAtBackwards + word.length() - 1));
			}
		});

		hiddenWords.removeAll(wordsFound);
	}

	// can be abstracted? All that was changed was row -> col, and formatting of
	// found message
	public static void findWordsInCol(Integer wordLength, HashMap<Integer, List<String>> hiddenWordsMap, String col,
			List<String> foundResults, Integer colIndex) {
		List<String> hiddenWords = hiddenWordsMap.get(wordLength);
		List<String> wordsFound = new ArrayList<String>();
		hiddenWords.forEach((String word) -> {
			int foundAt = col.indexOf(word);
			int foundAtBackwards = foundAt >= 0 ? -1 : col.indexOf(new StringBuilder(word).reverse().toString());

			if (foundAt >= 0) {
				wordsFound.add(word);
				foundResults.set(foundResults.indexOf(word), MessageFormat.format("{0} {2}:{1} {3}:{1}", word, colIndex,
						foundAt, foundAt + word.length() - 1));
			} else if (foundAtBackwards >= 0) {
				wordsFound.add(word);
				foundResults.set(foundResults.indexOf(word), MessageFormat.format("{0} {3}:{1} {2}:{1}", word, colIndex,
						foundAtBackwards, foundAtBackwards + word.length() - 1));
			}
		});

		hiddenWords.removeAll(wordsFound);
	}

	public static void findWordsInDiagonalDownRight(Integer wordLength, HashMap<Integer, List<String>> hiddenWordsMap,
			String col, List<String> foundResults, Integer colIndex, Integer correctedRowIndex) {
		List<String> hiddenWords = hiddenWordsMap.get(wordLength);
		List<String> wordsFound = new ArrayList<String>();
		hiddenWords.forEach((String word) -> {
			int foundAt = col.indexOf(word);
			int foundAtBackwards = foundAt >= 0 ? -1 : col.indexOf(new StringBuilder(word).reverse().toString());

			if (foundAt >= 0) {
				wordsFound.add(word);

				int wordRowStart = correctedRowIndex + foundAt;
				int wordColStart = colIndex + foundAt;

				int wordRowEnd = correctedRowIndex + foundAt + word.length() - 1;
				int wordColEnd = colIndex + foundAt + word.length() - 1;

				foundResults.set(foundResults.indexOf(word), MessageFormat.format("{0} {1}:{2} {3}:{4}", word,
						wordRowStart, wordColStart, wordRowEnd, wordColEnd));
			} else if (foundAtBackwards >= 0) {
				wordsFound.add(word);

				int wordRowStart = correctedRowIndex + foundAtBackwards + word.length() - 1;
				int wordColStart = colIndex + foundAtBackwards + word.length() - 1;

				int wordRowEnd = correctedRowIndex + foundAtBackwards;
				int wordColEnd = colIndex + foundAtBackwards;

				foundResults.set(foundResults.indexOf(word), MessageFormat.format("{0} {1}:{2} {3}:{4}", word,
						wordRowStart, wordColStart, wordRowEnd, wordColEnd));
			}
		});

		hiddenWords.removeAll(wordsFound);
	}

	public static void findWordsInDiagonalDownLeft(Integer wordLength, HashMap<Integer, List<String>> hiddenWordsMap,
			String col, List<String> foundResults, Integer colIndex, Integer correctedRowIndex) {
		List<String> hiddenWords = hiddenWordsMap.get(wordLength);
		List<String> wordsFound = new ArrayList<String>();
		hiddenWords.forEach((String word) -> {
			int foundAt = col.indexOf(word);
			int foundAtBackwards = foundAt >= 0 ? -1 : col.indexOf(new StringBuilder(word).reverse().toString());

			if (foundAt >= 0) {
				wordsFound.add(word);

				int wordRowStart = correctedRowIndex + foundAt;
				int wordColStart = colIndex - foundAt;

				int wordRowEnd = correctedRowIndex + foundAt + word.length() - 1;
				int wordColEnd = colIndex - foundAt - word.length() + 1;

				foundResults.set(foundResults.indexOf(word), MessageFormat.format("{0} {1}:{2} {3}:{4}", word,
						wordRowStart, wordColStart, wordRowEnd, wordColEnd));
			} else if (foundAtBackwards >= 0) {
				wordsFound.add(word);

				int wordRowStart = correctedRowIndex + foundAtBackwards + (word.length() - 1);
				int wordColStart = colIndex - (word.length() - 1) - foundAtBackwards;

				int wordRowEnd = correctedRowIndex + foundAtBackwards;
				int wordColEnd = colIndex - foundAtBackwards;

				foundResults.set(foundResults.indexOf(word), MessageFormat.format("{0} {1}:{2} {3}:{4}", word,
						wordRowStart, wordColStart, wordRowEnd, wordColEnd));
			}
		});

		hiddenWords.removeAll(wordsFound);

	}
}
