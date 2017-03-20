package Semantic;

public class EntryFunctionST extends SymbolTableScopeEntry {

	private String returnType;
	private int numberOfParameters;
	private String parameters;

	public EntryFunctionST(boolean properlyDeclared, SymbolTableScope childScope, String returnType,
			int numberOfParameters, String parameters) {
		super(properlyDeclared, childScope);
		this.returnType = returnType;
		this.numberOfParameters = numberOfParameters;
		this.parameters = parameters;
	}

	@Override
	public String printEntry() {
		StringBuilder sb = new StringBuilder();

		sb.append(" | Type=Function");
		sb.append(" | ReturnType=");
		sb.append(returnType);
		sb.append(" | NumberOfParameters=");
		sb.append(numberOfParameters);
		if (parameters != null) {
			sb.append("| Parameters=");
			sb.append(parameters);
		}
		sb.append(" | ProperlyDeclared=");
		sb.append(isProperlyDeclared());
		sb.append(" | hasChild=");

		if (getChildScope() == null) {
			sb.append("false");
		} else {
			sb.append("true");
		}

		return sb.toString();
	}

}
