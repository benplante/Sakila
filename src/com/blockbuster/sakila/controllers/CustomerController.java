package com.blockbuster.sakila.controllers;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

import com.blockbuster.sakila.database.SakilaDatabase;
import com.blockbuster.sakila.ui.CustomerForm;
import com.blockbuster.sakila.ui.CustomerListView;
import com.blockbuster.sakila.ui.utils.TableViewModel;
import com.blockbuster.sakila.viewmodels.CityViewModel;
import com.blockbuster.sakila.viewmodels.CustomerViewModel;

/**
 * @author Saja Alhadeethi, Colin Manliclic, Dahye Min, Ben Plante
 *
 *         Customer controller. Interacts with CustomerForm, CustomerListView and SakilaDatabase.
 */


public class CustomerController {
	// CusttomerController members
	private SakilaDatabase db;
	private CustomerListView customerListViewPanel;
	private CustomerForm customerFormFrame;
	private TableViewModel<CustomerViewModel> model;
	
	/**
	 * Method Name: CustomerController
	 * Purpose: CustomerController is instantiated in SuperController with a Singleton instance of MySqlSakilaDatabase.
	 * 					CustomerListView and CustomerForm is instantiated and passed this controller for listener events.
	 * 					TableViewModel is instantiated with a list of CustomerViewModels and populated via MySqlSakilaDatabase. 
	 * 					TableViewModel is then used to pass to the CustomerListView to generate the list in its tab.
	 * Accepts: A SakilaDatabase object.
	 * Returns: An CustomerController object.
	 */
	public CustomerController(SakilaDatabase db) {
		this.db = db;
		customerListViewPanel = new CustomerListView(this);
		customerFormFrame = new CustomerForm(this);
		model = new TableViewModel<>(getCustomersFromDB(), CustomerViewModel.class);
		customerListViewPanel.setCustomerList(model);
	}
	
	/**
	 *  Method Name: refreshDB
	 *  Purpose: Refreshes CustomerForm data on every tab change. Is essential if data from other tabs is deleted.
	 *  Accepts: Nothing.
	 *  Returns: Nothing.
	 */
	public void refreshDB() {
		customerFormFrame.setCities(getCitiesFromDB());
	}
	
	/** 
	 * Method Name: getCustomersFromDB
	 * Purpose: Gets the Customers from the database.
	 * Accepts: Nothing.
	 * Return: A list of CustomerViewModel objects.
	 */
	private List<CustomerViewModel> getCustomersFromDB() {
		try {
			return db.selectCustomers();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(customerListViewPanel, 
					"Error loading customers: " + e.getMessage(), 
					"Error loading customers", JOptionPane.ERROR_MESSAGE);
			return new ArrayList<>();
		}
	}
	
	/** 
	 * Method Name: getCitiesFromDB
	 * Purpose: Gets the Cities from the database. Used for CustomerForm's JComboBox cmbCities.
	 * Accepts: Nothing.
	 * Return: A list of CityViewModel objects.
	 */
	private List<CityViewModel> getCitiesFromDB() {
		try {
			return db.selectCities();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(customerListViewPanel, 
					"Error loading cities: " + e.getMessage(), 
					"Error loading cities", JOptionPane.ERROR_MESSAGE);
			return new ArrayList<>();
		}
	}

	/** 
	 * Method Name: openAddCustomerForm
	 * Purpose: The actions to be performed when listening for 'Add' button in CustomerListView.
	 * Accepts: Nothing.
	 * Return: Nothing.
	 */
	public void openAddCustomerForm() {
		customerListViewPanel.setEnabled(false);
		customerFormFrame.setTitle("Add Customer");
		customerFormFrame.setCustomer(null);
		customerFormFrame.setVisible(true);
	}

	/** 
	 * Method Name: openUpdateCustomerForm
	 * Purpose: The actions to be performed when listening for 'Update' button in CustomerListView.
	 * Accepts: Nothing.
	 * Return: Nothing.
	 */
	public void openUpdateCustomerForm() {
		CustomerViewModel vm = customerListViewPanel.getSelectedCustomer();
		if (vm == null) {
			return;
		}
		customerListViewPanel.setEnabled(false);
		customerFormFrame.setTitle("Update Customer");
		customerFormFrame.setCustomer(vm);
		customerFormFrame.setVisible(true);
	}
	
	/** 
	 * Method Name: deleteCustomer
	 * Purpose: The actions to be performed when listening for 'Delete' button in CustomerListView.
	 * Accepts: Nothing.
	 * Return: Nothing.
	 */
	public void deleteCustomer() {
		CustomerViewModel vm = customerListViewPanel.getSelectedCustomer();
		if (vm == null) {
			return;
		}
		try {
			db.deleteCustomer(vm);
			JOptionPane.showMessageDialog(customerFormFrame, "Delete customer succeeded!");
			model.setData(getCustomersFromDB());
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(customerFormFrame,  "Error: " + e.getMessage(), "Delete failed!", JOptionPane.ERROR_MESSAGE);
		}
	}

	/** 
	 * Method Name: closeCustomerForm
	 * Purpose: The actions to be performed when listening for 'Close' button in CustomerForm.
	 * Accepts: Nothing.
	 * Return: Nothing.
	 */
	public void closeCustomerForm() {
		customerListViewPanel.setEnabled(true);
		customerFormFrame.setVisible(false);
	}

	/** 
	 * Method Name: confirmAddCustomer
	 * Purpose: The actions to be performed when listening for 'Confirm' button in CustomerForm.
	 * Accepts: Nothing.
	 * Return: Nothing.
	 */
	public void confirmAddCustomer() {
		customerListViewPanel.setEnabled(true);
		CustomerViewModel vm = customerFormFrame.getCustomer();
		if (vm == null) {
			return;
		}
		String type = "";
		try {
			if (vm.customerId == -1) {
				type = "Add";
				db.insertCustomer(vm);
			} else {
				type = "Update";
				db.updateCustomer(vm);
			}
			JOptionPane.showMessageDialog(customerFormFrame, type + " customer succeeded!");
			customerFormFrame.setVisible(false);
			model.setData(getCustomersFromDB());
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(customerFormFrame,  "Error: " + e.getMessage(), type + " failed!", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	/** 
	 * Method Name: getPanel
	 * Purpose: Gets CustomerController's CustomerListView.
	 * 					Is used when adding a tab to the SuperController.
	 * Accepts: Nothing.
	 * Return: An CustomerListView object.
	 */
	public JPanel getPanel() {
		return customerListViewPanel;
	}
}
