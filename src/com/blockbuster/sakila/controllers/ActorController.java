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
	// ActorController members
	private SakilaDatabase db;
	private ActorListView actorListViewPanel;
	private ActorForm actorFormFrame;
	private TableViewModel<ActorViewModel> model;

	/**
	 * Method Name: ActorController
	 * Purpose: ActorController is instantiated in SuperController with a Singleton instance of MySqlSakilaDatabase.
	 * 					ActorListView and ActorForm is instantiated and passed this controller for listener events.
	 * 					TableViewModel is instantiated with a list of ActorViewModels and populated via MySqlSakilaDatabase. 
	 * 					TableViewModel is then used to pass to the ActorListView to generate the list in its tab.
	 * Accepts: A SakilaDatabase object.
	 * Returns: An ActorController object.
	 */
	public ActorController(SakilaDatabase db) {
		this.db = db;
		actorListViewPanel = new ActorListView(this);
		actorFormFrame = new ActorForm(this);
		model = new TableViewModel<ActorViewModel>(getActorsFromDB(), ActorViewModel.class);
		actorListViewPanel.setActorList(model);
	}
	
	/** 
	 * Method Name: getPanel
	 * Purpose: Gets ActorController's ActorListView.
	 * 					Is used when adding a tab to the SuperController.
	 * Accepts: Nothing.
	 * Return: An ActorListView object.
	 */
	public JPanel getPanel() {
		return actorListViewPanel;
	}
	
	/** 
	 * Method Name: getActorsFromDB
	 * Purpose: Gets the actors from the database.
	 * Accepts: Nothing.
	 * Return: A list of ActorViewModel objects.
	 */
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
	
	/** 
	 * Method Name: openAddActorForm
	 * Purpose: The actions to be performed when listening for 'Add' button in ActorListView.
	 * Accepts: Nothing.
	 * Return: Nothing.
	 */
	public void openAddActorForm() {
		actorListViewPanel.setEnabled(false);
		actorFormFrame.setTitle("Add Actor");
		actorFormFrame.setActor(null);
		actorFormFrame.setVisible(true);
	}
	
	/** 
	 * Method Name: openUpdateActorForm
	 * Purpose: The actions to be performed when listening for 'Update' button in ActorListView.
	 * Accepts: Nothing.
	 * Return: Nothing.
	 */
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
	
	/** 
	 * Method Name: deleteActor
	 * Purpose: The actions to be performed when listening for 'Delete' button in ActorListView.
	 * Accepts: Nothing.
	 * Return: Nothing.
	 */
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
		
	/** 
	 * Method Name: confirmAddActor
	 * Purpose: The actions to be performed when listening for 'Confirm' button in ActorForm.
	 * Accepts: Nothing.
	 * Return: Nothing.
	 */
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
	
	/** 
	 * Method Name: closeActorForm
	 * Purpose: The actions to be performed when listening for 'Close' button in ActorForm.
	 * Accepts: Nothing.
	 * Return: Nothing.
	 */
	public void closeActorForm() {
		actorListViewPanel.setEnabled(true);
		actorFormFrame.setVisible(false);
	}
}
