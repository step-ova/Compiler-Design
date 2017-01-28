package Main;
import LexicalAnalyzer.InvalidTokenException;
import LexicalAnalyzer.Token;
import LexicalAnalyzer.Tokenizer;

public class Main {

	private static String FILE_NAME = "test.txt";
	
	public static void main(String[] args) {
		
		Tokenizer t = new Tokenizer(FILE_NAME);
		Token token;
		
		
		while(true){	
			try{
				
				token = t.getNextToken();
				if(token == null){
					break;
				}
				else{
					System.out.println(token.toString());
				}

			}
			catch (InvalidTokenException e) {
				System.out.println(e.getMessage());
			}
		}
		
		

	}

}
