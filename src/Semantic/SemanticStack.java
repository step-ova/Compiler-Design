package Semantic;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Stack;
import java.util.regex.Pattern;

import SyntacticAnalyzer.Symbols;

public class SemanticStack {

	private Symbols allSymbols = new Symbols();

	private SymbolTable symbolTable; 

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
		symbolTable = new SymbolTable();
	}
	
	public SemanticStack(PrintWriter pw_semantic_error_file, PrintWriter pw_symbol_table_file){
		symbolTable = new SymbolTable(pw_semantic_error_file, pw_symbol_table_file);
	}
	
	
	public void push(Object item, int locationInParse) {

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
				
			} else if (symbol.equalsIgnoreCase("closeCurrentScope")) {
				symbolTable.closeCurrentScope();
				
			} else if (symbol.equalsIgnoreCase("transferAccumulation")) {
				//Transfers accumulation because it is a function definition
				accumulatedParametersFlag = true;
				accumulatedParametersArrayList.add(new StringBuilder());
				accumulatedParametersArrayList.get(0).append(accumulatedSymbols.toString());
				
				accumulateSymbolsFlag = false;
				accumulatedSymbols.setLength(0); //reset accumulated symbols
				
			}

			/*
			 * These actions below insert something in the symbol table
			 */
			
			else if (symbol.equalsIgnoreCase("startClassEntryAndTable")) {
				String[] accumulatedSymbolsArray = accumulatedSymbols.toString().trim().split(" ");
				
				String classIdentifier = accumulatedSymbolsArray[1];
				symbolTable.insertClassEntryAndEnterScope(classIdentifier, locationInParse);
				
				accumulatedSymbols.setLength(0); //reset stringbuilder
				
			} 
			
			else if (symbol.equalsIgnoreCase("createFuncTable")) {
				
				int numberOfParameters = accumulatedParametersArrayList.size()-1;
				String[] funcDefSplit = accumulatedParametersArrayList.get(0).toString().trim().split(" ");
				String returnType = funcDefSplit[0];
				String functionName = funcDefSplit[1]; 
				
				
				if(numberOfParameters == 0){
					symbolTable.insertFunctionWithoutParametersAndEnterScope(returnType, functionName, locationInParse);
				}
				else{
					String allParametersTypes = getAllFunctionParameters();
					
					//create function table and enter scope
					symbolTable.insertFunctionWithParametersAndEnterScope(returnType, functionName, numberOfParameters, allParametersTypes, locationInParse);
					
					//add all parameters in function scope
					for(int i = 1; i< accumulatedParametersArrayList.size(); i++){
						String parameter = accumulatedParametersArrayList.get(i).toString().trim();
						symbolTable.insertEntry("parameter", parameter, locationInParse);
						
					}
				}
				
				accumulatedParametersArrayList = new ArrayList<StringBuilder>();
				
			} 
			
			else if (symbol.equalsIgnoreCase("createVariableEntry")) {
				String accumulatedString = accumulatedSymbols.toString().trim();
				
				symbolTable.insertEntry("variable", accumulatedString, locationInParse);
				
				accumulatedSymbols.setLength(0); //reset stringbuilder
				
			} 
			
			else if(symbol.equalsIgnoreCase("CreateProgramFunction")){
				symbolTable.insertProgramFunctionAndEnterScope();
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

			}
		}

	}

	public boolean isGetSymbolsFlagOn() {
		return (accumulateSymbolsFlag || accumulatedParametersFlag);
	}

	public void printSymbolTable() {
		symbolTable.printSymbolTable();
	}
	
	/*
	 * retuns all parameter types in the functionDefinition
	 * ex: int[2][2],myClass2[3],float,myClass3
	 */
	private String getAllFunctionParameters(){
		StringBuilder allParametersTypes = new StringBuilder();
		for(int i = 1; i < accumulatedParametersArrayList.size(); i++){
			String parameter = accumulatedParametersArrayList.get(i).toString().trim();
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

}
