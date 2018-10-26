package gui.panels;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
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
	private List<Node> to_draw;
	private State centerState;
	public TreePanel(PuzzleSolver solver) {
		circle_radius = 50;
		segment_length = 200;
		this.solver = solver;
		this.setLayout(new BorderLayout());
		this.setBackground(Color.WHITE);
		tree_list = new ArrayList<State> ();
		nodes = new HashMap<>();
		lines = new HashMap<>();
		centerState = null;
		to_draw = new ArrayList<Node>();
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		g2.setStroke(new BasicStroke(2));
		for (int i = 0; to_draw != null && i < to_draw.size(); i++) {
			to_draw.get(i).draw(g2);
			if (lines.containsKey(to_draw.get(i).getState()) && !to_draw.get(i).getState().equals(centerState)) {
				lines.get(to_draw.get(i).getState()).draw(g2);
			}
		}
		g2.dispose();
	}
	public Node get_node(State state) {
		return nodes.get(state);
	}
	private List<State> modelTree(State initial_state) {
		tree_list.clear();
		Set<String> set = new HashSet<>();
		tree_list.add(initial_state);
		int[] children_layer = new int[solver.getSearchResult().search_depth + 2];
		int max_children = 0;
		for (int i = 0; i < tree_list.size(); i++) {
			State state = tree_list.get(i);
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
		//this.setPreferredSize(d);
		return tree_list;
	}
	
	private int calculate_child_offset(State child, int spacing) {
		State parent = child.getParent();
		int children = parent.getChildrenStates().size();
		int child_num = parent.getChildrenStates().indexOf(child);
		if (children == 1) {
			return 0;
		} else if (children == 2) {
			if (child_num == 0) {
				return - (spacing / 2);
			} else {
				return (spacing / 2);
			}
		} else if (children == 4) {
			int rel_space = 0;
			if (child_num < 2) {
				child_num -= 2;
				rel_space = spacing / 2;
			} else {
				child_num -= 1;
				rel_space -= spacing / 2;
			}
			return (child_num) * (spacing) + rel_space;
		} else {
			child_num -= 1;
			return child_num * (spacing);
		}
	}
	public void assign_center_node(State node) {
		State basic = node;
		if (node.getParent() != null) {
			basic = node.getParent();
		}
		centerState = basic;
		Dimension d = new Dimension();
		d.height = segment_length * 2 + 2 * circle_radius;
		d.width = 2 * (6 * 3 * circle_radius + circle_radius);
		this.setPreferredSize(d);
		int basic_depth = basic.getActualCost();
		Queue<State> queue = new LinkedList<State>();
		queue.add(basic);
		to_draw.clear();
		while(!queue.isEmpty()) {
			State m = queue.poll();
			int effective_depth = m.getActualCost() - basic_depth;
			if (effective_depth == 0) {
				Node gnode = nodes.get(m);
				System.out.println(m);
				System.out.println("Center");
				gnode.move_to(new Point(d.width / 2, 50));
				to_draw.add(gnode);
			} else if (effective_depth == 1) {
				Node pnode = nodes.get(m.getParent());
				Node gnode = nodes.get(m);
				if (gnode == null) {
					continue;
				}
				int offset = calculate_child_offset(m, 10 * circle_radius);
				gnode.move_to(new Point(
							(int) (offset + pnode.getCenter().getX())
						,
							segment_length * effective_depth
						));
				to_draw.add(gnode);
				Line l = lines.get(m);
				l.move_to(pnode.getCenter().x ,
						pnode.getCenter().y + circle_radius,
						gnode.getCenter().x,
						gnode.getCenter().y - circle_radius);
			} else {
				Node pnode = nodes.get(m.getParent());
				Node gnode = nodes.get(m);
				if (gnode == null) {
					continue;
				}
				int offset = calculate_child_offset(m, 3 * circle_radius);
				gnode.move_to(new Point(
						(int) (offset + pnode.getCenter().getX())
					,
						segment_length * effective_depth
					));
				to_draw.add(gnode);
				Line l = lines.get(m);
				l.move_to(pnode.getCenter().x ,
						pnode.getCenter().y + circle_radius,
						gnode.getCenter().x,
						gnode.getCenter().y - circle_radius);
			}
			for (int i = 0; i < m.getChildrenStates().size(); i++) {
				State child = m.getChildrenStates().get(i);
				if (child.getActualCost() - basic_depth < 3) {
					queue.add(child);
				}
			}
		}
		Node gnode = nodes.get(node);
		pane.getVerticalScrollBar().setValue((int) (gnode.getCenter().y - pane.getPreferredSize().getHeight() / 2));
		pane.getHorizontalScrollBar().setValue((int) (gnode.getCenter().x - pane.getPreferredSize().getWidth() / 2));
	}
	public void build_model() {
		if (!solver.getSearchResult().goal_path.isEmpty()) {
			modelTree(solver.getSearchResult().goal_path.get(0));
			assign_center_node(solver.getSearchResult().goal_path.get(0));
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
		solver.solvePuzzle(new PuzzleState("312645078"/* "142305678" */), 0, 0);
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
