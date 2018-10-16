package main;

import state.PuzzleState;

/**
 * Our main class.
 * Currently a dummy class just to start project.
 * @author amrnasr
 *
 */
public class Testproject {
	
	public static void main(String[] args) {
		PuzzleState state = new PuzzleState("123405678");
		state.generateChildrenStates();
		System.out.println(state);
		for (int i = 0; i < state.getChildrenStates().size(); i++) {
			System.out.println(state.getChildrenStates().get(i));
		}
		System.out.println(state);
	}

}
