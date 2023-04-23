package simulator.view;

import java.awt.BorderLayout;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.Map;

import javax.swing.*;

import simulator.control.Controller;
import simulator.model.BodiesGroup;
import simulator.model.Body;
import simulator.model.SimulatorObserver;

@SuppressWarnings("serial")
public class ViewerWindow extends JFrame implements SimulatorObserver {
	
	private Controller ctrl;
	private SimulationViewer viewer;
	private JFrame parent;
	
	ViewerWindow(JFrame parent, Controller ctrl) {
		super("Simulation Viewer");
		this.ctrl = ctrl;
		this.parent = parent;
		intiGUI();
		ctrl.addObserver(this);
	}
	
	private void intiGUI() {
		JPanel mainPanel = new JPanel(new BorderLayout());
		mainPanel.add(new JScrollPane());
		setContentPane(mainPanel);
		viewer = new Viewer();
		mainPanel.add(viewer, BorderLayout.CENTER);
		
		addWindowListener(new WindowListener() {
			@Override
			public void windowOpened(WindowEvent e) {}
			@Override
			public void windowClosing(WindowEvent e) {}
			@Override
			public void windowClosed(WindowEvent e) {
				removeObserver();
			}
			@Override
			public void windowIconified(WindowEvent e) {}

			@Override
			public void windowDeiconified(WindowEvent e) {}

			@Override
			public void windowActivated(WindowEvent e) {}

			@Override
			public void windowDeactivated(WindowEvent e) {}});
		pack();
			/*if (parent != null)
				setLocation(
						parent.getLocation().x + parent.getWidth()/2 - getWidth()/2,
						parent.getLocation().y + parent.getHeight()/2 - getHeight()/2);*/
		setVisible(true);
	}

	@Override
	public void onAdvance(Map<String, BodiesGroup> groups, double time) {
		viewer.update();
	}

	@Override
	public void onReset(Map<String, BodiesGroup> groups, double time, double dt) {
		viewer.reset();
	}

	@Override
	public void onRegister(Map<String, BodiesGroup> groups, double time, double dt) {
		for (String i:groups.keySet()) {
			viewer.addGroup(groups.get(i));
		}
	}

	@Override
	public void onGroupAdded(Map<String, BodiesGroup> groups, BodiesGroup g) {
		viewer.addGroup(g);
	}

	@Override
	public void onBodyAdded(Map<String, BodiesGroup> groups, Body b) {
		viewer.addBody(b);
	}

	@Override
	public void onDeltaTimeChanged(double dt) {}

	@Override
	public void onForceLawsChanged(BodiesGroup g) {}
	
	private void removeObserver() {
		ctrl.removeObserver(this);
	}

}
