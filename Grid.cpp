#include "Grid.hpp"

#include <algorithm>
#include <set>

// Returns the coordinates in a nicely formatted string.
// @return The formatted string.
std::string Grid::Coordinates::ToString()
{
    return std::to_string(StartX) + ":" + std::to_string(StartY) + " " + std::to_string(StopX) + ":" + std::to_string(StopY);
}

// Default Constructor
Grid::Grid() :
    CharGrid_(nullptr),
    GridSize_(),
    WordMap_()
{
}

// Copy Constructor
// @param[in] Copy The Grid you wish to copy.
Grid::Grid(const Grid& Copy) :
    CharGrid_(Copy.CharGrid_),
    GridSize_(Copy.GridSize_),
    WordMap_(Copy.WordMap_)
{
}

// Move Constructor
// @param[in] Copy The Grid you wish to move.
Grid::Grid(Grid&& Copy) :
    CharGrid_(std::move(Copy.CharGrid_)),
    GridSize_(std::move(Copy.GridSize_)),
    WordMap_(std::move(Copy.WordMap_))
{
}

// Copy Operator
// @param[in] Copy The Grid you wish to copy.
// @return The copied Grid.
Grid& Grid::operator=(const Grid& Copy)
{
    CharGrid_ = Copy.CharGrid_;
    GridSize_ = Copy.GridSize_;
    WordMap_  = Copy.WordMap_;

    return *this;
}

// Move Operator
// @param[in] Copy The Grid you wish to move.
// @return The moved Grid.
Grid& Grid::operator=(Grid&& Copy)
{
    CharGrid_ = std::move(Copy.CharGrid_);
    GridSize_ = std::move(Copy.GridSize_);
    WordMap_  = std::move(Copy.WordMap_);

    return *this;
}

// Destructor
Grid::~Grid()
{
    for (int i = 0; i < GridSize_.X; ++i)
    {
        delete[] CharGrid_[i];
    }
    delete[] CharGrid_;
}

// Public Methods

// Creates the Grid. Must be called before "FindWord" will find words.
// @param[in] SStream The string stream defining the Grid layout.
void Grid::CreateGrid(std::stringstream& SStream)
{
    for (int i = 0; i < GridSize_.X; ++i)
    {
        for (int j = 0; j < GridSize_.Y; ++j)
        {
            SStream >> CharGrid_[i][j];
        }
    }

    GenerateWords();
}

// Finds the given word in the grid. CreateGrid and SetDim must be called first.
// @param[in] Word The word you wish to find.
// @param[out] Coordinates The coordinates of the given word.
// @return Whether the word was found or not.
bool Grid::FindWord(const std::string& Word, Coordinates& Coordinates) const
{
    auto entry = WordMap_.find(Word);
    if (entry != WordMap_.end())
    {
        Coordinates = entry->second;
        return true;
    }

    return false;
}

// Sets the dimensions of the grid. Must be called before Created Grid is called.
// @param[in] The dimensions of the grid.
void Grid::SetDim(const Dimensions& Dim)
{
    if (CharGrid_)
    {
        for (int i = 0; i < GridSize_.X; ++i)
        {
            delete[] CharGrid_[i];
        }
        delete[] CharGrid_;
    }

    GridSize_ = Dim;

    CharGrid_ = new char* [Dim.X];
    for (int i = 0; i < Dim.X; ++i)
    {
        CharGrid_[i] = new char[Dim.Y];
    }
}

// Private Methods

// Populates WordMap_ with all of the different words in the grid.
void Grid::GenerateWords()
{
    std::set<Coordinates> savedCoords;
    WordMap_.clear();

    for (int i = 0; i < GridSize_.X; ++i)
    {
        for (int j = 0; j < GridSize_.Y; ++j)
        {
            for (int dir = 0; dir < NumDirections; ++dir)
            {
                Dimensions stepDir = Directions[dir];
                int steps = 0;

                Coordinates coords;
                std::string word;

                coords.StartX = i;
                coords.StartY = j;
                coords.StopX  = i;
                coords.StopY  = j;

                // Take a step in the direction we are currently going.
                while (coords.StopX >= 0 &&
                       coords.StopX < GridSize_.X &&
                       coords.StopY >= 0 &&
                       coords.StopY < GridSize_.Y)
                {
                    word += CharGrid_[coords.StopX][coords.StopY];

                    // Only save unique coordinate positions.
                    if (savedCoords.find(coords) == savedCoords.end())
                    {
                        savedCoords.insert(coords);
                        WordMap_.insert(std::make_pair(word, coords));
                    }

                    coords.StopX += stepDir.X;
                    coords.StopY += stepDir.Y;
                }
            }
        }
    }
}
