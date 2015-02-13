package com.redmart.challenge;

public class Operators {
	
	//possible enum declaration

	public float calculate(String operator, float left, float right) throws Exception{
		float answer = 0;
		switch(operator){
		case "+":
			answer = left + right;
			break;
		case "-":
			answer = left - right;
			break;
		case "*":
			answer = left * right;
			break;
		case "/":
			answer = left / right;
			break;
		default:
			throw new Exception("Illegal operation");
		}
		return answer;
	}
}
