package gui.workers;

import javax.swing.SwingWorker;

import gui.panels.SearchTreePanel;

public class TreeWorker extends SwingWorker<String, Void> {

	private SearchTreePanel panel;

	public TreeWorker(SearchTreePanel panel) {
		this.panel = panel;
	}

	@Override
	protected String doInBackground() throws Exception {
		// TODO Auto-generated method stub
		panel.repaint();
		return null;
	}

}
