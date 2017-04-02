package Semantic;

abstract class AbstractSymbolTableScopeEntry {
	
	private boolean properlyDeclared;
	
	private int lineNumber;
	
	private String codeGenerationIdentifierName;
	
	private SymbolTableScope childScope;

	public AbstractSymbolTableScopeEntry(boolean properlyDeclared, SymbolTableScope childScope, int lineNumber) {
		super();
		this.properlyDeclared = properlyDeclared;
		this.childScope = childScope;
		this.lineNumber = lineNumber;
		codeGenerationIdentifierName = "";
	}

	public String getCodeGenerationIdentifierName() {
		return codeGenerationIdentifierName;
	}

	public void setCodeGenerationIdentifierName(String codeGenerationIdentifierName) {
		this.codeGenerationIdentifierName = codeGenerationIdentifierName;
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
	
	public int getLineNumber() {
		return lineNumber;
	}

	public void setLineNumber(int lineNumber) {
		this.lineNumber = lineNumber;
	}
	
	public abstract String printEntry();
	
	public boolean hasChildScope(){
		return childScope != null;
	}
	
	

}
