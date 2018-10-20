package search_algorithms;

import java.util.ArrayList;
import java.util.List;

import state.State;

public class SearchResult {
	
	public List<State> expanded_list;
	public List<State> goal_path;
	public int goal_cost;
	public int search_depth;
	public long search_time;
	
	public SearchResult(State endState, int searchDepth, List<State> expanded_list, long time_taken) {
		this.expanded_list = expanded_list;
		this.search_depth = searchDepth;
		this.goal_cost = endState.getActualCost();
		this.goal_path = getTruePath(endState);
		this.search_time = time_taken;
	}
	
	private List<State> getTruePath(State target) {
		ArrayList<State> true_path = new ArrayList<>();
		while (target != null) {
			true_path.add(0, target);
			target = target.getParent();
		}
		return true_path;
	}
}
