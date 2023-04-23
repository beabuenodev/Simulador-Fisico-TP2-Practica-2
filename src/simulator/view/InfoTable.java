package simulator.view;

import java.awt.BorderLayout;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.TableModel;

@SuppressWarnings("serial")
public class InfoTable extends JPanel {
	
	String title;
	TableModel tableModel;
	
	InfoTable(String title, TableModel tableModel) {
		this.title = title;
		this.tableModel = tableModel;
		initGUI();
	}
	
	private void initGUI() {
		//cambiar el layout del panel a BorderLayout()
		this.setLayout(new BorderLayout());
		
		//añadir un borde con título al JPanel, con el texto _title
		this.setBorder(new TitledBorder(title));
		
		// TODO añadir un JTable (con barra de desplazamiento vertical) que use
		// _tableModel

		JTable table = new JTable(tableModel);
		JScrollPane tablepane = new JScrollPane(table);
		this.add(tablepane);
	}
}
