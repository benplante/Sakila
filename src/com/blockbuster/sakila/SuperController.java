package com.blockbuster.sakila;

import javax.swing.JFrame;
import javax.swing.JTabbedPane;

import com.blockbuster.sakila.controllers.ActorController;
import com.blockbuster.sakila.database.MySqlSakilaDatabase;

public class SuperController extends JFrame {

	/**
	 * @author Ben Plante
	 * 
	 * Main controller for all UI elements.
	 * Holds all functionality in a tabbed panel
	 */
	private static final long serialVersionUID = 1L;
	
	public SuperController() {
		super("Sakila Store Management");
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
		
		JTabbedPane container = new JTabbedPane();
		ActorController actorController = new ActorController(MySqlSakilaDatabase.getInstance());
		container.addTab("Actor Maintenance", actorController.getPanel());
		
		this.add(container);
		this.pack();
		this.setResizable(false);
		
		this.setVisible(true);
	}

}
