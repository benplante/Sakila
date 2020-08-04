package com.blockbuster.sakila.controllers;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

import com.blockbuster.sakila.database.SakilaDatabase;
import com.blockbuster.sakila.database.TableViewModel;
import com.blockbuster.sakila.ui.RentalForm;
import com.blockbuster.sakila.ui.RentalListView;
import com.blockbuster.sakila.viewmodels.CustomerViewModel;
import com.blockbuster.sakila.viewmodels.InventoryViewModel;
import com.blockbuster.sakila.viewmodels.RentalViewModel;

public class RentalController
{

	private SakilaDatabase db;
	private RentalListView rentalListViewPanel;
	private RentalForm rentalFormFrame;

	private TableViewModel<RentalViewModel> model;

	public RentalController(SakilaDatabase db) {
		this.db = db;

		rentalListViewPanel = new RentalListView(this);
		rentalFormFrame = new RentalForm(this);
		model = new TableViewModel<>(getRentalsFromDB(), RentalViewModel.class);
		rentalListViewPanel.setRentalList(model);
		rentalFormFrame.setInventories(getInventoriesFromDB());
		rentalFormFrame.setCustomers(getCustomersFromDB());
	}
	
	public JPanel getPanel() {
		return rentalListViewPanel;
	}

	private List<InventoryViewModel> getInventoriesFromDB()
	{
		try {
			return db.selectInventories();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(rentalListViewPanel, 
					"Error loading films: " + e.getMessage(), 
					"Error loading films", JOptionPane.ERROR_MESSAGE);
			return new ArrayList<>();
		}
	}



	private List<CustomerViewModel> getCustomersFromDB()
	{
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



	public void openAddRentalForm()
	{
		rentalListViewPanel.setEnabled(false);
		rentalFormFrame.setName("Add Rental");
		rentalFormFrame.setRental(null);
		rentalFormFrame.setVisible(true);
	}

	public void openUpdateRentalForm()
	{
		rentalListViewPanel.setEnabled(false);
		rentalFormFrame.setName("Update Rental");
		RentalViewModel vm = rentalListViewPanel.getSelectedRental();
		if(vm == null) {
			return;
		}
		else {
			rentalFormFrame.setRental(vm);
			rentalFormFrame.setVisible(true);
		}

	}

	public void confirmAddRental()
	{
		rentalListViewPanel.setEnabled(true);
		RentalViewModel vm = rentalFormFrame.getRental();
		String type = "";
		try {
			if (vm.rentalId == -1) {
				type = "Add";
				db.insertRental(vm);
			} else {
				type = "Update";
				//db.updateCustomer(vm);
			}
			JOptionPane.showMessageDialog(rentalListViewPanel, type + " Rental succeeded!");
			rentalListViewPanel.setVisible(false);
			model.setData(getRentalsFromDB());
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(rentalListViewPanel,  "Error: " + e.getMessage(), type + " failed!", JOptionPane.ERROR_MESSAGE);
		}
	}

	public void closeCustomerForm()
	{
		rentalListViewPanel.setEnabled(true);
		rentalFormFrame.setVisible(false);
	}
	
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
}
