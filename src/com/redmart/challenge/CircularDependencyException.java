package com.redmart.challenge;

public class CircularDependencyException extends Exception{
	
	String id;
	
	public CircularDependencyException(){
		
	}
	
	public CircularDependencyException(String message){
		super(message);
	}
	
	public CircularDependencyException(String message, String id){
		super(message);
		this.id = id;
	}
}
