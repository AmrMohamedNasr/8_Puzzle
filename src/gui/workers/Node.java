package gui.workers;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.Ellipse2D;

import state.State;

public class Node implements INode {
	private Point center;
	private int radius, left_upper_x, left_upper_y;
	private String content;
	private Color color;
	private Ellipse2D ellipse;
	private State state;

	public Node(State state, Point center) {
		this.state = state;
		this.center = center;
		this.left_upper_x = center.x - radius;
		this.left_upper_y = center.y - radius;
		radius = 50;
		content = "State: " + state.toString() + "\nActual Cost: " + state.getActualCost() + "\nHeuristic Cost: "
				+ state.getHeuristicCost() + "\nTotal Cost: " + state.getCost();
		this.color = Color.BLACK;
		ellipse = new Ellipse2D.Double(left_upper_x, left_upper_y, 2 * radius, 2 * radius);
	}

	@Override
	public void changeColor(Color color) {
		this.color = color;
	}

	@Override
	public void draw(Graphics2D g) {
		g.setColor(color);
		g.draw(ellipse);
		g.drawString(content, left_upper_x, center.y);
	}

	@Override
	public State getState() {
		return state;
	}

	@Override
	public Point getCenter() {
		return center;
	}
}
