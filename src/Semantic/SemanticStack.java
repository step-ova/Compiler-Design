package Semantic;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Stack;
import java.util.regex.Pattern;

import SyntacticAnalyzer.Symbols;

@SuppressWarnings("serial")
public class SemanticStack extends Stack<Object> {

	private Symbols allSymbols = new Symbols();

	private SymbolTable symbolTable = new SymbolTable();

	// flag used in parser to accumulate symbols
	// create an STSEntry and add it to the STS
	private boolean accumulateSymbolsFlag;
	private StringBuilder accumulatedSymbols = new StringBuilder();
	
	//Flag used to accumulate function and it's parameters
	private boolean accumulatedParametersFlag;
	//Contains a function's return type, identifier and all parameters
	private ArrayList<StringBuilder> accumulatedParametersArrayList = new ArrayList<StringBuilder>(); 


	// Constructor
	public SemanticStack() {
		super();
	}

	@Override
	public Object push(Object item) {
		Object pushedEntry = super.push(item);

		String symbol = (String) item;

		// check if semantic action
		if (allSymbols.isSemanticAction(symbol)) {

			if (symbol.equalsIgnoreCase("startAccumulation")) {
				accumulateSymbolsFlag = true;

			} else if (symbol.equalsIgnoreCase("stopAccumulation")) {
				accumulateSymbolsFlag = false;

			}  else if(symbol.equalsIgnoreCase("startParameterAccumulation")){
				accumulatedParametersFlag = true;
				accumulatedParametersArrayList.add(new StringBuilder());
				
			} else if(symbol.equalsIgnoreCase("stopParameterAccumulation")){
				accumulatedParametersFlag = false;
				
			}else if (symbol.equalsIgnoreCase("closeCurrentScope")) {
				symbolTable.closeCurrentScope();
				
			} else if (semanticActionInsertsSomethingInST(symbol)) {
				insertEntry(symbol);
				
			}
		} 
		//is symbol
		else {
			if (accumulateSymbolsFlag) {
				accumulatedSymbols.append(symbol);
				accumulatedSymbols.append(" ");
			}
			else if(accumulatedParametersFlag){
				//Get last added parameter in the arraylist
				StringBuilder lastSB = accumulatedParametersArrayList.get(accumulatedParametersArrayList.size() -1);
				
				lastSB.append(symbol);
				lastSB.append(" ");
				
//				//We do not add comma or openparen or closeparen because they are useless
//				if(! (symbol.equals(",") || symbol.equals("(") || symbol.equals(")")) ){
//					lastSB.append(symbol);
//					lastSB.append(" ");
//				}
			}
		}

		return pushedEntry;

	}

	public boolean isGetSymbolsFlagOn() {
		return accumulateSymbolsFlag;
	}

	private boolean semanticActionInsertsSomethingInST(String semanticAction) {

		String[] actions = { "startClassEntryAndTable" };

		return stringArrayContainsIgnoreCase(actions, semanticAction);
	}
	
	private void insertEntry(String semanticAction){
		
		
		if(semanticAction.equalsIgnoreCase("startClassEntryAndTable")){
			
			String[] accumulatedSymbolsArray = accumulatedSymbols.toString().trim().split(" ");
			
			String type = accumulatedSymbolsArray[0];
			String classIdentifier = accumulatedSymbolsArray[1];
			symbolTable.insertEntry(type, classIdentifier);
		}
		else if(semanticAction.equalsIgnoreCase("createFuncTable")){
			
			//TODO:
			String funcDef = accumulatedParametersArrayList.get(0).toString();
			String functionIdentifier =  null;
			
			
		}
		
		accumulatedSymbols.setLength(0); //reset stringbuilder
		
	}
	

//	private boolean startAccumulation(String semanticAction) {
//
//		String[] setFlagToTrueActions = { "startAccumulation" };
//
//		return stringArrayContainsIgnoreCase(setFlagToTrueActions, semanticAction);
//
//	}

	private boolean stringArrayContainsIgnoreCase(String[] actions, String semanticAction) {

		for (String s : actions) {
			if (s.equalsIgnoreCase(semanticAction)) {
				return true;
			}
		}

		return false;
	}

	public void printSymbolTable() {
		symbolTable.printSymbolTable();
	}

}
