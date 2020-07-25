package com.blockbuster.sakila.controllers;

import javax.swing.JPanel;

import com.blockbuster.sakila.database.Database;
import com.blockbuster.sakila.database.TableViewModel;
import com.blockbuster.sakila.ui.ActorForm;
import com.blockbuster.sakila.ui.ActorListView;
import com.blockbuster.sakila.viewmodels.ActorViewModel;

public class ActorController {
	private Database db;
	private ActorListView actorListViewPanel;
	private ActorForm actorFormFrame;
		
	private TableViewModel<ActorViewModel> model;
	public ActorController(Database db) {
		this.db = db;
		actorListViewPanel = new ActorListView(this);
		actorFormFrame = new ActorForm(this);
	
		model = new TableViewModel<ActorViewModel>(db.selectActors(), ActorViewModel.class);
		actorListViewPanel.setActorList(model);
	}
	
	public JPanel getPanel() {
		return actorListViewPanel;
	}

	
	public void openAddActorForm() {
		actorListViewPanel.setEnabled(false);
		actorFormFrame.setName("Add Actor");
		actorFormFrame.setActor(null);
		actorFormFrame.setVisible(true);
	}
	
	public void openUpdateActorForm() {
		actorListViewPanel.setEnabled(false);
		actorFormFrame.setName("Update Actor");
		actorFormFrame.setActor(actorListViewPanel.getSelectedActor());
		actorFormFrame.setVisible(true);
	}
	
	public void closeActorForm() {
		actorListViewPanel.setEnabled(true);
		actorFormFrame.setVisible(false);
	}
	
	public void confirmAddActor() {
		actorListViewPanel.setEnabled(true);
		ActorViewModel vm = actorFormFrame.getActor();
		if (vm.actorId == -1) {
			db.insertActor(vm);
		}
		else {
			db.updateActor(vm);
		}
		model.setData(db.selectActors());
		actorFormFrame.setVisible(false);
	}
	
	public void deleteActor() {
		db.deleteActor(actorListViewPanel.getSelectedActor());
		model.setData(db.selectActors());
	}
}
