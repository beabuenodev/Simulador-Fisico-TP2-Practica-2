package simulator.view;

import java.awt.BorderLayout;
import java.util.Map;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.JToolBar;

import simulator.control.Controller;
import simulator.model.BodiesGroup;
import simulator.model.Body;
import simulator.model.SimulatorObserver;

public class ControlPanel extends JPanel implements SimulatorObserver {
	
	private Controller ctrl;
	private JToolBar toolaBar;
	private JFileChooser fc;
	private boolean stopped = true;
	private JButton quitButton;
	private JButton fcButton;
	
	ControlPanel(Controller ctrl) {
		this.ctrl = ctrl;
		initGUI();
		this.ctrl.addObserver(this);
	}
	
	private void initGUI() {
		setLayout(new BorderLayout());
		toolaBar = new JToolBar();
		add(toolaBar, BorderLayout.PAGE_START);
		
		// TODO crear los diferentes botones/atributos y añadirlos a _toolaBar.
		// Todos ellos han de tener su correspondiente tooltip. Puedes utilizar
		// _toolaBar.addSeparator() para añadir la línea de separación vertical
		// entre las componentes que lo necesiten

		//QUIT BUTTON
		toolaBar.add(Box.createGlue()); // aligns button to right
		toolaBar.addSeparator();
		quitButton = new JButton();
		quitButton.setToolTipText("Quit");
		quitButton.setIcon(new ImageIcon("resources/icons/exit.png"));
		quitButton.addActionListener((e) -> Utils.quit(this));
		toolaBar.add(quitButton);
		
		//SELECTOR FICHEROS BUTTON
		fc = new JFileChooser();
		fcButton = new JButton();
		fcButton.setToolTipText("Choose a File");
		fcButton.setIcon(new ImageIcon("resources/icons/open.png"));
		//funcion fcButton
		
		//TODO ForceLawsButton
		
		//TODO ViewerWindowButton
		
		//TODO RunButton/StopButton
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
