import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;


public class Cell {

	private int row;
	private int column;
	private String expression;
	private boolean evaluated;
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
	
	public int parse(){
		char rowChar = (char) (this.row + 1 + 64);
		char colChar = (char) (48 + 1 + this.column);
		StringBuffer build = new StringBuffer();
		build.append(rowChar);
		build.append(colChar);
		id = build.toString();
		
		ArrayList<String> dependencies = postfix.getDependencies(expression);
		for(String s: dependencies){
			if(!s.equals(id))
				dependent.put(s, (float) 0);
		}
		
		int size = dependencies.size();
		if(size==0){
			this.evaluated = true;
			evaluatedExpression = postfix.evaluateTree();
		}
		
		return size;
	}
	
	public void evaluateAgain(){
		evaluatedExpression = postfix.evaluateTree();
	}
	
	public void setDependent(HashMap map){
		this.postfix.dependent = map;
	}

	public void setEvaluatedValue(float f) {
		this.evaluatedExpression = f;
	}
	
	public float getEvaluatedValue(){
		return evaluatedExpression;
	}
	
}
