package search_algorithms;

import java.util.List;

import state.State;
/**
 * Interface for the different algorithms used to solve a problem
 * by informed search methods.
 * @author amrnasr
 *
 */
public interface InformedSearchAlgorithm {
	/**
	 * Searches the state space for a solution.
	 * @param root
	 * The root state to begin search from.
	 * @param expaneded_list
	 * The expanded list in order of expansion. Filled by function.
	 * @param goal
	 * The goal state representation used to test if we reached the goal state.
	 * @param heurstic
	 * Contains the heuristic function used in calculations.
	 * @return
	 * The goal state reached by the search algorithm that we can obtain from
	 * it the path and cost of path found by the algorithm, or return null if no
	 * path to goal found in state space.
	 */
	State search(State root, List<State> expaneded_list, State goal,
			HeuristicFunction heurstic);
}
