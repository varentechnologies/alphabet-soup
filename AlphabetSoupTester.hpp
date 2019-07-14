#ifndef ALPHABETSOUP_ALPHABETSOUPTESTER
#define ALPHABETSOUP_ALPHABETSOUPTESTER

#include <fstream>

#include "AlphabetSoup.hpp"

//! This namespace contains methods for testing the functionality of the 
//! AlphabetSoup class.
namespace AlphabetSoupTester
{
    //! Makes multiple calls to the LoadFile method of the AlphabetSoup class.
    //! @return If all of the test scenarios successfully executed.
    bool TestScenarios();

    extern AlphabetSoup TestAlphabetSoup;
}

#endif