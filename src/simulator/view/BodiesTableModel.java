package simulator.view;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.swing.table.AbstractTableModel;

import simulator.control.Controller;
import simulator.model.BodiesGroup;
import simulator.model.Body;
import simulator.model.SimulatorObserver;

@SuppressWarnings("serial")
public class BodiesTableModel extends AbstractTableModel implements SimulatorObserver {

	String[] header = { "Id", "gId", "Mass", "Velocity", "Position", "Force" };
	List<Body> tabbodies;

	BodiesTableModel(Controller ctrl) {
		tabbodies = new ArrayList<>();
		ctrl.addObserver(this);
	}

	@Override
	public int getRowCount() {
		return tabbodies.size();
	}

	@Override
	public int getColumnCount() {
		return header.length;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		switch (columnIndex) {
		case 0:
			return tabbodies.get(rowIndex).getId();
		case 1:
			return tabbodies.get(rowIndex).getgId();
		case 2:
			return tabbodies.get(rowIndex).getMass();
		case 3:
			return tabbodies.get(rowIndex).getVelocity().toString();
		case 4:
			return tabbodies.get(rowIndex).getPosition().toString();
		case 5:
			return tabbodies.get(rowIndex).getForce().toString();
		default:
			return null;
		}
	}

	@Override
	public String getColumnName(int column) {
		return header[column];
	}

	@Override
	public void onAdvance(Map<String, BodiesGroup> groups, double time) {
		for (Body b1 : tabbodies) {
			BodiesGroup g = groups.get(b1.getgId());
			for (Body b2 : g) {
				if (b1.getId().equals(b2.getId()))
					b1 = b2;
			}
		}
		fireTableStructureChanged();
	}

	@Override
	public void onReset(Map<String, BodiesGroup> groups, double time, double dt) {
		tabbodies.clear();
		fireTableStructureChanged();
	}

	@Override
	public void onRegister(Map<String, BodiesGroup> groups, double time, double dt) {
		for (BodiesGroup g : groups.values()) {
			for (Body b : g) {
				tabbodies.add(b);
			}
		}
		fireTableStructureChanged();
	}

	@Override
	public void onGroupAdded(Map<String, BodiesGroup> groups, BodiesGroup g) {
		for (Body b : g) {
			tabbodies.add(b);
		}
		fireTableStructureChanged();
	}

	@Override
	public void onBodyAdded(Map<String, BodiesGroup> groups, Body b) {
		tabbodies.add(b);
		fireTableStructureChanged();
	}

	@Override
	public void onDeltaTimeChanged(double dt) {
	}

	@Override
	public void onForceLawsChanged(BodiesGroup g) {
	}

}
