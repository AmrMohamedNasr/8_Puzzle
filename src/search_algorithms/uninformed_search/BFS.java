package search_algorithms.uninformed_search;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;

import search_algorithms.SearchAlgorithm;
import search_algorithms.SearchResult;
import state.State;

/**
 * BFS search implementation.
 * 
 * @author amrnasr
 *
 */
public class BFS implements SearchAlgorithm {

	@Override
	public SearchResult search(State root, List<State> expanded_list, State goal) {
		long start_time = System.nanoTime();
		Set<State> exploreHash = new HashSet<State>();
		Queue<State> frontier = new LinkedList<State>();
		expanded_list.clear();
		State current = null, target = null;
		frontier.add(root);
		int max_depth = 0;
		while (!frontier.isEmpty()) {
			current = frontier.remove();
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
			for (int i = 0; i < current.getChildrenStates().size(); i++) {
				State child = current.getChildrenStates().get(i);
				if (!exploreHash.contains(child) && !frontier.contains(child)) {
					frontier.add(child);
				}
			}
		}
		long time_taken = System.nanoTime() - start_time;
		return new SearchResult(target, max_depth, expanded_list, time_taken);
	}

}
