output: Main.o AlphabetSoup.o AlphabetSoupTester.o Grid.o 
	g++ Main.o AlphabetSoup.o AlphabetSoupTester.o Grid.o -o AlphabetSoup

Main.o: Main.cpp
	g++ -c Main.cpp

AlphabetSoup.o: AlphabetSoup.cpp AlphabetSoup.hpp
	g++ -c AlphabetSoup.cpp

AlphabetSoupTester.o: AlphabetSoupTester.cpp AlphabetSoupTester.hpp
	g++ -c AlphabetSoupTester.cpp
	
Grid.o: Grid.cpp Grid.hpp
	g++ -c Grid.cpp
