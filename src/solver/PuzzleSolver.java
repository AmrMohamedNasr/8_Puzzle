package solver;

import java.util.ArrayList;
import java.util.List;

import search_algorithms.BasicSearchFactory;
import search_algorithms.SearchAlgorithm;
import search_algorithms.SearchFactory;
import search_algorithms.SearchResult;
import state.PuzzleState;
import state.State;

public class PuzzleSolver {
	
	private PuzzleState goal;
	private SearchResult searchResult;
	private SearchFactory factory;
	
	public PuzzleSolver(String goal) {
		this.goal = new PuzzleState(goal);
		this.factory = new BasicSearchFactory();
		this.searchResult = null;
	}
	
	public String[] getSearchMethodes() {
		return this.factory.getSupportedSearchMethodes();
	}
	public String[] getHeuristicFunctions() {
		return this.factory.getSupportedHeuristicFunctions();
	}
	public SearchResult getSearchResult() {
		return searchResult;
	}
	public boolean isInformativeSearch(int index) {
		return this.factory.isInformativeMethod(index);
	}
	public boolean solvePuzzle(State initialState, int search_method, int heursitic_function) {
		SearchAlgorithm alg = this.factory.createAlgorithm(search_method, heursitic_function);
		if (alg != null) {
			List<State> expanded_list = new ArrayList<State> ();
			searchResult = alg.search(initialState, expanded_list, goal);
			return true;
		} else {
			return false;
		}
	}
}
