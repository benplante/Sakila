package com.blockbuster.sakila.controllers;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

import com.blockbuster.sakila.database.SakilaDatabase;
import com.blockbuster.sakila.database.TableViewModel;
import com.blockbuster.sakila.ui.CustomerForm;
import com.blockbuster.sakila.ui.CustomerListView;
import com.blockbuster.sakila.viewmodels.CityViewModel;
import com.blockbuster.sakila.viewmodels.CustomerViewModel;

public class CustomerController {
	private SakilaDatabase db;
	private CustomerListView customerListViewPanel;
	private CustomerForm customerFormFrame;

	private TableViewModel<CustomerViewModel> model;

	public CustomerController(SakilaDatabase db) {
		this.db = db;

		customerListViewPanel = new CustomerListView(this);
		customerFormFrame = new CustomerForm(this);

		model = new TableViewModel<>(getCustomersFromDB(), CustomerViewModel.class);
		customerListViewPanel.setCustomerList(model);
		customerFormFrame.setCities(getCitiesFromDB());
	}
	
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

	public JPanel getPanel() {
		return customerListViewPanel;
	}

	public void openAddCustomerForm() {
		customerListViewPanel.setEnabled(false);
		customerFormFrame.setName("Add Customer");
		customerFormFrame.setCustomer(null);
		customerFormFrame.setVisible(true);
	}

	public void openUpdateCustomerForm() {
		customerListViewPanel.setEnabled(false);
		customerFormFrame.setName("Update Customer");
		customerFormFrame.setCustomer(customerListViewPanel.getSelectedCustomer());
		customerFormFrame.setVisible(true);
	}

	public void closeCustomerForm() {
		customerListViewPanel.setEnabled(true);
		customerFormFrame.setVisible(false);
	}


	public void confirmAddCustomer() {
		customerListViewPanel.setEnabled(true);
		CustomerViewModel vm = customerFormFrame.getCustomer();
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
}
