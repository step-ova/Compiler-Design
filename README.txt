-------------------------------------------------------------------------------
LIBRARIES NEEDED
-------------------------------------------------------------------------------

External library: dk.brics.automaton
Used it for regex. I added it in the lib folder. 
If you want to download it
Source: http://www.brics.dk/automaton/index.html


Also needs JUnit4 for test cases.

-------------------------------------------------------------------------------
FILE DESCRIPTIONS
-------------------------------------------------------------------------------

Main is located in src/Main/Main.java

src/LexicalAnalyzer has the Token, Tokenizer and InvalidTokenException class.

src/test is where tests are. 
LexicalAnalyzerTestSuite.java is a single class that runs all test cases.

-------------------------------------------------------------------------------
HOW TO USE
-------------------------------------------------------------------------------

Put your code in input.txt
output.txt outputs tokens.
lexical_error.txt outputs lexical errors. 

run src/Main/Main.java