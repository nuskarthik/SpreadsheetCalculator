package com.redmart.challenge;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.Scanner;


public class SpreadsheetCalculator {
	
	private final static boolean FLAG_DEBUG = false;

	public static int n;
	public static int m;
	public static Cell matrix[][];
	
	public static LinkedList<Cell> evalList;
	public static HashMap<String, HashMap<String, Boolean>> dependencyMap;
	public static HashMap<String, Boolean> errorCells;
	
	public static void main(String args[]) throws Exception{
		Scanner inputScanner = new Scanner(System.in);
		try {
			if(FLAG_DEBUG){
				inputScanner = new Scanner(new File("input.txt"));
			}

			init(inputScanner);
			readInput(inputScanner);
			calculateOutput();
			printNeatly();

			inputScanner.close();
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	private static void init(Scanner inputScanner) {
		
		n =inputScanner.nextInt();
		m =inputScanner.nextInt();
		
		matrix = new Cell[m][n];
		evalList = new LinkedList<>();
		dependencyMap = new HashMap<String, HashMap<String,Boolean>>();
		errorCells = new HashMap<String, Boolean>();
	}
	
	public static void printNeatly(){
		System.out.print(n+" "+m+"\n");
		for(int i=0;i<m;i++){
			for(int j = 0 ; j<n ;j++){
				if( errorCells.get( Cell.calculateId(i, j) ) == null )
				{
					String val = String.format("%.5f", matrix[i][j].getEvaluatedValue());
					System.out.println(val);
				}
				else{
					System.out.println("Circular Dependency");
				}
			}
		}
	}

	public static void readInput(Scanner inputScanner) throws Exception{
		String str = new String();

		str = inputScanner.nextLine();
		
		for(int i=0;i<m;i++){
			for(int j = 0 ; j<n ;j++){
				str = inputScanner.nextLine();
				Cell c = new Cell( i, j, str);
				ArrayList<String> dependency = c.parseAndGetDependencies();
				setDependencyMap(c.getId() , dependency);
				matrix[i][j] = c;
				if(dependency.size()>0){
					evalList.add(c);
				}
			}
		}
	}
	
	private static void setDependencyMap(String id, ArrayList<String> dependency){
		try{
		
		for(int i=0;i<dependency.size();i++){
			String currentDependency = dependency.get(i);
			if(errorCells.get(currentDependency)!=null){
				throw new CircularDependencyException("Circular Dependency",id);
			}
			if(id.equals(currentDependency)){
				throw new CircularDependencyException("Circular Dependency",id);
			}
			if(dependencyMap.get(currentDependency)!=null && dependencyMap.get(currentDependency).get(id)!=null){
					throw new CircularDependencyException("Circular Dependency",id);
			}
			else{
				if(dependencyMap.get(id)!=null){
					HashMap<String, Boolean> currentMapForId = dependencyMap.get(id);
					if(currentMapForId.get(currentDependency)!=null){
						currentMapForId.put(currentDependency, true);
						dependencyMap.put(id, currentMapForId);
					}
					else{
						currentMapForId.put(currentDependency, true);
						dependencyMap.put(id, currentMapForId);
					}
				}
				else{
					HashMap<String, Boolean> newMapForId = new HashMap<>();
					newMapForId.put(currentDependency, true);
					dependencyMap.put(id, newMapForId);
				}
			}
		}
		}catch(CircularDependencyException e){
			errorCells.put(e.id, true);
			for(int i=0;i<dependency.size();i++){
				HashMap<String, Boolean> checkNonDependence = dependencyMap.get(dependency.get(i));
				if(checkNonDependence!=null){
					errorCells.put(dependency.get(i), true);
				}
			}
		}
	}
	
	private static void calculateOutput() {
		while(!evalList.isEmpty()){
			
			Cell cur = evalList.get(0);
			evalList.remove(0);
			
			if(errorCells.get(cur.getId())==null){
			
				HashMap<String, Float> dep = findDependecies(cur);
				cur.setDependent(dep);
				cur.evaluateAgain();
				
				//check if dependencies change the answer
				boolean dependencyFlag = false;
				if(dep.size()>0){
					Iterator it = dep.entrySet().iterator();
					while(it.hasNext()){
						Map.Entry pairs = (Map.Entry) it.next();
						String cellId = (String) pairs.getKey();
						HashMap<String, Boolean> tempMap = dependencyMap.get(cur.getId());
						if(tempMap.get(cellId) != null){
							Cell c = getCell(cellId.charAt(0), cellId.charAt(1));
							//c.evaluateAgain();
							if(c.evaluated==false){
								evalList.add(0,c);
								dependencyFlag = true;
							}
						}
						dependencyMap.put(cur.getId(), tempMap);
					}
					if(dependencyFlag){
						cur.evaluated = false;
						evalList.add(cur);
					}
				}
			}
		}
	}
	
	public static HashMap<String, Float> findDependecies(Cell c) {
		HashMap<String, Float> cellValues = new HashMap<>();
		
		// retrieve dependencies values
		Iterator it = c.dependent.entrySet().iterator();
		while(it.hasNext()){
			Map.Entry pairs = (Map.Entry) it.next();
			String cellId = (String) pairs.getKey();
			
			cellValues.put(cellId, getCell(cellId.charAt(0), cellId.charAt(1)).getEvaluatedValue());
		}
		
		return cellValues;
	}
	
	public static Cell getCell(char rowChar, char colChar){
		int rowVal = (int) (rowChar - 65);
		int colVal = (int) (colChar - 49);
		return matrix[rowVal][colVal];
	}
}
