package gui.workers;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;

import state.State;

public interface INode {
	/**
	 * enable the program to change the color of the node accoridng to its
	 * current status;
	 * 
	 * @param color
	 *            color to be changed to.
	 */
	public void changeColor(Color color);

	/**
	 * draw the node on the panel using the Graphics2D instance
	 * 
	 * @param g
	 *            Graphics2D instance to draw on the panel with.
	 */
	public void draw(Graphics2D g);

	/**
	 * returns the object wrapped in the node instance
	 * 
	 * @return State state
	 */
	public State getState();

	/**
	 * returns the center point of the node
	 * 
	 * @return Point center
	 */
	public Point getCenter();
	public void move_to(Point center);
}
