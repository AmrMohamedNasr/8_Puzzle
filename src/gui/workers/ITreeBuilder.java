package gui.workers;

import java.util.List;

import state.State;

public interface ITreeBuilder {

	public void changeRootState(State state);

	public void buildTree();

	public void placeNodes();

	public List<Node> getNodes();
}
