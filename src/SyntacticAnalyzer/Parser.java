package SyntacticAnalyzer;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Stack;

import LexicalAnalyzer.InvalidTokenException;
import LexicalAnalyzer.Token;
import LexicalAnalyzer.Tokenizer;
import Semantic.SemanticStack;

public class Parser {

	private Symbols allSymbols = new Symbols();
	private ParsingTable parsingTable = new ParsingTable();

	private Tokenizer t;
	private FirstFollowArrays firstFollowArrays;

	private SemanticStack semanticStack;

	private PrintWriter pw_token_output_file;
	private PrintWriter pw_derivation_file;
	private PrintWriter pw_syntax_error_file;
	private PrintWriter pw_error_file;
	
	private int parseCount = 0;

	@SuppressWarnings("rawtypes")
	Stack<Enum> stack = new Stack<Enum>();

	public Parser(Tokenizer t) {

		this.t = t;

		firstFollowArrays = new FirstFollowArrays();

		semanticStack = new SemanticStack();

	}

	public Parser(Tokenizer t, PrintWriter pw_token_output_file, PrintWriter pw_error_file, PrintWriter pw_derivation_file,
			PrintWriter pw_syntax_error_file, SemanticStack semanticStack) {

		this.t = t;

		firstFollowArrays = new FirstFollowArrays();

		this.pw_token_output_file = pw_token_output_file;

		this.pw_derivation_file = pw_derivation_file;

		this.pw_syntax_error_file = pw_syntax_error_file;
		
		this.pw_error_file = pw_error_file;

		this.semanticStack = semanticStack;
	}

	public boolean parse() throws InvalidTokenException {
		stack.push(Symbols.terminals.$);
		stack.push(Symbols.non_terminals.Prog);

		Token tok = getNextTokenAndPrintToTokenOutputFile();
		boolean error = false;
		while (stack.peek() != Symbols.terminals.$) {
			Enum x = stack.peek();
			String symbolName = x.name();

			if (pw_derivation_file != null && parseCount == 0) {
				pw_derivation_file.println(symbolName);
			}
			
			//remove comments
			String comment = tok.getTokenLexeme();
			if(comment.equals("//") || comment.equals("*/") || comment.equals("/*")){
				tok = getNextTokenAndPrintToTokenOutputFile();
				continue;
			}

			// Semantic part
			if (allSymbols.isSemanticAction(symbolName)) {
				semanticStack.push(symbolName, parseCount);
				stack.pop();
				continue;
			}

			// regular parsing algorithm
			else if (allSymbols.isTerminal(symbolName)) {
				if (tok.getTokenName().equalsIgnoreCase(symbolName)) {
					
					//semantic part
					semanticStack.push(tok);

					stack.pop();
					tok = getNextTokenAndPrintToTokenOutputFile();
				} else if (symbolName.equalsIgnoreCase("epsilon")) {
					stack.pop();
				} else {
					skipErrors(tok);
					error = true;
				}
			}

			else {
				int nonTerminalSymbolTableIndex = allSymbols.getNonTerminalIndex(x.name());
				int terminalSymbolTableIndex = allSymbols.getTerminalIndex(tok.getTokenName());

				if (!parsingTable.isError(nonTerminalSymbolTableIndex, terminalSymbolTableIndex)) {
					stack.pop();
					inverseRHSPush(parsingTable.getRule(nonTerminalSymbolTableIndex, terminalSymbolTableIndex));
				} else {
					skipErrors(tok);
					error = true;
				}

			}
		}

		parseCount++;
		
		if (!tok.getTokenName().equals("$") || error == true) {
			return false;
		} else {
			stack = new Stack<Enum>();
			return true;
		}
		

	}

	public void skipErrors(Token tok) throws InvalidTokenException {

		tok = getNextTokenAndPrintToTokenOutputFile();

		if (pw_syntax_error_file != null) {
			pw_syntax_error_file
					.println("Syntax error: " + tok.getTokenLexeme() + " at line number " + tok.getTokenPosition());
		}

		Enum x = stack.peek();

		if (tok.getTokenName().equals("$") || firstFollowArrays.followContains(x, tok.getTokenName())) {
			if (pw_syntax_error_file != null) {
				pw_syntax_error_file.println("Popping...");
			}

			stack.pop();
		}

		else {
			while (firstFollowArrays.firstContains(x, tok.getTokenName())
					|| (firstFollowArrays.firstContains(x, "EPSILON")
							&& firstFollowArrays.followContains(x, tok.getTokenName()))) {

				if (pw_syntax_error_file != null) {
					pw_syntax_error_file.println("Seeking...");
				}

				tok = getNextTokenAndPrintToTokenOutputFile();
			}
		}
	}

	public void inverseRHSPush(ArrayList<Enum> rule) {
		for (int i = rule.size() - 1; i >= 0; i--) {
			stack.push(rule.get(i));
		}
	}

	private Token getNextTokenAndPrintToTokenOutputFile() throws InvalidTokenException {
		Token temp = null;
		boolean invalidToken = false;
		
		while(!invalidToken){
			try
			{
				temp = t.getNextToken();
				invalidToken = true;
				if (pw_token_output_file != null && parseCount == 0) {
					pw_token_output_file.println(temp.toString());
				}
				
			}
			catch (InvalidTokenException e) {
				if (pw_error_file != null && parseCount == 0) {
					pw_error_file.println(e.getMessage());
				}
				
			}
		}
		
		
		return temp;
	}

	public void printSymbolTable() {
		semanticStack.printSymbolTable();
	}
	
	public void printCodeGeneration(){
		semanticStack.closeCodeGenerationOutputFile();
	}
}
