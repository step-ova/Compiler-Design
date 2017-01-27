public class Main {

	private static String FILE_NAME = "test.txt";
	
	public static void main(String[] args) {
		
		Tokenizer t = new Tokenizer(FILE_NAME);
		
		System.out.println(t.getNextToken().toString());
		System.out.println(t.getNextToken().toString());
		System.out.println(t.getNextToken().toString());
		System.out.println(t.getNextToken().toString());
		System.out.println(t.getNextToken().toString());
		System.out.println(t.getNextToken().toString());
		System.out.println(t.getNextToken().toString());
		System.out.println(t.getNextToken().toString());

	}

}
