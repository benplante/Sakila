package com.blockbuster.sakila;

import javax.swing.JFrame;
import javax.swing.JTabbedPane;

import com.blockbuster.sakila.controllers.ActorController;
import com.blockbuster.sakila.controllers.CustomerController;
import com.blockbuster.sakila.database.MySqlSakilaDatabase;

public class SuperController extends JFrame {

	/**
	 * @author Ben Plante
	 * 
	 *         Main controller for all UI elements. Holds all functionality in a
	 *         tabbed panel
	 */
	private static final long serialVersionUID = 1L;

	public SuperController() {
		super("Sakila Store Management");
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);


		JTabbedPane container = new JTabbedPane();
		ActorController actorController = new ActorController(MySqlSakilaDatabase.getInstance());
		container.addTab("Actor Maintenance", actorController.getPanel());

		// Customer part
		CustomerController customerController = new CustomerController(MySqlSakilaDatabase.getInstance());
		container.addTab("Customer Maintenance", customerController.getPanel());

		this.add(container);

		this.pack();
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.setSize(900, 700);

		this.setVisible(true);

	}

}
