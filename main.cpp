/*
main.cpp is the driver for WordSearch.
------------------------------------------
- It takes in a file from the command line
    i.e. ./{program} inFile.txt
- Input Example
    3x3
    A B C
    D E F
    G H I
    ABC
    AEI

Other Notes:
compiled using g++ -o -g program main.cpp WordSearch.cpp
ran using ./program test.txt
*/

#include <iostream>
#include <vector>
#include <fstream>
#include <string>
#include <algorithm>

#include "WordSearch.h"


using namespace std;

void ReadFile(WordSearch &WordSearch, string filename);

int main(int argc, char *argv[])
{
    string filename = argv[1];
    WordSearch WordSearch;
 
    ReadFile(WordSearch, filename);

    WordSearch.Search();

    WordSearch.Print();
    

    return 0;
}

void ReadFile(WordSearch &WordSearch, string filename)
{
    vector<int> rows;
    int sizeA;
    int sizeB;
    char letter;
    char temp;
    string getWord = "";

    try
    {
        ifstream myFile;
        myFile.open(filename);

        // reading the dimensions of the grid
        myFile >> sizeA;
        myFile >> temp;
        myFile >> sizeB;
        
        WordSearch.set(sizeA, sizeB);

        // storing the grid
        for (int i = 0; i < sizeA; ++i)
        {
            for (int j = 0; j < sizeB; ++j)
            {
                myFile >> letter;

                rows.push_back(letter);
            }
            WordSearch.Grid.push_back(rows);
            rows.clear();
        }

        // to get to the next line to start reading the words
        getline(myFile, getWord);   

        // reading in the list of words
        while (getline(myFile, getWord))
        {
            Words insertWord;
            insertWord.Word = getWord;
            insertWord.Word.erase(remove_if(insertWord.Word.begin(), insertWord.Word.end(), ::isspace), insertWord.Word.end());
            WordSearch.WordList.push_back(insertWord);
        }
        

        myFile.close();
    }
    catch(const std::exception& e)
    {
        cerr << e.what() << '\n';
    }
    
    
}
