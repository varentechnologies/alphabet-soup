/*
The WordSearch class contains helper methods to find words in a WordSearch

Edge cases: empty grid, multiple paths
*/

#include <iostream>
#include "WordSearch.h"
#include <vector>

using namespace std;


tuple<int, int> getNextDirectionIdx(int x, int y, Direction direction)
{
    int new_x;
    int new_y;

    switch (direction)
    {
        case topLeft:
        {
            new_x = x - 1;
            new_y = y - 1;
            break;
        }
        case top:
        {
            new_x = x - 1;
            new_y = y;
            break;
        }
        case topRight:
        {
            new_x = x - 1;
            new_y = y + 1;
            break;
        }
        case Left:
        {
            new_x = x;
            new_y = y - 1;
            break;
        }
        case Right:
        {
            new_x = x;
            new_y = y + 1;
            break;
        }
        case bottomLeft:
        {
            new_x = x + 1;
            new_y = y - 1;
            break;
        }
        case bottom:
        {
            new_x = x + 1;
            new_y = y;
            break;
        }
        case bottomRight:
        {
            new_x = x + 1;
            new_y = y + 1;
            break;
        }
        default:
        break;
    }

    return tuple<int,int>(new_x, new_y);
}


bool WordSearch::FindWord(string word, int i, int j, int wordListIdx)
{
    int wordidx = 1;
    int next_i = i;
    int next_j = j;
    //check all neighboring letters to see if it matches
    for (int dir = topLeft; dir <= bottomRight; ++dir)
    {
        tuple<int, int> idx = getNextDirectionIdx(i, j, (Direction)dir);
        
        // next letter matches and it's within bounds
        while (!(get<0>(idx) < 0 || get<0>(idx) >= SizeA || get<1>(idx) < 0 || get<1>(idx) >= SizeB)
                &&
                wordidx < word.length()
                &&
                Grid[get<0>(idx)][get<1>(idx)] == word[wordidx])
        {
            next_i = get<0>(idx);
            next_j = get<1>(idx);
            wordidx++;
            idx = getNextDirectionIdx(next_i, next_j, (Direction)dir);
        }

        // if last letter found 
        if (wordidx == word.length())
        {
            WordList[wordListIdx].endPoint = tuple<int, int>(next_i, next_j);
            return true;
        }
        wordidx = 1;
        
    }

    return false;

}

void WordSearch::Search()
{
    bool wordFound = false;
    for (int k = 0; k < WordList.size(); ++k)
    {
        // length of word must be shorter than grid size
        if (WordList[k].Word.length() <= SizeA || WordList[k].Word.length() <= SizeB)
        {
            for (int i = 0; i < SizeA; ++i)
            {
                for (int j = 0; j < SizeB; ++j)
                {
                    // if the first letter matches, look for the word
                    if (WordList[k].Word[0] == Grid[i][j])
                    {   
                        bool wordFound = FindWord(WordList[k].Word, i, j, k);
                        if (wordFound)
                        {
                            WordList[k].startPoint =  tuple<int,int>(i, j);
                            break;
                        }
                    }
                }
                
                if (wordFound)
                {
                    break;
                }
            }
        }

        wordFound = false;
    }

    return;
}

void WordSearch::Print()
{
    for (int i = 0; i < WordList.size(); ++i)
    {
        cout << WordList[i].Word << " "
            << get<0>(WordList[i].startPoint) << ":" << get<1>(WordList[i].startPoint)
            << " "
            << get<0>(WordList[i].endPoint) << ":" << get<1>(WordList[i].endPoint)
            << endl;

    }
    return;
}

void WordSearch::set(int inSizeA, int inSizeB)
{
    SizeA = inSizeA;
    SizeB = inSizeB;

    return;
}