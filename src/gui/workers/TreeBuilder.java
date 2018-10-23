package gui.workers;

import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import state.State;

public class TreeBuilder implements ITreeBuilder {
	private State root_state;
	private List<Node> nodes;
	private List<State> tree_list;
	private int segment_length = 400;

	public TreeBuilder(State state) {
		this.root_state = state;
	}

	@Override
	public void changeRootState(State state) {
		this.root_state = state;
		nodes = null;
		tree_list = null;
	}

	@Override
	public void buildTree() {
		if (tree_list != null) {
			return;
		}
		tree_list = new ArrayList<>();
		Set<String> set = new HashSet<>();
		tree_list.add(root_state);
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
	}

	@Override
	public void placeNodes() {
		if (tree_list == null) {
			buildTree();
		}
		HashMap<State, Node> pos = new HashMap<>();
		Node root = new Node(root_state, new Point(250, 50));
		pos.put(tree_list.get(0), root);
		for (int i = 0; i < tree_list.size(); i++) {
			State state = tree_list.get(i);
			Point p = pos.get(state).getCenter();
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
				Point new_center = new Point((int) (p.x + segment_length * Math.sin(radian)),
						(int) (p.y + segment_length * child.getActualCost()));
				Node child_node = new Node(child, new_center);
				pos.put(child, child_node);
				/*
				 * g.drawLine((int) (p.x + r * Math.sin(radian)), (int) (p.y + r
				 * * Math.cos(radian)), (int) (new_center.x - r *
				 * Math.sin(radian)), (int) (new_center.y - r *
				 * Math.cos(radian)));
				 */
				if (n % 2 == 0 && j == (n / 2 - 1)) {
					angle_counter += (2 * angle);
				} else {
					angle_counter += angle;
				}
			}
		}
	}

	@Override
	public List<Node> getNodes() {
		return nodes;
	}
}
