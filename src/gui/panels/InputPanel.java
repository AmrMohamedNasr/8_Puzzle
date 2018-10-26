package gui.panels;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import gui.PuzzleFrame;
import gui.workers.SolverWorker;
import solver.PuzzleSolver;

public class InputPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private JLabel input_label, algos_label, heuristics_label;

	private JTextField input;

	private JComboBox<String> algos, heuristics;
	
	private PuzzleSolver solver;
	
	private PuzzleFrame parent;
	
	private JButton reset, start;
	
	private SolverWorker worker;
	
	public InputPanel(Font font, Color background_color, PuzzleSolver solv, PuzzleFrame paren) {
		this.solver = solv;
		this.parent = paren;
		this.setLayout(new GridLayout(1, 8, 1, 1));
		this.setBackground(background_color);
		input_label = new JLabel();
		initializeLabel(input_label, "Initial State:", font);

		input = new JTextField();
		input.setForeground(Color.BLACK);
		input.setBackground(Color.LIGHT_GRAY);
		input.setPreferredSize(new Dimension(150, 40));
		input.setHorizontalAlignment(SwingConstants.CENTER);
		input.setFont(font);
		this.add(input);

		algos_label = new JLabel();
		initializeLabel(algos_label, "Algorithm:", font);

		algos = new JComboBox<>(solver.getSearchMethodes());
		algos.setBounds(250, 30, 150, 40);
		algos.setForeground(Color.BLACK);
		algos.setBackground(Color.LIGHT_GRAY);
		algos.setFont(font);
		algos.addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent arg0) {
				boolean show_heuristic = solver.isInformativeSearch(algos.getSelectedIndex());
				heuristics.setEnabled(show_heuristic);
				heuristics.setVisible(show_heuristic);
				heuristics_label.setVisible(show_heuristic);
			}
		});
		this.add(algos);

		heuristics_label = new JLabel();
		initializeLabel(heuristics_label, "Heuristic Function:", font);

		heuristics = new JComboBox<>(solver.getHeuristicFunctions());
		heuristics.setBounds(500, 30, 200, 40);
		heuristics.setForeground(Color.BLACK);
		heuristics.setBackground(Color.LIGHT_GRAY);
		heuristics.setFont(font);
		heuristics.setVisible(false);
		heuristics_label.setVisible(false);
		this.add(heuristics);
		
		start = new JButton();
		initializeButton(start, "solve.png", "Solve");
		start.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				parent.informOutputUnready();
				int algorithm = algos.getSelectedIndex();
				int heuristic = heuristics.getSelectedIndex();
				if (worker != null) {
					worker.cancel(true);
				}
				worker = new SolverWorker(solver, parent, algorithm,
						heuristic, reset, input.getText().trim(), start);
				worker.execute();
			}
		});
		worker = null;
		reset = new JButton();
		initializeButton(reset, "reset.png", "Reset");
		reset.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (worker != null) {
					worker.cancel(true);
				}
				input.setText("");
				parent.informOutputUnready();
				start.setEnabled(true);
			}
		});

	}
	
	private void initializeLabel(JLabel label, String text, Font font) {
		label.setText(text);
		label.setFont(font);
		label.setForeground(Color.LIGHT_GRAY);
		this.add(label);
	}
	
	private void initializeButton(JButton button, String path, String text) {
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
		this.add(button);
	}
}
