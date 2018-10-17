package search_algorithms.uninformed_search;

import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

import search_algorithms.SearchAlgorithm;
import state.State;

public class DFS implements SearchAlgorithm {

	@Override
	public State search(State root, List<State> expanded_list, State goal) {
		Stack<State> frontier = new Stack<State>();
		State current = null, target = null;
		frontier.add(root);
		expanded_list.clear();
		while(!frontier.isEmpty()) {
			current = frontier.pop();
			current.generateChildrenStates();
			expanded_list.add(current);
			if (goal.equals(current)) {
				target = current;
				break;
			}
			for (int i = 0; i < current.getChildrenStates().size(); i++) {
				State child = current.getChildrenStates().get(i);
				if (!expanded_list.contains(child) && !frontier.contains(child)) {
					frontier.add(child);
				}
			}
		}
		return target;
	}

}
