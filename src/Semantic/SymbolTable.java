package Semantic;

public class SymbolTable {

	private SymbolTableScope globalScope = new SymbolTableScope("GlobalScope", null); // parent
																						// =
																						// null

	private SymbolTableScope currentScope = globalScope;

	public boolean search(SymbolTableScope symtblScope, String symbol) {
		SymbolTableScope searchCurrentScope = symtblScope;
		boolean hasSymbol = searchCurrentScope.hasSymbol(symbol);

		while (!hasSymbol) {
			searchCurrentScope = searchCurrentScope.getParentScope();

			if (searchCurrentScope == globalScope) {
				return globalScope.hasSymbol(symbol);
			}

			hasSymbol = searchCurrentScope.hasSymbol(symbol);
		}

		return true;

	}
	
	public void insertClassEntryAndEnterScope(String identifier){
		String identifierScope = identifier + "Scope";
		// Create new scope with parent as current scope
		SymbolTableScope classScope = new SymbolTableScope(identifierScope, currentScope);

		// create a new class entry for the current table with a child as
		// the classScope
		SymbolTableScopeEntry scopeEntry = new EntryClassST(true, classScope);
		currentScope.insert(identifier, scopeEntry);

		// Change the scope to the new created scope
		currentScope = classScope;
	}
	
	public void insertFunctionWithoutParametersAndEnterScope(String returnType, String functionName){
		String identifierScope = functionName + "Scope";

		SymbolTableScope functionScope = new SymbolTableScope(identifierScope, currentScope);

		SymbolTableScopeEntry functionEntry = new EntryFunctionST(true, functionScope, returnType, 0, null);
		currentScope.insert(functionName, functionEntry);

		currentScope = functionScope;
		
	}
	
	public void insertFunctionWithParametersAndEnterScope(String returnType, String functionName, int numberOfParameters, String parameters){
		String identifierScope = functionName + "Scope";

		SymbolTableScope functionScope = new SymbolTableScope(identifierScope, currentScope);
		
		SymbolTableScopeEntry functionEntry = new EntryFunctionST(true, functionScope, returnType, numberOfParameters, parameters);
		currentScope.insert(functionName, functionEntry);

		currentScope = functionScope;
	}

	// TODO
	public void insertEntry(String type, String identifier) {
		
		if (type.equals("variable") || type.equals("parameter")) {
			
			System.out.println(identifier);

			String[] identifierArray = identifier.split(" ");

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
			
			System.out.println(identifierArray.length);
			// new variable entry with no child (null)
			SymbolTableScopeEntry variableEntry = new EntryVariableST(properlyDefinied, null, type, structure,
					numberOfDimensions);
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

	// TODO
	public void deleteScope() {

	}

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

		tabs.append('\t');

		scope.getTableEntries().forEach((k, v) -> {
			System.out.println(tabs.toString() + "Name: " + k + " " + v.printEntry());
			if (v.hasChildScope()) {
				printScopeValues(v.getChildScope(), numberOfTabCharacthers + 2);
			}

		});
	}

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

	private boolean checkIfIntOrFloat(String type) {
		// If identifier is a class
		if (!(type.equals("int") || type.equals("float"))) {
			return false;
		}
		// is int or float
		return true;
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

}
