package test.LexicalAnalyserTest;

import static org.junit.Assert.fail;

import org.junit.Test;

import LexicalAnalyzer.InvalidTokenException;
import LexicalAnalyzer.Token;
import LexicalAnalyzer.Tokenizer;

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
	
	//These are not ID's
	private final String t11 = "_abc"; 
	private final String t12 = "1abc";
	
	
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
		
		//should throw exception
		try {
			rtk1 = t.getNextToken();

		} catch (InvalidTokenException e) {
			if(!e.getMessage().equals(Tokenizer.INVALID_CHARACTER + "_")){
				fail();
			};  
		}
		
		//abc
		try {
			rtk2 = t.getNextToken();

		} catch (InvalidTokenException e) {
			fail();
		}

		assert (tk2.equals(rtk2));
	}
	
	@Test
	public void test12() {
		
		//String: 1abc
		
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
	
}
