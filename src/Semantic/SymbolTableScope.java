package Semantic;

import java.util.HashMap;

public class SymbolTableScope {
	//name of the scope
	private String scopeName;
	
	public String getScopeName() {
		return scopeName;
	}

	public void setScopeName(String scopeName) {
		this.scopeName = scopeName;
	}

	//symbols kept in hashmap indexed by name
	private HashMap<String, AbstractSymbolTableScopeEntry> tableEntries = 
			new HashMap<String, AbstractSymbolTableScopeEntry>();
	
	// Parent scope to go up the tree if needed
	private SymbolTableScope parentScope;
	
	
	//Constructor
	public SymbolTableScope(String scopeName, SymbolTableScope parentScope) {
		this.scopeName = scopeName;
		this.parentScope = parentScope;
	}
	
	public void insert(String symbolName, AbstractSymbolTableScopeEntry scopeEntry){
		tableEntries.put(symbolName, scopeEntry);
	}
	
	public boolean hasSymbol(String symbol){
		return tableEntries.containsKey(symbol);
	}
	
	public SymbolTableScope getParentScope(){
		return parentScope;
	}
	
	public SymbolTableScope getChildScope(String symbol){
		return tableEntries.get(symbol).getChildScope();
	}

	public HashMap<String, AbstractSymbolTableScopeEntry> getTableEntries() {
		return tableEntries;
	}

	public void setTableEntries(HashMap<String, AbstractSymbolTableScopeEntry> tableEntries) {
		this.tableEntries = tableEntries;
	}
	
	public AbstractSymbolTableScopeEntry getScopeEntry(String key){
		return tableEntries.get(key);
	}
	
	
	
}
