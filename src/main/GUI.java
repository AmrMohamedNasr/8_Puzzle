package main;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.FileNotFoundException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import search_algorithms.SearchAlgorithm;
import search_algorithms.heuristic_function.ManhattanDistanceHeurstic;
import search_algorithms.informed_search.AStar;
import search_algorithms.uninformed_search.BFS;
import search_algorithms.uninformed_search.DFS;
import state.PuzzleState;
import state.State;

/**
 * The Class GUI.
 */
public class GUI extends JFrame {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The panel. */
	private Panel panel;

	private JScrollPane scroll_pane;

	private JButton reset, start;

	private JLabel input_label, algos_label, heuristics_label;

	private JTextField input;

	private JComboBox<String> algos, heuristics;

	private Font font;

	private Cursor cursor;

	private JTextField[][] puzzle;

	private String goal;

	private SearchAlgorithm algo;

	private List<State> list;

	/**
	 * Launch the application.
	 *
	 * @param args
	 *            the arguments
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GUI window = new GUI();
					window.pack();
					window.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 * 
	 * @throws URISyntaxException
	 * @throws FileNotFoundException
	 */
	public GUI() throws FileNotFoundException, URISyntaxException {
		initialize();
	}

	/**
	 * Initialize frame.
	 */
	private void initialize() {
		setTitle("8_Puzzle Solver");
		setPreferredSize(new Dimension(1000, 600));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(null);
		getContentPane().setBackground(Color.BLACK);

		// To get Full-screen mode
		setExtendedState(JFrame.MAXIMIZED_BOTH);

		goal = "012345678";

		font = new Font(Font.SERIF, Font.BOLD, 20);

		input_label = new JLabel();
		initializeLabel(input_label, "Initial State:", 20);

		input = new JTextField();
		input.setForeground(Color.BLACK);
		input.setBackground(Color.LIGHT_GRAY);
		input.setBounds(20, 30, 150, 40);
		input.setHorizontalAlignment(SwingConstants.CENTER);
		input.setFont(font);
		getContentPane().add(input);

		algos_label = new JLabel();
		initializeLabel(algos_label, "Algorithm:", 250);

		algos = new JComboBox<>(new String[] { "A*", "BFS", "DFS" });
		algos.setBounds(250, 30, 150, 40);
		algos.setForeground(Color.BLACK);
		algos.setBackground(Color.LIGHT_GRAY);
		algos.setFont(font);
		algos.addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent arg0) {
				heuristics.setEnabled(arg0.getItem().toString().equals("A*"));
				heuristics_label.setVisible(arg0.getItem().toString().equals("A*"));
			}
		});
		getContentPane().add(algos);

		heuristics_label = new JLabel();
		initializeLabel(heuristics_label, "Heuristic Function:", 500);

		heuristics = new JComboBox<>(new String[] { "Manhattan Distance", "Euclidean Distance" });
		heuristics.setBounds(500, 30, 200, 40);
		heuristics.setForeground(Color.BLACK);
		heuristics.setBackground(Color.LIGHT_GRAY);
		heuristics.setFont(font);
		getContentPane().add(heuristics);

		cursor = new Cursor(Cursor.HAND_CURSOR);

		start = new JButton();
		initializeButton(start, "start.png", "Simulate", 800);
		start.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				int algorithm = algos.getSelectedIndex();
				if (algorithm == 0) {
					int heuristic = heuristics.getSelectedIndex();
					if (heuristic == 0) {
						algo = new AStar(new ManhattanDistanceHeurstic());
					}
				} else if (algorithm == 1) {
					algo = new BFS();
				} else {
					algo = new DFS();
				}

				list = new ArrayList<>();
				try {
					State target = algo.search(new PuzzleState(input.getText().trim()), list, new PuzzleState(goal));
					if (target == null) {
						JOptionPane.showMessageDialog(null, "No Valid Solution");
						reset.doClick();
						return;
					}
					List<State> true_path = getTruePath(target);
					for (int i = 0; i < true_path.size(); i++) {
						int[][] matrix = toMatrix(true_path.get(i));
						draw(matrix);
					}
				} catch (RuntimeException e) {
					JOptionPane.showMessageDialog(null, e.getMessage());
					reset.doClick();
					return;
				}
			}
		});

		reset = new JButton();
		initializeButton(reset, "reset.png", "Reset", 900);
		reset.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				input.setText("");
				for (int i = 0; i < 3; i++) {
					for (int j = 0; j < 3; j++) {
						puzzle[i][j].setBackground(Color.DARK_GRAY);
						puzzle[i][j].setText("");
					}
				}
			}
		});

		panel = new Panel();

		scroll_pane = new JScrollPane(panel);
		scroll_pane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		scroll_pane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		scroll_pane.setBounds(350, 120, 1000, 580);
		scroll_pane.setAutoscrolls(true);
		getContentPane().add(scroll_pane);

		puzzle = new JTextField[3][3];
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				puzzle[i][j] = new JTextField();
				puzzle[i][j].setEditable(false);
				puzzle[i][j].setFont(font);
				puzzle[i][j].setBackground(Color.DARK_GRAY);
				puzzle[i][j].setHorizontalAlignment(SwingConstants.CENTER);
				puzzle[i][j].setBounds(20 + j * 50, 120 + i * 50, 50, 50);
				getContentPane().add(puzzle[i][j]);
			}
		}
	}

	private void initializeLabel(JLabel label, String text, int x) {
		label.setBounds(x, 10, 180, 20);
		label.setText(text);
		label.setFont(font);
		label.setForeground(Color.LIGHT_GRAY);
		getContentPane().add(label);
	}

	private void initializeButton(JButton button, String path, String text, int x) {
		button.setBounds(x, 15, 60, 60);
		ImageIcon icon = new ImageIcon(path);
		Image img = icon.getImage();
		Image newimg = img.getScaledInstance(button.getWidth(), button.getHeight(), java.awt.Image.SCALE_SMOOTH);
		icon = new ImageIcon(newimg);
		button.setIcon(icon);
		button.setToolTipText(text);
		// button.setContentAreaFilled(false);
		// button.setFocusPainted(false);
		button.setCursor(cursor);
		getContentPane().add(button);
	}

	private List<State> getTruePath(State target) {
		ArrayList<State> true_path = new ArrayList<>();
		while (target != null) {
			true_path.add(0, target);
			target = target.getParent();
		}
		return true_path;
	}

	private int[][] toMatrix(State state) {
		int[][] matrix = new int[3][3];
		String s = state.toString();
		for (int i = 0; i < s.length(); i++) {
			matrix[i / 3][i % 3] = Integer.parseInt(s.charAt(i) + "");
		}
		return matrix;
	}

	private void draw(int[][] matrix) {
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
