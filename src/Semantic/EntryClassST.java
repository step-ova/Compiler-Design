package Semantic;

public class EntryClassST extends AbstractSymbolTableScopeEntry {

	public EntryClassST(boolean properlyDeclared, SymbolTableScope childScope, int lineNumber) {
		super(properlyDeclared, childScope, lineNumber);
	}

	@Override
	public String printEntry() {
		StringBuilder sb = new StringBuilder();
		
		sb.append(" | Type=Class");
		sb.append(" | ProperlyDeclared=");
		sb.append(isProperlyDeclared());
		sb.append(" | hasChild=");
		
		if(getChildScope() == null){
			sb.append("false");
		}
		else{
			sb.append("true");
		}
		
		return sb.toString();
		
	}

}
