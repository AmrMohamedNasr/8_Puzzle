package search_algorithms.heuristic_function;

import search_algorithms.HeuristicFunction;
import state.PuzzleState;
import state.State;
/**
 * Implements Manhattan distance heuristic.
 * @author amrnasr
 *
 */
public class ManhattanDistanceHeurstic implements HeuristicFunction {

	@Override
	public int calculateHeursiticCost(State current, State goal) {
		int distance = 0;
		String s = current.toString();
		String g = goal.toString();
		int[][] coords = new int[s.length()][2];
		for (int i = 0; i < s.length(); i++) {
			coords[s.charAt(i) - '0'][0] = i / PuzzleState.ROW_SIZE;
			coords[s.charAt(i) - '0'][1] = i % PuzzleState.ROW_SIZE;
		}
		for (int j = 0; j < g.length(); j++) {
			int x = j / PuzzleState.ROW_SIZE;
			int y = j % PuzzleState.ROW_SIZE;
			int index = g.charAt(j)- '0';
			distance += Math.abs(coords[index][0] - x) + Math.abs(coords[index][1] - y); 
		}
		return distance;
	}

}
