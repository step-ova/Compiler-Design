package test.SyntacticAnalyzerTest;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)

@Suite.SuiteClasses({
	CorrectSyntaxTest.class,
	IncorrectSyntaxTest.class, 
})

public class SyntacticAnalyzerTestSuite {

}
