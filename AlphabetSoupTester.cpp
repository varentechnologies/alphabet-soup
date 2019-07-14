#include "AlphabetSoupTester.hpp"

#include <iostream>

namespace AlphabetSoupTester
{
    AlphabetSoup TestAlphabetSoup;

    // Makes multiple calls to the LoadFile method of the AlphabetSoup class.
    // @return If all of the test scenarios successfully executed.
    bool TestScenarios()
    {
        bool passed = true;

        std::vector<std::string> fileNames({ "TestInput.txt", "TestInput2.txt", "TestInput3.txt"});

        for (const auto& fileName : fileNames)
        {
            std::ifstream testFile(fileName);
            if (testFile.is_open())
            {
                TestAlphabetSoup.LoadFile(testFile);
            }
            else
            {
                std::cout << "Unable to find test file " << fileName << "!" << std::endl;
                passed = false;
            }
        }

        return passed;
    }
}