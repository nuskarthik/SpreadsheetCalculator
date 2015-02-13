package com.redmart.challenge;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Stack;
import java.util.regex.Pattern;


public class ExpressionTree {

	 private final String[] postfix;
	 private TreeNode root;
	 
	 public ArrayList<String> dependencies;
	 Operators apply;
	 public HashMap<String, Float> dependent;
	 
	 public ExpressionTree(String exp){
		 while(exp.contains("++")){
			 int plusIndex = exp.indexOf("++");
			 exp = exp.substring(0, plusIndex)+"1 +"+exp.substring(plusIndex+2, exp.length() );
		 }
		 while(exp.contains("--")){
			 int plusIndex = exp.indexOf("--");
			 exp = exp.substring(0, plusIndex)+"1 -"+exp.substring(plusIndex+2, exp.length() );
		 }
		 this.postfix = exp.trim().split("\\s+");
		 createExpressionTree();
		 apply = new Operators();
		 this.dependent = new HashMap<>();
	 }
	 
	  private static class TreeNode {
	        TreeNode left;
	        String ch;
	        TreeNode right;

	        TreeNode(TreeNode left, String ch, TreeNode right) {
	            this.left = left;
	            this.ch = ch;
	            this.right = right;
	        }
	  }
	  
	  public void createExpressionTree() {
	        final Stack<TreeNode> nodes = new Stack<TreeNode>();
	        for (int i = 0; i < postfix.length; i++) {
	            String ch  = postfix[i];
	            if (isOperator(ch)) {
	               TreeNode rightNode = nodes.pop();
	               TreeNode leftNode = nodes.pop();
	               nodes.push(new TreeNode(leftNode, ch, rightNode));
	            } else {
	                nodes.add(new TreeNode(null, ch, null));
	            }
	        }
	        root = nodes.pop();
	    }
	    
	  private boolean isOperator(String c) {
		    return "+-*/".indexOf(c) != -1;
		}
	  
	  public ArrayList<String> getDependencies(String str){
		   	ArrayList<String> retValues = new ArrayList<String>();
			String[] strValues = str.split("\\s+");
			
			
			for(String check : strValues){
				if(Pattern.matches("[A-Z]\\d+", check)){
					retValues.add(check);
				}
			}
			return retValues;
	  }
	  
	  public float evaluateTree(){
		  float f = 0;
		  try {
			f = evaluate(root);
		} catch (Exception e) {
			e.printStackTrace();
		}
		 return f; 
	  }
	  
	  public float evaluate(TreeNode n) throws Exception{
		  if(n!=null){
			  //leaf
			  if(n.left==null&&n.right==null){
				  if(dependent.get(n.ch) != null){
					  return dependent.get(n.ch);
				  }
				  return Float.parseFloat(n.ch);
			  }
			  else{
				  float left = evaluate(n.left);
				  float right = evaluate(n.right);
				  return apply.calculate(n.ch, left, right);
			  }
		  }
		return 0;
	  }

}
