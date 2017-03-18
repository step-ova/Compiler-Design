package Semantic;

public class EntryVariableST extends SymbolTableScopeEntry {

	private String kindOfVariable; //normal or parameter
	private String structure; //num, array, class
	
	
	public EntryVariableST(boolean properlyDeclared, SymbolTableScope childScope) {
		super(properlyDeclared, childScope);
	}


	@Override
	public String printEntry() {
		return null;
	}

}
