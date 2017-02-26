package SyntacticAnalyzer;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

/* 
 * Parsing table with the rules inside as an arraylist of Enums
 * Where there is no values, we insert null
 */
public class ParsingTable {
	
	private Symbols allSymbols = new Symbols(); 
	
	private ArrayList<ArrayList<ArrayList<Enum>>> parsingTable;

	private final String fileLocat = (new File("").getAbsolutePath().toString()) + "\\src\\" + "\\SyntacticAnalyzer\\";

	private final String sTableFileName = "parsingtable.txt";

	public ParsingTable() {
		Scanner sc = null;

		try {
			sc = new Scanner(new FileInputStream(fileLocat + sTableFileName));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		parsingTable = new ArrayList<ArrayList<ArrayList<Enum>>>();

		while (sc.hasNextLine()) {
			String line = sc.nextLine();

			ArrayList<ArrayList<Enum>> enumArray = new ArrayList<ArrayList<Enum>>();

			// if line contains parsing table values
			if (line.contains(",")) {
				String[] values = line.split(",", -1);

				// for each T[X,X] values
				for (String s : values) {
					
					ArrayList<Enum> enumValues = new ArrayList<Enum>();

					// if T[X,X] does not contain any values
					if (s == null || s.equals("")) {
						enumArray.add(enumValues);
						continue;
					}
					

					// if T[X,X] contains multiple symbols
					else if (s.contains(" ")) {

						String[] symbols = s.split(" ");

						for (String symb : symbols) {
							enumValues.add(allSymbols.getSymbol(symb));
						}

					}
					// then T[X,X] has a single symbol
					else {
						enumValues.add(allSymbols.getSymbol(s));
					}

					enumArray.add(enumValues);
				}
				
				parsingTable.add(enumArray);
			}
		}

	}
	
	
	
	public boolean isError(int nonTerminalIndex, int terminalIndex){
		if(getRule(nonTerminalIndex, terminalIndex).size() == 0){
			return true;
		}
		return false;
	}
	
	public ArrayList<Enum> getRule(int nonTerminalIndex, int terminalIndex){
		return parsingTable.get(nonTerminalIndex).get(terminalIndex);
	}
	
	
	public void printParsingTable(){
		for(ArrayList<ArrayList<Enum>> arr1 : parsingTable){
			StringBuilder sb = new StringBuilder();
			for(ArrayList<Enum> arr2 : arr1){
				
				if(arr2.size() == 0){
					sb.append(',');
				}
				else{
					for(Enum e : arr2){
						sb.append(e.name());
						sb.append(' ');
					}
				}
			}
			
			System.out.println(sb.toString());
		}
	}
	
	
}
