package SyntacticAnalyzer;

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

	@SuppressWarnings("rawtypes")
	Stack<Enum> stack = new Stack<Enum>();

	public Parser(Tokenizer t) {
		this.t = t;

		firstFollowArrays = new FirstFollowArrays();
	}

	public boolean parse() throws InvalidTokenException {

		stack.push(Symbols.terminals.$);
		stack.push(Symbols.non_terminals.Prog);

		Token tok = t.getNextToken();

		boolean error = false;

		while (stack.peek() != Symbols.terminals.$) {
			Enum x = stack.peek();
			System.out.println(x.name());

			if (allSymbols.isTerminal(x.name())) {
				if (tok.getTokenName().equalsIgnoreCase(x.name())) {
					stack.pop();
					tok = t.getNextToken();
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
		System.out.println("Syntax error at: " + tok.getTokenPosition());

		Enum x = stack.peek();

		
		if (tok.getTokenName().equals("$") || firstFollowArrays.followContains(x, tok.getTokenName())) {
			stack.pop();
		} else {
			while (firstFollowArrays.firstContains(x, tok.getTokenName())
					|| (firstFollowArrays.firstContains(x, "EPSILON")
							&& firstFollowArrays.followContains(x, tok.getTokenName()))) 
			{
				tok = t.getNextToken();
			}
		}
	}

	public void inverseRHSPush(ArrayList<Enum> rule) {
		for (int i = rule.size() - 1; i >= 0; i--) {
			stack.push(rule.get(i));
		}
	}
}
