package com.blockbuster.sakila.controllers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;

import com.blockbuster.sakila.database.Database;
import com.blockbuster.sakila.ui.ActorForm;
import com.blockbuster.sakila.viewmodels.ActorViewModel;

public class ActorController implements ActionListener {
	private Database db;
	private ActorForm actorForm;
	
	public ActorController(Database db) {
		this.db = db;
		actorForm = new ActorForm(this);
	}
	
	public JPanel getPanel() {
		return actorForm;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		switch (e.getActionCommand()) {
		case ActorForm.BTN_CONFIRM:
			ActorViewModel vm = actorForm.getActor();
			db.insertActor(vm);
			break;
		}
	}

}
