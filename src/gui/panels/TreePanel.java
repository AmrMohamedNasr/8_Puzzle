package gui.panels;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import solver.PuzzleSolver;
import state.PuzzleState;
import state.State;

public class TreePanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private PuzzleSolver solver;
	List<State> tree_list;
	public JScrollPane pane;
	private int circle_radius, segment_length;

	public TreePanel(PuzzleSolver solver) {
		circle_radius = 50;
		segment_length = 400;
		this.solver = solver;
		this.setLayout(new BorderLayout());
		setPreferredSize(
				new Dimension((int) (Math.pow(4, solver.getSearchResult().search_depth + 1) * (4 * circle_radius)),
						(solver.getSearchResult().search_depth + 1) * (4 * circle_radius)));
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		g2.setStroke(new BasicStroke(2));
		int r = circle_radius, l = segment_length;
		if (solver.getSearchResult() != null) {
			modelTree(solver.getSearchResult().goal_path.get(0));
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
		}
		g2.dispose();
		// this.setPreferredSize(maxDimension);
		pane.setViewportView(this);
		// this.setPreferredSize(maxDimension);
	}

	private List<State> modelTree(State initial_state) {
		if (tree_list != null) {
			return tree_list;
		}
		tree_list = new ArrayList<>();
		Set<String> set = new HashSet<>();
		tree_list.add(initial_state);
		for (int i = 0; i < tree_list.size(); i++) {
			// System.out.println(tree_list.size());
			State state = tree_list.get(i);
			set.add(state.toString());
			state.generateChildrenStates();
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

	public static void main(String[] args) {
		PuzzleSolver solver = new PuzzleSolver("012345678");
		solver.solvePuzzle(new PuzzleState("123405678"/* "142305678" */), 2, 0);
		TreePanel panel = new TreePanel(solver);
		JScrollPane pane = new JScrollPane(panel, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		pane.setPreferredSize(new Dimension(1500, 500));
		panel.pane = pane;
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					// PuzzleSolver solver = new PuzzleSolver("012345678");
					// solver.solvePuzzle(new PuzzleState("123405678"/*
					// "142305678" */), 2, 0);
					JFrame window = new JFrame();
					window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
					/*
					 * TreePanel panel = new TreePanel(solver); JScrollPane pane
					 * = new JScrollPane(panel,
					 * JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
					 * JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
					 * pane.setPreferredSize(new Dimension(1500, 500));
					 * panel.pane = pane;
					 */
					window.add(pane, BorderLayout.CENTER);
					window.pack();
					window.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}
