package LexicalAnalyzer;

public class InvalidTokenException extends Exception {
	
	//generated serialVersionId
	private static final long serialVersionUID = -6351915989467463895L;
	
	private static final String LINE_NUMBER_STRING = " ==== Line number: ";
	
	
	public InvalidTokenException(String message){
		super(message);
		
		
	}
	
	
	
}
