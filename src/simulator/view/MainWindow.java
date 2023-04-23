package simulator.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import simulator.control.Controller;

import javax.swing.*;

@SuppressWarnings("serial")
public class MainWindow extends JFrame {
	
	private Controller ctrl;
	
	public MainWindow(Controller ctrl) {
		super("Physics Simulator");
		this.ctrl = ctrl;
		initGUI();
	}
	
	public void initGUI() {
		JPanel mainPanel = new JPanel(new BorderLayout());
		setContentPane(mainPanel);
		
		// crear ControlPanel y añadirlo en PAGE_START de mainPanel
		ControlPanel controlPanel = new ControlPanel(ctrl);
		mainPanel.add(controlPanel, BorderLayout.PAGE_START);

		// crear StatusBar y añadirlo en PAGE_END de mainPanel
		StatusBar statusBar = new StatusBar(ctrl);
		mainPanel.add(statusBar, BorderLayout.PAGE_END);
		
		// Definición del panel de tablas (usa un BoxLayout vertical)
		JPanel contentPanel = new JPanel();
		contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
		mainPanel.add(contentPanel, BorderLayout.CENTER);
		
		// crear la tabla de grupos y añadirla a contentPanel.
		// Usa setPreferredSize(new Dimension(500, 250)) para fijar su tamaño
		InfoTable groupsTable = new InfoTable("Groups", new GroupsTableModel(ctrl));
		groupsTable.setPreferredSize(new Dimension(500,250));
		contentPanel.add(groupsTable);
		
		// crear la tabla de cuerpos y añadirla a contentPanel.
		// Usa setPreferredSize(new Dimension(500, 250)) para fijar su tamaño
		InfoTable bodiesTable = new InfoTable("Bodies", new BodiesTableModel(ctrl));
		bodiesTable.setPreferredSize(new Dimension(500,250));
		contentPanel.add(bodiesTable);
		
		// llama a Utils.quit(MainWindow.this) en el método windowClosing
		addWindowListener(new WindowListener() {

			@Override
			public void windowOpened(WindowEvent e) { }

			@Override
			public void windowClosing(WindowEvent e) {
				Utils.quit(MainWindow.this);
			}

			@Override
			public void windowClosed(WindowEvent e) { }

			@Override
			public void windowIconified(WindowEvent e) { }

			@Override
			public void windowDeiconified(WindowEvent e) { }

			@Override
			public void windowActivated(WindowEvent e) { }

			@Override
			public void windowDeactivated(WindowEvent e) { }
			
		});

		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		pack();
		setVisible(true);
	}
}
