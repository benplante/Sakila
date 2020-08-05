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
import com.blockbuster.sakila.viewmodels.FilmViewModel;
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

	public JPanel getPanel() {
		return rentalListViewPanel;
	}

	public void openAddRentalForm()
	{
		rentalListViewPanel.setEnabled(false);
		rentalFormFrame.setName("Add Rental");
		rentalFormFrame.setRental(null);
		rentalFormFrame.setVisible(true);
	}
	
}
