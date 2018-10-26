package search_algorithms.uninformed_search;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Stack;

import search_algorithms.SearchAlgorithm;
import search_algorithms.SearchResult;
import state.State;

/**
 * DFS search implementation
 * 
 * @author Michael
 *
 */
public class DFS implements SearchAlgorithm {

	@Override
	public SearchResult search(State root, List<State> expanded_list, State goal) {
		long start_time = System.nanoTime();
		Set<State> exploreHash = new HashSet<State>();
		Set<State> frontierHash = new HashSet<State>();
		Stack<State> frontier = new Stack<State>();
		State current = null, target = null;
		frontier.add(root);
		frontierHash.add(root);
		expanded_list.clear();
		int max_depth = 0;
		while (!frontier.isEmpty()) {
			current = frontier.pop();
			frontierHash.remove(current);
			if (max_depth < current.getActualCost()) {
				max_depth = current.getActualCost();
			}
			expanded_list.add(current);
			exploreHash.add(current);
			if (goal.equals(current)) {
				target = current;
				break;
			}
			current.generateChildrenStates();
			for (int i = current.getChildrenStates().size() - 1; i >= 0; i--) {
				State child = current.getChildrenStates().get(i);
				if ((!exploreHash.contains(child)) && (!frontierHash.contains(child))) {
					frontier.add(child);
					frontierHash.add(child);
				}
			}
		}
		long time_taken = System.nanoTime() - start_time;
		return new SearchResult(target, max_depth, expanded_list, time_taken);
	}

}
