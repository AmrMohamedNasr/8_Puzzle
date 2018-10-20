package search_algorithms.informed_search;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

import search_algorithms.HeuristicFunction;
import search_algorithms.SearchAlgorithm;
import search_algorithms.SearchResult;
import state.State;

/**
 * A* implementation.
 * 
 * @author amrnasr
 *
 */
public class AStar implements SearchAlgorithm {

	/**
	 * Heuristic function used in search.
	 */
	private HeuristicFunction heuristic;

	/**
	 * Constructor for the informed search method A*.
	 * 
	 * @param heuristic
	 *            Must supply a heuristic function to be used in the search.
	 */
	public AStar(HeuristicFunction heuristic) {
		if (heuristic == null) {
			throw new RuntimeException("Must give heurstic function to A* search");
		}
		this.heuristic = heuristic;
	}

	@Override
	public SearchResult search(State root, List<State> expanded_list, State goal) {
		long start_time = System.nanoTime();
		PriorityQueue<State> frontier = new PriorityQueue<State>();
		Map<State, Double> frontier_map = new HashMap<State, Double>();
		root.setHeuristicCost(heuristic.calculateHeursiticCost(root, goal));
		frontier.add(root);
		frontier_map.put(root, root.getCost());
		State current = null, target = null;
		expanded_list.clear();
		int max_depth = 0;
		while (!frontier.isEmpty()) {
			current = frontier.poll();
			frontier_map.remove(current);
			if (max_depth < current.getActualCost()) {
				max_depth = current.getActualCost();
			}
			expanded_list.add(current);
			if (goal.equals(current)) {
				target = current;
				break;
			}
			current.generateChildrenStates();
			for (int i = 0; i < current.getChildrenStates().size(); i++) {
				State child = current.getChildrenStates().get(i);
				if (!expanded_list.contains(child) && !frontier_map.containsKey(child)) {
					child.setHeuristicCost(heuristic.calculateHeursiticCost(child, goal));
					frontier.add(child);
					frontier_map.put(child, child.getCost());
				} else if (frontier_map.containsKey(child)) {
					child.setHeuristicCost(heuristic.calculateHeursiticCost(child, goal));
					if (child.getCost() < frontier_map.get(child)) {
						frontier_map.remove(child);
						frontier.remove(child);
						frontier.add(child);
						frontier_map.put(child, child.getCost());
					}
				}
			}
		}
		long time_taken = System.nanoTime() - start_time;
		return new SearchResult(target, max_depth, expanded_list, time_taken);
	}

}
