import java.util.ArrayList;
import java.util.HashMap;


public class Cell {

	private int row;
	private int column;
	private String expression;
	public boolean evaluated;
	private float evaluatedExpression;
	public HashMap<String, Float> dependent;
	
	private String id;
	
	ExpressionTree postfix;
	
	public Cell( int r, int c, String value ){
		this.row = r;
		this.column = c;
		this.expression = value;
		this.evaluated = false;
		this.dependent = new HashMap<>();
		postfix = new ExpressionTree(value);
	}
	
	public ArrayList<String> parseAndGetDependencies(){
		this.id = calculateId(this.row, this.column);
		
		ArrayList<String> dependencies = postfix.getDependencies(expression);
		for(String s: dependencies){
			dependent.put(s, (float) 0);
		}
		
		if(dependencies.size()==0){
			this.evaluated = true;
			evaluatedExpression = postfix.evaluateTree();
		}
		
		return dependencies;
	}
	
	public void evaluateAgain(){
		this.evaluated = true;
		evaluatedExpression = postfix.evaluateTree();
	}
	
	public String getId(){
		return id;
	}
	
	public void setDependent(HashMap map){
		this.postfix.dependent = map;
	}
	
	public float getEvaluatedValue(){
		return evaluatedExpression;
	}
	
	//utility function
	public static String calculateId(int row, int col){
		char rowChar = (char) (row + 1 + 64);
		char colChar = (char) (48 + 1 + col);
		StringBuffer build = new StringBuffer();
		build.append(rowChar);
		build.append(colChar);
		return build.toString();
	}
	
}
