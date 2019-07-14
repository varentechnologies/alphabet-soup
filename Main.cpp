#include "AlphabetSoupTester.hpp"

#include <iostream>

//! This main simply calls a Tester class in order to test the functionality of the "AlphabetSoup" class.
int main()
{
    if (AlphabetSoupTester::TestScenarios())
    {
        std::cout << "All tests completed successfully!" << std::endl;
    }

    return 0;
}