package simulator.view;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.SwingUtilities;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import simulator.control.Controller;
import simulator.model.BodiesGroup;
import simulator.model.Body;
import simulator.model.SimulatorObserver;

@SuppressWarnings("serial")
public class ControlPanel extends JPanel implements SimulatorObserver {

	private Controller ctrl;
	private boolean stopped = true;
	private int steps = 10000;

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

	private JSpinner stepsSpinner;
	private JTextField deltaTimeField;

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

		// SELECTOR FICHEROS BUTTON
		fc = new JFileChooser("resources/examples/input");
		fchooserButton = new JButton();
		fchooserButton.setToolTipText("Choose a File");
		fchooserButton.setIcon(new ImageIcon("resources/icons/open.png"));
		fchooserButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				loadFile();
			}
		});
		toolaBar.add(fchooserButton);

		// ForceLawsButton
		toolaBar.addSeparator();
		forcelawsButton = new JButton();
		forcelawsButton.setToolTipText("Open Force Laws");
		forcelawsButton.setIcon(new ImageIcon("resources/icons/physics.png"));
		forcelawsButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				showForceLaws();
			}
		});
		toolaBar.add(forcelawsButton);

		// ViewerWindowButton
		viewerButton = new JButton();
		viewerButton.setToolTipText("Open Viewer Window");
		viewerButton.setIcon(new ImageIcon("resources/icons/viewer.png"));
		viewerButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				showViewerWindow();
			}
		});
		toolaBar.add(viewerButton);

		// RunButton
		toolaBar.addSeparator();
		runButton = new JButton();
		runButton.setToolTipText("Run Simulation");
		runButton.setIcon(new ImageIcon("resources/icons/run.png"));
		runButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				run(steps);
			}
		});
		toolaBar.add(runButton);

		// StopButton
		stopButton = new JButton();
		stopButton.setToolTipText("Stop Simulation");
		stopButton.setIcon(new ImageIcon("resources/icons/stop.png"));
		stopButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				stopped = true;
			}
		});
		stopButton.setEnabled(false);
		toolaBar.add(stopButton);

		// Steps JSpinner
		toolaBar.addSeparator();
		stepsSpinner = new JSpinner();
		stepsSpinner.setValue(10000);
		stepsSpinner.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				steps = Integer.parseInt(stepsSpinner.getValue().toString());
			}
		});

		toolaBar.add(new JLabel("Steps:"));
		toolaBar.add(stepsSpinner);

		// DeltaTime JTextField
		toolaBar.addSeparator();
		deltaTimeField = new JTextField();
		deltaTimeField.setText("2500.0");
		deltaTimeField.setEditable(true);
		toolaBar.add(new JLabel("DeltaTime:"));
		toolaBar.add(deltaTimeField);

		// QUIT BUTTON
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
				Utils.showErrorMsg("Simulation encountered a problem...");
				enableButtons();
				stopped = true;
				return;
			}

			SwingUtilities.invokeLater(() -> run_sim(n - 1));
		} else {
			enableButtons();
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
		vw.add(new ViewerWindow(new JFrame("Viewer Window"), ctrl));
	}

	private void run(int steps) {
		try {
			fchooserButton.setEnabled(false);
			forcelawsButton.setEnabled(false);
			viewerButton.setEnabled(false);
			stopButton.setEnabled(true);
			ctrl.setDeltaTime(Double.parseDouble(deltaTimeField.getText().toString()));
			stopped = false;
			run_sim(steps);
		} catch (NumberFormatException e) {
			Utils.showErrorMsg("You must add delta time...");
			stopped = true;
			enableButtons();
		}
	}

	private void enableButtons() {
		fchooserButton.setEnabled(true);
		forcelawsButton.setEnabled(true);
		viewerButton.setEnabled(true);
		stopButton.setEnabled(false);
	}

	@Override
	public void onAdvance(Map<String, BodiesGroup> groups, double time) {
	}

	@Override
	public void onReset(Map<String, BodiesGroup> groups, double time, double dt) {
	}

	@Override
	public void onRegister(Map<String, BodiesGroup> groups, double time, double dt) {
	deltaTimeField.setText(""+dt);
	}

	@Override
	public void onGroupAdded(Map<String, BodiesGroup> groups, BodiesGroup g) {
	}

	@Override
	public void onBodyAdded(Map<String, BodiesGroup> groups, Body b) {
	}

	@Override
	public void onDeltaTimeChanged(double dt) {
	}

	@Override
	public void onForceLawsChanged(BodiesGroup g) {
	}

}
