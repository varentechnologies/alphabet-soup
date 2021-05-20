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
		AlphabetSoupPuzzle puzzle = AlphabetSoupPuzzle.build(fileUrl, fileCharset);
		puzzle.printSolution();
	}
}
