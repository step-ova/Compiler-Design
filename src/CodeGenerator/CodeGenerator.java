package CodeGenerator;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;

import LexicalAnalyzer.Token;

public class CodeGenerator {
	
	private PrintWriter code_generation_output_file;
	private final String GEN_CODE_FILE = "generated_code.txt";
	
	private StringBuilder declarationsCode;
	private StringBuilder programCode;
	
	private int uniqueVariableCounter = 0;
	
	private String previousStored = "";
	
	public CodeGenerator(){
		declarationsCode = new StringBuilder();
		programCode = new StringBuilder();
		programCode.append("entry\n"); //append entry
		try {
			code_generation_output_file = new PrintWriter(new FileOutputStream(GEN_CODE_FILE));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	
	public String generateIntegerDeclaration(String variableName){
		
		String uniqueVariableName = generateUniqueVariable(variableName);
		
		declarationsCode.append(uniqueVariableName);
		declarationsCode.append('\t');
		declarationsCode.append("dw");
		declarationsCode.append('\t');
		declarationsCode.append(0);
		
		declarationsCode.append('\n');
		
		return uniqueVariableName;
		
	}
	
	public void assignStatSingleVariable(String lhs, String rhs){
		
		programCode.append("lw r1,");
		programCode.append(rhs);
		programCode.append("(r0)");
		programCode.append('\n');
		
		programCode.append("sw ");
		programCode.append(lhs);
		programCode.append("(r0),r1");
		programCode.append('\n');

	}
	
	public void assignStatSingleInt(String lhs, int rhs){
		
		programCode.append("sub r1,r1,r1");
		programCode.append('\n');
		
		programCode.append("addi r1,r1,");
		programCode.append(rhs);
		programCode.append('\n');
		
		programCode.append("sw ");
		programCode.append(lhs);
		programCode.append("(r0),r1");
		programCode.append('\n');

	}
	
	public String generateExpression(String val1, String op, String val2){
		
		if(isInteger(val1)){
			subAddi("r1", val1);
		}
		else{
			//load
			programCode.append("lw r1,");
			programCode.append(val1);
			programCode.append("(r0)");
			programCode.append('\n');

		}
		
		if(isInteger(val2)){
			subAddi("r2", val2);
		}
		else{
			programCode.append("lw r2, ");
			programCode.append(val2);
			programCode.append("(r0)");
			programCode.append('\n');
		}

		//add or sub
		if(op.equals("+")){
			programCode.append("add r3,");
		}
		else{
			programCode.append("sub r3,");
		}
		programCode.append("r1,r2");
		programCode.append('\n');
		
		
		//Generate new and store it
		previousStored = generateIntegerDeclaration("t");
		programCode.append("sw ");
		programCode.append(previousStored);
		programCode.append("(r0),r3");
		programCode.append('\n');
		
		return previousStored;
	}
	
	public void closeCodeGenerationOutputFile(){
		
		printToOutputFile();
		
		code_generation_output_file.close();
	}
	
	private void printToOutputFile(){
		
		programCode.append("hlt");
		code_generation_output_file.println(programCode.toString());
		code_generation_output_file.println();
		code_generation_output_file.println(declarationsCode.toString());
	}
	
	
	private String generateUniqueVariable(String variableName){
		StringBuilder sb = new StringBuilder();
		sb.append(variableName);
		sb.append(uniqueVariableCounter++);
		return sb.toString();
	}
	
	public boolean isInteger( String input ) {
	    try {
	        Integer.parseInt( input );
	        return true;
	    }
	    catch( Exception e ) {
	        return false;
	    }
	}
	
	private void subAddi(String reg, String val){
		programCode.append("sub ");
		programCode.append(reg);
		programCode.append(",");
		programCode.append(reg);
		programCode.append(",");
		programCode.append(reg);
		programCode.append('\n');
		
		programCode.append("addi ");
		programCode.append(reg);
		programCode.append(",");
		programCode.append(reg);
		programCode.append(",");
		programCode.append(val);
		programCode.append('\n');
	}
	
	
}
