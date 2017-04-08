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
		
		programCode.append("lw r1, ");
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
}
