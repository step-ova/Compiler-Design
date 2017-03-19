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
	// create a SymbolTableEntry and add it to the SymbolTable
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

			} else if(symbol.equalsIgnoreCase("cancelAccumulation")){
				accumulateSymbolsFlag = false;
				accumulatedSymbols.setLength(0); //reset accumulated symbols
				
			}else if(symbol.equalsIgnoreCase("startParameterAccumulation")){
				accumulatedParametersFlag = true;
				accumulatedParametersArrayList.add(new StringBuilder());
				
			} else if(symbol.equalsIgnoreCase("stopParameterAccumulation")){
				accumulatedParametersFlag = false;
				
			}else if (symbol.equalsIgnoreCase("closeCurrentScope")) {
				symbolTable.closeCurrentScope();
				
			} 

			/*
			 * These actions below insert something in the symbol table
			 */
			
			else if (symbol.equalsIgnoreCase("startClassEntryAndTable")) {
				String[] accumulatedSymbolsArray = accumulatedSymbols.toString().trim().split(" ");
				
				String type = accumulatedSymbolsArray[0];
				String classIdentifier = accumulatedSymbolsArray[1];
				symbolTable.insertEntry(type, classIdentifier);
				
				accumulatedSymbols.setLength(0); //reset stringbuilder
				
			} 
			
			else if (symbol.equalsIgnoreCase("createFuncTable")) {
				
				//TODO:
				String funcDef = accumulatedParametersArrayList.get(0).toString();
				String functionIdentifier =  null;
				
				
			} 
			
			else if (symbol.equalsIgnoreCase("createVariableEntry")) {
				String accumulatedString = accumulatedSymbols.toString().trim();
				int firstSeperation =  accumulatedString.indexOf(" ");
				
				String type = accumulatedString.substring(0, firstSeperation);
				
				symbolTable.insertEntry("variable", accumulatedString);
				
				if(type.equalsIgnoreCase("int") || type.equalsIgnoreCase("float")){
					
				}
				
				//we have a class variable
				else{
					
				}
				
			} 
			
		} 
		
		
		/*
		 * is symbol
		 */
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

	public void printSymbolTable() {
		symbolTable.printSymbolTable();
	}

}
