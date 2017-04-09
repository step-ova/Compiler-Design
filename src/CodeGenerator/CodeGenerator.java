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
	private String elseClause = ""; // used for if statements
	private String endifClause = ""; // used for if statements

	public CodeGenerator() {
		declarationsCode = new StringBuilder();
		programCode = new StringBuilder();
		programCode.append("entry\n"); // append entry
		try {
			code_generation_output_file = new PrintWriter(new FileOutputStream(GEN_CODE_FILE));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	public String generateIntegerDeclaration(String variableName) {

		String uniqueVariableName = generateUniqueVariable(variableName);

		declarationsCode.append(uniqueVariableName);
		declarationsCode.append('\t');
		declarationsCode.append("dw");
		declarationsCode.append('\t');
		declarationsCode.append(0);

		declarationsCode.append('\n');

		return uniqueVariableName;

	}

	public void genIf1() {
		load("r1", previousStored);
		programCode.append("bz r1,");

		elseClause = generateUniqueVariable("else");
		programCode.append(elseClause);
		programCode.append("\n");

	}

	public void genIf2() {
		endifClause = generateUniqueVariable("endif");
		programCode.append("j ");
		programCode.append(endifClause);
		programCode.append("\n");

		programCode.append(elseClause);
		programCode.append("\n");
	}

	public void genIf3() {
		programCode.append(endifClause);
		programCode.append("\n");
	}

	public void expressionSingleVariable(String rhs) {
		load("r1", rhs);

		previousStored = generateIntegerDeclaration("t");
		assignStat("r1", previousStored);
	}

	public void expressionSingleInt(int rhs) {
		String rhsStr = "" + rhs;
		subAddi("r1", rhsStr);

		previousStored = generateIntegerDeclaration("t");
		assignStat("r1", previousStored);
	}

	public void assignStatSingleVariable(String lhs, String rhs) {

		load("r1", rhs);

		assignStat("r1", lhs);

	}

	public void assignStatSingleInt(String lhs, int rhs) {
		String rhsStr = "" + rhs;

		subAddi("r1", rhsStr);

		assignStat("r1", lhs);

	}

	public String generateExpression(String val1, String op, String val2) {

		if (isInteger(val1)) {
			subAddi("r1", val1);
		} else {
			load("r1", val1);
		}

		if (isInteger(val2)) {
			subAddi("r2", val2);
		} else {
			load("r2", val2);
		}

		// add or sub
		if (op.equals("+")) {
			programCode.append("add r3,");
		} else {
			programCode.append("sub r3,");
		}
		programCode.append("r1,r2");
		programCode.append('\n');

		// Generate new and store it
		previousStored = generateIntegerDeclaration("t");

		assignStat("r3", previousStored);

		return previousStored;
	}

	public void closeCodeGenerationOutputFile() {

		printToOutputFile();

		code_generation_output_file.close();
	}

	private void printToOutputFile() {

		programCode.append("hlt");
		code_generation_output_file.println(programCode.toString());
		code_generation_output_file.println();
		code_generation_output_file.println(declarationsCode.toString());
	}

	private String generateUniqueVariable(String variableName) {
		StringBuilder sb = new StringBuilder();
		sb.append(variableName);
		sb.append(uniqueVariableCounter++);
		return sb.toString();
	}

	public String getPrevious() {
		return previousStored;
	}

	public boolean isInteger(String input) {
		try {
			Integer.parseInt(input);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	private void subAddi(String reg, String val) {
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

	private void assignStat(String reg, String lhs) {
		programCode.append("sw ");
		programCode.append(lhs);
		programCode.append("(r0),");
		programCode.append(reg);
		programCode.append('\n');
	}

	private void load(String reg, String val) {
		programCode.append("lw ");
		programCode.append(reg);
		programCode.append(",");
		programCode.append(val);
		programCode.append("(r0)");
		programCode.append('\n');
	}

}
