package main;

import java.util.ArrayList;
import java.util.List;

import search_algorithms.HeuristicFunction;
import search_algorithms.SearchAlgorithm;
import search_algorithms.SearchResult;
import search_algorithms.heuristic_function.EuclideanDistanceHeurstic;
import search_algorithms.informed_search.AStar;
import state.PuzzleState;
import state.State;

/**
 * Our main class. Currently a dummy class just to start project.
 * 
 * @author amrnasr
 *
 */
public class Testproject {

	public static void main(String[] args) {
		PuzzleState root = new PuzzleState("123405678");
		PuzzleState goal = new PuzzleState("012345678");
		HeuristicFunction hs = new EuclideanDistanceHeurstic();
		SearchAlgorithm algo;
		algo = new AStar(hs);
		// algo = new DFS();
		List<State> expanded_list = new ArrayList<State>();
		SearchResult target = algo.search(root, expanded_list, goal);
		System.out.print("Expansion list : ");
		System.out.println(target.expanded_list.size());
		System.out.print("Cost : ");
		System.out.println(target.goal_cost);
		System.out.print("Path : ");
		for (int i = 0; i < target.goal_path.size(); i++) {
			System.out.println(target.goal_path.get(i));
		}
		System.out.println("............");
	}

}
