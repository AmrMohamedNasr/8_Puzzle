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
		} catch (InvalidStateException e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
			reset.doClick();
		}
		return null;
	}
	
	@Override
    public void done() {
        panel.setCursor(Cursor.getDefaultCursor());
    }

}
