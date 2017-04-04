package Semantic;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Stack;

import CodeGenerator.CodeGenerator;
import LexicalAnalyzer.Token;
import Semantic.SemanticStackEntries.InterfaceSemanticStackEntries;
import Semantic.SemanticStackEntries.ParameterEntry;

public class SemanticStack {

	private Stack<InterfaceSemanticStackEntries> semanticStack = new Stack<>();

	private SymbolTable symbolTable;
	
	private CodeGenerator codeGenerator;

	// Constructor
	public SemanticStack() {
		symbolTable = new SymbolTable();
		codeGenerator = new CodeGenerator();
	}

	public SemanticStack(PrintWriter pw_semantic_error_file, PrintWriter pw_symbol_table_file) {
		symbolTable = new SymbolTable(pw_semantic_error_file, pw_symbol_table_file);
		codeGenerator = new CodeGenerator();
	}

	// Used to push terminals
	public void push(InterfaceSemanticStackEntries token) {
		semanticStack.push(token);
	}

	public void push(String semanticAction, int parseCount) {

		switch (parseCount) {
		case 0:
			firstPass(semanticAction);
			break;
		case 1:
			secondPass(semanticAction);
			break;
		case 2:
			break;
		}

	}

	private boolean isSemanticStackTopOfTokenType(String type) {
		InterfaceSemanticStackEntries topOfStack = semanticStack.peek();

		if (topOfStack.getClass() == Token.class) {
			return ((Token) topOfStack).getTokenName().equalsIgnoreCase(type);
		}
		return false;

	}

	private String getVariableDeclTokens() {
		StringBuilder accumulatedSymbols = new StringBuilder();
		String symbolLexeme;

		// gets arrays if available
		while (!isSemanticStackTopOfTokenType("ID")) {
			symbolLexeme = popAndGetTokenLexeme();
			accumulatedSymbols.append(symbolLexeme);
			accumulatedSymbols.append(' ');
		}

		// add id
		symbolLexeme = popAndGetTokenLexeme();
		accumulatedSymbols.append(symbolLexeme);
		accumulatedSymbols.append(' ');

		// add type
		symbolLexeme = popAndGetTokenLexeme();
		accumulatedSymbols.append(symbolLexeme);

		// We need to reverse the words because the stack reverses
		// the token order
		return reverseWords(accumulatedSymbols.toString());
	}

	private ArrayList<InterfaceSemanticStackEntries> getAllParameters() {
		ArrayList<InterfaceSemanticStackEntries> allParameters = new ArrayList<InterfaceSemanticStackEntries>();

		/*
		 * gets all parameters if available discards commas and open parenthesis
		 */
		while (!isSemanticStackTopOfTokenType("ID")) {
			InterfaceSemanticStackEntries topOfStack = semanticStack.pop();
			if (topOfStack.getClass() == ParameterEntry.class) {
				allParameters.add(topOfStack);
			}
		}
		return allParameters;
	}

	private String popAndGetTokenLexeme() {
		return ((Token) semanticStack.pop()).getTokenLexeme();
	}

	private String getAllFunctionParameters(ArrayList<InterfaceSemanticStackEntries> allParameters) {
		StringBuilder allParametersTypes = new StringBuilder();
		for (int i = 0; i < allParameters.size(); i++) {
			String parameter = ((ParameterEntry) allParameters.get(i)).getEntry();
			String[] splitParameter = parameter.split(" ");
			boolean isArray = parameter.endsWith("]");

			if (!isArray) {
				allParametersTypes.append(splitParameter[0]);
			}
			// Is an array
			else {
				StringBuilder sbArray = new StringBuilder();
				// Build the type + array ex: Class[1][2][3]
				sbArray.append(splitParameter[0]);

				// x=2 is the starting position of first '['
				for (int x = 2; x < splitParameter.length; x++) {
					sbArray.append(splitParameter[x]);
				}

				allParametersTypes.append(sbArray.toString());

			}

			allParametersTypes.append(',');

		}

		// remove last comma inserted
		allParametersTypes.setLength(allParametersTypes.length() - 1);

		return allParametersTypes.toString();
	}

	public void printSymbolTable() {
		symbolTable.printSymbolTable();
	}

	// Reverses words in a string
	// Inspired by
	// https://discuss.leetcode.com/category/159/reverse-words-in-a-string
	private String reverseWords(String s) {

		if (s.equals("")) {
			return "";
		}

		String[] array = s.split(" ");

		StringBuilder sb = new StringBuilder();

		if (array.length == 0) {
			return "";
		}

		for (int i = array.length - 1; i >= 0; i--) {
			if (!array[i].equals("")) {
				sb.append(array[i]);
				sb.append(' ');
			}
		}

		sb.setLength(sb.length() - 1);

		return sb.toString();
	}

	public void firstPass(String semanticAction) {
		if (semanticAction.equalsIgnoreCase("closeCurrentScope")) {
			symbolTable.closeCurrentScope();

		}

		else if (semanticAction.equalsIgnoreCase("startClassEntryAndTable")) {

			Token identifierToken = (Token) semanticStack.pop();

			String classIdentifier = identifierToken.getTokenLexeme();
			int locationInParse = identifierToken.getTokenPosition();
			symbolTable.insertClassEntryAndEnterScope(classIdentifier, locationInParse);

		}

		else if (semanticAction.equalsIgnoreCase("createVariableEntry")) {

			int locationInParse = ((Token) semanticStack.peek()).getTokenPosition();

			String accumulatedString = getVariableDeclTokens();

			symbolTable.insertEntry("variable", accumulatedString, locationInParse);

		}

		// Pushes parameter entry in the semanticStack so the createFuncTable
		// gets it from the stack.
		else if (semanticAction.equalsIgnoreCase("createParameterEntry")) {

			// int locationInParse = ((Token)
			// semanticStack.peek()).getTokenPosition();

			String accumulatedString = getVariableDeclTokens();

			InterfaceSemanticStackEntries parameterEntry = new ParameterEntry(accumulatedString);

			semanticStack.push(parameterEntry);

		}

		else if (semanticAction.equalsIgnoreCase("createFuncTable")) {

			int locationInParse = ((Token) semanticStack.peek()).getTokenPosition();
			ArrayList<InterfaceSemanticStackEntries> allParameters = getAllParameters();
			int numberOfParameters = allParameters.size();

			// Order of pop matters
			String functionName = popAndGetTokenLexeme();
			String returnType = popAndGetTokenLexeme();

			if (numberOfParameters == 0) {
				symbolTable.insertFunctionWithoutParametersAndEnterScope(returnType, functionName, locationInParse);
			} else {
				String allParametersTypes = getAllFunctionParameters(allParameters);

				symbolTable.insertFunctionWithParametersAndEnterScope(returnType, functionName, numberOfParameters,
						allParametersTypes, locationInParse);

				for (InterfaceSemanticStackEntries parameter : allParameters) {
					ParameterEntry parameterEntry = (ParameterEntry) parameter;
					symbolTable.insertEntry("parameter", parameterEntry.getEntry(), locationInParse);
				}

			}
		}

		else if (semanticAction.equalsIgnoreCase("CreateProgramFunction")) {

			int locationInParse = ((Token) semanticStack.peek()).getTokenPosition();

			symbolTable.insertProgramFunctionAndEnterScope(locationInParse);

		} else if (semanticAction.equalsIgnoreCase("CheckProperlyDeclaredAll")) {

			symbolTable.checkIfAllIsProperlyDeclared();

		}

	}

	private void secondPass(String semanticAction) {

		if (semanticAction.equalsIgnoreCase("closeCurrentScope")) {
			symbolTable.closeCurrentScope();

		}

		else if (semanticAction.equalsIgnoreCase("GenerateVariableEntry")) {

			String variableDeclationTokens = getVariableDeclTokens();

			String[] varDeclSplit = variableDeclationTokens.split(" ");
			String type = varDeclSplit[0];
			String identifier = varDeclSplit[1];
			String codeGeneratedIdentifierName = "";

			if (type.equalsIgnoreCase("int") && !variableDeclationTokens.contains("[")) {
				codeGeneratedIdentifierName = codeGenerator.generateIntegerDeclaration(identifier);
			}

			// insert generated unique variable into symbol table
			symbolTable.enterScopeAndSetCodeGeneratedIdentifierName(identifier, codeGeneratedIdentifierName);

		}

		else if (semanticAction.equalsIgnoreCase("CheckVariable")) {

			ArrayList<Token> variable = getVariable();
			int size = variable.size();

			if (size == 1) {
				symbolTable.searchHigherScopesSingleVariableOnly(variable.get(0));
			}

		}
		/*
		 * These are used only to enter in the scope
		 */
		else if (semanticAction.equalsIgnoreCase("startClassEntryAndTable")) {

			Token identifierToken = (Token) semanticStack.pop();

			String classIdentifier = identifierToken.getTokenLexeme();

			symbolTable.enterScope(classIdentifier);

		}

		else if (semanticAction.equalsIgnoreCase("createParameterEntry")) {

			// int locationInParse = ((Token)
			// semanticStack.peek()).getTokenPosition();

			String accumulatedString = getVariableDeclTokens();

			InterfaceSemanticStackEntries parameterEntry = new ParameterEntry(accumulatedString);

			semanticStack.push(parameterEntry);

		}

		else if (semanticAction.equalsIgnoreCase("createFuncTable")) {

			int locationInParse = ((Token) semanticStack.peek()).getTokenPosition();
			ArrayList<InterfaceSemanticStackEntries> allParameters = getAllParameters();
			int numberOfParameters = allParameters.size();

			// Order of pop matters
			String functionName = popAndGetTokenLexeme();
			String returnType = popAndGetTokenLexeme();

			symbolTable.enterScope(functionName);

		}

		else if (semanticAction.equalsIgnoreCase("CreateProgramFunction")) {

			symbolTable.enterScope("program");

		}
	}

	public void closeCodeGenerationOutputFile() {
		codeGenerator.closeCodeGenerationOutputFile();
	}

	/*
	 * Gets tokens from the stack
	 */
	private ArrayList<Token> getVariable() {

		ArrayList<Token> listOfTokens = new ArrayList<Token>();

		while (true) {
			InterfaceSemanticStackEntries topOfStack = null;
			while (!isSemanticStackTopOfTokenType("ID")) {
				addTokenToArrayListForVariableChecking(listOfTokens, topOfStack);
			}

			// append id too
			addTokenToArrayListForVariableChecking(listOfTokens, topOfStack);

			// If we have a dot, then there is more to accumulate
			if (isSemanticStackTopOfTokenType("dot")) {
				continue;
			} else {
				break;
			}

		}

		Collections.reverse(listOfTokens);
		return listOfTokens;

	}

	/*
	 * used for variableChecking
	 */
	private void addTokenToArrayListForVariableChecking(ArrayList<Token> listOfTokens,
			InterfaceSemanticStackEntries topOfStack) {
		topOfStack = semanticStack.pop();
		if (topOfStack.getClass() == Token.class) {
			Token top = (Token) topOfStack;
			listOfTokens.add(top);
		}
	}

}
