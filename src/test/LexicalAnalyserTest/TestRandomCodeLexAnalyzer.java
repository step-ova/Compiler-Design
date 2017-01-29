package test.LexicalAnalyserTest;

import static org.junit.Assert.fail;

import java.io.File;
import java.util.ArrayList;

import org.junit.Test;

import LexicalAnalyzer.InvalidTokenException;
import LexicalAnalyzer.Token;
import LexicalAnalyzer.Tokenizer;

public class TestRandomCodeLexAnalyzer extends CommonTestLexAnalyzer {
	
	private final String t1 = "test1.txt";
	private final String t2 = "test2.txt";
	private final String t3 = "test3.txt";
	
	private static final String folder = "\\src\\test\\LexicalAnalyserTest\\randomCodeTests\\";
	
	private static String testPath = new File("").getAbsolutePath(); 
	
	@Test
	public void test1(){
		
		File testFile = new File(getAbsPath(testPath, folder, t1));
		tokenizer = new Tokenizer(getTokenizer(testFile));
		
		Token tk1 = new Token("FLOAT", "0.0", 0);
		Token tk2 = new Token("INTEGER", "0", 0);
		Token tk3 = new Token("FLOAT", "0.000012300123", 0);
		Token tk4 = new Token("DOT", ".", 0);
		Token tk5 = new Token("INTEGER", "0", 0);
		Token tk6 = new Token("INTEGER", "12300", 0);
		Token tk7 = new Token("ID", "abc123", 0);
		Token tk8 = new Token("MULTIPLYSIGN", "*", 0);
		Token tk9 = new Token("LESSTHAN", "<", 0);
		Token tk10 = new Token("INLINECOMMENT", "//", 0);
		Token tk11 = new Token("ID", "for01program0ab", 1);
		Token tk12 = new Token("MULTIPLYSIGN", "*", 1);
		Token tk13 = new Token("ID", "ac", 1);

		Token rtk1 = null;
		Token rtk2 = null;
		Token rtk3 = null;
		Token rtk4 = null;
		Token rtk5 = null;
		Token rtk6 = null;
		Token rtk7 = null;
		Token rtk8 = null;
		Token rtk9 = null;
		Token rtk10 = null;
		Token rtk11 = null;
		Token rtk12 = null;
		Token rtk13 = null;

		try {
			rtk1 = tokenizer.getNextToken();
			rtk2 = tokenizer.getNextToken();
			rtk3 = tokenizer.getNextToken();
			rtk4 = tokenizer.getNextToken();
			rtk5 = tokenizer.getNextToken();
			rtk6 = tokenizer.getNextToken();
			rtk7 = tokenizer.getNextToken();
			rtk8 = tokenizer.getNextToken();
			rtk9 = tokenizer.getNextToken();
			rtk10 = tokenizer.getNextToken();
			rtk11 = tokenizer.getNextToken();
			rtk12 = tokenizer.getNextToken();
			rtk13 = tokenizer.getNextToken();

		} catch (InvalidTokenException e) {
			fail();
		}

		assert (tk1.equals(rtk1) 
				&& tk2.equals(rtk2)
				&& tk3.equals(rtk3)
				&& tk4.equals(rtk4)
				&& tk5.equals(rtk5)
				&& tk6.equals(rtk6)
				&& tk7.equals(rtk7)
				&& tk8.equals(rtk8)
				&& tk9.equals(rtk9)
				&& tk10.equals(rtk10)
				&& tk11.equals(rtk11)
				&& tk12.equals(rtk12)
				&& tk13.equals(rtk13));
	}
	
	@Test
	public void test2(){
		File testFile = new File(getAbsPath(testPath, folder, t2));
		tokenizer = new Tokenizer(getTokenizer(testFile));

		
		Token tk1 = new Token("INLINECOMMENT", "//", 0);
		Token tk2 = new Token("OPENMULTILINECOMMENT", "/*", 1);
		Token tk3 = new Token("CLOSEMULTILINECOMMENT", "*/", 3);
		
		Token rtk1 = null;
		Token rtk2 = null;
		Token rtk3 = null;
		Token rtk4 = null;
		
		try {
			rtk1 = tokenizer.getNextToken();
			rtk2 = tokenizer.getNextToken();
			rtk3 = tokenizer.getNextToken();
			rtk4 = tokenizer.getNextToken();

		} catch (InvalidTokenException e) {
			if (!e.getMessage().equals(Tokenizer.generateErrorMessage(Tokenizer.COMPILER_ERROR_CLOSE_COMMENT_WITHOUT_OPEN, 3))) {
				fail("InvalidTokenException fail");
			}
		}

		assert (tk1.equals(rtk1) 
				&& tk2.equals(rtk2)
				&& tk3.equals(rtk3));
	}
	
	@Test
	public void test3(){
		File testFile = new File(getAbsPath(testPath, folder, t3));
		tokenizer = new Tokenizer(getTokenizer(testFile));
		
		Token tk1 = new Token("EQUALEQUAL", "==", 0);
		Token tk2 = new Token("EQUAL", "=", 0);
		Token tk3 = new Token("LESSTHAN", "<", 0);
		Token tk4 = new Token("NOTEQUAL", "<>", 0);
		Token tk5 = new Token("GREATERTHAN", ">", 0);
		Token tk6 = new Token("LESSTHAN", "<", 0);
		Token tk7 = new Token("PLUSSIGN", "+", 0);
		Token tk8 = new Token("GREATERTHANEQUAL", ">=", 0);
		Token tk9 = new Token("OPENCURLYBRAC", "{", 0);
		Token tk10 = new Token("CLOSECURLYBRAC", "}", 0);
		Token tk11 = new Token("DOT", ".", 0);
		Token tk12 = new Token("COMMA", ",", 0);
		Token tk13 = new Token("ID", "a", 0);

		Token rtk1 = null;
		Token rtk2 = null;
		Token rtk3 = null;
		Token rtk4 = null;
		Token rtk5 = null;
		Token rtk6 = null;
		Token rtk7 = null;
		Token rtk8 = null;
		Token rtk9 = null;
		Token rtk10 = null;
		Token rtk11 = null;
		Token rtk12 = null;
		Token rtk13 = null;

		try {
			rtk1 = tokenizer.getNextToken();
			rtk2 = tokenizer.getNextToken();
			rtk3 = tokenizer.getNextToken();
			rtk4 = tokenizer.getNextToken();
			rtk5 = tokenizer.getNextToken();
			rtk6 = tokenizer.getNextToken();
			rtk7 = tokenizer.getNextToken();
			rtk8 = tokenizer.getNextToken();
			rtk9 = tokenizer.getNextToken();
			rtk10 = tokenizer.getNextToken();
			rtk11 = tokenizer.getNextToken();
			rtk12 = tokenizer.getNextToken();
			rtk13 = tokenizer.getNextToken();

		} catch (InvalidTokenException e) {
			fail();
		}

		assert (tk1.equals(rtk1) 
				&& tk2.equals(rtk2)
				&& tk3.equals(rtk3)
				&& tk4.equals(rtk4)
				&& tk5.equals(rtk5)
				&& tk6.equals(rtk6)
				&& tk7.equals(rtk7)
				&& tk8.equals(rtk8)
				&& tk9.equals(rtk9)
				&& tk10.equals(rtk10)
				&& tk11.equals(rtk11)
				&& tk12.equals(rtk12)
				&& tk13.equals(rtk13));
		
	}
	

}
