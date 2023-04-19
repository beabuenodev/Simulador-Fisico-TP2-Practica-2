package simulator.view;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.swing.table.AbstractTableModel;

import simulator.control.Controller;
import simulator.model.BodiesGroup;
import simulator.model.Body;
import simulator.model.SimulatorObserver;

public class GroupsTableModel extends AbstractTableModel implements SimulatorObserver {
	
	String[] header = { "Id", "Force Laws", "Bodies" };
	List<BodiesGroup> tabgroups;
	
	GroupsTableModel(Controller ctrl) {
		tabgroups = new ArrayList<>();
		ctrl.addObserver(this);
	}

	@Override
	public int getRowCount() {
		return tabgroups.size();
	}

	@Override
	public int getColumnCount() {
		return header.length;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		switch (columnIndex) {
		case 0:
			return tabgroups.get(rowIndex).getId();
		case 1:
			return tabgroups.get(rowIndex).getForceLawsInfo();
		case 2:
			String bodies = "";
			for (Body b: tabgroups.get(rowIndex)) {
				bodies = bodies + b.getId() + " ";
			}
			return bodies;
		default:
			return null;
		}
	}
	
	@Override
	public String getColumnName(int column) {
		return header[column];
	}

	@Override
	public void onAdvance(Map<String, BodiesGroup> groups, double time) {}

	@Override
	public void onReset(Map<String, BodiesGroup> groups, double time, double dt) {
		tabgroups.clear();
		fireTableStructureChanged();
	}

	@Override
	public void onRegister(Map<String, BodiesGroup> groups, double time, double dt) {
		for(BodiesGroup g: groups.values()) {
			tabgroups.add(g);
		}
		fireTableStructureChanged();
		
	}

	@Override
	public void onGroupAdded(Map<String, BodiesGroup> groups, BodiesGroup g) {
		tabgroups.add(g);
		fireTableStructureChanged();
	}

	@Override
	public void onBodyAdded(Map<String, BodiesGroup> groups, Body b) {
		for (BodiesGroup g: tabgroups) {
			if (g.getId().equals(b.getgId())) 
				g = groups.get(b.getgId());
		}
		fireTableStructureChanged();
	}

	@Override
	public void onDeltaTimeChanged(double dt) {}

	@Override
	public void onForceLawsChanged(BodiesGroup g) {
		for (BodiesGroup gaux: tabgroups) {
			if (gaux.getId().equals(g.getId())) 
				gaux = g;
		}
		fireTableStructureChanged();
	}

}
