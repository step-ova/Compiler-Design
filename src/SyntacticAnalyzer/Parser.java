package SyntacticAnalyzer;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Stack;

import LexicalAnalyzer.InvalidTokenException;
import LexicalAnalyzer.Token;
import LexicalAnalyzer.Tokenizer;

public class Parser {

	private Symbols allSymbols = new Symbols();
	private ParsingTable parsingTable = new ParsingTable();

	private Tokenizer t;
	private FirstFollowArrays firstFollowArrays;
	
	private PrintWriter pw_token_output_file;
	private PrintWriter pw_derivation_file;
	private PrintWriter pw_syntax_error_file;
	

	@SuppressWarnings("rawtypes")
	Stack<Enum> stack = new Stack<Enum>();
	
	public Parser(Tokenizer t){
		
		this.t = t;
		
		firstFollowArrays = new FirstFollowArrays();
		
	}

	public Parser(Tokenizer t, PrintWriter pw_token_output_file, PrintWriter pw_derivation_file, PrintWriter pw_syntax_error_file) {
		
		this.t = t;

		firstFollowArrays = new FirstFollowArrays();
		
		this.pw_token_output_file = pw_token_output_file;
		
		this.pw_derivation_file = pw_derivation_file;
		
		this.pw_syntax_error_file = pw_syntax_error_file;
		
	}

	public boolean parse() throws InvalidTokenException {

		stack.push(Symbols.terminals.$);
		stack.push(Symbols.non_terminals.Prog);

		Token tok = getNextTokenAndPrintToTokenOutputFile();

		boolean error = false;

		while (stack.peek() != Symbols.terminals.$) {
			Enum x = stack.peek();
			
			if(pw_derivation_file != null){
				pw_derivation_file.println(x.name());
			}
			

			if (allSymbols.isTerminal(x.name())) {
				if (tok.getTokenName().equalsIgnoreCase(x.name())) {
					stack.pop();
					tok = getNextTokenAndPrintToTokenOutputFile();
				} else if (x.name().equalsIgnoreCase("epsilon")) {
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

		if (!tok.getTokenName().equals("$") || error == true) {
			return false;
		} else {
			return true;
		}

	}

	public void skipErrors(Token tok) throws InvalidTokenException {
		
		if(pw_syntax_error_file != null){
			pw_syntax_error_file.println("Syntax error: " + tok.getTokenLexeme() +  " at line number " + tok.getTokenPosition());
		}
		
		
		tok = getNextTokenAndPrintToTokenOutputFile();
		Enum x = stack.peek();
		
		
		if (tok.getTokenName().equals("$") || firstFollowArrays.followContains(x, tok.getTokenName())) {
			if(pw_syntax_error_file != null){
				pw_syntax_error_file.println("Popping...");
			}
			
			stack.pop();
		} 
		
		else {
			while (firstFollowArrays.firstContains(x, tok.getTokenName())
					|| (firstFollowArrays.firstContains(x, "EPSILON")
							&& firstFollowArrays.followContains(x, tok.getTokenName()))) 
			{
				
				if(pw_syntax_error_file != null){
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
	
	private Token getNextTokenAndPrintToTokenOutputFile() throws InvalidTokenException{
		Token temp;
		temp = t.getNextToken();
		if(pw_token_output_file != null){
			pw_token_output_file.println(temp.toString());
		}
		
		return temp;
	}
}
