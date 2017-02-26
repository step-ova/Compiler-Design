package Main;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.PropertyResourceBundle;

import LexicalAnalyzer.InvalidTokenException;
import LexicalAnalyzer.Token;
import LexicalAnalyzer.Tokenizer;
import SyntacticAnalyzer.Parser;
import SyntacticAnalyzer.ParsingTable;
import SyntacticAnalyzer.Symbols;

public class Main {

	private static final String INPUT_FILE_NAME = "input.txt";
	private static final String OUTPUT_FILE_NAME = "output.txt";
	private static final String OUPUT_ERROR_FILE = "lexical_error.txt";

	private static PrintWriter pw_output_file;
	private static PrintWriter pw_error_file;

	public static void main(String[] args) {

		Tokenizer t = new Tokenizer(INPUT_FILE_NAME);
		
		initializeOutputFiles();
		
		
		Parser p = new Parser(t);
		
		try {
			System.out.println(p.parse());
		} catch (InvalidTokenException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

		/*
		Token token;
		while (true) {
			try {

				token = t.getNextToken();
				if (token.getTokenName().equals("$")) {
					break;
				} else {
					
					String tokenString = token.toString();
					
					System.out.println(tokenString);
					pw_output_file.println(tokenString);
				}

			} catch (InvalidTokenException e) {
				
				String errorMessage = e.getMessage();
				
				System.out.println(errorMessage);
				pw_error_file.println(errorMessage);
			}
		}
		*/
		
		pw_output_file.close();
		pw_error_file.close();
		
		

	}
	
	
	public static void initializeOutputFiles(){
		try {
			pw_output_file = new PrintWriter(new FileOutputStream(OUTPUT_FILE_NAME));
			pw_error_file = new PrintWriter(new FileOutputStream(OUPUT_ERROR_FILE));
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		pw_output_file.println("Token, Lexeme, LineNumber");
	}


}
