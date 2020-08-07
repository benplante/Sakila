package com.blockbuster.sakila.controllers;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

import com.blockbuster.sakila.database.SakilaDatabase;
import com.blockbuster.sakila.ui.RentalForm;
import com.blockbuster.sakila.ui.RentalListView;
import com.blockbuster.sakila.ui.utils.TableViewModel;
import com.blockbuster.sakila.viewmodels.CustomerViewModel;
import com.blockbuster.sakila.viewmodels.InventoryViewModel;
import com.blockbuster.sakila.viewmodels.RentalViewModel;

/**
 * @author Saja Alhadeethi, Colin Manliclic, Dahye Min, Ben Plante
 *
 *         Rental controller. Interacts with RentalForm, RentalListView and SakilaDatabase.
 */

public class RentalController
{
	// RentalController members
	private SakilaDatabase db;
	private RentalListView rentalListViewPanel;
	private RentalForm rentalFormFrame;
	private TableViewModel<RentalViewModel> model;

	/**
	 * Method Name: RentalController
	 * Purpose: RentalController is instantiated in SuperController with a Singleton instance of MySqlSakilaDatabase.
	 * 					RentalListView and RentalForm is instantiated and passed this controller for listener events.
	 * 					TableViewModel is instantiated with a list of RentalViewModels and populated via MySqlSakilaDatabase. 
	 * 					TableViewModel is then used to pass to the RentalListView to generate the list in its tab.
	 * Accepts: A SakilaDatabase object.
	 * Returns: A RentalController object.
	 */
	public RentalController(SakilaDatabase db) {
		this.db = db;
		rentalListViewPanel = new RentalListView(this);
		rentalFormFrame = new RentalForm(this);
		model = new TableViewModel<>(getRentalsFromDB(), RentalViewModel.class);
		rentalListViewPanel.setRentalList(model);
	}

	/**
	 *  Method Name: refreshDB
	 *  Purpose: Refreshes RentalForm and RentalListView data on every tab change.
	 *  				 Is essential if data from other tabs is deleted.
	 *  Accepts: Nothing.
	 *  Returns: Nothing.
	 */
	public void refreshDB() {
		rentalFormFrame.setInventories(getInventoriesFromDB());
		rentalFormFrame.setCustomers(getCustomersFromDB());
		model.setData(getRentalsFromDB());
	}
	
	/** 
	 * Method Name: getPanel
	 * Purpose: Gets RentalController's RentalListView.
	 * 					Is used when adding a tab to the SuperController.
	 * Accepts: Nothing.
	 * Return: A RentalListView object.
	 */
	public JPanel getPanel() {
		return rentalListViewPanel;
	}
	

	/** 
	 * Method Name: getRentalsFromDB
	 * Purpose: Gets the rentals from the database.
	 * 					Used to display list in RentalListView.
	 * Accepts: Nothing.
	 * Return: A list of CategoryViewModel objects.
	 */
	private List<RentalViewModel> getRentalsFromDB() {
		try {
			return db.selectRentals();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(rentalListViewPanel, 
					"Error loading rentals: " + e.getMessage(), 
					"Error loading rentals", JOptionPane.ERROR_MESSAGE);
			return new ArrayList<>();
		}
	}

	/** 
	 * Method Name: getInventoriesFromDB
	 * Purpose: Gets the Inventories from the database.
	 * 					Used for RentalForm's JComboBox cmbInventories.
	 * Accepts: Nothing.
	 * Return: A list of InventoryViewModel objects.
	 */
	private List<InventoryViewModel> getInventoriesFromDB() {
		try {
			return db.selectInventories();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(rentalListViewPanel, 
					"Error loading Rentals: " + e.getMessage(), 
					"Error loading Rentals", JOptionPane.ERROR_MESSAGE);
			return new ArrayList<>();
		}
	}

	/** 
	 * Method Name: getCustomersFromDB
	 * Purpose: Gets the actors (from store 1) from the database.
	 * 					Used for RentalForm's JCombo cmbCustomers.
	 * Accepts: Nothing.
	 * Return: A list of ActorViewModel objects.
	 */
	private List<CustomerViewModel> getCustomersFromDB() {
		try {
			List<CustomerViewModel> customersFromStore1 = db.selectCustomers();
			customersFromStore1.removeIf(c -> c.getStoreId() > 1);
			return customersFromStore1;
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(rentalListViewPanel, 
					"Error loading customers: " + e.getMessage(), 
					"Error loading customers", JOptionPane.ERROR_MESSAGE);
			return new ArrayList<>();
		}
	}

	/** 
	 * Method Name: openAddRentalForm
	 * Purpose: The actions to be performed when listening for 'Add' button in RentalListView.
	 * Accepts: Nothing.
	 * Return: Nothing.
	 */
	public void openAddRentalForm() {
		rentalListViewPanel.setEnabled(false);
		rentalFormFrame.setTitle("Add Rental");
		rentalFormFrame.setRental();
		rentalFormFrame.setVisible(true);
	}
	
	/** 
	 * Method Name: deleteRental
	 * Purpose: The actions to be performed when listening for 'Delete' button in RentalListView.
	 * Accepts: Nothing.
	 * Return: Nothing.
	 */
	public void deleteRental() {
		RentalViewModel vm = rentalListViewPanel.getSelectedRental();
		if (vm == null) {
			return;
		}
		try {
			db.deleteRental(vm);
			JOptionPane.showMessageDialog(rentalFormFrame, "Delete rental succeeded!");
			model.setData(getRentalsFromDB());
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(rentalFormFrame,  "Error: " + e.getMessage(), "Delete failed!", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	/** 
	 * Method Name: confirmAddRental
	 * Purpose: The actions to be performed when listening for 'Confirm' button in RentalForm.
	 * Accepts: Nothing.
	 * Return: Nothing.
	 */
	public void confirmAddRental() {
		rentalListViewPanel.setEnabled(true);
		RentalViewModel vm = rentalFormFrame.getRental();
		if (vm == null) {
			return;
		}
		try {
			db.insertRental(vm);
			JOptionPane.showMessageDialog(rentalFormFrame, "Add Rental succeeded!");
			rentalFormFrame.setVisible(false);
			model.setData(getRentalsFromDB());
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(rentalFormFrame,  "Error: " + e.getMessage(), "Add failed!", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	/** 
	 * Method Name: closeRentalForm
	 * Purpose: The actions to be performed when listening for 'Close' button in RentalForm.
	 * Accepts: Nothing.
	 * Return: Nothing.
	 */
	public void closeRentalForm() {
		rentalListViewPanel.setEnabled(true);
		rentalFormFrame.setVisible(false);
	}
}
