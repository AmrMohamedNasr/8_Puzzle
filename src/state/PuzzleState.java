package state;

import java.util.ArrayList;
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
	 * rows concatenated to each other.<br>
	 * [ 1  2  3 ]<br>
	 * [ 5  7  8 ] ---->  "123578460"<br>
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
	
	/**
	 * Checks if a given string is a valid puzzle string.
	 * @param puzzle
	 * The string containing the puzzle.
	 * @return
	 * True if it contains a valid string representation.
	 */
	private boolean isValidPuzzleString(String puzzle) {
		if (puzzle.length() != ROW_NUM * ROW_SIZE) {
			return false;
		}
		int[] found = new int[ROW_NUM * ROW_SIZE];
		for (int i = 0; i < puzzle.length(); i++) {
			found[puzzle.charAt(i) - '0']++;
		}
		for (int i = 0; i < found.length; i++) {
			if (found[i] != 1) {
				return false;
			}
		}
		return true;
	}
	
	/**
	 * Makes a play in the puzzle by switching pieces at i and j.
	 * @param i
	 * The index of first piece.
	 * @param j
	 * The index of second piece.
	 * @return
	 * The new puzzle string representation.
	 */
	private String makeMove(int i, int j) {
		if (i > j) {
			int temp = j;
			j = i;
			i = temp;
		}
		StringBuilder newPuzzle = new StringBuilder();
		newPuzzle.append(this.puzzle.substring(0, i));
		newPuzzle.append(this.puzzle.charAt(j));
		newPuzzle.append(this.puzzle.substring(i + 1, j));
		newPuzzle.append(this.puzzle.charAt(i));
		newPuzzle.append(this.puzzle.substring(j + 1));
		return newPuzzle.toString();
	}
	
	private int getEmptyTilePosition(String puzzle) {
		for (int i = 0; i < puzzle.length(); i++) {
			if (puzzle.charAt(i) == '0') {
				return i;
			}
		}
		return -1;
	}
	/**
	 * Construct root state.
	 * @param puzzle
	 * The state string representation.
	 */
	public PuzzleState(String puzzle) {
		this(puzzle, 0, null);
	}
	/**
	 * Construct new state with given parameters.
	 * @param puzzle
	 * The state string representation.
	 * @param cost
	 * The cost to reach this state.
	 * @param parent
	 * The parent of this state.
	 */
	public PuzzleState(String puzzle, int cost, PuzzleState parent) {
		if (!isValidPuzzleString(puzzle)) {
			throw new RuntimeException("Invalid puzzle string " + puzzle);
		}
		this.puzzle = puzzle;
		this.children = new ArrayList<State> ();
		this.parent = parent;
		this.actual_cost = cost;
		this.heuristic_cost = 0;
		this.empty_tile = getEmptyTilePosition(puzzle);
	}
	@Override
	public void setParent(State state) {
		this.parent = state;
	}
	@Override
	public void generateChildrenStates() {
		int newCost = this.actual_cost + 1;
		if (empty_tile - ROW_SIZE >= 0) {
			children.add(new PuzzleState(
					makeMove(empty_tile - ROW_SIZE, empty_tile),
					newCost, this));
		}
		if (empty_tile + ROW_SIZE < ROW_SIZE * ROW_NUM) {
			children.add(new PuzzleState(
					makeMove(empty_tile, empty_tile + ROW_SIZE),
					newCost, this));
		}
		if (empty_tile % ROW_SIZE != 0) {
			children.add(new PuzzleState(
					makeMove(empty_tile - 1, empty_tile),
					newCost, this));
		}
		if (empty_tile % ROW_SIZE != ROW_SIZE - 1) {
			children.add(new PuzzleState(
					makeMove(empty_tile, empty_tile + 1),
					newCost, this));
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
