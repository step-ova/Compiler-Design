package SyntacticAnalyzer;

public class Symbols {
	
	
	
	/*
	 * Order is important. 
	 * Same order as symbol table
	 */
	public enum terminals{
		
		CLASS,
		ID,
		OPENCURLYBRAC,
		CLOSECURLYBRAC,
		SEMICOLON,
		PROGRAM,
		OPENPAREN,
		CLOSEPAREN,
		IF,
		THEN, 
		ELSE, 
		FOR,
		GET,
		PUT,
		RETURN,
		PLUSSIGN,
		MINUSSIGN,
		NOT,
		DOT,
		OPENBRAC,
		CLOSEBRAC,
		INTEGER,
		INT,
		FLOAT,
		COMMA,
		EQUAL,
		EQUALEQUAL,
		NOTEQUAL,
		LESSTHAN,
		GREATERTHAN, 
		LESSTHANEQUAL,
		GREATERTHANEQUAL, 
		OR,
		MULTIPLYSIGN,
		DIVIDESIGN,
		AND,
		
		$, //end of file
		EPSILON
	}
	
	
	
	/*
	 * Order is important. 
	 * Same order as symbol table
	 */
	public enum non_terminals{
		Prog,
		ClassDeclStar,
		ClassDecl,
		VarDeclStarFuncDefStar,
		VarA,
		FuncDefStar,
		ProgBody,
		FuncHead,
		FuncDef,
		FuncDefWithoutTypeId,
		FuncBody,
		StatementStar,
		VarDeclStarstatementStar,
		A1,
		A2,
		VarDeclWithoutTypeId,
		ArraySizeStar,
		Statement,
		StatementWithoutAssignStat,
		AssignStat,
		AssignStatWithoutVariable,
		StatBlock,
		Expr,
		RelExpr2,
		RelExpr,
		ArithExpr,
		ArithExpr1,
		Sign,
		Term,
		Term1,
		Factor,
		VariableFactor,
		Var1,
		Variable,
		Var2,
		Indice,
		ArraySize,
		Type,
		TypeWithoutId,
		FParams,
		FParamsTailStar,
		AParams,
		AParamsTailsStar,
		FParamsTail,
		AParamsTails,
		AssignOp,
		RelOp,
		AddOp,
		MultOp,
		Num
	}
	
	public enum semantic_actions{
		
		
		
		startClassEntryAndTable,
		closeCurrentScope,
		createVariableEntry,
		createFuncTable,
		CreateProgramFunction,
		CreateParameterEntry,
		CheckProperlyDeclaredAll,
		GenerateVariableEntry,
		CheckVariable ,
		CheckFunction,

	
	}
	
	private enum TYPE{
		TERMINAL,
		NON_TERMINAL,
		SEMANTIC_ACTION
	}
	
	
	
	public boolean isTerminal(String symbol) {
		return isType(TYPE.TERMINAL, symbol);
	}

	public boolean isNonTerminal(String symbol) {
		return isType(TYPE.NON_TERMINAL, symbol);
	}
	
	public boolean isSemanticAction(String symbol) {
		return isType(TYPE.SEMANTIC_ACTION, symbol);
	}

	// Type terminal == 1
	// type non_terminal == 2
	private boolean isType(TYPE type, String symbol) {

		Object[] enumValues = null;

		if (type == TYPE.SEMANTIC_ACTION) {
			enumValues = semantic_actions.class.getEnumConstants();
		} else if (type == TYPE.TERMINAL) {
			enumValues = terminals.class.getEnumConstants();
		} else if (type == TYPE.NON_TERMINAL) {
			enumValues = non_terminals.class.getEnumConstants();
		} 

		for (Object e : enumValues) {
			if (((Enum) e).name().equalsIgnoreCase(symbol)) {
				return true;
			}
		}

		return false;

	}

	
	// returns -1 if terminal not found
	public int getTerminalIndex(String symbol) {
		return getTypeIndex(TYPE.TERMINAL, symbol);
	}
	
	public int getNonTerminalIndex(String symbol){
		return getTypeIndex(TYPE.NON_TERMINAL, symbol);
	}
	
	private int getSemanticActionIndex(String symbol) {
		return getTypeIndex(TYPE.SEMANTIC_ACTION, symbol);
	}
	
	//return -1 if not found
	private int getTypeIndex(TYPE type, String symbol){
		
		Enum[] enumValues = null;
		
		if (type == TYPE.TERMINAL) {
			enumValues = terminals.values();
		} else if (type == TYPE.NON_TERMINAL) {
			enumValues = non_terminals.values();
		}else if (type == TYPE.SEMANTIC_ACTION) {
			enumValues = semantic_actions.values();
		}
		
		for (int i = 0; i < enumValues.length; i++) {
			if (enumValues[i].name().equalsIgnoreCase(symbol)) {
				return i;
			}
		}
		return -1;
	}
	
	public Enum getSymbol(String symbol){
		
		int terminalIndex = getTerminalIndex(symbol);
		int nonTerminalIndex = getNonTerminalIndex(symbol);
		int semanticActionIndex = getSemanticActionIndex(symbol);
		
		if(terminalIndex != -1){
			return terminals.values()[terminalIndex];
		}
		else if(nonTerminalIndex != -1){
			return non_terminals.values()[nonTerminalIndex];
		}
		else {
			return semantic_actions.values()[semanticActionIndex];
		}
	}

	
	
}
