package test.LexicalAnalyserTest;

import static org.junit.Assert.fail;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.LineNumberReader;
import java.io.StringReader;

import org.junit.After;

import LexicalAnalyzer.InvalidTokenException;
import LexicalAnalyzer.Token;
import LexicalAnalyzer.Tokenizer;

public class CommonTestLexAnalyzer {

	protected Tokenizer tokenizer;

	// Checks after each @Test if there are not more token to be taken
	@After
	public void checkLastToken() {
		try {
			if (tokenizer.getNextToken() != null) {
				fail("Next token is not null");
			}
		} catch (InvalidTokenException e) {
			fail(e.getMessage());
		}
	}

	protected LineNumberReader getTokenizer(String s) {
		return new LineNumberReader(new StringReader(s));
	}
	
	protected LineNumberReader getTokenizer(File file){
		try {
			return new LineNumberReader(new FileReader(file));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			System.out.println("Problem reading the file "
					+ file.getAbsolutePath());
			
		}
		return null;
	}
	
	//	Generic test for test cases that return a single token
	protected boolean genericSingleTokenTestCase(String token, String lexeme){
		tokenizer = new Tokenizer(getTokenizer(lexeme));

		Token tk1 = new Token(token, lexeme, 0);

		Token rtk1 = null;

		try {
			rtk1 = tokenizer.getNextToken();

		} catch (InvalidTokenException e) {
			fail();
		}

		return tk1.equals(rtk1);
	}
	
	protected String getAbsPath(String path, String folder, String file){
		StringBuilder sb = new StringBuilder();
		sb.append(path);
		sb.append(folder);
		sb.append(file);
		return sb.toString();
	}

}
