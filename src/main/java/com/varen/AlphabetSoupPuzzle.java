package com.varen;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.IntStream;

public class AlphabetSoupPuzzle {
	private final String fileUrl;
	private final String fileCharset;
	private final List<String> inputLines = new ArrayList<String>();
	private final List<String> foundResults = new ArrayList<String>();
	private final HashMap<Integer, List<String>> hiddenWordsMap = new HashMap<Integer, List<String>>();
	private final Integer numberOfRows;
	private final Integer numberOfCols;

	private AlphabetSoupPuzzle(String fileUrl, String fileCharset)
			throws UnsupportedEncodingException, FileNotFoundException, IOException {
		this.fileUrl = fileUrl;
		this.fileCharset = fileCharset;

		readInputFile();

		/*
		 * Process Grid metadata to obtain number of rows and columns
		 */
		String gridDimensions = inputLines.get(0);
		int indexOfFirstNonNumeric = gridDimensions
				.indexOf(gridDimensions.chars().filter((int i) -> !Character.isDigit((char) i)).findFirst().getAsInt());

		this.numberOfRows = numberOfRowsFromDimensions(gridDimensions, indexOfFirstNonNumeric);
		this.numberOfCols = numberOfColsFromDimensions(gridDimensions, indexOfFirstNonNumeric);

		/*
		 * Process the hidden words portion of the file to populate internal collections
		 */
		processHiddenWords();

		/*
		 * Find the hidden words to solve the puzzle
		 */
		solve();
	}

	private void readInputFile() throws UnsupportedEncodingException, FileNotFoundException, IOException {
		try (BufferedReader bufferedFileReader = new BufferedReader(
				new InputStreamReader(new FileInputStream(new File(fileUrl)), fileCharset))) {

			bufferedFileReader.lines().forEach((String line) -> {
				inputLines.add(line.replaceAll("[^A-Za-z0-9]", "").toUpperCase());
			});
		}
	}

	private void processHiddenWords() {
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
	}

	private void solve() {
		// Our word scramble grid begins at index (line number) 1
		IntStream.range(1, numberOfRows + 1).forEach((rowIndex -> {
			String row = inputLines.get(rowIndex);

			hiddenWordsMap.keySet().stream().filter((Integer wordLength) -> {
				return wordLength.intValue() <= row.length();
			}).forEach((Integer wordLength) -> findWords(rowIndex - 1, 0, wordLength, row,
					(correctedRowIndex, colIndex, foundAt,
							lambdaWordLength) -> new int[] { correctedRowIndex, foundAt, correctedRowIndex,
									foundAt + lambdaWordLength - 1 },
					(correctedRowIndex, colIndex, foundAt, lambdaWordLength) -> new int[] { correctedRowIndex,
							foundAt + lambdaWordLength - 1, correctedRowIndex, foundAt }));

			/*
			 * When processing the first row, we will check all columns and the majority of
			 * diagonals
			 */
			if (rowIndex == 1) {
				solveFirstRow();
			}
			// On All subsequent rows, we must check the leftmost and rightmost diagonals.
			// May be able to abstract this and the above code to a single function
			else {
				solveRow(rowIndex);
			}

		}));
	}

	private void solveFirstRow() {
		int rowIndex = 1;
		IntStream.range(0, numberOfCols).forEach((colIndex) -> {

			StringBuilder colBuilder = new StringBuilder();
			StringBuilder diagDownRightBuilder = new StringBuilder();
			StringBuilder diagDownLeftBuilder = new StringBuilder();

			IntStream.range(rowIndex, numberOfRows + 1).forEach((colStringIndex) -> {
				String downRow = inputLines.get(colStringIndex);
				int diagonalOffset = colStringIndex - rowIndex;

				colBuilder.append(downRow.charAt(colIndex));
				diagDownRightBuilder.append(
						colIndex + diagonalOffset >= numberOfCols ? "" : downRow.charAt(colIndex + diagonalOffset));
				diagDownLeftBuilder
						.append(colIndex - diagonalOffset < 0 ? "" : downRow.charAt(colIndex - diagonalOffset));
			});
			String col = colBuilder.toString();

			hiddenWordsMap.keySet().stream().filter((Integer wordLength) -> {
				return wordLength.intValue() <= col.length();
			}).forEach((Integer wordLength) -> findWords(rowIndex - 1, colIndex, wordLength, col,
					(correctedRowIndex, lambdaColIndex, foundAt,
							lambdaWordLength) -> new int[] { foundAt, lambdaColIndex,
									foundAt + lambdaWordLength - 1,
									lambdaColIndex },
					(correctedRowIndex, lambdaColIndex, foundAtBackwards,
							lambdaWordLength) -> new int[] { foundAtBackwards + lambdaWordLength - 1, lambdaColIndex,
									foundAtBackwards,
									lambdaColIndex }));

			String diagDownRight = diagDownRightBuilder.toString();
			String diagDownLeft = diagDownLeftBuilder.toString();

			hiddenWordsMap.keySet().stream().filter((Integer wordLength) -> {
				return wordLength.intValue() <= diagDownRight.length();
			}).forEach((Integer wordLength) -> findWords(rowIndex - 1, colIndex, wordLength, diagDownRight,
					(correctedRowIndex, lambdaColIndex, foundAt,
							lambdaWordLength) -> new int[] { correctedRowIndex + foundAt, 
									lambdaColIndex + foundAt,
									correctedRowIndex + foundAt + lambdaWordLength - 1,
									lambdaColIndex + foundAt + lambdaWordLength - 1 },
					(correctedRowIndex, lambdaColIndex, foundAtBackwards,
							lambdaWordLength) -> new int[] {
									correctedRowIndex + foundAtBackwards + (lambdaWordLength - 1),
									lambdaColIndex + foundAtBackwards + (lambdaWordLength - 1),
									correctedRowIndex + foundAtBackwards, 
									lambdaColIndex + foundAtBackwards }));

			hiddenWordsMap.keySet().stream().filter((Integer wordLength) -> {
				return wordLength.intValue() <= diagDownLeft.length();
			}).forEach((Integer wordLength) -> findWords(rowIndex - 1, colIndex, wordLength, diagDownLeft,
					(correctedRowIndex, lambdaColIndex, foundAt,
							lambdaWordLength) -> new int[] { correctedRowIndex + foundAt, lambdaColIndex - foundAt,
									correctedRowIndex + foundAt + lambdaWordLength - 1,
									colIndex - foundAt - lambdaWordLength + 1 },
					(correctedRowIndex, lambdaColIndex, foundAtBackwards,
							lambdaWordLength) -> new int[] {
									correctedRowIndex + foundAtBackwards + (lambdaWordLength - 1),
									lambdaColIndex - (lambdaWordLength - 1) - foundAtBackwards,
									correctedRowIndex + foundAtBackwards, lambdaColIndex - foundAtBackwards }));
		});
	}

	private void solveRow(int rowIndex) {
		StringBuilder diagDownRightBuilder = new StringBuilder();
		StringBuilder diagDownLeftBuilder = new StringBuilder();

		IntStream.range(rowIndex, numberOfRows + 1).forEach((colStringIndex) -> {
			String downRow = inputLines.get(colStringIndex);
			int diagonalOffset = colStringIndex - rowIndex;

			diagDownRightBuilder.append(diagonalOffset >= numberOfCols ? "" : downRow.charAt(diagonalOffset));
			diagDownLeftBuilder.append(
					numberOfCols - 1 - diagonalOffset < 0 ? "" : downRow.charAt(numberOfCols - 1 - diagonalOffset));
		});

		String diagDownRight = diagDownRightBuilder.toString();
		String diagDownLeft = diagDownLeftBuilder.toString();

		hiddenWordsMap.keySet().stream().filter((Integer wordLength) -> {
			return wordLength.intValue() <= diagDownRight.length();
		}).forEach((Integer wordLength) -> findWords(rowIndex - 1, 0, wordLength, diagDownRight,
				(correctedRowIndex, colIndex, foundAt,
						lambdaWordLength) -> new int[] { correctedRowIndex + foundAt, 
								colIndex + foundAt,
								correctedRowIndex + foundAt + lambdaWordLength - 1,
								colIndex + foundAt + lambdaWordLength - 1 },
				(correctedRowIndex, colIndex, foundAtBackwards,
						lambdaWordLength) -> new int[] {
								correctedRowIndex + foundAtBackwards + (lambdaWordLength - 1),
								colIndex + foundAtBackwards + (lambdaWordLength - 1),
								correctedRowIndex + foundAtBackwards, 
								colIndex + foundAtBackwards }));

		hiddenWordsMap.keySet().stream().filter((Integer wordLength) -> {
			return wordLength.intValue() <= diagDownLeft.length();
		}).forEach((Integer wordLength) -> findWords(rowIndex - 1, numberOfCols - 1, wordLength, diagDownLeft,
				(correctedRowIndex, colIndex, foundAt,
						lambdaWordLength) -> new int[] { correctedRowIndex + foundAt, colIndex - foundAt,
								correctedRowIndex + foundAt + lambdaWordLength - 1,
								colIndex - foundAt - lambdaWordLength + 1 },
				(correctedRowIndex, colIndex, foundAtBackwards,
						lambdaWordLength) -> new int[] {
								correctedRowIndex + foundAtBackwards + (lambdaWordLength - 1),
								colIndex - (lambdaWordLength - 1) - foundAtBackwards,
								correctedRowIndex + foundAtBackwards, colIndex - foundAtBackwards }));
	}

	private void findWords(Integer correctedRowIndex, Integer colIndex, Integer wordLength, String gridSlice,
			FoundWordCoordinates forwardComputeFunction, FoundWordCoordinates backwardComputeFunction) {
		List<String> hiddenWords = hiddenWordsMap.get(wordLength);
		List<String> wordsFound = new ArrayList<String>();
		hiddenWords.forEach((String word) -> {
			int foundAt = gridSlice.indexOf(word);
			int foundAtBackwards = foundAt >= 0 ? -1 : gridSlice.indexOf(new StringBuilder(word).reverse().toString());

			if (foundAt >= 0) {
				wordsFound.add(word);

				int[] coordinates = forwardComputeFunction.compute(correctedRowIndex, colIndex, foundAt, word.length());
				foundResults.set(foundResults.indexOf(word), MessageFormat.format("{0} {1}:{2} {3}:{4}", word,
						coordinates[0], coordinates[1], coordinates[2], coordinates[3]));

			} else if (foundAtBackwards >= 0) {
				wordsFound.add(word);

				int[] coordinates = backwardComputeFunction.compute(correctedRowIndex, colIndex, foundAtBackwards,
						word.length());
				foundResults.set(foundResults.indexOf(word), MessageFormat.format("{0} {1}:{2} {3}:{4}", word,
						coordinates[0], coordinates[1], coordinates[2], coordinates[3]));
			}
		});

		hiddenWords.removeAll(wordsFound);
	}

	protected void printSolution() {
		foundResults.forEach((s) -> {
			System.out.println(s);
		});
	}

	private interface FoundWordCoordinates {
		int[] compute(int correctedRowIndex, int colIndex, int foundAt, int wordLength);
	}

	private static Integer numberOfRowsFromDimensions(String gridDimensions, int indexOfFirstNonNumeric) {
		return Integer.valueOf(gridDimensions.substring(0, indexOfFirstNonNumeric));
	}

	private static Integer numberOfColsFromDimensions(String gridDimensions, int indexOfFirstNonNumeric) {
		return Integer.valueOf(gridDimensions.substring(indexOfFirstNonNumeric + 1, gridDimensions.length()));
	}

	protected static AlphabetSoupPuzzle build(String fileUrl, String fileCharset)
			throws UnsupportedEncodingException, FileNotFoundException, IOException {
		return new AlphabetSoupPuzzle(fileUrl, fileCharset);
	}

}
