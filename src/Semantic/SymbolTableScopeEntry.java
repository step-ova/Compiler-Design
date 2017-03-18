package Semantic;

abstract class SymbolTableScopeEntry {
	
	private boolean properlyDeclared;
	
	private SymbolTableScope childScope;

	public SymbolTableScopeEntry(boolean properlyDeclared, SymbolTableScope childScope) {
		super();
		this.properlyDeclared = properlyDeclared;
		this.childScope = childScope;
	}

	public boolean isProperlyDeclared() {
		return properlyDeclared;
	}

	public void setProperlyDeclared(boolean properlyDeclared) {
		this.properlyDeclared = properlyDeclared;
	}

	public SymbolTableScope getChildScope() {
		return childScope;
	}

	public void setChildScope(SymbolTableScope childScope) {
		this.childScope = childScope;
	}
	
	public abstract String printEntry();
	
	public boolean hasChildScope(){
		return childScope != null;
	}
	
	

}
