import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import dk.brics.automaton.Automaton;
import dk.brics.automaton.RegExp;

public class Tokenizer {

	private LineNumberReader br;

	private static final String DIGIT_REGEX = "[0-9]";
	private static final String NONZERO_REGEX = "[1-9]";
	private static final String LETTER_REGEX = "([a-z]|[A-Z])";

	private static final String UNDERSCORE = "_";
	private static final String ALPHANUM_REGEX = "(" + LETTER_REGEX + "|" + DIGIT_REGEX + "|" + UNDERSCORE + ")";
	private static final String ID_REGEX = LETTER_REGEX + ALPHANUM_REGEX + "*";

	private static final String INTEGER_REGEX = "(" + NONZERO_REGEX + DIGIT_REGEX + "*" + "|" + "0" + ")";
	private static final String FRACTION_REGEX = "(\\." + DIGIT_REGEX + "*" + NONZERO_REGEX + "|" + "\\.0)";
	private static final String FLOAT_REGEX = "(" + INTEGER_REGEX + FRACTION_REGEX + ")";

	// private static final Pattern DIGIT_PATTERN =
	// Pattern.compile(DIGIT_REGEX);
	// private static final Pattern NON_ZERO_PATTERN =
	// Pattern.compile(NONZERO_REGEX);
	// private static final Pattern LETTER_PATTERN =
	// Pattern.compile(LETTER_REGEX);

	Automaton IS_DIGIT_PATTERN = new RegExp(DIGIT_REGEX).toAutomaton();
	Automaton IS_NON_ZERO_PATTERN = new RegExp(NONZERO_REGEX).toAutomaton();
	Automaton IS_LETTER_PATTERN = new RegExp(LETTER_REGEX).toAutomaton();
	Automaton IS_ALPHANUM_PATTERN = new RegExp(LETTER_REGEX).toAutomaton();
	Automaton IS_INTEGER_PATTERN = new RegExp(INTEGER_REGEX).toAutomaton();

	String[] RESERVED_WORDS = { "IF", "THEN", "ELSE", "FOR", "CLASS", "INT", "FLOAT", "GET", "PUT", "RETURN", "PROGRAM",
			"AND", "NOT", "OR" };

	public Tokenizer(String filename) {
		try {
			br = new LineNumberReader(new FileReader(filename));
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Problem with reading " + filename);
		}

	}

	public Token getNextToken() {
		try {
			char c = (char) br.read();
			StringBuilder sb = new StringBuilder();
			sb.append(c);
			String firstChar = sb.toString();
			
			
			if(Character.isWhitespace(c)){
				while(Character.isWhitespace(c)){
					br.mark(1);
					c = (char) br.read();
				}
				br.reset();
			}
			// Check if it is a letter
			else if (IS_LETTER_PATTERN.run(firstChar)) {
				c = (char) br.read();

				// while c is alphanumeric
				while (IS_ALPHANUM_PATTERN.run(String.valueOf(c))) {
					sb.append(c);
					br.mark(1); // save spot in case the character is not
								// alphanumeric
					c = (char) br.read();
				}

				// go back to char that was not alphanumeric
				br.reset();

				// remove last char from StringBuilder because is not
				// alphanumeric
				sb.setLength(sb.length() - 1);

				// Check if final string is a reserved word
				if (Arrays.asList(RESERVED_WORDS).contains(sb.toString())) {
					return new Token(sb.toString().toUpperCase(), sb.toString(), br.getLineNumber());
				} else {
					return new Token("ID", sb.toString(), br.getLineNumber());
				}

			} else if (IS_NON_ZERO_PATTERN.run(firstChar)) {
				c = (char) br.read();

				while (IS_DIGIT_PATTERN.run(String.valueOf(c))) {
					sb.append(c);
					br.mark(1); // save spot in case the character is not digit
					c = (char) br.read();
				}

				if (c == '.') {
					sb.append(c);
					return getFractionToken(sb);

				} else {
					// go back to char that was not digit
					br.reset();
					// remove last char from StringBuilder because is not digit
					sb.setLength(sb.length() - 1);

					return new Token("INTEGER", sb.toString(), br.getLineNumber());
				}

			} else if (firstChar.equals("0")) {
				

			} else if (firstChar.equals("{")) {
				return new Token("OPENCURLYBRAC", sb.toString(), br.getLineNumber());

			} else if (firstChar.equals("}")) {
				return new Token("CLOSECURLYBRAC", sb.toString(), br.getLineNumber());

			} else if (firstChar.equals("(")) {
				return new Token("OPENPAREN", sb.toString(), br.getLineNumber());

			} else if (firstChar.equals(")")) {
				return new Token("CLOSEPAREN", sb.toString(), br.getLineNumber());

			} else if (firstChar.equals("[")) {
				return new Token("OPENBRAC", sb.toString(), br.getLineNumber());

			} else if (firstChar.equals("]")) {
				return new Token("CLOSEBRAC", sb.toString(), br.getLineNumber());

			} else if (firstChar.equals("<")) {

				br.mark(100);
				c = (char) br.read();
				
				char symbol = '>';
				
				if(c == symbol){
					sb.append(symbol);
					return new Token("NOTEQUAL", sb.toString(), br.getLineNumber());
				}
				else{
					br.reset();
					return new Token("LESSTHAN", sb.toString(), br.getLineNumber());
				}

			} else if (firstChar.equals(">")) {
				return new Token("GREATERTHAN", sb.toString(), br.getLineNumber());

			} else if (firstChar.equals("=")) {
				br.mark(0);
				c = (char) br.read();
				
				char symbol = '=';
				
				if(c == symbol){
					sb.append(symbol);
					return new Token("EQUALEQUAL", sb.toString(), br.getLineNumber());
				}
				else{
					br.reset();
					return new Token("EQUAL", sb.toString(), br.getLineNumber());
				}
				
			} else if (firstChar.equals("+")) {
				return new Token("PLUSSIGN", sb.toString(), br.getLineNumber());

			} else if (firstChar.equals("-")) {
				return new Token("MINUSSIGN", sb.toString(), br.getLineNumber());

			} else if (firstChar.equals("*")) {
				return new Token("MULTIPLYSIGN", sb.toString(), br.getLineNumber());

			} else if (firstChar.equals("/")) {
				return new Token("DIVIDESIGN", sb.toString(), br.getLineNumber());

			} else if (firstChar.equals(";")) {
				return new Token("SEMICOLON", sb.toString(), br.getLineNumber());

			} else if (firstChar.equals(",")) {
				return new Token("COMMA", sb.toString(), br.getLineNumber());

			} else if (firstChar.equals(".")) {
				return new Token("DOT", sb.toString(), br.getLineNumber());

			}

		} catch (IOException e) {
			e.printStackTrace();
		}

		return new Token("test", "test", 1);

	}

	private Token getFractionToken(StringBuilder sb) throws IOException {
		br.mark(50);
		//reads char after .
		char c = (char) br.read();
		int counter = 0;
		boolean is_all_zero = true;

		while (true) {

			if (IS_DIGIT_PATTERN.run(String.valueOf(c))) {
				sb.append(c);
				br.mark(50);

				if (c == '0') {
					counter++;
				}
				// c is non digit
				else {
					counter = 0;
					is_all_zero = false;
				}

				c = (char) br.read();
				continue;
			} else
				break;
		}

		char lastchar = sb.toString().charAt(sb.length() - 1);
		// We have something like "(digit*).(non_digit)"
		if (lastchar == '.') {
			// go back to dot
			br.reset();
			// remove last char from StringBuilder because it was a
			// dot
			sb.setLength(sb.length() - 1);

			return new Token("INTEGER", sb.toString(), br.getLineNumber());

		}
		// We have something like (digit*).(digit*)
		else if (counter == 0) {
			// go back to non digit char
			br.reset();

			return new Token("FLOAT", sb.toString(), br.getLineNumber());
		}
		// We have something like (digit*).(digit*)0 OR (digit*).(0+)(non_digit)
		else {
			// number of times we have to backtrack
			
			int indexOfDot = sb.toString().indexOf('.');
			
			//If we have something like 123.0a, we return 123.0
			if(sb.toString().length() - indexOfDot == 2){
				br.reset();
				return new Token("FLOAT", sb.toString(), br.getLineNumber());
			}
			///If we have something like 123.00000, return 123.0
			else if(is_all_zero){
				for(int i =0; i < counter-1; i++){
					br.reset();
				}
				
				sb.setLength(sb.length() - counter +1);
				return new Token("FLOAT", sb.toString(), br.getLineNumber());
			}
			//if we have something like 123.1230000
			else{
				int backtrack = counter + 1;
				for (int i = 0; i < backtrack; i++) {
					br.reset();
				}

				// remove all 0 characters
				sb.setLength(sb.length() - counter);

				return new Token("FLOAT", sb.toString(), br.getLineNumber());
			}
			
			
		}

	}
}
