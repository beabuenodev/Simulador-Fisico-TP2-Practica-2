package simulator.view;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.swing.*;

import simulator.control.Controller;
import simulator.model.BodiesGroup;
import simulator.model.Body;
import simulator.model.SimulatorObserver;

public class ControlPanel extends JPanel implements SimulatorObserver {
	
	private Controller ctrl;
	private boolean stopped = true;
	
	private JToolBar toolaBar;
	private JFileChooser fc;
	private ForceLawsDialog fld;
	private List<ViewerWindow> vw;
	
	private JButton quitButton;
	private JButton fchooserButton;
	private JButton forcelawsButton;
	private JButton viewerButton;
	private JButton runButton;
	private JButton stopButton;
	
	
	ControlPanel(Controller ctrl) {
		this.ctrl = ctrl;
		initGUI();
		this.ctrl.addObserver(this);
		vw = new ArrayList<ViewerWindow>();
	}
	
	private void initGUI() {
		setLayout(new BorderLayout());
		toolaBar = new JToolBar();
		add(toolaBar, BorderLayout.PAGE_START);
		
		//SELECTOR FICHEROS BUTTON
		fc = new JFileChooser();
		fchooserButton = new JButton();
		fchooserButton.setToolTipText("Choose a File");
		fchooserButton.setIcon(new ImageIcon("resources/icons/open.png"));
		fchooserButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) { loadFile(); }
		});
		toolaBar.add(fchooserButton);
		
		//ForceLawsButton
		toolaBar.addSeparator();
		forcelawsButton = new JButton();
		forcelawsButton.setToolTipText("Open Force Laws");
		forcelawsButton.setIcon(new ImageIcon("resources/icons/physics.png"));
		forcelawsButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) { showForceLaws(); }
		});
		toolaBar.add(forcelawsButton);
		
		//ViewerWindowButton
		viewerButton = new JButton();
		viewerButton.setToolTipText("Open Viewer Window");
		viewerButton.setIcon(new ImageIcon("resources/icons/viewer.png"));
		viewerButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) { }
		});
		toolaBar.add(viewerButton);
		
		//RunButton
		toolaBar.addSeparator();
		runButton = new JButton();
		runButton.setToolTipText("Run Simulation");
		runButton.setIcon(new ImageIcon("resources/icons/run.png"));
		
		toolaBar.add(runButton);
		
		//StopButton
		stopButton = new JButton();
		stopButton.setToolTipText("Stop Simulation");
		stopButton.setIcon(new ImageIcon("resources/icons/stop.png"));
		
		toolaBar.add(stopButton);
		
		//Steps JSpinner
		
		//DeltaTime JTextField

		//QUIT BUTTON
		toolaBar.add(Box.createGlue()); // aligns button to right
		toolaBar.addSeparator();
		quitButton = new JButton();
		quitButton.setToolTipText("Quit");
		quitButton.setIcon(new ImageIcon("resources/icons/exit.png"));
		quitButton.addActionListener((e) -> Utils.quit(this));
		toolaBar.add(quitButton);
		
		
	}
	
	private void run_sim(int n) {
		if (n > 0 && !stopped) {
			
			try {
				ctrl.run(1);
			} catch (Exception e) {
				// TODO llamar a Utils.showErrorMsg con el mensaje de error que
				// corresponda
				// TODO activar todos los botones
				stopped = true;
				return;
			}
			
			SwingUtilities.invokeLater(() -> run_sim(n - 1));
		} else {
		// TODO activar todos los botones
		stopped = true;
		}
	}
	
	private void loadFile() {
		Component window = Utils.getWindow(this);
		int res = fc.showOpenDialog(window);
		if (res != JFileChooser.CANCEL_OPTION && res != JFileChooser.ERROR_OPTION) {
			File file = fc.getSelectedFile();
			ctrl.reset();
			try {
				ctrl.loadData(new FileInputStream(file));
			} catch (FileNotFoundException e1) {
				e1.printStackTrace();
			}
		}
	}
	
	private void showForceLaws() {
		if (fld == null)
			fld = new ForceLawsDialog(Utils.getWindow(this), ctrl);
		fld.open();
	}
	
	private void showViewerWindow() {
		//vw.add(new ViewerWindow(ctrl))
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
