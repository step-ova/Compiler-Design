package Main;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.io.PrintWriter;
import java.nio.file.Files;

import LexicalAnalyzer.InvalidTokenException;
import LexicalAnalyzer.Tokenizer;
import Semantic.SemanticStack;
import SyntacticAnalyzer.Parser;


public class Main {

	private static final String INPUT_FILE_NAME = "input.txt";
	private static final String OUTPUT_TOKEN_FILE_NAME = "output.txt";
	private static final String OUPUT_ERROR_FILE = "lexical_error.txt";
	private static final String OUTPUT_SYNTAX_DERIVATION_FILE = "derivation_syntax.txt";
	private static final String OUTPUT_SYNTAX_ERROR_FILE = "syntax_error.txt";
	private static final String OUTPUT_SEMANTIC_ERROR_FILE = "semantic_error.txt";
	private static final String OUTPUT_SYMBOL_TABLE = "symboltable.txt";

	private static PrintWriter pw_token_output_file;
	private static PrintWriter pw_error_file;
	private static PrintWriter pw_derivation_file;
	private static PrintWriter pw_syntax_error_file;
	private static PrintWriter pw_semantic_error_file;
	private static PrintWriter pw_symbol_table_file;
	
	private static ByteArrayInputStream bais;
	

	public static void main(String[] args) throws IOException {

		initializeOutputFiles();
		LineNumberReader lnr = getNewLineReader();
		
		Tokenizer t = new Tokenizer(lnr);
		
		SemanticStack semanticStack = new SemanticStack(pw_semantic_error_file, pw_symbol_table_file);
		Parser p = new Parser(t, pw_token_output_file, pw_derivation_file, pw_syntax_error_file, semanticStack);
		
		
		try {
			p.parse();
			
			incrementParse(lnr, p);
			
			p.parse();
		} catch (InvalidTokenException e) {
			e.printStackTrace();
		}
		
		p.printSymbolTable();
		
		closeOutputFiles();
		

	}
	
	
	public static void initializeOutputFiles(){
		try {
			pw_token_output_file = new PrintWriter(new FileOutputStream(OUTPUT_TOKEN_FILE_NAME));
			pw_error_file = new PrintWriter(new FileOutputStream(OUPUT_ERROR_FILE));
			pw_derivation_file = new PrintWriter(new FileOutputStream(OUTPUT_SYNTAX_DERIVATION_FILE));
			pw_syntax_error_file = new PrintWriter(new FileOutputStream(OUTPUT_SYNTAX_ERROR_FILE));
			pw_semantic_error_file = new PrintWriter(new FileOutputStream(OUTPUT_SEMANTIC_ERROR_FILE));
			pw_symbol_table_file = new PrintWriter(new FileOutputStream(OUTPUT_SYMBOL_TABLE));
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		pw_token_output_file.println("Token, Lexeme, LineNumber");
	}
	
	public static void closeOutputFiles(){
		pw_token_output_file.close();
		pw_error_file.close();
		pw_derivation_file.close();
		pw_syntax_error_file.close();
		pw_semantic_error_file.close();
		pw_symbol_table_file.close();
	}

	/*
	 * Some hoops and hops to be able to read through an input stream multiple times
	 * inspired by: https://stackoverflow.com/questions/9501237/read-stream-twice
	 */
	public static LineNumberReader getNewLineReader(){
		
		LineNumberReader lnr = null;
		try {
			
			byte[] bytes = Files.readAllBytes(new File(INPUT_FILE_NAME).toPath());
			
			//used to reset the input stream
			bais = new ByteArrayInputStream(bytes);
			
			lnr = new LineNumberReader(new InputStreamReader(bais));
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return lnr;
	}
	
	/*
	 * Increments the parse count
	 */
	public static void incrementParse(LineNumberReader lnr, Parser p){
		bais.reset(); //2nd pass
		lnr.setLineNumber(0); //reset line
		p.incrementParseCount();
	}

}
