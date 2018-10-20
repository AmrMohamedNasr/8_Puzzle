package search_algorithms.uninformed_search;

import java.util.List;
import java.util.Stack;

import search_algorithms.SearchAlgorithm;
import search_algorithms.SearchResult;
import state.State;
/**
 * DFS search implementation
 * @author Michael
 *
 */
public class DFS implements SearchAlgorithm {

	@Override
	public SearchResult search(State root, List<State> expanded_list, State goal) {
		long start_time = System.nanoTime();
		Stack<State> frontier = new Stack<State>();
		State current = null, target = null;
		frontier.add(root);
		expanded_list.clear();
		int max_depth = 0;
		while (!frontier.isEmpty()) {
			current = frontier.pop();
			if (max_depth < current.getActualCost()) {
				max_depth = current.getActualCost();
			}
			current.generateChildrenStates();
			expanded_list.add(current);
			if (goal.equals(current)) {
				target = current;
				break;
			}
			for (int i = current.getChildrenStates().size() - 1; i >= 0 ; i--) {
				State child = current.getChildrenStates().get(i);
				if (!expanded_list.contains(child) && !frontier.contains(child)) {
					frontier.add(child);
				}
			}
		}
		long time_taken = System.nanoTime() - start_time;
		return new SearchResult(target, max_depth, expanded_list, time_taken);
	}

}
