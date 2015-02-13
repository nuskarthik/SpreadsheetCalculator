import java.util.ArrayList;
import java.util.Stack;


public class ExpressionTree {

	 private final String[] postfix;
	 private TreeNode root;
	 
	 public ArrayList<String> dependencies;
	 
	 public ExpressionTree(String exp){
		 this.postfix = exp.trim().split("\\s+");
		 createExpressionTree();
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
				if(!isOperator(check)){
					retValues.add(check);
				}
			}
			return retValues;
	  }
}
