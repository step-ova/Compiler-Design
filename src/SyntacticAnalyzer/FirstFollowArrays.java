package SyntacticAnalyzer;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class FirstFollowArrays {
	
	private Symbols allSymbols = new Symbols(); 
	
	private ArrayList<ArrayList<Enum>> first;
	private ArrayList<ArrayList<Enum>> follow;

	private final String fileLocat = (new File("").getAbsolutePath().toString()) + "\\src\\"
			+ "\\SyntacticAnalyzer\\";

	private final String firstFileName = "firstSet.txt";
	private final String followFileName = "followSet.txt";

	public FirstFollowArrays() {
		Scanner scFirst = null;
		Scanner scFollow = null;

		try {
			scFirst = new Scanner(new FileInputStream(fileLocat + firstFileName));
			scFollow = new Scanner(new FileInputStream(fileLocat + followFileName));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		first = new ArrayList<ArrayList<Enum>>();
		follow = new ArrayList<ArrayList<Enum>>();

		populateArray(scFirst, first);
		populateArray(scFollow, follow);

	}

	private void populateArray(Scanner sc, ArrayList<ArrayList<Enum>> arrayList) {
		while (sc.hasNextLine()) {
			String line = sc.nextLine();

			ArrayList<Enum> enumArray = new ArrayList<Enum>();
			
			//if contains multiple values
			if (line.contains(", ")) {
				String[] values = line.split(", ");

				for (String s : values) {
					enumArray.add(allSymbols.getSymbol(s));
				}

			} else{
				enumArray.add(allSymbols.getSymbol(line));
			}

			arrayList.add(enumArray);

		}
	}
	
	
	public boolean followContains(Enum top, String lookahead){
		for(ArrayList<Enum> followSets : follow){
			if(followSets.get(0) == top){
				return containsSymbol(followSets, lookahead);
			}
		}
		
		return false;
	}
	
	public boolean firstContains(Enum top, String lookahead){
		for(ArrayList<Enum> firstSets: first){
			if(firstSets.get(0) == top){
				return containsSymbol(firstSets,lookahead);
			}
		}
		
		return false;
	}
	
	
	/*
	 * Checks if the Enum arraylist contains the lookahead symbol
	 */
	private boolean containsSymbol(ArrayList<Enum> symbols, String lookahead){
		for(Enum sym : symbols){
			if(sym.name().equalsIgnoreCase(lookahead)){
				return true;
			}
		}
		
		return false;
	}

}
