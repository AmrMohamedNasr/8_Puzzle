package gui.panels;

import java.awt.BasicStroke;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.swing.JPanel;

import solver.PuzzleSolver;
import state.State;

public class TreePanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private PuzzleSolver solver;
	List<State> tree_list;

	public TreePanel(PuzzleSolver solver) {
		this.solver = solver;
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		g2.setStroke(new BasicStroke(2));
		if (solver.getSearchResult() != null) {
			modelTree(solver.getSearchResult().expanded_list);
			HashMap<String, Point> pos = new HashMap<>();
			pos.put(tree_list.get(0).toString(), new Point(getWidth() / 2, 20));
			for (int i = 0; i < tree_list.size(); i++) {
				State state = tree_list.get(i);
				Point p = pos.get(state.toString());
				g.drawOval(p.x, p.y, 100, 50);
				List<State> children = state.getChildrenStates();
				int n = children.size();
				double angle = 120.0 / n;
				double angle_counter = -60.0;
				for (int j = 0; j < n; j++) {
					State child = children.get(j);
					Point new_center = new Point((int) (p.x + 70 * Math.sin(angle_counter)),
							(int) (p.y + 70 * Math.cos(angle_counter)));
					pos.put(child.toString(), new_center);
					g.drawLine((int) (p.x + Math.sin(angle)), (int) (p.y + Math.cos(angle)),
							(int) (new_center.x - Math.sin(angle_counter)),
							(int) (new_center.y - Math.cos(angle_counter)));
					if (n % 2 == 0 && j == (n / 2 - 1)) {
						angle_counter += (2 * angle);
					} else {
						angle_counter += angle;
					}
				}
			}
		}
		g2.dispose();
	}

	private List<State> modelTree(List<State> list) {
		if (tree_list != null) {
			return tree_list;
		}
		tree_list = new ArrayList<>();
		Set<String> set = new HashSet<>();
		for (int i = 0; i < list.size(); i++) {
			State state = list.get(i);
			if (set.contains(state.toString())) {
				continue;
			}
			set.add(state.toString());
			tree_list.add(state);
			for (State child : state.getChildrenStates()) {
				if (set.contains(child.toString())) {
					continue;
				}
				set.add(child.toString());
				tree_list.add(child);
			}
		}
		return tree_list;
	}
}
