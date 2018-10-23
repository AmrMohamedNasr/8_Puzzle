package gui.workers;

import java.awt.Point;
import java.util.HashMap;
import java.util.List;

import javax.swing.SwingWorker;

import gui.panels.SearchTreePanel;
import gui.panels.TreePanel;
import solver.PuzzleSolver;
import state.State;

public class TreeWorker extends SwingWorker<String, Void> {

	private TreePanel panel;

	public TreeWorker(TreePanel panel, PuzzleSolver solver) {
		this.panel = panel;
	}

	@Override
	protected String doInBackground() throws Exception {
		
		/*int r = circle_radius, l = segment_length;
		if (!tree_list.isEmpty()) {
			HashMap<String, Point> pos = new HashMap<>();
			pos.put(tree_list.get(0).toString(), new Point(getWidth() / 2, 50));
			for (int i = 0; i < tree_list.size(); i++) {
				State state = tree_list.get(i);
				Point p = pos.get(state.toString());
				g.drawOval(p.x - r, p.y - r, 2 * r, 2 * r);
				List<State> children = state.getChildrenStates();
				int n = children.size();
				if (n == 0) {
					continue;
				}
				double angle = 120.0 / (n / 2 * 2);
				double angle_counter = -60.0;
				double radian;
				for (int j = 0; j < n; j++) {
					State child = children.get(j);
					radian = angle_counter * Math.PI / 360.0;
					Point new_center = new Point((int) (p.x + l * Math.sin(radian)),
							(int) (p.y + l * child.getActualCost()));
					pos.put(child.toString(), new_center);
					g.drawLine((int) (p.x + r * Math.sin(radian)), (int) (p.y + r * Math.cos(radian)),
							(int) (new_center.x - r * Math.sin(radian)), (int) (new_center.y - r * Math.cos(radian)));
					if (n % 2 == 0 && j == (n / 2 - 1)) {
						angle_counter += (2 * angle);
					} else {
						angle_counter += angle;
					}
				}
			}
		}*/
		panel.repaint();
		return null;
	}

}
