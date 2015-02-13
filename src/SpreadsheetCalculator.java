import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;


public class SpreadsheetCalculator {
	
	private final static boolean FLAG_DEBUG = true;

	public static int n;
	public static int m;
	public static Cell matrix[][];
	
	SpreadsheetCalculator(){
		
	}
	
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

		inputScanner.close();
	}
	
	private static void init(Scanner inputScanner) {
		
		n =inputScanner.nextInt();
		m =inputScanner.nextInt();
		
		matrix = new Cell[m][n];
	}

	public static void readInput(Scanner inputScanner){
		String str = new String();

		str = inputScanner.nextLine();
		
		for(int i=0;i<n;i++){
			for(int j = 0 ; j<m ;j++){
				str = inputScanner.nextLine();
				Cell c = new Cell( i, j, str);
				matrix[i][j] = c;
				c.parse();
			}
		}
	}
}
