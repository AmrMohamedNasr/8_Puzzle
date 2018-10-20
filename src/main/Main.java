package main;

import java.awt.EventQueue;

import gui.PuzzleFrame;

public class Main {
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					PuzzleFrame window = new PuzzleFrame();
					window.pack();
					window.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}
