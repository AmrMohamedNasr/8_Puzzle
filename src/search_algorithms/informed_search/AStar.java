package search_algorithms.informed_search;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;


import search_algorithms.HeuristicFunction;
import search_algorithms.SearchAlgorithm;
import state.State;
/**
 * A* implementation.
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
	 * @param heuristic
	 * Must supply a heuristic function to be used in the search.
	 */
	public AStar(HeuristicFunction heuristic) {
		if (heuristic == null) {
			throw new RuntimeException("Must give heurstic function to A* search");
		}
		this.heuristic = heuristic;
	}
	@Override
	public State search(State root, List<State> expanded_list, State goal) {
		PriorityQueue<State> frontier = new PriorityQueue<State>();
		Map<State, Integer> frontier_map = new HashMap<State, Integer>();
		root.setHeuristicCost(heuristic.calculateHeursiticCost(root, goal));
		frontier.add(root);
		frontier_map.put(root, root.getCost());
		State current = null, target = null;
		expanded_list.clear();
		while (!frontier.isEmpty()) {
			current = frontier.poll();
			frontier_map.remove(current);
			current.generateChildrenStates();
			expanded_list.add(current);
			if (goal.equals(current)) {
				target = current;
				break;
			}
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
		return target;
	}

}
