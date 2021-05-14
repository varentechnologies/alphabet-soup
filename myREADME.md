# Alphabet Soup

## Quick Start

Run the program as follows:

```
./gradlew bootRun --args='src/test/resources/sample1.txt'
./gradlew bootRun --args='src/test/resources/sample2.txt'
./gradlew bootRun --args='src/test/resources/sample3.txt'
./gradlew bootRun --args='src/test/resources/sample4.txt'

```

The four inputs above represent: 1) the provided input, 2) a larger input I found online, 3) a matrix with more rows than columns, and 4)
a matrix with more columns than rows. 

The unit tests are integration tests. To run the tests and see the output on your screen:

```
./gradlew clean test --info
```

## High Level Design

Please see the JavaDocs in the `WordSearchService` for more detailed guidance in following the program logic. 

Here I describe the high level design choices and explain important decisions. 

This is a Spring Boot application. Spring Initializr is a convenient mechanism to create much of the necessary boiler plate for an application and to make use of the Spring Framework to take care of common tasks. I used it here to get started. In particular, I relied heavily on the application context and annotations to connect the application. Gradle comes along, and so unit test and logging. 

In addition, I made use of the `CommandLineRunner`. Alternatively, I might not have used Spring and instead produced a jar file and fed it command line arguments. The Spring Framework streamlines this process and adds capabilities for ease of development and use. 

## Logic Design

I considered the high level steps that were necessary to solve a word search. There is setup of the puzzle, and then the same algorithm is to be run for each word you need to find. The algorithm can be described as follows: Scan the matrix for the first letter of your word and circle the letter looking for the second letter; once you have the second letter you have a direction. The direction should be followed until we have a match or find that we do not. I broke the problem down into functions describing each of these steps. This practice helps create readable and maintainable code that is easier for others to follow and modify if necessary. 

## Gotchas and Special Notes

Because the ReadMe states that we can assume valid input, much work is saved in avoiding validation and handling edge cases. Presumably valid input means all the input words can be found in the grid, but I made a special note for the user if the word is not in the grid. Using a larger grid allows one to examine issues of scale. The running time of the algorithm can be described as O(N) where N = x * y, where x and y are the rows and columns. The 




I decided to provide them as an example, but seeing that validation was not required, I did not think extensive unit tests were appropriate either. The integration tests do not use assertions, as I normally would.

