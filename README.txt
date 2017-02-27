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

src/SyntacticalAnalyzer has the FirstFollowArrays, Parser, ParsingTable, Symbols, firstSet.txt, followSet.txt and parsingtable.txt

src/test is where tests are. 
LexicalAnalyzerTestSuite.java is a single class that runs all Lexical test cases.
SyntacticAnalyzerTestSuite.java is a single class that runs all Syntactic test cases

-------------------------------------------------------------------------------
HOW TO USE
-------------------------------------------------------------------------------

Put your code in input.txt
output.txt outputs tokens.
lexical_error.txt outputs lexical errors. 
derivation_syntax.txt output the derivation of the syntax.
syntax_error.txt output the syntax errors.

run src/Main/Main.java