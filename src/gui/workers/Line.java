package gui.workers;

import java.awt.Graphics2D;

public class Line {
	private int x1, x2, y1, y2;
	
	public Line(int x1, int y1, int x2, int y2) {
		this.x1 = x1;
		this.x2 = x2;
		this.y1 = y1;
		this.y2 = y2;
	}
	
	public void draw(Graphics2D g) {
		g.drawLine(x1, y1, x2, y2);
	}
	
	public void move_to(int x1, int y1, int x2, int y2) {
		this.x1 = x1;
		this.x2 = x2;
		this.y1 = y1;
		this.y2 = y2;
	}

}
