package com.blockbuster.sakila;

import javax.swing.JFrame;
import javax.swing.JTabbedPane;

import com.blockbuster.sakila.controllers.ActorController;
import com.blockbuster.sakila.controllers.CustomerController;
import com.blockbuster.sakila.controllers.FilmController;
import com.blockbuster.sakila.controllers.RentalController;
import com.blockbuster.sakila.controllers.ReportController;
import com.blockbuster.sakila.database.MySqlSakilaDatabase;

@SuppressWarnings("serial")
public class SuperController extends JFrame {

	/**
	 * @author Saja Alhadeethi, Colin Manliclic, Dahye Min, Ben Plante
	 * 
	 *         Main controller for all UI elements. Holds all functionality in a
	 *         tabbed panel
	 */
	
	public SuperController() {
		super("Sakila Store Management");
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		JTabbedPane container = new JTabbedPane();
		
		// Actor Tab
		ActorController actorController = new ActorController(MySqlSakilaDatabase.getInstance());
		container.addTab("Actor Maintenance", actorController.getPanel());

		// Customer Tab
		CustomerController customerController = new CustomerController(MySqlSakilaDatabase.getInstance());
		container.addTab("Customer Maintenance", customerController.getPanel());
		
		// Film Tab
		FilmController filmController = new FilmController(MySqlSakilaDatabase.getInstance());
		container.addTab("Film Maintenance", filmController.getPanel());
		
		// Rental Tab
		RentalController rentalController = new RentalController(MySqlSakilaDatabase.getInstance());
		container.addTab("Rental Maintenance", rentalController.getPanel());
		
		// Report Tab
		ReportController reportController = new ReportController(MySqlSakilaDatabase.getInstance());
		container.addTab("Report", reportController.getPanel());
		
		// Refreshes each view's data on every tab change
		container.addChangeListener(e -> {
			customerController.refreshDB();
			filmController.refreshDB();
			rentalController.refreshDB();
			reportController.refreshDB();
		});

		this.add(container);
		this.pack();
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.setSize(900, 700);
		this.setVisible(true);
	}
}
