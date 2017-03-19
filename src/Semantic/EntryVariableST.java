package Semantic;

public class EntryVariableST extends SymbolTableScopeEntry {

	private String kindOfVariable; // variable or parameter
	private String structure; // number, array, class
	private int numberOfDimensions; //if array, otherwise 0

	/*
	 * Used for non arrays
	 */
	public EntryVariableST(boolean properlyDeclared, SymbolTableScope childScope, String kindOfVariable,
			String structure) {
		super(properlyDeclared, childScope);
		this.kindOfVariable = kindOfVariable;
		this.structure = structure;
	}
	
	/*
	 * Used for arrays
	 */
	public EntryVariableST(boolean properlyDeclared, SymbolTableScope childScope, String kindOfVariable,
			String structure, int numberOfDimensions) {
		super(properlyDeclared, childScope);
		this.kindOfVariable = kindOfVariable;
		this.structure = structure;
		this.numberOfDimensions = numberOfDimensions;
	}

	@Override
	public String printEntry() {
		StringBuilder sb = new StringBuilder();

		sb.append(" | Kind=");
		sb.append(kindOfVariable);
		sb.append(" | Structure=");
		sb.append(structure);
		if(numberOfDimensions == 0 || structure.equalsIgnoreCase("array")){
			sb.append(" | NumberOfDimension=");
			sb.append(numberOfDimensions);
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
