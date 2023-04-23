package simulator.view;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import org.json.JSONArray;
import org.json.JSONObject;

import simulator.control.Controller;
import simulator.model.BodiesGroup;
import simulator.model.Body;
import simulator.model.SimulatorObserver;

@SuppressWarnings("serial")
public class ForceLawsDialog extends JDialog implements SimulatorObserver {

	private DefaultComboBoxModel<String> lawsModel;
	private DefaultComboBoxModel<String> groupsModel;
	private DefaultTableModel dataTableModel;
	private Controller ctrl;
	private List<JSONObject> forceLawsInfo;
	private String[] _headers = { "Key", "Value", "Description" };
	private int selectedflind;
	private int status;

	private JComboBox<String> lawsModelBox;
	private JComboBox<String> groupsModelBox;
	private JButton okButton;
	private JButton cancelButton;
	private JTable dataTable;

	ForceLawsDialog(Frame parent, Controller ctrl) {
		super(parent, true);
		this.ctrl = ctrl;
		initGUI();
		ctrl.addObserver(this);
	}

	private void initGUI() {
		setTitle("Force Laws Selection");
		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
		setContentPane(mainPanel);
		forceLawsInfo = ctrl.getForceLawsInfo();
		dataTableModel = new DefaultTableModel() {
			@Override
			public boolean isCellEditable(int row, int column) {
				return column == 1;
			}
		};
		dataTableModel.setColumnIdentifiers(_headers);
		dataTable = new JTable(dataTableModel);
		JScrollPane scrollPane = new JScrollPane(dataTable);
		mainPanel.add(scrollPane);

		lawsModel = new DefaultComboBoxModel<>();
		for (JSONObject f : forceLawsInfo) {
			lawsModel.addElement(f.getString("desc"));
		}
		lawsModelBox = new JComboBox<String>(lawsModel);
		lawsModelBox.setAlignmentX(Component.CENTER_ALIGNMENT);
		lawsModelBox.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				lawsModelBoxAction();
			}
		});

		groupsModel = new DefaultComboBoxModel<>();
		groupsModelBox = new JComboBox<String>(groupsModel);
		groupsModelBox.setAlignmentX(Component.CENTER_ALIGNMENT);

		Box boxComboBox = Box.createHorizontalBox();
		boxComboBox.add(new JLabel("Force Law: "));
		boxComboBox.add(lawsModelBox);
		boxComboBox.add(new JLabel("Group: "));
		boxComboBox.add(groupsModelBox);
		mainPanel.add(boxComboBox);

		JSeparator s = new JSeparator(JSeparator.HORIZONTAL);
		s.setPreferredSize(new Dimension(20, 20));
		mainPanel.add(s);

		okButton = new JButton("OK");
		okButton.setAlignmentX(CENTER_ALIGNMENT);
		okButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				okAction();
			}
		});
		mainPanel.add(okButton);

		cancelButton = new JButton("Cancel");
		cancelButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				cancelAction();
			}

		});
		cancelButton.setAlignmentX(CENTER_ALIGNMENT);
		mainPanel.add(cancelButton);

		Box boxButton = Box.createHorizontalBox();
		boxButton.add(okButton);
		boxButton.add(cancelButton);
		mainPanel.add(boxButton);

		setPreferredSize(new Dimension(700, 400));

		pack();

		setResizable(false);
		setVisible(false);

	}

	public int open() {
		if (groupsModel.getSize() == 0)
			return status;

		this.setLocationRelativeTo(getParent());
		pack();
		setVisible(true);
		return status;
	}

	private void lawsModelBoxAction() {
		int opt = lawsModelBox.getSelectedIndex();

		if (dataTableModel.getRowCount() > 0) {
			int rowcount = dataTableModel.getRowCount();
			for (int i = 0; i < rowcount; i++)
				dataTableModel.removeRow(0);
		}

		JSONObject forceLawsData = forceLawsInfo.get(opt).getJSONObject("data");
		for (String s : forceLawsData.keySet()) {
			Vector<String> row = new Vector<String>();
			row.add(s);
			row.add("");
			row.add(forceLawsData.getString(s));
			dataTableModel.addRow(row);
		}

		selectedflind = opt;
		dataTableModel.fireTableStructureChanged();
	}

	private void okAction() {
		JSONObject forcelawdata = new JSONObject();
		JSONObject data = new JSONObject();

		try {
			for (int i = 0; i < dataTableModel.getRowCount(); i++) {
				String aux = dataTableModel.getValueAt(i, 1).toString();
				System.out.println(aux);
				System.out.println(dataTableModel.getRowCount());
				if (aux.startsWith("[")) { // if field is a vector
					JSONArray vector = new JSONArray();
					String newaux = aux.replace("[", "");
					newaux = newaux.replace("]", "");
					String vectoraux[] = newaux.split(",");

					vector.put(Double.parseDouble(vectoraux[0]));
					vector.put(Double.parseDouble(vectoraux[1]));
					data.put(dataTableModel.getValueAt(i, 0).toString(), vector);
				} else if (aux.contains(".")) { // if field is a double
					data.put(dataTableModel.getValueAt(i, 0).toString(), Double.parseDouble(aux));
					System.out.println(data.toString());
				} else if (aux.isBlank()) { // if value is blank
					data.put("", "");
				}
			}
			forcelawdata.put("data", data);
			forcelawdata.put("type", forceLawsInfo.get(selectedflind).getString("type"));
			ctrl.setForceLaws(groupsModelBox.getSelectedItem().toString(), forcelawdata);
			status = 1;
			setVisible(false);
		} catch (Exception e) {
			Utils.showErrorMsg("ForceLaws aplication has failed!");
			e.printStackTrace();
		}
	}

	private void cancelAction() {
		status = 0;
		setVisible(false);
	}

	@Override
	public void onAdvance(Map<String, BodiesGroup> groups, double time) {
	}

	@Override
	public void onReset(Map<String, BodiesGroup> groups, double time, double dt) {
		groupsModel.removeAllElements();
	}

	@Override
	public void onRegister(Map<String, BodiesGroup> groups, double time, double dt) {
		for (String i : groups.keySet()) {
			groupsModel.addElement(groups.get(i).getId());
		}
		selectedflind = 0;
	}

	@Override
	public void onGroupAdded(Map<String, BodiesGroup> groups, BodiesGroup g) {
		groupsModel.addElement(g.getId());
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
