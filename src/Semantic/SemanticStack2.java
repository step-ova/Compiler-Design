package Semantic;

import java.util.ArrayList;
import java.util.Stack;

import LexicalAnalyzer.Token;
import Semantic.SemanticStackEntries.InterfaceSemanticStackEntries;
import Semantic.SemanticStackEntries.ParameterEntry;
import SyntacticAnalyzer.Symbols;

public class SemanticStack2 {

	private Stack<InterfaceSemanticStackEntries> semanticStack = new Stack<>();

	private SymbolTable symbolTable;

	// Used to push terminals
	public void push(InterfaceSemanticStackEntries token) {
		semanticStack.push(token);
	}

	public void push(String semanticAction) {
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

			int locationInParse = ((Token) semanticStack.peek()).getTokenPosition();

			String accumulatedString = getVariableDeclTokens();

			InterfaceSemanticStackEntries parameterEntry = new ParameterEntry(accumulatedString);

			semanticStack.push(parameterEntry);

		}

		else if (semanticAction.equalsIgnoreCase("createFuncTable")) {
			
			int locationInParse = ((Token) semanticStack.peek()).getTokenPosition();
			ArrayList<InterfaceSemanticStackEntries> allParameters = getAllParameters();
			int numberOfParameters = allParameters.size();
			
			//Order of pop matters
			String functionName = popAndGetTokenLexeme();
			String returnType = popAndGetTokenLexeme();
			
			
			
			
			if(numberOfParameters == 0){
				symbolTable.insertFunctionWithoutParametersAndEnterScope(returnType, functionName, locationInParse);
			}
			else{
				String allParametersTypes = getAllFunctionParameters(allParameters);
				
				symbolTable.insertFunctionWithParametersAndEnterScope(returnType, functionName, numberOfParameters, allParametersTypes, locationInParse);
				
				for(InterfaceSemanticStackEntries parameter : allParameters){
					ParameterEntry parameterEntry = (ParameterEntry) parameter;
					symbolTable.insertEntry("parameter", parameterEntry.getEntry(), locationInParse);
				}
				
			}
		}
		
		else if (semanticAction.equalsIgnoreCase("CreateProgramFunction")) {

			symbolTable.insertProgramFunctionAndEnterScope();

		}	
	}

	

	private boolean isSemanticStackTopOfTokenType(String type) {
		return ((Token) semanticStack.peek()).getTokenName().equalsIgnoreCase(type);
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

		return accumulatedSymbols.toString();
	}

	private ArrayList<InterfaceSemanticStackEntries> getAllParameters() {
		ArrayList<InterfaceSemanticStackEntries> allParameters = new ArrayList<InterfaceSemanticStackEntries>();

		/*
		 * gets all parameters if available
		 * discards commas and open parenthesis
		 */
		while (!isSemanticStackTopOfTokenType("ID")) {
			InterfaceSemanticStackEntries topOfStack = semanticStack.pop();
			if(topOfStack.getClass() == ParameterEntry.class){
				allParameters.add(topOfStack);
			}
		}
		return allParameters;
	}
	
	private String popAndGetTokenLexeme(){
		return ((Token) semanticStack.pop()).getTokenLexeme();
	}

	private String getAllFunctionParameters(ArrayList<InterfaceSemanticStackEntries> allParameters) {
		StringBuilder allParametersTypes = new StringBuilder();
		for(int i = 0; i < allParameters.size(); i++){
			String parameter = ((ParameterEntry) allParameters.get(i)).getEntry();
			String[] splitParameter = parameter.split(" ");
			boolean isArray = parameter.endsWith("]");
			
			if(!isArray){
				allParametersTypes.append(splitParameter[0]);
			}
			//Is an array
			else{
				StringBuilder sbArray = new StringBuilder();
				//Build the type + array ex: Class[1][2][3]
				sbArray.append(splitParameter[0]);
				
				// x=2 is the starting position of first '['
				for(int x = 2; x< splitParameter.length; x++){
					sbArray.append(splitParameter[x]);
				}
				
				allParametersTypes.append(sbArray.toString());
				
				
			}
			
			allParametersTypes.append(',');
			
		}
		
		//remove last comma inserted
		allParametersTypes.setLength(allParametersTypes.length()-1);
		
		return allParametersTypes.toString();
	}
	
	public void printSymbolTable() {
		symbolTable.printSymbolTable();
	}
	
}
