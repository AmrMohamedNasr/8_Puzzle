package file;

import java.io.PrintStream;
/**
 * puzzle printer class.
 * @author Michael
 *
 */
public class PuzzleFilePrinter {
	/**
	 * printer stream.
	 */
	private PrintStream ps;
	
	/**
	 * border size.
	 */
	private int borderSize = 3; 
	/**
	 * constructor for printer class.
	 * @param ps
	 * printer stream used in file writer.
	 */
	public PuzzleFilePrinter(PrintStream ps) {
		this.ps = ps;
	}
	
	/**
	 * print puzzle design.
	 * @param puzzle
	 * string of puzzle state.
	 */
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
	/**
	 * print horizontal Border.
	 */
	private void printHorizontalBorder() {
		// 10 for each square +1 in corner
		for (int i = 0; i < (borderSize * 10 + 1); i++) {
			ps.print("-");
		}
		ps.println();
	}
	/**
	 * print vertical border with no data line.
	 */
	private void printBorderWithNoNumbers() {
		for (int i = 0; i < borderSize; i++)
			ps.print("|         ");
		ps.print("|");
		ps.println();
	}
	/**
	 * print vertical border with data line.
	 */
	private void printBorderWithNumbers(String nums) {
		for (int i = 0; i < borderSize; i++)
			ps.print("|    "+ nums.charAt(i) +"    ");
		ps.print("|");
		ps.println();
	}
}
