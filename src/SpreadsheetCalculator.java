import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.Scanner;


public class SpreadsheetCalculator {
	
	private final static boolean FLAG_DEBUG = true;

	public static int n;
	public static int m;
	public static Cell matrix[][];
	
	public static LinkedList<Cell> evalList;
	
	public static void main(String args[]){
		Scanner inputScanner = new Scanner(System.in);
		
		if(FLAG_DEBUG){
			try {
				inputScanner = new Scanner(new File("input.txt"));
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		init(inputScanner);
		readInput(inputScanner);
		calculateOutput();
		printNeatly();

		inputScanner.close();
	}

	private static void init(Scanner inputScanner) {
		
		n =inputScanner.nextInt();
		m =inputScanner.nextInt();
		
		matrix = new Cell[m][n];
		evalList = new LinkedList<>();
	}
	
	public static void printNeatly(){
		System.out.print(n+" "+m+"\n");
		for(int i=0;i<m;i++){
			for(int j = 0 ; j<n ;j++){
				System.out.println(matrix[i][j].getEvaluatedValue());
			}
		}
	}

	public static void readInput(Scanner inputScanner){
		String str = new String();

		str = inputScanner.nextLine();
		
		for(int i=0;i<m;i++){
			for(int j = 0 ; j<n ;j++){
				str = inputScanner.nextLine();
				Cell c = new Cell( i, j, str);
				int cSize = c.parse();
				matrix[i][j] = c;
				if(cSize>0){
					evalList.add(c);
				}
			}
		}
	}
	
	private static void calculateOutput() {
		for( int i =0 ;i< evalList.size() ;i++){
			Cell cur = evalList.get(i);
			HashMap<String, Float> dep = findDependecies(cur);
			cur.setDependent(dep);
			cur.evaluateAgain();
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
