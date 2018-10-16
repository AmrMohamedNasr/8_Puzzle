package state;

import java.util.List;

/**
 * State interface.
 * All functions needed for a state.
 * @author amrnasr
 *
 */
public interface State {
	/**
	 * Sets the state parent.
	 * @param state
	 * The parent state of this state.
	 */
	void setParent(State state);
	/**
	 * Getter for the parent of the state.
	 * @return
	 * The parent state, or null if called on root state.
	 */
	State getParent();
	/**
	 * Getter for the cost to reach a child.
	 * @return
	 * The actual cost for reaching a child.
	 */
	int getActualCost();
	/**
	 * Getter for the heuristic remaining cost to reach the goal.
	 * @return
	 * The heuristic remaining cost for reaching the goal.
	 */
	int getHeuristicCost();
	/**
	 * Get the heuristic total cost for reaching the goal.
	 * @return
	 * The actual cost to reach the child + the heuristic remaining
	 * cost to reach the goal.
	 */
	int getCost();
	/**
	 * Setter for the actual cost.
	 * @param cost
	 * The cost to reach this state.
	 */
	void setActualCost(int cost);
	/**
	 * Setter for the heuristic cost.
	 * @param cost
	 * The heuristic cost to reach the goal from this state.
	 */
	void setHeuristicCost(int cost);
	/**
	 * Generates all possible states that can be reached from this state.
	 */
	void generateChildrenStates();
	/**
	 * Returns all children states reachable from this state.
	 * @return
	 * A list of states.
	 */
	List<State> getChildrenStates();
	/**
	 * Transforms the state into a string representation.
	 * @return
	 * The string representation of the state.
	 */
	String toString();
	/**
	 * Checks if an object is equal to this state.
	 * @param obj
	 * The object to check if it is equal.
	 * @return
	 * True if they are equal.
	 */
	boolean equals(Object obj);
}
