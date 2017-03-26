package Semantic.SemanticStackEntries;

public class ParameterEntry implements InterfaceSemanticStackEntries {
	
	private String entries;
	
	public ParameterEntry(String entries){
		this.entries  = entries;
	}
	
	public String getEntry(){
		return entries;
	}
	
}
