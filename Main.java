import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileReader;
import java.util.Scanner;

import dk.brics.automaton.Automaton;
import dk.brics.automaton.RegExp;

public class Main {

	private static String FILE_NAME = "test.txt";
	
	public static void main(String[] args) {
		
		String digit = "[0-9]";
		String non_zero = "[1-9]";
		String letter = "([a-z]|[A-Z])";
		
		String underscore = "_";
		String alphanum = "(" + letter + "|" + digit + "|" + underscore + ")";
		String id = letter + alphanum + "*";
		
		String integer_lex = "(" + non_zero + digit + "*" + "|" + "0" + ")";
		String fraction_lex = "(\\." + digit + "*" + non_zero + "|" + "\\.0)"; 
		String float_lex = "(" + integer_lex + fraction_lex + ")";
		
		System.out.println(integer_lex);
		
		RegExp r = new RegExp(integer_lex);
		Automaton a = r.toAutomaton();
		String s = "12341234";
		System.out.println("Match: " + a.run(s));
		
		
		
		
		

	}

}
