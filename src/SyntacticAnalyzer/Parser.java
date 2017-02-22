package SyntacticAnalyzer;

import java.util.ArrayList;
import java.util.Stack;

import LexicalAnalyzer.InvalidTokenException;
import LexicalAnalyzer.Token;
import LexicalAnalyzer.Tokenizer;

public class Parser {
	
	private Symbols allSymbols = new Symbols(); 
	private SymbolTable symboltable = new SymbolTable();
	
	private Tokenizer t;
	
	@SuppressWarnings("rawtypes")
	Stack<Enum> stack = new Stack<Enum>();
	
	public Parser(Tokenizer t){
		this.t = t;
	}
	
	public boolean parse() throws InvalidTokenException{
		
		stack.push(Symbols.terminals.$);
		stack.push(Symbols.non_terminals.Prog);
		
		Token tok = t.getNextToken();
		
		boolean error = false;
		
		while(stack.peek() != Symbols.terminals.$){
			Enum x = stack.peek();
			
			//TODO: handle epsilon case
			if(allSymbols.isTerminal(x.name())){
				if(tok.toString().equalsIgnoreCase(x.name())){
					stack.pop();
					tok = t.getNextToken();
				}
				else if(x.name().equalsIgnoreCase("epsilon")){
					stack.pop();
				}
				else{
					skipErrors();
					error = true;
				}
			}
			
			else{
				int nonTerminalSymbolTableIndex = allSymbols.getNonTerminalIndex(x.name());
				int terminalSymbolTableIndex = allSymbols.getTerminalIndex(tok.toString());
				
				if(!symboltable.isError(nonTerminalSymbolTableIndex, terminalSymbolTableIndex)){
					stack.pop();
					inverseRHSPush(symboltable.getRule(nonTerminalSymbolTableIndex, terminalSymbolTableIndex));
				}
				else{
					skipErrors();
					error = true;
				}
				
			}
		}
		
		if(tok.toString() != null || error == true){
			return false;
		}
		else{
			return true;	
		}

		
	}
	
	
	public void skipErrors(){
		
	}
	
	public void inverseRHSPush(ArrayList<Enum> rule){
		for(int i = rule.size() - 1; i>=0; i--){
			stack.push(rule.get(i));
		}
	}
}
