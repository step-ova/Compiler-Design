package test.LexicalAnalyserTest;

import static org.junit.Assert.fail;

import org.junit.Test;

import LexicalAnalyzer.InvalidTokenException;
import LexicalAnalyzer.Token;
import LexicalAnalyzer.Tokenizer;

public class TestOpPuncLexAnalyzer extends CommonTestLexAnalyzer {

	private final String t1 = "==";
	private final String t2 = "<>";
	private final String t3 = "<";
	private final String t4 = ">";
	private final String t5 = "<=";
	private final String t6 = ">=";
	private final String t7 = ";";
	private final String t8 = ",";
	private final String t9 = ".";

	private final String t10 = "+";
	private final String t11 = "-";
	private final String t12 = "*";
	private final String t13 = "/";
	private final String t14 = "=";

	private final String t15 = "(";
	private final String t16 = ")";
	private final String t17 = "{";
	private final String t18 = "}";
	private final String t19 = "[";
	private final String t20 = "]";
	private final String t21 = "/*"; // invalid
	private final String t22 = "*/"; // invalid
	private final String t23 = "//";

	private final String t24 = "// a b c a";
	private final String t25 = "/* abc abc */";
	private final String t26 = "/* /* abc abc */";

	// error Strings
	private final String t27 = "/* test test */ */"; //invalid
	private final String t28 = "/"; //invalid

	@Test
	public void test1() {
		assert (genericSingleTokenTestCase("EQUALEQUAL", t1));
	}

	@Test
	public void test2() {
		assert (genericSingleTokenTestCase("NOTEQUAL", t2));
	}

	@Test
	public void test3() {
		assert (genericSingleTokenTestCase("LESSTHAN", t3));
	}

	@Test
	public void test4() {
		assert (genericSingleTokenTestCase("GREATERTHAN", t4));
	}

	@Test
	public void test5() {
		assert (genericSingleTokenTestCase("LESSTHANEQUAL", t5));
	}

	@Test
	public void test6() {
		assert (genericSingleTokenTestCase("GREATERTHANEQUAL", t6));
	}

	@Test
	public void test7() {
		assert (genericSingleTokenTestCase("SEMICOLON", t7));
	}

	@Test
	public void test8() {
		assert (genericSingleTokenTestCase("COMMA", t8));
	}

	@Test
	public void test9() {
		assert (genericSingleTokenTestCase("DOT", t9));
	}

	@Test
	public void test10() {
		assert (genericSingleTokenTestCase("PLUSSIGN", t10));
	}

	@Test
	public void test11() {
		assert (genericSingleTokenTestCase("MINUSSIGN", t11));
	}

	@Test
	public void test12() {
		assert (genericSingleTokenTestCase("MULTIPLYSIGN", t12));
	}

	@Test
	public void test13() {
		assert (genericSingleTokenTestCase("DIVIDESIGN", t13));
	}

	@Test
	public void test14() {
		assert (genericSingleTokenTestCase("EQUAL", t14));
	}

	@Test
	public void test15() {
		assert (genericSingleTokenTestCase("OPENPAREN", t15));
	}

	@Test
	public void test16() {
		assert (genericSingleTokenTestCase("CLOSEPAREN", t16));
	}

	@Test
	public void test17() {
		assert (genericSingleTokenTestCase("OPENCURLYBRAC", t17));
	}

	@Test
	public void test18() {
		assert (genericSingleTokenTestCase("CLOSECURLYBRAC", t18));
	}

	@Test
	public void test19() {
		assert (genericSingleTokenTestCase("OPENBRAC", t19));
	}

	@Test
	public void test20() {
		assert (genericSingleTokenTestCase("CLOSEBRAC", t20));
	}

	@Test
	public void test21() {

		t = new Tokenizer(getTokenizer(t21));

		Token tk1 = new Token("OPENMULTILINECOMMENT", t21, 0);

		Token rtk1 = null;
		Token rtk2 = null;

		try {
			rtk1 = t.getNextToken();

		} catch (InvalidTokenException e) {
			fail();
		}

		// Should be invalid
		try {
			rtk2 = t.getNextToken();

		} catch (InvalidTokenException e) {
			if (!e.getMessage().equals(Tokenizer.generateErrorMessage(Tokenizer.COMPILER_ERROR_NOT_CLOSED_COMMENT, 0))) {
				fail();
			}
		}

		assert (tk1.equals(rtk1));
	}

	@Test
	public void test22() {

		t = new Tokenizer(getTokenizer(t22));


		Token rtk1 = null;
		
		//should be invalid
		try {
			rtk1 = t.getNextToken();

		} catch (InvalidTokenException e) {
			if (!e.getMessage().equals(Tokenizer.generateErrorMessage(Tokenizer.COMPILER_ERROR_CLOSE_COMMENT_WITHOUT_OPEN, 0))) {
				fail();
			}
		}

		assert (true);
	}

	@Test
	public void test23() {
		assert (genericSingleTokenTestCase("INLINECOMMENT", t23));
	}

	@Test
	public void test24() {

		t = new Tokenizer(getTokenizer(t24));

		Token tk1 = new Token("INLINECOMMENT", "//", 0);

		Token rtk1 = null;

		try {
			rtk1 = t.getNextToken();

		} catch (InvalidTokenException e) {
			fail();
		}

		assert (tk1.equals(rtk1));
	}
	
	@Test
	public void test25() {

		t = new Tokenizer(getTokenizer(t25));

		Token tk1 = new Token("OPENMULTILINECOMMENT", "/*", 0);
		Token tk2 = new Token("CLOSEMULTILINECOMMENT", "*/", 0);

		Token rtk1 = null;
		Token rtk2 = null;

		try {
			rtk1 = t.getNextToken();
			rtk2 = t.getNextToken();

		} catch (InvalidTokenException e) {
			fail();
		}

		assert (tk1.equals(rtk1)
				&& tk2.equals(rtk2));
	}
	
	@Test
	public void test26() {

		t = new Tokenizer(getTokenizer(t26));

		Token tk1 = new Token("OPENMULTILINECOMMENT", "/*", 0);
		Token tk2 = new Token("CLOSEMULTILINECOMMENT", "*/", 0);

		Token rtk1 = null;
		Token rtk2 = null;

		try {
			rtk1 = t.getNextToken();
			rtk2 = t.getNextToken();

		} catch (InvalidTokenException e) {
			fail();
		}

		assert (tk1.equals(rtk1)
				&& tk2.equals(rtk2));
	}
	
	@Test
	public void test27() {

		t = new Tokenizer(getTokenizer(t27));

		Token tk1 = new Token("OPENMULTILINECOMMENT", "/*", 0);
		Token tk2 = new Token("CLOSEMULTILINECOMMENT", "*/", 0);

		Token rtk1 = null;
		Token rtk2 = null;
		Token rtk3 = null;

		try {
			rtk1 = t.getNextToken();
			rtk2 = t.getNextToken();

		} catch (InvalidTokenException e) {
			fail();
		}
		
		//invalid close
		try{
			rtk3 = t.getNextToken();
		}
		catch (InvalidTokenException e) {
			if (!e.getMessage().equals(Tokenizer.generateErrorMessage(Tokenizer.COMPILER_ERROR_CLOSE_COMMENT_WITHOUT_OPEN, 0))) {
				fail();
			}
		}
		

		assert (tk1.equals(rtk1)
				&& tk2.equals(rtk2));
	}
	
	@Test
	public void test28() {

		t = new Tokenizer(getTokenizer(t28));

		Token rtk1 = null;

		try {
			rtk1 = t.getNextToken();

		} catch (InvalidTokenException e) {
			if (!e.getMessage().equals(Tokenizer.INVALID_CHARACTER + "/")) {
				fail();
			}
		}

		assert (true);
	}
	

}
