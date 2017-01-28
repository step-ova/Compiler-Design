package test.LexicalAnalyserTest;

import static org.junit.Assert.fail;

import java.io.LineNumberReader;
import java.io.StringReader;

import org.junit.After;

import LexicalAnalyzer.InvalidTokenException;
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
			System.out.println(e.getMessage());
		}
	}

	protected LineNumberReader getTokenizer(String s) {
		return new LineNumberReader(new StringReader(s));
	}

}
