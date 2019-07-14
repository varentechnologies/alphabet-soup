#include "AlphabetSoup.hpp"

#include <iostream>

//! Default Constructor
AlphabetSoup::AlphabetSoup() :
    Grid_()
{
}

//! Copy Constructor
//! @param[in] Copy The AlphabetSoup you wish to copy.
AlphabetSoup::AlphabetSoup(const AlphabetSoup& Copy) :
    Grid_(Copy.Grid_)
{
}

//! Move Constructor
//! @param[in] Copy The AlphabetSoup you wish to move.
AlphabetSoup::AlphabetSoup(AlphabetSoup&& Copy) :
    Grid_(std::move(Copy.Grid_))
{
}

//! Copy Operator
//! @param[in] Copy The AlphabetSoup you wish to copy.
//! @return The copied Grid.
AlphabetSoup& AlphabetSoup::operator=(const AlphabetSoup& Copy)
{
    Grid_    = Copy.Grid_;

    return *this;
}

//! Move Operator
//! @param[in] Copy The AlphabetSoup you wish to move.
//! @return The moved Grid.
AlphabetSoup& AlphabetSoup::operator=(AlphabetSoup&& Copy)
{
    Grid_    = std::move(Copy.Grid_);

    return *this;
}

//! Destructor
AlphabetSoup::~AlphabetSoup()
{
}

// Public Methods

//! Loads a given file, creates a grid, and finds all of the given words
//! defined within said file.
//! @param[in] InputFile The file you wish to load.
void AlphabetSoup::LoadFile(std::ifstream& InputFile)
{
    std::string line;

    Grid::Dimensions dim;
    
    // Read the dimensions of the grid.
    std::getline(InputFile, line, 'x');
    dim.X = std::stoi(line);

    std::getline(InputFile, line);
    dim.Y = std::stoi(line);

    Grid_.SetDim(dim);

    std::stringstream ss;

    // Now read all of the rows.
    for (int i = 0; i < dim.Y; ++i)
    {
        std::getline(InputFile, line);

        ss << line;
    }

    Grid_.CreateGrid(ss);

    // Find all of the words within the grid.
    Grid::Coordinates coords;

    while (std::getline(InputFile, line))
    {
        if (Grid_.FindWord(line, coords))
        {
            std::cout << line << " " << coords.ToString() << std::endl;
        }
    }
}