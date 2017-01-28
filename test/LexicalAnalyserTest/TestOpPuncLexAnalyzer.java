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

		t = new Tokenizer(getTokenizer(t1));

		Token tk1 = new Token("EQUALEQUAL", t1, 0);

		Token rtk1 = null;

		try {
			rtk1 = t.getNextToken();

		} catch (InvalidTokenException e) {
			fail();
		}

		assert (tk1.equals(rtk1));
	}

	@Test
	public void test2() {

		t = new Tokenizer(getTokenizer(t2));

		Token tk1 = new Token("NOTEQUAL", t2, 0);

		Token rtk1 = null;

		try {
			rtk1 = t.getNextToken();

		} catch (InvalidTokenException e) {
			fail();
		}

		assert (tk1.equals(rtk1));
	}

	@Test
	public void test3() {

		t = new Tokenizer(getTokenizer(t3));

		Token tk1 = new Token("LESSTHAN", t3, 0);

		Token rtk1 = null;

		try {
			rtk1 = t.getNextToken();

		} catch (InvalidTokenException e) {
			fail();
		}

		assert (tk1.equals(rtk1));
	}

	@Test
	public void test4() {

		t = new Tokenizer(getTokenizer(t4));

		Token tk1 = new Token("GREATERTHAN", t4, 0);

		Token rtk1 = null;

		try {
			rtk1 = t.getNextToken();

		} catch (InvalidTokenException e) {
			fail();
		}

		assert (tk1.equals(rtk1));
	}

	@Test
	public void test5() {

		t = new Tokenizer(getTokenizer(t5));

		Token tk1 = new Token("LESSTHANEQUAL", t5, 0);

		Token rtk1 = null;

		try {
			rtk1 = t.getNextToken();

		} catch (InvalidTokenException e) {
			fail();
		}

		assert (tk1.equals(rtk1));
	}

	@Test
	public void test6() {

		t = new Tokenizer(getTokenizer(t6));

		Token tk1 = new Token("GREATERTHANEQUAL", t6, 0);

		Token rtk1 = null;

		try {
			rtk1 = t.getNextToken();

		} catch (InvalidTokenException e) {
			fail();
		}

		assert (tk1.equals(rtk1));
	}

	@Test
	public void test7() {

		t = new Tokenizer(getTokenizer(t7));

		Token tk1 = new Token("SEMICOLON", t7, 0);

		Token rtk1 = null;

		try {
			rtk1 = t.getNextToken();

		} catch (InvalidTokenException e) {
			fail();
		}

		assert (tk1.equals(rtk1));
	}

	@Test
	public void test8() {

		t = new Tokenizer(getTokenizer(t8));

		Token tk1 = new Token("COMMA", t8, 0);

		Token rtk1 = null;

		try {
			rtk1 = t.getNextToken();

		} catch (InvalidTokenException e) {
			fail();
		}

		assert (tk1.equals(rtk1));
	}

	@Test
	public void test9() {

		t = new Tokenizer(getTokenizer(t9));

		Token tk1 = new Token("DOT", t9, 0);

		Token rtk1 = null;

		try {
			rtk1 = t.getNextToken();

		} catch (InvalidTokenException e) {
			fail();
		}

		assert (tk1.equals(rtk1));
	}

	@Test
	public void test10() {

		t = new Tokenizer(getTokenizer(t10));

		Token tk1 = new Token("PLUSSIGN", t10, 0);

		Token rtk1 = null;

		try {
			rtk1 = t.getNextToken();

		} catch (InvalidTokenException e) {
			fail();
		}

		assert (tk1.equals(rtk1));
	}

	@Test
	public void test11() {

		t = new Tokenizer(getTokenizer(t11));

		Token tk1 = new Token("MINUSSIGN", t11, 0);

		Token rtk1 = null;

		try {
			rtk1 = t.getNextToken();

		} catch (InvalidTokenException e) {
			fail();
		}

		assert (tk1.equals(rtk1));
	}

	@Test
	public void test12() {

		t = new Tokenizer(getTokenizer(t12));

		Token tk1 = new Token("MULTIPLYSIGN", t12, 0);

		Token rtk1 = null;

		try {
			rtk1 = t.getNextToken();

		} catch (InvalidTokenException e) {
			fail();
		}

		assert (tk1.equals(rtk1));
	}

	@Test
	public void test13() {

		t = new Tokenizer(getTokenizer(t13));

		Token tk1 = new Token("DIVIDESIGN", t13, 0);

		Token rtk1 = null;

		try {
			rtk1 = t.getNextToken();

		} catch (InvalidTokenException e) {
			fail();
		}

		assert (tk1.equals(rtk1));
	}

	@Test
	public void test14() {

		t = new Tokenizer(getTokenizer(t14));

		Token tk1 = new Token("EQUAL", t14, 0);

		Token rtk1 = null;

		try {
			rtk1 = t.getNextToken();

		} catch (InvalidTokenException e) {
			fail();
		}

		assert (tk1.equals(rtk1));
	}

	@Test
	public void test15() {

		t = new Tokenizer(getTokenizer(t15));

		Token tk1 = new Token("OPENPAREN", t15, 0);

		Token rtk1 = null;

		try {
			rtk1 = t.getNextToken();

		} catch (InvalidTokenException e) {
			fail();
		}

		assert (tk1.equals(rtk1));
	}

	@Test
	public void test16() {

		t = new Tokenizer(getTokenizer(t16));

		Token tk1 = new Token("CLOSEPAREN", t16, 0);

		Token rtk1 = null;

		try {
			rtk1 = t.getNextToken();

		} catch (InvalidTokenException e) {
			fail();
		}

		assert (tk1.equals(rtk1));
	}

	@Test
	public void test17() {

		t = new Tokenizer(getTokenizer(t17));

		Token tk1 = new Token("OPENCURLYBRAC", t17, 0);

		Token rtk1 = null;

		try {
			rtk1 = t.getNextToken();

		} catch (InvalidTokenException e) {
			fail();
		}

		assert (tk1.equals(rtk1));
	}

	@Test
	public void test18() {

		t = new Tokenizer(getTokenizer(t18));

		Token tk1 = new Token("CLOSECURLYBRAC", t18, 0);

		Token rtk1 = null;

		try {
			rtk1 = t.getNextToken();

		} catch (InvalidTokenException e) {
			fail();
		}

		assert (tk1.equals(rtk1));
	}

	@Test
	public void test19() {

		t = new Tokenizer(getTokenizer(t19));

		Token tk1 = new Token("OPENBRAC", t19, 0);

		Token rtk1 = null;

		try {
			rtk1 = t.getNextToken();

		} catch (InvalidTokenException e) {
			fail();
		}

		assert (tk1.equals(rtk1));
	}

	@Test
	public void test20() {

		t = new Tokenizer(getTokenizer(t20));

		Token tk1 = new Token("CLOSEBRAC", t20, 0);

		Token rtk1 = null;

		try {
			rtk1 = t.getNextToken();

		} catch (InvalidTokenException e) {
			fail();
		}

		assert (tk1.equals(rtk1));
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
			if (!e.getMessage().equals(Tokenizer.COMPILER_ERROR_NOT_CLOSED_COMMENT)) {
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
			if (!e.getMessage().equals(Tokenizer.COMPILER_ERROR_CLOSE_COMMENT_WITHOUT_OPEN)) {
				fail();
			}
		}

		assert (true);
	}

	@Test
	public void test23() {

		t = new Tokenizer(getTokenizer(t23));

		Token tk1 = new Token("INLINECOMMENT", t23, 0);

		Token rtk1 = null;

		try {
			rtk1 = t.getNextToken();

		} catch (InvalidTokenException e) {
			fail();
		}

		assert (tk1.equals(rtk1));
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
			if (!e.getMessage().equals(Tokenizer.COMPILER_ERROR_CLOSE_COMMENT_WITHOUT_OPEN)) {
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
