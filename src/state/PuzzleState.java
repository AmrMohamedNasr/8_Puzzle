package state;

import java.util.List;
/**
 * Class that represents a puzzle state,
 * containing the important information of pointers to its children,
 * to its parent and its cost. 
 * @author amrnasr
 *
 */
public class PuzzleState implements State {
	/**
	 * Row size of the puzzle.
	 */
	static final int ROW_SIZE = 3;
	/**
	 * Row number in the puzzle. 
	 */
	static final int ROW_NUM = 3;
	/**
	 * String containing the puzzle under the format all
	 * rows concatenated to each other.
	 * [ 1  2  3 ] 
	 * [ 5  7  8 ] ---->  "123578460"
	 * [ 4  6  - ]
	 */
	private String puzzle;
	/**
	 * Index of empty tile.
	 */
	private int empty_tile;
	/**
	 * Cost of reaching this state.
	 */
	private int actual_cost;
	/**
	 * Heuristic cost of reaching the goal.
	 */
	private int heuristic_cost;
	/**
	 * Children states that can be reached from this state.
	 */
	private List<State> children;
	/**
	 * The parent state of this state.
	 */
	private State parent;
	public PuzzleState(String puzzle) {
		
	}
	public PuzzleState(String puzzle, int cost, PuzzleState parent) {
		//ToDo
	}
	@Override
	public void setParent(State state) {
		this.parent = state;
	}
	@Override
	public void generateChildrenStates() {
		if (empty_tile - ROW_SIZE >= 0) {
			
		}
		if (empty_tile % ROW_SIZE != 0) {
			
		}
		if (empty_tile + ROW_SIZE < ROW_SIZE * ROW_NUM) {
			
		}
		if (empty_tile % ROW_SIZE == ROW_SIZE - 1) {
			
		}
	}
	@Override
	public List<State> getChildrenStates() {
		return children;
	}
	@Override
	public String toString() {
		return puzzle;
	}
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof PuzzleState) {
			return this.toString().equals(obj.toString());
		} else {
			return false;
		}
	}
	@Override
	public State getParent() {
		return this.parent;
	}
	@Override
	public int getActualCost() {
		return this.actual_cost;
	}
	@Override
	public int getHeuristicCost() {
		return this.heuristic_cost;
	}
	@Override
	public int getCost() {
		return this.heuristic_cost + this.actual_cost;
	}
	@Override
	public void setActualCost(int cost) {
		this.actual_cost = cost;
	}
	@Override
	public void setHeuristicCost(int cost) {
		this.heuristic_cost = cost;
	}
}
