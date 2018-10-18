package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JPanel;

public class Panel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1;

	public Panel() {
		initialize();
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
	}

	private void initialize() {
		setBackground(Color.WHITE);
		setPreferredSize(new Dimension(1100, 570));
	}
}
