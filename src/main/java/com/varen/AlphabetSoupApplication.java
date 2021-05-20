package com.varen;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URLDecoder;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.stream.IntStream;

public class AlphabetSoupApplication {

	public static void main(String[] args) throws IOException {
		String path = AlphabetSoupApplication.class.getProtectionDomain().getCodeSource().getLocation().getPath();
		String jarPath = URLDecoder.decode(path, "UTF-8");
		String defaultFilePath = jarPath.endsWith(".jar")
				? new File(jarPath).getParent() + System.getProperty("file.separator") + "alphabet-soup.input"
				: jarPath + System.getProperty("file.separator") + "alphabet-soup.input";

		String fileUrl = args.length >= 1 ? args[0] : defaultFilePath;
		String fileCharset = args.length >= 2 ? args[1] : "Cp1252";

		AlphabetSoupPuzzle puzzle = AlphabetSoupPuzzle.build(fileUrl, fileCharset);
		puzzle.printSolution();
	}
}
