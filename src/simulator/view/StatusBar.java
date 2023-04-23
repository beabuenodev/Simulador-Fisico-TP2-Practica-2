package simulator.view;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.Map;

import javax.swing.*;

import simulator.control.Controller;
import simulator.model.BodiesGroup;
import simulator.model.Body;
import simulator.model.SimulatorObserver;

@SuppressWarnings("serial")
public class StatusBar extends JPanel implements SimulatorObserver {

	private JLabel timeLabel;
	private JLabel groupLabel;
	
	
	StatusBar(Controller ctrl) {
		initGUI();
		ctrl.addObserver(this);
	}
	
	private void initGUI() {
		this.setLayout(new FlowLayout(FlowLayout.LEFT));
		this.setBorder(BorderFactory.createBevelBorder(1));
		
		// timeLabel
		timeLabel = new JLabel();
		this.add(new JLabel("time: "));
		this.add(timeLabel);
		JSeparator s1 = new JSeparator(JSeparator.VERTICAL);
		s1.setPreferredSize(new Dimension(10, 20));
		this.add(s1);
		
		// groupLabel
		groupLabel = new JLabel();
		this.add(new JLabel("groups: "));
		this.add(groupLabel);
		JSeparator s2 = new JSeparator(JSeparator.VERTICAL);
		s2.setPreferredSize(new Dimension(10, 20));
		this.add(s2);
		
	}
	
	@Override
	public void onAdvance(Map<String, BodiesGroup> groups, double time) {
		timeLabel.setText(Double.toString(time));
	}

	@Override
	public void onReset(Map<String, BodiesGroup> groups, double time, double dt) {
		timeLabel.setText("0");
	}

	@Override
	public void onRegister(Map<String, BodiesGroup> groups, double time, double dt) {}

	@Override
	public void onGroupAdded(Map<String, BodiesGroup> groups, BodiesGroup g) {
		groupLabel.setText(Integer.toString(groups.size() + 1));
	}

	@Override
	public void onBodyAdded(Map<String, BodiesGroup> groups, Body b) {}

	@Override
	public void onDeltaTimeChanged(double dt) {}

	@Override
	public void onForceLawsChanged(BodiesGroup g) {}

}
