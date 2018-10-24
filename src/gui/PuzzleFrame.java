package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.JFrame;

import gui.panels.InformationPanel;
import gui.panels.InputPanel;
import gui.panels.PuzzlePanel;
import gui.panels.SearchTreePanel;
import solver.PuzzleSolver;

public class PuzzleFrame extends JFrame implements OutputDependentComponent {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private InputPanel inputPanel;
	private PuzzlePanel puzzlePanel;
	private SearchTreePanel treePanel;
	private InformationPanel infoPanel;
	private PuzzleSolver solver;

	public PuzzleFrame() {
		setTitle("8_Puzzle Solver");
		setPreferredSize(new Dimension(1000, 600));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		this.setLayout(new BorderLayout());
		Font font = new Font(Font.SERIF, Font.BOLD, 20);
		Color bg_color = Color.BLACK;
		getContentPane().setBackground(bg_color);
		solver = new PuzzleSolver("012345678");
		inputPanel = new InputPanel(font, bg_color, solver, this);
		this.add(inputPanel, BorderLayout.NORTH);
		puzzlePanel = new PuzzlePanel(font, bg_color, solver);
		this.add(puzzlePanel, BorderLayout.WEST);
		treePanel = new SearchTreePanel(solver, this);
		this.add(treePanel, BorderLayout.CENTER);
		infoPanel = new InformationPanel(font, bg_color, solver);
		this.add(infoPanel, BorderLayout.SOUTH);
	}

	@Override
	public void informOutputReady() {
		this.puzzlePanel.informOutputReady();
		this.infoPanel.informOutputReady();
		this.treePanel.informOutputReady();
	}

	@Override
	public void informOutputUnready() {
		this.puzzlePanel.informOutputUnready();
		this.infoPanel.informOutputUnready();
		this.treePanel.informOutputUnready();
	}
}
