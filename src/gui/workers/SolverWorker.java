package gui.workers;

import java.awt.Cursor;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.SwingWorker;

import gui.PuzzleFrame;
import solver.PuzzleSolver;
import state.PuzzleState;
import state.exceptions.InvalidStateException;

public class SolverWorker extends SwingWorker<String, Void>{
	/**
	 * Row size of the puzzle.
	 */
	public static final int ROW_SIZE = 3;
	/**
	 * Row number in the puzzle. 
	 */
	public static final int ROW_NUM = 3;
	private final PuzzleSolver solver;
	private final PuzzleFrame panel;
	private final JButton reset;
	private final int search_index, heuristic_index;
	private final String state;
	
	public SolverWorker(PuzzleSolver solver, PuzzleFrame panel,
			int search_index, int heuristic_index, JButton reset, String state) {
		this.solver = solver;
		this.panel = panel;
		this.search_index = search_index;
		this.heuristic_index = heuristic_index;
		this.reset = reset;
		this.state = state;
	}
	@Override
	protected String doInBackground() throws Exception {
		panel.setCursor(new Cursor(Cursor.WAIT_CURSOR));
		try {
			if (isValidPuzzleString(state) && !isPuzzleSolvable(state)) {
				JOptionPane.showMessageDialog(null, "ERROR UnSolvable Puzzle!");
				reset.doClick();
			} else {
				if(solver.solvePuzzle(new PuzzleState(state), search_index, heuristic_index)) {
					if (solver.getSearchResult().goal_path.isEmpty()) {
						JOptionPane.showMessageDialog(null, "No Valid Solution");
						reset.doClick();
					} else {
						panel.informOutputReady();
					}
				} else {
					JOptionPane.showMessageDialog(null, "Invalid search methods chosen !");
					reset.doClick();
				}
			}
		} catch (InvalidStateException e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
			reset.doClick();
		}
		return null;
	}
	
	/**
	 * Checks if a given string is a valid puzzle string.
	 * @param puzzle
	 * The string containing the puzzle.
	 * @return
	 * True if it contains a valid string representation.
	 */
	private boolean isValidPuzzleString(String puzzle) {
		if (puzzle.length() != ROW_NUM * ROW_SIZE) {
			return false;
		}
		int[] found = new int[ROW_NUM * ROW_SIZE];
		for (int i = 0; i < puzzle.length(); i++) {
			found[puzzle.charAt(i) - '0']++;
		}
		for (int i = 0; i < found.length; i++) {
			if (found[i] != 1) {
				return false;
			}
		}
		return true;
	}
	
	private boolean isPuzzleSolvable(String puzzle) {
		int inversions = 0;
		for (int i = 0; i < puzzle.length(); i++) {
			for (int j = i + 1; j < puzzle.length(); j++) {
				if (Integer.parseInt(String.valueOf(puzzle.charAt(j))) < Integer.parseInt(String.valueOf(puzzle.charAt(i))) 
						&& Integer.parseInt(String.valueOf(puzzle.charAt(j))) != 0) {
					inversions++;
				}
			}
		}
		if (inversions % 2 == 0) {
			return true;
		}
		return false;
	}
	
	@Override
    public void done() {
        panel.setCursor(Cursor.getDefaultCursor());
    }

}
