package search_algorithms;

import java.util.List;

import state.State;
/**
 * Interface for the different algorithms used to solve a problem
 * by search methods.
 * @author amrnasr
 *
 */
public interface SearchAlgorithm {
	/**
	 * Searches the state space for a solution.
	 * @param root
	 * The root state to begin search from.
	 * @param expanded_list
	 * The expanded list in order of expansion. Filled by function.
	 * @param goal
	 * The goal state representation used to test if we reached the goal state.
	 * @return
	 * The search results containing all needed information about the search and the results.
	 */
	SearchResult search(State root, List<State> expanded_list, State goal);
}
