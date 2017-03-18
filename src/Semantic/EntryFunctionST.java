package Semantic;

public class EntryFunctionST extends SymbolTableScopeEntry {

	private String returnType;
	private int numberOfParameters;
	//private LinkedList<SymbolTableScope> parameters;
	
	
	public EntryFunctionST(boolean properlyDeclared, SymbolTableScope childScope, String returnType, int numberOfParameters) {
		super(properlyDeclared, childScope);
		this.returnType = returnType;
		this.numberOfParameters = numberOfParameters;
	}


	@Override
	public String printEntry() {
		StringBuilder sb = new StringBuilder();
		
		sb.append(" | Type=Function");
		sb.append(" | ReturnType=");
		sb.append(returnType);
		sb.append(" | NumberOfParameters=");
		sb.append(numberOfParameters);
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
