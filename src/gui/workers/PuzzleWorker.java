package gui.workers;

import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.swing.SwingWorker;

import gui.panels.PuzzlePanel;
import solver.PuzzleSolver;
import state.State;

public class PuzzleWorker extends SwingWorker<String, Void> {
	
	private final PuzzleSolver solver;
	private final PuzzlePanel panel;
	
	public PuzzleWorker(PuzzleSolver solver, PuzzlePanel panel) {
		this.solver = solver;
		this.panel = panel;
	}
	@Override
	protected String doInBackground() throws Exception {
		List<State> true_path = solver.getSearchResult().goal_path;
		for (int i = 0; i < true_path.size(); i++) {
			panel.draw(true_path.get(i));
			panel.revalidate();
			TimeUnit.SECONDS.sleep(1);
		}
		return null;
	}
	
}
