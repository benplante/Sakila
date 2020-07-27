package com.blockbuster.sakila.controllers;

import javax.swing.JPanel;

import com.blockbuster.sakila.database.SakilaDatabase;
import com.blockbuster.sakila.database.TableViewModel;
import com.blockbuster.sakila.ui.CustomerForm;
import com.blockbuster.sakila.ui.CustomerListView;
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

		model = new TableViewModel<>(db.selectCustomers(), CustomerViewModel.class);
		customerListViewPanel.setCustomerList(model);

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
		if (vm.customerId == -1) {
			db.insertCustomer(vm);
		} else {
			db.updateCustomer(vm);
		}
		model.setData(db.selectCustomers());
		customerFormFrame.setVisible(false);
	}

//    public void deleteActor() {
//        db.deleteActor(customerListViewPanel.getSelectedActor());
//        model.setData(db.selectActors());
//    }
}
