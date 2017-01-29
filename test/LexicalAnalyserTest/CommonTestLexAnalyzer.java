package test.LexicalAnalyserTest;

import static org.junit.Assert.fail;

import java.io.LineNumberReader;
import java.io.StringReader;

import org.junit.After;

import LexicalAnalyzer.InvalidTokenException;
import LexicalAnalyzer.Token;
import LexicalAnalyzer.Tokenizer;

public class CommonTestLexAnalyzer {

	protected Tokenizer t;

	// Checks after each @Test if there are not more token to be taken
	@After
	public void checkLastToken() {
		try {
			if (t.getNextToken() != null) {
				fail("Next token is not null");
			}
		} catch (InvalidTokenException e) {
			fail(e.getMessage());
		}
	}

	protected LineNumberReader getTokenizer(String s) {
		return new LineNumberReader(new StringReader(s));
	}
	
	//	Generic test for test cases that return a single token
	protected boolean genericSingleTokenTestCase(String token, String lexeme){
		t = new Tokenizer(getTokenizer(lexeme));

		Token tk1 = new Token(token, lexeme, 0);

		Token rtk1 = null;

		try {
			rtk1 = t.getNextToken();

		} catch (InvalidTokenException e) {
			fail();
		}

		return tk1.equals(rtk1);
	}

}
