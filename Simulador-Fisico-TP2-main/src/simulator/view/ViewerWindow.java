package simulator.view;

import java.awt.BorderLayout;
import java.awt.event.WindowListener;
import java.util.Map;

import javax.swing.JFrame;
import javax.swing.JPanel;

import simulator.control.Controller;
import simulator.model.BodiesGroup;
import simulator.model.Body;
import simulator.model.SimulatorObserver;

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
		
		// TODO poner contentPane como mainPanel con scrollbars (JScrollPane)
		// TODO crear el viewer y añadirlo a mainPanel (en el centro)
		// TODO en el método windowClosing, eliminar ‘this’ de los observadores
		
		addWindowListener(new WindowListener() { /*rellenar*/ });
		pack();
			if (parent != null)
				setLocation(
						parent.getLocation().x + parent.getWidth()/2 - getWidth()/2,
						parent.getLocation().y + parent.getHeight()/2 - getHeight()/2);
		setVisible(true);
	}

	@Override
	public void onAdvance(Map<String, BodiesGroup> groups, double time) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onReset(Map<String, BodiesGroup> groups, double time, double dt) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onRegister(Map<String, BodiesGroup> groups, double time, double dt) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onGroupAdded(Map<String, BodiesGroup> groups, BodiesGroup g) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onBodyAdded(Map<String, BodiesGroup> groups, Body b) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onDeltaTimeChanged(double dt) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onForceLawsChanged(BodiesGroup g) {
		// TODO Auto-generated method stub

	}

}
