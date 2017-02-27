package test.SyntacticAnalyzerTest;

import static org.junit.Assert.assertFalse;

import java.io.File;

import org.junit.Test;

import LexicalAnalyzer.InvalidTokenException;
import LexicalAnalyzer.Tokenizer;
import SyntacticAnalyzer.Parser;

public class IncorrectSyntaxTest {
	
	private final String t1 = "incorrect_missingCommaAtEnd.txt";
	private final String t2 = "incorrect_noProgram.txt";
	private final String t3 = "incorrect_wrongId.txt";
	private final String t4 = "incorrect_funcDef.txt";
	private final String t5 = "incorrect_varDecl_1.txt";
	private final String t6 = "incorrect_varDecl_2.txt";
	private final String t7 = "incorrect_varDecl_3.txt";
	private final String t8 = "incorrect_varDecl_4.txt";
	private final String t9 = "incorrect_varDecl_5.txt";
	private final String t10 = "incorrect_varDecl_6.txt";
	

	private static final String folder = "\\src\\test\\SyntacticAnalyzerTest\\testFiles\\";

	private static String testPath = new File("").getAbsolutePath();

	private Tokenizer t;

	@Test
	public void testMissingCommaAtEnd() {
		assertFalse(getParse(t1));
	}

	@Test
	public void testNoProgrm() {
		assertFalse(getParse(t2));
	}

	@Test
	public void testWrongId() {
		assertFalse(getParse(t3));
	}

	@Test
	public void testFuncDef() {
		assertFalse(getParse(t4));
	}

	@Test
	public void testVarDecl1() {
		assertFalse(getParse(t5));
	}
	
	@Test
	public void testVarDecl2() {
		assertFalse(getParse(t6));
	}
	
	@Test
	public void testVarDecl3() {
		assertFalse(getParse(t7));
	}
	
	@Test
	public void testVarDecl4() {
		assertFalse(getParse(t8));
	}
	
	@Test
	public void testVarDecl5() {
		assertFalse(getParse(t9));
	}
	
	@Test
	public void testVarDecl6() {
		assertFalse(getParse(t10));
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
