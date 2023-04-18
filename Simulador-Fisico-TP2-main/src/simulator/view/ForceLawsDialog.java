package simulator.view;

import java.awt.Dimension;
import java.awt.Frame;
import java.util.List;
import java.util.Map;

import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.table.DefaultTableModel;

import org.json.JSONObject;

import simulator.control.Controller;
import simulator.model.BodiesGroup;
import simulator.model.Body;
import simulator.model.SimulatorObserver;

public class ForceLawsDialog extends JDialog implements SimulatorObserver {
	
	private DefaultComboBoxModel<String> lawsModel;
	private DefaultComboBoxModel<String> groupsModel;
	private DefaultTableModel dataTableModel;
	private Controller ctrl;
	private List<JSONObject> forceLawsInfo;
	private String[] _headers = { "Key", "Value", "Description" };
	
	// TODO en caso de ser necesario, añadir los atributos aquí…
	
	ForceLawsDialog(Frame parent, Controller ctrl) {
		super(parent, true);
		this.ctrl = ctrl;
		initGUI();
		// TODO registrar this como observer;
	}
	
	private void initGUI() {
		
		setTitle("Force Laws Selection");
		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
		setContentPane(mainPanel);
		
		// _forceLawsInfo se usará para establecer la información en la tabla
		forceLawsInfo = ctrl.getForceLawsInfo();
		// TODO crear un JTable que use _dataTableModel, y añadirla al panel
		dataTableModel = new DefaultTableModel() {
			@Override
			public boolean isCellEditable(int row, int column) {
			// TODO hacer editable solo la columna 1
			}
		};
		
	dataTableModel.setColumnIdentifiers(_headers);
	lawsModel = new DefaultComboBoxModel<>();
	
	// TODO añadir la descripción de todas las leyes de fuerza a _lawsModel
	// TODO crear un combobox que use _lawsModel y añadirlo al panel
	
	groupsModel = new DefaultComboBoxModel<>();
	
	// TODO crear un combobox que use _groupsModel y añadirlo al panel
	// TODO crear los botones OK y Cancel y añadirlos al panel
	
	setPreferredSize(new Dimension(700, 400));
	
	pack();
	
	setResizable(false);
	setVisible(false);
	
	}
	
	public void open() {
		if (groupsModel.getSize() == 0)
		//return status;
			
		// TODO Establecer la posición de la ventana de diálogo de tal manera que se
		// abra en el centro de la ventana principal
		pack();
		setVisible(true);
		//return status;
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
