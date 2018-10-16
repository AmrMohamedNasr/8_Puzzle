package search_algorithms.uninformed_search;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import search_algorithms.SearchAlgorithm;
import state.State;
/**
 * BFS search implementation.
 * @author amrnasr
 *
 */
public class BFS implements SearchAlgorithm {

	@Override
	public State search(State root, List<State> expanded_list, State goal) {
		Queue<State> frontier = new LinkedList<State>();
		expanded_list.clear();
		State current = null, target = null;
		frontier.add(root);
		while(!frontier.isEmpty()) {
			current = frontier.remove();
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
