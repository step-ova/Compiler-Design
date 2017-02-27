package test.SyntacticAnalyzerTest;

import static org.junit.Assert.assertTrue;

import java.io.File;
import org.junit.Test;

import LexicalAnalyzer.InvalidTokenException;
import LexicalAnalyzer.Tokenizer;
import SyntacticAnalyzer.Parser;

public class CorrectSyntaxTest {

	private final String t1 = "correct_factorTest.txt";
	private final String t2 = "correct_multipleClasses.txt";
	private final String t3 = "correct_multipleClassesAndFunctions.txt";
	private final String t4 = "correct_originalCode.txt";
	private final String t5 = "correct_statementTest.txt";
	private final String t6 = "correct_relArithExprTest.txt";

	private static final String folder = "\\src\\test\\SyntacticAnalyzerTest\\testFiles\\";

	private static String testPath = new File("").getAbsolutePath();

	private Tokenizer t;

	@Test
	public void testFactor() {
		assertTrue(getParse(t1));
	}

	@Test
	public void testMultipleClasses() {
		assertTrue(getParse(t2));
	}

	@Test
	public void testMulipleClassesAndFunctions() {
		assertTrue(getParse(t3));
	}

	@Test
	public void testOriginalCode() {
		assertTrue(getParse(t4));
	}

	@Test
	public void testStatement() {
		assertTrue(getParse(t5));
	}
	
	@Test
	public void testRelArithExpr() {
		assertTrue(getParse(t6));
	}
	

	private boolean getParse(String fileName) {
		t = new Tokenizer(getTestFileLocation(fileName));

		Parser p = new Parser(t);

		try {

			return p.parse();

		} catch (InvalidTokenException e) {
			e.printStackTrace();
		}

		return false;
	}

	private String getTestFileLocation(String textFile) {

		StringBuilder sb = new StringBuilder();
		sb.append(testPath);
		sb.append(folder);
		sb.append(textFile);

		return sb.toString();
	}

}
