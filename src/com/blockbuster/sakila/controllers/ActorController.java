package com.blockbuster.sakila.controllers;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

import com.blockbuster.sakila.database.SakilaDatabase;
import com.blockbuster.sakila.ui.ActorForm;
import com.blockbuster.sakila.ui.ActorListView;
import com.blockbuster.sakila.ui.utils.TableViewModel;
import com.blockbuster.sakila.viewmodels.ActorViewModel;

/**
 * @author Saja Alhadeethi, Colin Manliclic, Dahye Min, Ben Plante
 *
 *         Actor controller. Interacts with ActorForm, ActorListView and SakilaDatabase.
 */


public class ActorController {
	private SakilaDatabase db;
	private ActorListView actorListViewPanel;
	private ActorForm actorFormFrame;

	private TableViewModel<ActorViewModel> model;

	public ActorController(SakilaDatabase db) {
		this.db = db;
	
		actorListViewPanel = new ActorListView(this);
		actorFormFrame = new ActorForm(this);
		
		model = new TableViewModel<ActorViewModel>(getActorsFromDB(), ActorViewModel.class);
		actorListViewPanel.setActorList(model);
	}
	
	public JPanel getPanel() {
		return actorListViewPanel;
	}
	
	public void refreshDB() {

	}
	
	private List<ActorViewModel> getActorsFromDB() {
		try {
			return db.selectActors();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(actorListViewPanel, 
					"Error loading customers: " + e.getMessage(), 
					"Error loading customers", JOptionPane.ERROR_MESSAGE);
			return new ArrayList<>();
		}
	}

	public void openAddActorForm() {
		actorListViewPanel.setEnabled(false);
		actorFormFrame.setTitle("Add Actor");
		actorFormFrame.setActor(null);
		actorFormFrame.setVisible(true);
	}

	public void openUpdateActorForm() {
		ActorViewModel vm = actorListViewPanel.getSelectedActor();
		if (vm == null) {
			return;
		}
		actorListViewPanel.setEnabled(false);
		actorFormFrame.setTitle("Update Actor");
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
		String type = "";
		try {
			if (vm == null) {
				return;
			} else if (vm.actorId == -1) {
				type = "Add";
				db.insertActor(vm);
			} else {
				type = "Update";
				db.updateActor(vm);
			}
			JOptionPane.showMessageDialog(actorFormFrame, type + " actor succeeded!");
			actorFormFrame.setVisible(false);
			model.setData(getActorsFromDB());
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(actorFormFrame,  "Error: " + e.getMessage(), type + " failed!", JOptionPane.ERROR_MESSAGE);
		}
	}

	public void deleteActor() {
		ActorViewModel vm = actorListViewPanel.getSelectedActor();
		if (vm == null) {
			return;
		}
		try {
			db.deleteActor(vm);
			JOptionPane.showMessageDialog(actorFormFrame, "Delete actor succeeded!");
			model.setData(getActorsFromDB());
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(actorFormFrame,  "Error: " + e.getMessage(), "Delete failed!", JOptionPane.ERROR_MESSAGE);
		}
	}
}
