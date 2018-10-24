package gui.workers;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.Ellipse2D;

import state.State;

public class Node implements INode {
	private Point center;
	private int radius, left_upper_x, left_upper_y;
	private String content[];
	private Color color;
	private Ellipse2D ellipse;
	private State state;

	public Node(State state, Point center) {
		this.state = state;
		this.center = center;
		radius = 50;
		this.left_upper_x = center.x - radius;
		this.left_upper_y = center.y - radius;
		content = new String[4];
		content[0] = "s:" + state.toString();
		content[1] = "g(s): " + state.getActualCost();
		content[2] = "h(s): " + state.getHeuristicCost();
		content[3] = "f(s): " + state.getCost();
		this.color = Color.BLACK;
		ellipse = new Ellipse2D.Double(left_upper_x, left_upper_y, 2 * radius, 2 * radius);
	}

	public void move_to(Point center) {
		this.center = center;
		this.left_upper_x = center.x - radius;
		this.left_upper_y = center.y - radius;
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
		g.drawOval(left_upper_x, left_upper_y, 2 * radius, 2 * radius);
		for (int i = 0; i < content.length; i++) {
			if (i == 0) {
				g.drawString(content[i], center.x - (radius) + 8, center.y + ( 2 - i) * (2 + radius / 4) - 5);
			} else {
				g.drawString(content[i], center.x - (radius / 2), center.y + ( 2 - i) * (2 + radius / 4) - 5);
			}
		}
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
