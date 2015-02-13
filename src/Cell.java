import java.util.ArrayList;
import java.util.HashMap;


public class Cell {

	private int row;
	private int column;
	private String expression;
	private boolean evaluated;
	private float evaluatedExpression;
	private HashMap<String, Boolean> dependent;
	
	ExpressionTree postfix;
	
	public Cell( int r, int c, String value ){
		this.row = r;
		this.column = c;
		this.expression = value;
		this.evaluated = false;
		this.dependent = new HashMap<>();
		postfix = new ExpressionTree(value);
	}
	
	public void parse(){
		ArrayList<String> dependencies = postfix.getDependencies(expression);
		for(String s: dependencies){
			dependent.put(s, true);
		}
	}
	
}
