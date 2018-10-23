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
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import gui.workers.Line;
import gui.workers.Node;
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
	private HashMap<State, Node> nodes;
	private HashMap<State, Line> lines;
	private State centerState;
	public TreePanel(PuzzleSolver solver) {
		circle_radius = 50;
		segment_length = 200;
		this.solver = solver;
		this.setLayout(new BorderLayout());
		tree_list = new ArrayList<State> ();
		nodes = new HashMap<>();
		lines = new HashMap<>();
		centerState = null;
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		g2.setStroke(new BasicStroke(2));
		Iterator<Node> it = nodes.values().iterator();
		while (it.hasNext()) {
			Node n = it.next();
			n.draw(g2);
			if (lines.containsKey(n.getState())) {
				lines.get(n.getState()).draw(g2);
			}
		}
		g2.dispose();
	}

	private List<State> modelTree(State initial_state) {
		tree_list.clear();
		Set<String> set = new HashSet<>();
		tree_list.add(initial_state);
		int[] children_layer = new int[solver.getSearchResult().search_depth + 2];
		int max_children = 0;
		for (int i = 0; i < tree_list.size(); i++) {
			State state = tree_list.get(i);
			System.out.println(solver.getSearchResult().search_depth + 1);
			System.out.println(state.getActualCost());
			children_layer[state.getActualCost()]++;
			max_children = max_children > children_layer[state.getActualCost()] ? max_children : children_layer[state.getActualCost()];
			set.add(state.toString());
			for (State child : state.getChildrenStates()) {
				if (set.contains(child.toString())) {
					continue;
				}
				tree_list.add(child);
			}
		}
		int r = circle_radius, l = segment_length;
		Dimension d = new Dimension();
		d.width = 2 * (((max_children + 1) / 2) * (3 * r) + r);
		d.height = l * (solver.getSearchResult().search_depth + 1) + 2 * r;
		int[] start_child_at = new int[solver.getSearchResult().search_depth + 2];
		for (int i = 0; i < children_layer.length; i++) {
			int half_child = (children_layer[i] + 1) / 2;
			start_child_at[i] = (d.width / 2) - (half_child) * (3 * r);
		}
		if (!tree_list.isEmpty()) {
			HashMap<String, Point> pos = new HashMap<>();
			nodes.clear();
			lines.clear();
			int[] current_child = new int[solver.getSearchResult().search_depth + 2];
			pos.put(tree_list.get(0).toString(), new Point(d.width / 2, 50));
			for (int i = 0; i < tree_list.size(); i++) {
				State state = tree_list.get(i);
				Point p = pos.get(state.toString());
				Node node = new Node(state, p);
				nodes.put(state, node);
				List<State> children = state.getChildrenStates();
				int n = children.size();
				if (n == 0) {
					continue;
				}
				for (int j = 0; j < n; j++) {
					State child = children.get(j);
					Point new_center = new Point((int) (start_child_at[child.getActualCost()] + (3 * r) * current_child[child.getActualCost()]),
							(int) (l * child.getActualCost()));
					current_child[child.getActualCost()]++;
					pos.put(child.toString(), new_center);
					Line line = new Line((int) (p.x), (int) (p.y + r ),
							(int) (new_center.x), (int) (new_center.y - r));
					lines.put(child, line);
				}
			}
		}
		d.width += 2 * r;
		d.height += 2 * r;
		this.setPreferredSize(d);
		return tree_list;
	}

	public void build_model() {
		if (!solver.getSearchResult().goal_path.isEmpty()) {
			modelTree(solver.getSearchResult().goal_path.get(0));
			this.revalidate();
			this.repaint();
			pane.setViewportView(this);
		}
	}
	
	public void reset_model() {
		tree_list.clear();
	}
	public static void main(String[] args) {
		PuzzleSolver solver = new PuzzleSolver("012345678");
		solver.solvePuzzle(new PuzzleState("123405678"/* "142305678" */), 2, 0);
		TreePanel panel = new TreePanel(solver);
		JScrollPane pane = new JScrollPane(panel, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		pane.setPreferredSize(new Dimension(1500, 500));
		panel.pane = pane;
		panel.build_model();
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
