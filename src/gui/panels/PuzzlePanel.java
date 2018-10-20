package gui.panels;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import gui.OutputDependentComponent;
import gui.workers.PuzzleWorker;
import solver.PuzzleSolver;
import state.PuzzleState;
import state.State;

public class PuzzlePanel extends JPanel implements OutputDependentComponent {


	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private JTextField[][] puzzle;
	private PuzzleSolver solver;
	private JButton start, reset;
	private PuzzleWorker puzzleWorker;
	
	public PuzzlePanel(Font font, Color bg, PuzzleSolver solv) {
		this.setLayout(new GridBagLayout());
		this.setBackground(bg);
		GridBagConstraints gbc = new GridBagConstraints();
		puzzle = new JTextField[3][3];
		solver = solv;
		Dimension labelDimension = new Dimension(60, 60);
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				puzzle[i][j] = new JTextField();
				puzzle[i][j].setEditable(false);
				puzzle[i][j].setFont(font);
				puzzle[i][j].setBackground(Color.DARK_GRAY);
				puzzle[i][j].setHorizontalAlignment(SwingConstants.CENTER);
				puzzle[i][j].setPreferredSize(labelDimension);
				gbc.gridx = j;
				gbc.gridy = i + 1;
				gbc.gridheight = 1;
				gbc.gridwidth = 1;
				gbc.fill = GridBagConstraints.NONE;
				this.add(puzzle[i][j],gbc);
			}
		}
		start = new JButton();
		initializeButton(start, "start.png", "Simulate",0, 0);
		PuzzlePanel panel = this;
		start.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				puzzleWorker.cancel(true);
				puzzleWorker = new PuzzleWorker(solver, panel);
				puzzleWorker.execute();
				reset.setEnabled(true);
				start.setEnabled(false);
			}
		});

		reset = new JButton();
		initializeButton(reset, "reset.png", "Reset", 2, 0);
		reset.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				puzzleWorker.cancel(true);
				reset.setEnabled(false);
				start.setEnabled(true);
				draw(solver.getSearchResult().goal_path.get(0));
			}
		});
		start.setEnabled(false);
		reset.setEnabled(false);
		draw(new PuzzleState("012345678"));
		puzzleWorker = new PuzzleWorker(solver, this);
	}
	
	private void initializeButton(JButton button, String path, String text, int x, int y) {
		Cursor cursor = new Cursor(Cursor.HAND_CURSOR);
		Dimension buttonDimensions = new Dimension(60, 60);
		button.setPreferredSize(buttonDimensions);
		button.setSize(buttonDimensions);
		ImageIcon icon = new ImageIcon(path);
		Image img = icon.getImage();
		Image newimg = img.getScaledInstance((int)buttonDimensions.getWidth(),
				(int)buttonDimensions.getHeight(), java.awt.Image.SCALE_SMOOTH);
		icon = new ImageIcon(newimg);
		button.setIcon(icon);
		button.setToolTipText(text);
		button.setCursor(cursor);
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridx = x;
		gbc.gridy = y;
		gbc.gridheight = 1;
		gbc.gridwidth = 1;
		gbc.fill = GridBagConstraints.NONE;
		this.add(button, gbc);
	}


	@Override
	public void informOutputReady() {
		puzzleWorker.cancel(true);
		puzzleWorker = new PuzzleWorker(solver, this);
		start.setEnabled(true);
		reset.setEnabled(false);
		draw(solver.getSearchResult().goal_path.get(0));
	}


	@Override
	public void informOutputUnready() {
		puzzleWorker.cancel(true);
		puzzleWorker = new PuzzleWorker(solver, this);
		start.setEnabled(false);
		reset.setEnabled(false);
		draw(new PuzzleState("012345678"));
	}
	
	private int[][] toMatrix(State state) {
		int[][] matrix = new int[3][3];
		String s = state.toString();
		for (int i = 0; i < s.length(); i++) {
			matrix[i / 3][i % 3] = Integer.parseInt(s.charAt(i) + "");
		}
		return matrix;
	}
	
	public void draw(State state) {
		int[][] matrix = toMatrix(state);
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				int k = matrix[i][j];
				if (k == 0) {
					puzzle[i][j].setBackground(Color.DARK_GRAY);
					puzzle[i][j].setText("");
					continue;
				}
				puzzle[i][j].setText(k + "");
				puzzle[i][j].setBackground(Color.getHSBColor(k * 12 / 256.0f, 1.0f, 1.0f));
			}
		}
	}
}
