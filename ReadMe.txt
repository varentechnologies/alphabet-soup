AlphabetSoup
-------------------------------------------------------------------------------
This program contains a class that allows for the reading of various wordsearch
grids. It will output the given words and their coordinates in the following
format.

[word] X:Y X2:Y2

Where X and Y are the starting coordinates and X2 and Y2 are the ending
coordinates of the word.

Files must also be in the following format.

[n]x[m]
1 2 3 4 5 6 ... m
2
3
4
.
.
.
n
[Word1]
[Word2]
.
.
.
[WordN]

There is also a helper class splitshappentester, which will run through
various examples strings and check them for correctness.

This project can be compiled using the included Makefile, which will create
an output file that can be executed.