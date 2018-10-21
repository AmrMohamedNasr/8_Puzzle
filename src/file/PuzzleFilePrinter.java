package file;

import java.io.PrintStream;
/**
 * 
 * @author Michael
 *
 */
public class PuzzleFilePrinter {
	
	private PrintStream ps;
	
	private int borderSize = 3; 
	
	public PuzzleFilePrinter(PrintStream ps) {
		this.ps = ps;
	}
	
	public void printPuzzle(String puzzle) {
		for(int i = 0; i < borderSize; i++) {
			printHorizontalBorder();
			printBorderWithNoNumbers();
			printBorderWithNumbers(puzzle.substring(3 * i, 3 * (i + 1)));
			printBorderWithNoNumbers();
		}
		printHorizontalBorder();
		ps.println();
		ps.println();
		ps.println();
	}
	
	private void printHorizontalBorder() {
		// 9 for each square + 1 in corner
		for (int i = 0; i < 31; i++) {
			ps.print("-");
		}
		ps.println();
	}
	
	private void printBorderWithNoNumbers() {
		for (int i = 0; i < borderSize; i++)
			ps.print("|         ");
		ps.print("|");
		ps.println();
	}
	
	private void printBorderWithNumbers(String nums) {
		for (int i = 0; i < borderSize; i++)
			ps.print("|    "+ nums.charAt(i) +"    ");
		ps.print("|");
		ps.println();
	}
}
