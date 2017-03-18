package Semantic;

public class SymbolTable {

	private SymbolTableScope globalScope = new SymbolTableScope("GlobalScope", null); // parent = null

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

	// TODO
	public void insertEntry(String type, String identifier) {
		if (type.equalsIgnoreCase("class")) {

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
		
		StringBuilder tabs = new  StringBuilder();
		for(int i = 0; i< numberOfTabCharacthers; i++){
			tabs.append('\t');
		}
		
		System.out.println(tabs.toString() + scope.getScopeName() + "->");
		
		tabs.append('\t');
		
		scope.getTableEntries().forEach((k, v) -> {
			System.out.println(tabs.toString() + "Name: " + k + " " + v.printEntry());
			if(v.hasChildScope()){
				printScopeValues(v.getChildScope(), numberOfTabCharacthers+2);
			}
			
		}
		);
	}


}
