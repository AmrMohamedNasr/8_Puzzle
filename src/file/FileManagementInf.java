package file;

import java.io.File;

import solver.PuzzleSolver;

/**
 * file writer interface.
 * @author Michael
 *
 */
public interface FileManagementInf {
	public void writeToFile(File file, PuzzleSolver solver);
}
