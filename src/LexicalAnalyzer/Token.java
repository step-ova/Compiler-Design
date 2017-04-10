package LexicalAnalyzer;

import Semantic.SemanticStackEntries.InterfaceSemanticStackEntries;

public class Token implements InterfaceSemanticStackEntries {

	private String token;
	private String lexeme;
	private int position;

	public Token(String token, String lexeme, int position) {
		this.token = token.toUpperCase();
		this.lexeme = lexeme;
		this.position = position + 1;
	}
	
	public String getTokenName(){
		return token;
	}
	
	public String getTokenLexeme(){
		return lexeme;
	}
	
	public int getTokenPosition(){
		return position;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(token);
		sb.append(", ");
		sb.append(lexeme);
		sb.append(", ");
		sb.append(position);
		return sb.toString();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null)
			return false;
		else if (getClass() != obj.getClass())
			return false;
		else {
			Token otherToken = (Token) obj;
			return (this.token.equals(otherToken.token) && this.lexeme.equals(otherToken.lexeme)
					&& this.position == otherToken.position);
		}
	}

}
