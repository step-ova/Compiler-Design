package Semantic;

public class EntryVariableST extends AbstractSymbolTableScopeEntry {

	private String kindOfVariable; // variable or parameter
	private String structure; // number, array, class
	private String type; //int[1][2], int, float, MyClass2
	private int numberOfDimensions; //if array, otherwise 0

	/*
	 * Used for non arrays
	 */
	public EntryVariableST(boolean properlyDeclared, SymbolTableScope childScope, String kindOfVariable,
			String structure, String type) {
		super(properlyDeclared, childScope);
		this.kindOfVariable = kindOfVariable;
		this.structure = structure;
		this.type = type;
	}
	
	/*
	 * Used for arrays
	 */
	public EntryVariableST(boolean properlyDeclared, SymbolTableScope childScope, String kindOfVariable,
			String structure, String type, int numberOfDimensions) {
		super(properlyDeclared, childScope);
		this.kindOfVariable = kindOfVariable;
		this.structure = structure;
		this.type = type;
		this.numberOfDimensions = numberOfDimensions;
	}

	public String getKindOfVariable() {
		return kindOfVariable;
	}

	public String getStructure() {
		return structure;
	}

	public String getType() {
		return type;
	}

	public int getNumberOfDimensions() {
		return numberOfDimensions;
	}

	public boolean equalsSameType(String structure, String type, int numberOfDimensions){
		return this.structure.equals(structure)
				&& this.type.equals(type)
				&& this.numberOfDimensions >= numberOfDimensions;
				
	}

	@Override
	public String printEntry() {
		StringBuilder sb = new StringBuilder();

		sb.append(" | Kind=");
		sb.append(kindOfVariable);
		sb.append(" | Structure=");
		sb.append(structure);
		sb.append(" | Type=");
		sb.append(type);
		if(numberOfDimensions != 0 || structure.equalsIgnoreCase("array")){
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
