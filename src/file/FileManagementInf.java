package file;

import java.io.File;

import solver.PuzzleSolver;

/**
 * file writer interface.
 * @author Michael
 *
 */
public interface FileManagementInf {
	/**
	 * write puzzle solver details and path to goal into a file
	 * @param file
	 * file to be written.
	 * @param solver
	 * puzzle solver object contains all data needed to view.
	 */
	public void writeToFile(File file, PuzzleSolver solver);
}
