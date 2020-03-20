#ifndef WORDSEARCH_H
#define WORDSEARCH_H

#include <vector>
#include <tuple>


enum Direction {none, topLeft, top, topRight, Left, Right, bottomLeft, bottom, bottomRight};

struct Words 
{
    std::string Word = "";               // The word you're looking for
    std::tuple<int, int> startPoint;
    std::tuple<int, int> endPoint;
};

class WordSearch
{
    private:
        int SizeA;
        int SizeB;

    public:
        std::vector<std::vector<int>> Grid;
        std::vector<Words> WordList;

        WordSearch() 
        {
        }

        // Looks for the word given that the first character matches.
        // Only checks possible words at one index
        bool FindWord(std::string word, int i, int j, int wordListIdx);

        // Finds all the words in the word search
        void Search();

        // prints the word and it's starting and ending location
        void Print();

        void set(int inSizeA, int inSizeB);
};

#endif