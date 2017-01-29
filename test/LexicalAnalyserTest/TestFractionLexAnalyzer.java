package test.LexicalAnalyserTest;

import org.junit.Test;

import LexicalAnalyzer.InvalidTokenException;
import LexicalAnalyzer.Token;
import LexicalAnalyzer.Tokenizer;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;


/*
 * Tests floating point numbers
 * If there is another token available after the running each test
 * then the test fails
 */
public class TestFractionLexAnalyzer extends CommonTestLexAnalyzer {

	private final String t1 = "123.";
	private final String t2 = "123.123";
	private final String t3 = "123.010203";
	private final String t4 = "123.0";
	private final String t5 = "123.000";
	private final String t6 = "123.1230";
	private final String t7 = "123.123000";
	private final String t8 = "123.a";
	private final String t9 = "123.123a";
	private final String t10 = "0123.123";
	private final String t11 = "123.0.0";
	private final String t12 = "123.00.0";
	private final String t13 = "0.0";
	private final String t14 = "000.000";

	@Test
	public void test1() {

		t = new Tokenizer(getTokenizer(t1));

		Token tk1 = new Token("INTEGER", "123", 0);
		Token tk2 = new Token("DOT", ".", 0);

		Token rtk1 = null;
		Token rtk2 = null;

		try {
			rtk1 = t.getNextToken();
			rtk2 = t.getNextToken();

		} catch (InvalidTokenException e) {
			fail();
		}

		assert (tk1.equals(rtk1) && tk2.equals(rtk2));
	}

	@Test
	public void test2() {
		assert (genericSingleTokenTestCase("FLOAT", t2));
	}

	@Test
	public void test3() {
		assert (genericSingleTokenTestCase("FLOAT", t3));
	}

	@Test
	public void test4() {
		assert (genericSingleTokenTestCase("FLOAT", t4));
	}

	@Test
	public void test5() {

		t = new Tokenizer(getTokenizer(t5));

		Token tk1 = new Token("FLOAT", "123.0", 0);
		Token tk2 = new Token("INTEGER", "0", 0);
		Token tk3 = new Token("INTEGER", "0", 0);

		Token rtk1 = null;
		Token rtk2 = null;
		Token rtk3 = null;

		try {
			rtk1 = t.getNextToken();
			rtk2 = t.getNextToken();
			rtk3 = t.getNextToken();
		} catch (InvalidTokenException e) {
			fail();
		}

		assert (tk1.equals(rtk1) && tk2.equals(rtk2) && tk3.equals(rtk3));
	}

	@Test
	public void test6() {

		t = new Tokenizer(getTokenizer(t6));

		Token tk1 = new Token("FLOAT", "123.123", 0);
		Token tk2 = new Token("INTEGER", "0", 0);

		Token rtk1 = null;
		Token rtk2 = null;

		try {
			rtk1 = t.getNextToken();
			rtk2 = t.getNextToken();
		} catch (InvalidTokenException e) {
			fail();
		}

		assert (tk1.equals(rtk1) && tk2.equals(rtk2));
	}

	@Test
	public void test7() {

		t = new Tokenizer(getTokenizer(t7));

		Token tk1 = new Token("FLOAT", "123.123", 0);
		Token tk2 = new Token("INTEGER", "0", 0);
		Token tk3 = new Token("INTEGER", "0", 0);
		Token tk4 = new Token("INTEGER", "0", 0);

		Token rtk1 = null;
		Token rtk2 = null;
		Token rtk3 = null;
		Token rtk4 = null;

		try {
			rtk1 = t.getNextToken();
			rtk2 = t.getNextToken();
			rtk3 = t.getNextToken();
			rtk4 = t.getNextToken();
		} catch (InvalidTokenException e) {
			fail();
		}

		assert (tk1.equals(rtk1) && tk2.equals(rtk2) && tk3.equals(rtk3) && tk4.equals(rtk4));
	}

	@Test
	public void test8() {

		t = new Tokenizer(getTokenizer(t8));

		Token tk1 = new Token("INTEGER", "123", 0);
		Token tk2 = new Token("DOT", ".", 0);
		Token tk3 = new Token("ID", "a", 0);

		Token rtk1 = null;
		Token rtk2 = null;
		Token rtk3 = null;

		try {
			rtk1 = t.getNextToken();
			rtk2 = t.getNextToken();
			rtk3 = t.getNextToken();
		} catch (InvalidTokenException e) {
			fail();
		}

		assert (tk1.equals(rtk1) && tk2.equals(rtk2) && tk3.equals(rtk3));
	}

	@Test
	public void test9() {

		t = new Tokenizer(getTokenizer(t9));

		Token tk1 = new Token("FLOAT", "123.123", 0);
		Token tk2 = new Token("ID", "a", 0);

		Token rtk1 = null;
		Token rtk2 = null;

		try {
			rtk1 = t.getNextToken();
			rtk2 = t.getNextToken();
		} catch (InvalidTokenException e) {
			fail();
		}

		assert (tk1.equals(rtk1) && tk2.equals(rtk2));
	}

	@Test
	public void test10() {

		t = new Tokenizer(getTokenizer(t10));

		Token tk1 = new Token("INTEGER", "0", 0);
		Token tk2 = new Token("FLOAT", "123.123", 0);

		Token rtk1 = null;
		Token rtk2 = null;

		try {
			rtk1 = t.getNextToken();
			rtk2 = t.getNextToken();
		} catch (InvalidTokenException e) {
			fail();
		}

		assert (tk1.equals(rtk1) && tk2.equals(rtk2));
	}

	@Test
	public void test11() {

		t = new Tokenizer(getTokenizer(t11));

		Token tk1 = new Token("FLOAT", "123.0", 0);
		Token tk2 = new Token("DOT", ".", 0);
		Token tk3 = new Token("INTEGER", "0", 0);

		Token rtk1 = null;
		Token rtk2 = null;
		Token rtk3 = null;

		try {
			rtk1 = t.getNextToken();
			rtk2 = t.getNextToken();
			rtk3 = t.getNextToken();
		} catch (InvalidTokenException e) {
			fail();
		}

		assert (tk1.equals(rtk1) && tk2.equals(rtk2) && tk3.equals(rtk3));
	}

	@Test
	public void test12() {

		t = new Tokenizer(getTokenizer(t12));

		Token tk1 = new Token("FLOAT", "123.0", 0);
		Token tk2 = new Token("FLOAT", "0.0", 0);

		Token rtk1 = null;
		Token rtk2 = null;

		try {
			rtk1 = t.getNextToken();
			rtk2 = t.getNextToken();

		} catch (InvalidTokenException e) {
			fail();
		}

		assert (tk1.equals(rtk1) && tk2.equals(rtk2));
	}

	@Test
	public void test13() {
		assert (genericSingleTokenTestCase("FLOAT", t13));
	}

	@Test
	public void test14() {

		t = new Tokenizer(getTokenizer(t14));

		Token tk1 = new Token("INTEGER", "0", 0);
		Token tk2 = new Token("INTEGER", "0", 0);
		Token tk3 = new Token("FLOAT", "0.0", 0);
		Token tk4 = new Token("INTEGER", "0", 0);
		Token tk5 = new Token("INTEGER", "0", 0);

		Token rtk1 = null;
		Token rtk2 = null;
		Token rtk3 = null;
		Token rtk4 = null;
		Token rtk5 = null;

		try {
			rtk1 = t.getNextToken();
			rtk2 = t.getNextToken();
			rtk3 = t.getNextToken();
			rtk4 = t.getNextToken();
			rtk5 = t.getNextToken();

		} catch (InvalidTokenException e) {
			fail();
		}

		assert (tk1.equals(rtk1) 
				&& tk2.equals(rtk2)
				&& tk3.equals(rtk3)
				&& tk4.equals(rtk4)
				&& tk5.equals(rtk5));
	}
}
