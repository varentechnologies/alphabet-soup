#ifndef ALPHABETSOUP_ALPHABETSOUP
#define ALPHABETSOUP_ALPHABETSOUP

#include <fstream>

#include "Grid.hpp"

class AlphabetSoup
{
public:

    //! Default Constructor
    AlphabetSoup();

    //! Copy Constructor
    //! @param[in] Copy The AlphabetSoup you wish to copy.
    AlphabetSoup(const AlphabetSoup& Copy);

    //! Move Constructor
    //! @param[in] Copy The AlphabetSoup you wish to move.
    AlphabetSoup(AlphabetSoup&& Copy);

    //! Copy Operator
    //! @param[in] Copy The AlphabetSoup you wish to copy.
    //! @return The copied Grid.
    AlphabetSoup& operator=(const AlphabetSoup& Copy);

    //! Move Operator
    //! @param[in] Copy The AlphabetSoup you wish to move.
    //! @return The moved Grid.
    AlphabetSoup& operator=(AlphabetSoup&& Copy);

    //! Destructor
    ~AlphabetSoup();

    //! Loads a given file, creates a grid, and finds all of the given words
    //! defined within said file.
    //! @param[in] InputFile The file you wish to load.
    void LoadFile(std::ifstream& InputFile);

private:

    //! The grid used that will be populated with the given file.
    Grid Grid_;
};

#endif