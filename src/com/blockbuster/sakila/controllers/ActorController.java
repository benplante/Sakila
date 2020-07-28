package com.blockbuster.sakila.controllers;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import com.blockbuster.sakila.database.SakilaDatabase;
import com.blockbuster.sakila.database.TableViewModel;
import com.blockbuster.sakila.ui.ActorForm;
import com.blockbuster.sakila.ui.ActorListView;
import com.blockbuster.sakila.viewmodels.ActorViewModel;

public class ActorController {
	private SakilaDatabase db;
	private ActorListView actorListViewPanel;
	private ActorForm actorFormFrame;

	private TableViewModel<ActorViewModel> model;

	public ActorController(SakilaDatabase db) {
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
		ActorViewModel vm = actorListViewPanel.getSelectedActor();
		if(vm == null)
		{
			return;
		}
		actorListViewPanel.setEnabled(false);
		actorFormFrame.setName("Update Actor");
		actorFormFrame.setActor(vm);
		actorFormFrame.setVisible(true);
	}

	public void closeActorForm() {
		actorListViewPanel.setEnabled(true);
		actorFormFrame.setVisible(false);
	}

	public void confirmAddActor() {
		actorListViewPanel.setEnabled(true);
		ActorViewModel vm = actorFormFrame.getActor();
		if(vm == null) {
			return;
		} else if (vm.actorId == -1) {
			db.insertActor(vm);
		} else {
			db.updateActor(vm);
		}
		model.setData(db.selectActors());
		actorFormFrame.setVisible(false);
	}

	public void deleteActor() {
		ActorViewModel vm = actorListViewPanel.getSelectedActor();
		if(vm == null) {
			return;
		}
		db.deleteActor(vm);
		model.setData(db.selectActors());
	}
}
