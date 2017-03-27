package Semantic;

import java.io.PrintWriter;

public class SymbolTable {

	// parent = null
	private SymbolTableScope globalScope = new SymbolTableScope("GlobalScope", null);

	private SymbolTableScope currentScope = globalScope;

	private PrintWriter pw_semantic_error_file;
	private PrintWriter pw_symbol_table_file;

	public SymbolTable() {

	}

	public SymbolTable(PrintWriter pw_semantic_error_file, PrintWriter pw_symbol_table_file) {
		this.pw_semantic_error_file = pw_semantic_error_file;
		this.pw_symbol_table_file = pw_symbol_table_file;
	}

	/*
	 * Recursively goes upward to find the symbol Returns false if symbol is not
	 * found Returns true if symbol found
	 */
	public boolean searchHigherScopes(SymbolTableScope symtblScope, String symbol) {
		SymbolTableScope searchCurrentScope = symtblScope;
		boolean hasSymbol = searchCurrentScope.hasSymbol(symbol);

		while (!hasSymbol) {
			searchCurrentScope = searchCurrentScope.getParentScope();

			if (searchCurrentScope == null) {
				return false;
			}

			hasSymbol = searchCurrentScope.hasSymbol(symbol);
		}

		return hasSymbol;

	}
	
	/*
	 * Searches the given scope if a symbol with the same type exists
	 */
	public boolean search(SymbolTableScope symtblScope, String symbol, AbstractSymbolTableScopeEntry entry){
		Class<?> currentEntryClass = entry.getClass();
		
		
		if(symtblScope.hasSymbol(symbol)){
			AbstractSymbolTableScopeEntry searchEntry = symtblScope.getScopeEntry(symbol);
			Class<?> searchEntryClass = searchEntry.getClass();

			//if both entries are of the same type then we have to check their structure/return type etc
			if(searchEntryClass == currentEntryClass){
				
				if(currentEntryClass == EntryVariableST.class){
					EntryVariableST check1 = (EntryVariableST) searchEntry;
					EntryVariableST check2 = (EntryVariableST) entry;
					
					//TODO: verify what is supposed to be equal
					
					String b = check1.getStructure();
					int c = check1.getNumberOfDimensions();
					String d = check1.getType().substring(0,1); //get first characters
					
					String f = check2.getStructure();
					int g = check2.getNumberOfDimensions();
					String h = check2.getType().substring(0,1); //get first characters
					
					return(b.equals(f) && c >= g);
					
					
				}
				
				else if(currentEntryClass == null){
					
				}
			}
			return true;
			
		}
		return false;
	}

	public void insertClassEntryAndEnterScope(String identifier, int locationOfParse) {

		if (searchHigherScopes(currentScope, identifier)) {

			String error = "Semantic error: Duplicate class found: " + identifier + " (line " +locationOfParse + ")";

			pw_semantic_error_file.println(error);
			System.out.println(error);

		}

		String identifierScope = identifier + "Scope";
		// Create new scope with parent as current scope
		SymbolTableScope classScope = new SymbolTableScope(identifierScope, currentScope);

		// create a new class entry for the current table with a child as
		// the classScope
		AbstractSymbolTableScopeEntry scopeEntry = new EntryClassST(true, classScope);
		currentScope.insert(identifier, scopeEntry);

		// Change the scope to the new created scope
		currentScope = classScope;
	}

	public void insertFunctionWithoutParametersAndEnterScope(String returnType, String functionName, int locationOfParse) {

		if (searchHigherScopes(currentScope, functionName)) {

			String error = "Duplicate identifier found for function name: " + functionName + " (line " +locationOfParse + ")";

			pw_semantic_error_file.println(error);
			System.out.println(error);

		}

		String identifierScope = functionName + "Scope";

		SymbolTableScope functionScope = new SymbolTableScope(identifierScope, currentScope);

		AbstractSymbolTableScopeEntry functionEntry = new EntryFunctionST(true, functionScope, returnType, 0, null);
		currentScope.insert(functionName, functionEntry);

		currentScope = functionScope;

	}

	public void insertFunctionWithParametersAndEnterScope(String returnType, String functionName,
			int numberOfParameters, String parameters, int locationOfParse) {

		if (searchHigherScopes(currentScope, functionName)) {

			String error = "Duplicate identifier found for function name: " + functionName + " (line " +locationOfParse + ")";

			pw_semantic_error_file.println(error);
			System.out.println(error);

		}

		String identifierScope = functionName + "Scope";

		SymbolTableScope functionScope = new SymbolTableScope(identifierScope, currentScope);

		AbstractSymbolTableScopeEntry functionEntry = new EntryFunctionST(true, functionScope, returnType,
				numberOfParameters, parameters);
		currentScope.insert(functionName, functionEntry);

		currentScope = functionScope;
	}

	public void insertProgramFunctionAndEnterScope() {

		// Create new scope with parent as current scope
		SymbolTableScope classScope = new SymbolTableScope("programScope", currentScope);

		// create a new program entry for the current table with a child as the
		// programScope
		AbstractSymbolTableScopeEntry scopeEntry = new EntryClassST(true, classScope);
		currentScope.insert("program", scopeEntry);

		// Change the scope to the new created scope
		currentScope = classScope;
	}

	/*
	 * inserts Variable or parameter
	 */
	public void insertEntry(String kindOfVariable, String identifier, int locationOfParse) {

		if (kindOfVariable.equals("variable") || kindOfVariable.equals("parameter")) {

			String[] identifierArray = identifier.split(" ");

			if (searchHigherScopes(currentScope, identifierArray[1])) {
				String error;
				if(kindOfVariable.equals("variable")){
					error = "Duplicate variable found: " + identifierArray[1] + " (line " +locationOfParse + ")" ;
				}
				else{
					error = "Duplicate parmeter found: " + identifierArray[1] + " (line " +locationOfParse + ")";
				}

				pw_semantic_error_file.println(error);
				System.out.println(error);
			}

			// True if "int" or "float", false if it is class
			boolean properlyDefinied = checkIfIntOrFloat(identifierArray[0]);
			// 0 if no dimensions array
			int numberOfDimensions = checkNumberOfDimensionsArray(identifier);
			String structure;

			if (!properlyDefinied) {
				structure = "class";
			} else if (numberOfDimensions != 0) {
				structure = "array";
			} else {
				structure = "number";
			}
			
			AbstractSymbolTableScopeEntry variableEntry;

			if (numberOfDimensions == 0) {
				variableEntry = new EntryVariableST(properlyDefinied, null, kindOfVariable, structure,
						identifierArray[0], numberOfDimensions);
			} else {
				String type = getVariableOrParameterType(identifier);
				variableEntry = new EntryVariableST(properlyDefinied, null, kindOfVariable, structure, type,
						numberOfDimensions);
			}

			// new variable entry with no child (null)
			currentScope.insert(identifierArray[1], variableEntry);
		}

	}

	/*
	 * Go to the parent scope if not null, If null then we are at global scope
	 * i.e do nothing
	 */
	public void closeCurrentScope() {
		if (currentScope.getParentScope() != null) {
			currentScope = currentScope.getParentScope();
		}
	}

	
	public void deleteScope(SymbolTableScope scope) {
		scope = null;
	}

	/*
	 * Prints symbol table
	 */
	public void printSymbolTable() {
		printScopeValues(globalScope, 0);
	}

	/*
	 * print symbol table with tabs
	 */
	private void printScopeValues(SymbolTableScope scope, int numberOfTabCharacthers) {

		StringBuilder tabs = new StringBuilder();
		for (int i = 0; i < numberOfTabCharacthers; i++) {
			tabs.append('\t');
		}

		System.out.println(tabs.toString() + scope.getScopeName() + "->");
		pw_symbol_table_file.println(tabs.toString() + scope.getScopeName() + "->");

		tabs.append('\t');

		scope.getTableEntries().forEach((k, v) -> {
			System.out.println(tabs.toString() + "Name: " + k + " " + v.printEntry());
			pw_symbol_table_file.println(tabs.toString() + "Name: " + k + " " + v.printEntry());
			if (v.hasChildScope()) {
				printScopeValues(v.getChildScope(), numberOfTabCharacthers + 2);
			}

		});
	}

	/*
	 * Finds the number of occurrences of pattern in value
	 */
	private int findNumberOfOccurences(String value, String pattern) {
		int lastIndex = 0;
		int count = 0;

		while (lastIndex != -1) {

			lastIndex = value.indexOf(pattern, lastIndex);

			if (lastIndex != -1) {
				count++;
				lastIndex += pattern.length();
			}
		}

		return count;
	}

	// True if is int or float, false if class like "MyClass2"
	private boolean checkIfIntOrFloat(String type) {
		return (type.equals("int") || type.equals("float"));
	}

	/*
	 * returns 0 if the array has no dimensions
	 */
	private int checkNumberOfDimensionsArray(String identifier) {
		// If we have an array
		if (identifier.endsWith("]")) {
			// Get the number of dimensions by look at the number of open
			// brackets
			return findNumberOfOccurences(identifier, "[");
		}

		return 0;

	}

	/*
	 * Gets the type for a variable or parameter
	 */
	private String getVariableOrParameterType(String identifier) {
		StringBuilder returnType = new StringBuilder();
		String trimmedIdentifier = identifier.trim();
		String[] splitIdentifier = trimmedIdentifier.split(" ");

		returnType.append(splitIdentifier[0]);

		for (int i = 2; i < splitIdentifier.length; i++) {
			returnType.append(splitIdentifier[i]);
		}

		return returnType.toString();
	}
	
	private boolean checkIfClassNameIsDefinied(String symbol){
		
		 if(globalScope.getTableEntries().containsKey(symbol)){
			 if(globalScope.getTableEntries().get(symbol) instanceof EntryClassST){
				 return true;
			 }
		}
		 return false;
	}
	
	private void tryToProperlyDeclareAllEntriesInSymbolTable(SymbolTableScope scope){
		scope.getTableEntries().forEach((k, v) -> {
			if(v.getChildScope() != null){
				tryToProperlyDeclareAllEntriesInSymbolTable(v.getChildScope());
			}
			else if(!v.isProperlyDeclared()){
				if(checkIfClassNameIsDefinied(k)){
					v.setProperlyDeclared(true);
				}
			}
		});
	}


}
