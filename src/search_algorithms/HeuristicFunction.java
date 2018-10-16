package search_algorithms;

import state.State;
/**
 * Interface for the heuristic function that can be used.
 * @author amrnasr
 *
 */
public interface HeuristicFunction {
	/**
	 * Calculates the heuristic cost from current state to goal.
	 * @param current
	 * The current state.
	 * @param goal
	 * The goal state.
	 * @return
	 * The heuristic cost between the current state and the goal state.
	 */
	int calculateHeursiticCost(State current, State goal);
}
