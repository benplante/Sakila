package com.blockbuster.sakila;

import javax.swing.JFrame;
import javax.swing.JTabbedPane;

import com.blockbuster.sakila.controllers.ActorController;
import com.blockbuster.sakila.database.DatabaseImpl;
import com.blockbuster.sakila.ui.ActorForm;

public class SuperController extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public SuperController() {
		super("Sakila Store Management");
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setLocationRelativeTo(null);
		
		JTabbedPane container = new JTabbedPane();
		ActorController actorController = new ActorController(DatabaseImpl.getInstance());
		container.addTab("Actor Maintenance", actorController.getPanel());
		
		this.add(container);
		this.pack();
		this.setResizable(false);
		
		this.setVisible(true);
	}

}
