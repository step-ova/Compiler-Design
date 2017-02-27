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
	private static final String OUTPUT_SYNTAX_DERIVATION_FILE = "derivation_syntax.txt";
	private static final String OUTPUT_SYNTAX_ERROR_FILE = "syntax_error.txt";

	private static PrintWriter pw_output_file;
	private static PrintWriter pw_error_file;
	private static PrintWriter pw_derivation_file;
	private static PrintWriter pw_syntax_error_file;

	public static void main(String[] args) {

		initializeOutputFiles();
		
		Tokenizer t = new Tokenizer(INPUT_FILE_NAME);
		Parser p = new Parser(t, pw_derivation_file, pw_syntax_error_file);
		
		try {
			System.out.println(p.parse());
		} catch (InvalidTokenException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		closeOutputFiles();
		

	}
	
	
	public static void initializeOutputFiles(){
		try {
			pw_output_file = new PrintWriter(new FileOutputStream(OUTPUT_FILE_NAME));
			pw_error_file = new PrintWriter(new FileOutputStream(OUPUT_ERROR_FILE));
			pw_derivation_file = new PrintWriter(new FileOutputStream(OUTPUT_SYNTAX_DERIVATION_FILE));
			pw_syntax_error_file = new PrintWriter(new FileOutputStream(OUTPUT_SYNTAX_ERROR_FILE));
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		pw_output_file.println("Token, Lexeme, LineNumber");
	}
	
	public static void closeOutputFiles(){
		pw_output_file.close();
		pw_error_file.close();
	}


}
