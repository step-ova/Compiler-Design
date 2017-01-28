package test.LexicalAnalyserTest;

import static org.junit.Assert.fail;

import org.junit.Test;

import LexicalAnalyzer.InvalidTokenException;
import LexicalAnalyzer.Token;
import LexicalAnalyzer.Tokenizer;

/*
 * Tests Identifiers
 * If there is another token available after the running each test
 * then the test fails
 */
public class TestIDLexAnalyzer extends CommonTestLexAnalyzer {

	private final String t1 = "a";
	private final String t2 = "abc";
	private final String t3 = "A";
	private final String t4 = "ABC";
	private final String t5 = "a_b_c_";
	private final String t6 = "AbCd";
	private final String t7 = "A_b_C_d";
	private final String t8 = "a123";
	private final String t9 = "A123";
	private final String t10 = "A_1_b_2";

	// These are not ID's
	private final String t11 = "_abc";
	private final String t12 = "1abc";

	// reserved words
	private final String t13 = "if Then eLSE FOR clasS int float gEt put return program and not or";

	@Test
	public void test1() {

		t = new Tokenizer(getTokenizer(t1));

		Token tk1 = new Token("ID", t1, 0);

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

		Token tk1 = new Token("ID", t2, 0);

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

		Token tk1 = new Token("ID", t3, 0);

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

		Token tk1 = new Token("ID", t4, 0);

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

		Token tk1 = new Token("ID", t5, 0);

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

		Token tk1 = new Token("ID", t6, 0);

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

		Token tk1 = new Token("ID", t7, 0);

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

		Token tk1 = new Token("ID", t8, 0);

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

		Token tk1 = new Token("ID", t9, 0);

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

		Token tk1 = new Token("ID", t10, 0);

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

		// String: _abc

		t = new Tokenizer(getTokenizer(t11));

		Token tk2 = new Token("ID", "abc", 0);

		Token rtk1 = null;
		Token rtk2 = null;

		// should throw exception
		try {
			rtk1 = t.getNextToken();

		} catch (InvalidTokenException e) {
			if (!e.getMessage().equals(Tokenizer.generateErrorMessage('_', 0))) {
				fail();
			}
			;
		}

		// abc
		try {
			rtk2 = t.getNextToken();

		} catch (InvalidTokenException e) {
			fail();
		}

		assert (tk2.equals(rtk2));
	}

	@Test
	public void test12() {

		// String: 1abc

		t = new Tokenizer(getTokenizer(t12));

		Token tk1 = new Token("INTEGER", "1", 0);
		Token tk2 = new Token("ID", "abc", 0);

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

		// String: if Then eLSE FOR clasS int float gEt put return program and
		// not or

		t = new Tokenizer(getTokenizer(t13));

		Token tk1 = new Token("IF", "if", 0);
		Token tk2 = new Token("THEN", "Then", 0);
		Token tk3 = new Token("ELSE", "eLSE", 0);
		Token tk4 = new Token("FOR", "FOR", 0);
		Token tk5 = new Token("CLASS", "clasS", 0);
		Token tk6 = new Token("INT", "int", 0);
		Token tk7 = new Token("FLOAT", "float", 0);
		Token tk8 = new Token("GET", "gEt", 0);
		Token tk9 = new Token("PUT", "put", 0);
		Token tk10 = new Token("RETURN", "return", 0);
		Token tk11 = new Token("PROGRAM", "program", 0);
		Token tk12 = new Token("AND", "and", 0);
		Token tk13 = new Token("NOT", "not", 0);
		Token tk14 = new Token("OR", "or", 0);

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
		Token rtk14 = null;

		try {
			rtk1 = t.getNextToken();
			rtk2 = t.getNextToken();
			rtk3 = t.getNextToken();
			rtk4 = t.getNextToken();
			rtk5 = t.getNextToken();
			rtk6 = t.getNextToken();
			rtk7 = t.getNextToken();
			rtk8 = t.getNextToken();
			rtk9 = t.getNextToken();
			rtk10 = t.getNextToken();
			rtk11 = t.getNextToken();
			rtk12 = t.getNextToken();
			rtk13 = t.getNextToken();
			rtk14 = t.getNextToken();

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
				&& tk13.equals(rtk13)
				&& tk14.equals(rtk14));
	}

}
