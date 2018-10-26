package gui.workers;

import java.awt.Color;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import javax.swing.SwingWorker;

import gui.panels.TreePanel;
import solver.PuzzleSolver;
import state.State;

public class TreeWorker extends SwingWorker<String, Void> {

	private TreePanel panel;
	private PuzzleSolver solver;

	public TreeWorker(TreePanel panel, PuzzleSolver solver) {
		this.panel = panel;
		this.solver = solver;
	}
	private void change_color_if_possible(State state, Color color) {
		Node node = panel.get_node(state);
		if (node != null) {
			node.changeColor(color);
		}
	}
	@Override
	protected String doInBackground() throws Exception {
		State old_explored = null;
		List<State> expand = solver.getSearchResult().expanded_list;
		Set<State> hashSet = new HashSet<State>();
		for (int i = 0; i < expand.size(); i++) {
			State current = expand.get(i);
			hashSet.add(current);
			change_color_if_possible(current, Color.RED);
			if (old_explored != null) {
				change_color_if_possible(old_explored, Color.YELLOW);
			}
			
			for (int j = 0; j < current.getChildrenStates().size(); j++) {
				State child = current.getChildrenStates().get(j);
				if (!hashSet.contains(child)) {
					change_color_if_possible(child, Color.GREEN);
				}
			}
			old_explored = current;
			panel.assign_center_node(current);
			panel.repaint();
			TimeUnit.SECONDS.sleep(2);
		}
		if (old_explored != null) {
			change_color_if_possible(old_explored, Color.MAGENTA);
			panel.repaint();
		}
		return null;
	}

}
