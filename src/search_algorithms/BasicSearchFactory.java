package search_algorithms;

import search_algorithms.heuristic_function.EuclideanDistanceHeurstic;
import search_algorithms.heuristic_function.ManhattanDistanceHeurstic;
import search_algorithms.informed_search.AStar;
import search_algorithms.uninformed_search.BFS;
import search_algorithms.uninformed_search.DFS;

public class BasicSearchFactory implements SearchFactory {
	
	private final String[] searchMethodes = {"BFS", "DFS", "A*"};
	private final String[] heuristicFunctions = {"Manhattan", "Euclidean"};
	private final int InformedStartIndex = 2;
	
	@Override
	public SearchAlgorithm createAlgorithm(int search_index, int heuristic_index) {
		if (search_index < 0 || search_index >= searchMethodes.length) {
			return null;
		}
		if (search_index < InformedStartIndex) {
			switch(search_index) {
				case 0:
					return new BFS();
				case 1:
					return new DFS();
				default:
					return null;
			}
		} else {
			if (heuristic_index < 0 || heuristic_index >= heuristicFunctions.length) {
				return null;
			}
			HeuristicFunction hs;
			switch(heuristic_index) {
				case 0:
					hs = new ManhattanDistanceHeurstic();
					break;
				case 1:
					hs = new EuclideanDistanceHeurstic();
					break;
				default:
					hs = null;
					break;
			}
			switch(search_index) {
				case 2:
					return new AStar(hs);
				default:
					return null;
			}
		}
	}

	@Override
	public String[] getSupportedSearchMethodes() {
		return searchMethodes;
	}

	@Override
	public String[] getSupportedHeuristicFunctions() {
		return heuristicFunctions;
	}
	
	@Override
	public boolean isInformativeMethod(int search_index) {
		return search_index >= InformedStartIndex;
	}
}
