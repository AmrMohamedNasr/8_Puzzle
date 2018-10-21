package file;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;

import solver.PuzzleSolver;
/**
 * File management writer class.
 * @author Michael
 *
 */
public class FileManagement implements FileManagementInf{

	@Override
	public void writeToFile(File file, PuzzleSolver solver) {
		// TODO Auto-generated method stub
		FileOutputStream f;
		try {
			f = new FileOutputStream(file);
			PrintStream ps = new PrintStream(f);
			printSolverDetails(ps, solver);
			ps.print("Puzzle Path To Goal:");
			ps.println();
			PuzzleFilePrinter printer = new PuzzleFilePrinter(ps);
			for( int i = 0; i < solver.getSearchResult().goal_path.size(); i++) {
				printer.printPuzzle(solver.getSearchResult().goal_path.get(i).toString());
				if (i < solver.getSearchResult().goal_path.size() - 1) {
					ps.print("To:");
					ps.println();
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
	}
	/**
	 * print details of puzzle solver.
	 * @param ps
	 * print stream used.
	 * @param solver
	 * puzzle solver containing all details.
	 */
	private void printSolverDetails(PrintStream ps, PuzzleSolver solver) {
		String cost = String.valueOf(solver.getSearchResult().goal_cost);
		String nodes = String.valueOf(solver.getSearchResult().expanded_list.size());
		String depth = String.valueOf(solver.getSearchResult().search_depth);
		String time = String.valueOf(solver.getSearchResult().search_time / 1e6);
		ps.print("Cost: " + cost +".");
		ps.println();
		ps.print("Number of Expanded Nodes: " + nodes +".");
		ps.println();
		ps.print("Search Depth: " + depth +".");
		ps.println();
		ps.print("Time Taken: " + time +" second.");
		ps.println();
		ps.println();
	}

}
