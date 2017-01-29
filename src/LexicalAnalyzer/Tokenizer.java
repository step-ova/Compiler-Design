package LexicalAnalyzer;
import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;
import dk.brics.automaton.Automaton;
import dk.brics.automaton.RegExp;

public class Tokenizer {
	
	public static final String INVALID_CHARACTER = "Invalid characther: ";
	public static final String COMPILER_ERROR_NOT_CLOSED_COMMENT = "COMPILER ERROR: End of file reached. Missing \"*/\" to close multi line comment";
	public static final String COMPILER_ERROR_CLOSE_COMMENT_WITHOUT_OPEN = "COMPILER ERROR: There is a close comment \"*/\" without one being opened";
	
	private LineNumberReader br;
	private boolean multi_line_comment_open;
	
	private static final String DIGIT_REGEX = "[0-9]";
	private static final String NONZERO_REGEX = "[1-9]";
	private static final String LETTER_REGEX = "([a-z]|[A-Z])";
	private static final String UNDERSCORE = "_";
	private static final String ALPHANUM_REGEX = "(" + LETTER_REGEX + "|" + DIGIT_REGEX + "|" + UNDERSCORE + ")";

	private static Automaton IS_DIGIT_PATTERN = new RegExp(DIGIT_REGEX).toAutomaton();
	private static Automaton IS_NON_ZERO_PATTERN = new RegExp(NONZERO_REGEX).toAutomaton();
	private static Automaton IS_LETTER_PATTERN = new RegExp(LETTER_REGEX).toAutomaton();
	private static Automaton IS_ALPHANUM_PATTERN = new RegExp(ALPHANUM_REGEX).toAutomaton();
	
	private static final String LINE_NUMBER_STRING = " ==== Line number: "; //for error messages

	private static final String[] RESERVED_WORDS = { "IF", "THEN", "ELSE", "FOR", "CLASS", "INT", "FLOAT", "GET", "PUT",
			"RETURN", "PROGRAM", "AND", "NOT", "OR" };

	public Tokenizer(String filename) {

		multi_line_comment_open = false;

		try {
			br = new LineNumberReader(new FileReader(filename));
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Problem with reading " + filename);
		}

	}
	
	public Tokenizer(LineNumberReader lnr){
		multi_line_comment_open = false;
		br = lnr;
	}

	public Token getNextToken() throws InvalidTokenException {

		try {
			int end_of_file = br.read();
			char c = (char) end_of_file;

			while (Character.isWhitespace(c)) {
				end_of_file = br.read();
				c = (char) end_of_file;
			}

			// returns null if eof
			if (end_of_file == -1) {
				return null;
			}

			StringBuilder sb = new StringBuilder();
			sb.append(c);
			String firstChar = sb.toString();

			while (multi_line_comment_open) {
				// get closing multi-line comment
				while (true) {

					if (end_of_file == -1) {
						multi_line_comment_open = false;
						throw new InvalidTokenException(generateErrorMessage(COMPILER_ERROR_NOT_CLOSED_COMMENT, br.getLineNumber()));
					}

					else if (c != '*') {
						end_of_file = br.read();
						c = (char) end_of_file;
						continue;
					}

					char nextChar = (char) br.read();
					br.mark(1);// in case the next char is not a "/". To accept
								// a * in comments

					if (nextChar == '/') {
						multi_line_comment_open = false;
						return new Token("CLOSEMULTILINECOMMENT", "*/", br.getLineNumber());
					}

					else {
						c = nextChar;
						br.reset();
						continue;
					}
				}
			}

			if (firstChar.equals("/")) {
				br.mark(1);
				c = (char) br.read();

				char symbol = '/';
				char symbol2 = '*';

				// Start of //, ignore inline comment until \n
				if (c == symbol) {

					sb.append(c);
					int line = br.getLineNumber();

					// remove all comments
					while (c != '\n') {
						end_of_file = br.read();
						c = (char) end_of_file;
						
						if(end_of_file == -1){
							return new Token("INLINECOMMENT", sb.toString(), line);
						}
						
					}

					return new Token("INLINECOMMENT", sb.toString(), line);
				}

				// Start of "/*", ignore multi-line comment
				else if (c == symbol2) {

					sb.append(c);
					int line = br.getLineNumber();

					multi_line_comment_open = true;

					return new Token("OPENMULTILINECOMMENT", sb.toString(), line);

				} else {
					br.reset();
					return new Token("DIVIDESIGN", sb.toString(), br.getLineNumber());
				}

			}

			// Check if it is a letter
			else if (IS_LETTER_PATTERN.run(firstChar)) {
				br.mark(1);
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

				// 1 character id
				if (sb.length() == 1) {
					return new Token("ID", sb.toString(), br.getLineNumber());
				} else {
					// remove last char from StringBuilder because is not
					// alphanumeric
					sb.setLength(sb.length());

					// Check if final string is a reserved word
					if (isReservedWord(sb.toString(), RESERVED_WORDS)) {
						return new Token(sb.toString().toUpperCase(), sb.toString(), br.getLineNumber());
					} else {
						return new Token("ID", sb.toString(), br.getLineNumber());
					}
				}

			} else if (IS_NON_ZERO_PATTERN.run(firstChar)) {
				br.mark(1);
				c = (char) br.read();

				while (IS_DIGIT_PATTERN.run(String.valueOf(c))) {
					sb.append(c);
					br.mark(50); // save spot in case the character is not digit
					c = (char) br.read();
				}

				if (c == '.') {
					sb.append(c);
					return getFractionToken(sb);

				} else {
					// go back to char that was not digit
					br.reset();
					return new Token("INTEGER", sb.toString(), br.getLineNumber());
					
				}

			} else if (firstChar.equals("0")) {
				br.mark(50);
				c = (char) br.read();

				if (c == '.') {
					sb.append(c);
					return getFractionToken(sb);
				}

				else {
					br.reset();
					return new Token("INTEGER", sb.toString(), br.getLineNumber());
				}

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

				br.mark(1);
				c = (char) br.read();

				char symbol = '>';
				char symbol2 = '=';

				if (c == symbol) {
					sb.append(symbol);
					return new Token("NOTEQUAL", sb.toString(), br.getLineNumber());
				} else if (c == symbol2) {
					sb.append(symbol2);
					return new Token("LESSTHANEQUAL", sb.toString(), br.getLineNumber());
				} else {
					br.reset();
					return new Token("LESSTHAN", sb.toString(), br.getLineNumber());
				}

			} else if (firstChar.equals(">")) {

				br.mark(1);
				c = (char) br.read();

				char symbol = '=';

				if (c == symbol) {
					sb.append(symbol);
					return new Token("GREATERTHANEQUAL", sb.toString(), br.getLineNumber());
				} else {
					br.reset();
					return new Token("GREATERTHAN", sb.toString(), br.getLineNumber());
				}

			} else if (firstChar.equals("=")) {
				br.mark(1);
				c = (char) br.read();

				char symbol = '=';

				if (c == symbol) {
					sb.append(symbol);
					return new Token("EQUALEQUAL", sb.toString(), br.getLineNumber());
				} else {
					br.reset();
					return new Token("EQUAL", sb.toString(), br.getLineNumber());
				}

			} else if (firstChar.equals("+")) {
				return new Token("PLUSSIGN", sb.toString(), br.getLineNumber());

			} else if (firstChar.equals("-")) {
				return new Token("MINUSSIGN", sb.toString(), br.getLineNumber());

			} else if (firstChar.equals("*")) {
				br.mark(1);
				c = (char) br.read();
				
				char symbol = '/';
				if (c == symbol) {
					throw new InvalidTokenException(generateErrorMessage(COMPILER_ERROR_CLOSE_COMMENT_WITHOUT_OPEN, br.getLineNumber()));
				}
				else{
					br.reset();
					return new Token("MULTIPLYSIGN", sb.toString(), br.getLineNumber());
				}

			} else if (firstChar.equals(";")) {
				return new Token("SEMICOLON", sb.toString(), br.getLineNumber());

			} else if (firstChar.equals(",")) {
				return new Token("COMMA", sb.toString(), br.getLineNumber());

			} else if (firstChar.equals(".")) {
				return new Token("DOT", sb.toString(), br.getLineNumber());

			} else {
				throw new InvalidTokenException(generateErrorMessage(c, br.getLineNumber()));
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
		return new Token("This should never be called", "", 0);
	}

	private Token getFractionToken(StringBuilder sb) throws IOException {
		// reads char after "."
		char c = (char) br.read();
		int zero_counter = 0;
		boolean is_all_zero = true;

		while (true) {

			// if between 0-9
			if (IS_DIGIT_PATTERN.run(String.valueOf(c))) {
				sb.append(c);

				if (c == '0') {
					zero_counter++;
				}
				// c is between 1-9
				else {
					zero_counter = 0;
					is_all_zero = false;
				}

				c = (char) br.read();
				continue;
			} else
				break;
		}

		char lastchar = sb.toString().charAt(sb.length() - 1);
		// We have something like "(digit*).(not_a_digit)"
		if (lastchar == '.') {
			// go back to "."
			br.reset();
			// remove last char from StringBuilder because it was a "."
			sb.setLength(sb.length() - 1);

			return new Token("INTEGER", sb.toString(), br.getLineNumber());

		}
		// We entered the else and we have something like
		// (digit*).(digit*)(non_zero_digit)
		else if (zero_counter == 0) {
			// go back to "."
			br.reset();

			// Just used for forward calculation
			String s = sb.toString();

			// number of digits after the '.'
			int forward = s.length() - s.indexOf('.');

			// undo backtrack to digit
			for (int i = 0; i < forward; i++) {
				br.read();
			}

			return new Token("FLOAT", sb.toString(), br.getLineNumber());
		}
		// We have something like (digit*).(digit*)0 OR (digit*).(0+)(non_digit)
		else {

			// reset to "."
			br.reset();

			if (is_all_zero) {

				br.read();
				br.read();

				sb.setLength(sb.length() - zero_counter + 1); // remove
																// exceeding 0's
				return new Token("FLOAT", sb.toString(), br.getLineNumber());
			}
			// if we have something like (digit*).(digit*)(zero+) ex
			// 123.01230123000
			else {

				// Just used for forward calculation
				String s = sb.toString();

				// number of digits after the '.', including the .
				int forward = s.length() - s.indexOf('.') - zero_counter;

				// undo backtrack to digit
				for (int i = 0; i < forward; i++) {
					br.read();
				}

				// remove all 0 characters
				sb.setLength(sb.length() - zero_counter);

				return new Token("FLOAT", sb.toString(), br.getLineNumber());
			}

		}

	}

	private boolean isReservedWord(String word, String[] array) {
		for (String s : array) {
			if (s.equalsIgnoreCase(word)) {
				return true;
			}
		}
		return false;
	}
	
	public static String generateErrorMessage(String errorMessage, int lineNumber){
		StringBuilder sb = new StringBuilder();
		sb.append(errorMessage);
		sb.append(LINE_NUMBER_STRING);
		sb.append(lineNumber);
		return sb.toString();
	}
	
	public static String generateErrorMessage(char errorChar, int lineNumber){
		StringBuilder sb = new StringBuilder();
		sb.append(INVALID_CHARACTER);
		sb.append(errorChar);
		sb.append(" . Line number: ");
		sb.append(lineNumber);
		return sb.toString();
	}
}
