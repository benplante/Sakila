package com.blockbuster.sakila.ui;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;

import com.blockbuster.sakila.controllers.CustomerController;
import com.blockbuster.sakila.ui.utils.TableViewModel;
import com.blockbuster.sakila.viewmodels.CustomerViewModel;

/**
 * @author Saja Alhadeethi, Colin Manliclic, Dahye Min, Ben Plante
 *
 *         Panel for viewing a list of all Customers in the database Displays
 *         customers in a JTable and contains buttons to add, update and delete a
 *         customer
 */

@SuppressWarnings("serial")
public class CustomerListView extends JPanel {
	// CustomerListView members
	private JTable customerTable;
	private JButton btnAdd;
	private JButton btnUpdate;
	private JButton btnDelete;
	private TableViewModel<CustomerViewModel> model;

	/** 
	 * Method Name: CustomerListView
	 * Purpose: CustomerListView is a JPanel to show list of customers and its attributes.
	 * Accepts: CustomerController to handle for listener events.
	 * Return: A CustomerListView object.
	 */
	public CustomerListView(CustomerController controller) {
		super();

		customerTable = new JTable();
		JScrollPane scrollPane = new JScrollPane(customerTable);

		btnAdd = new JButton("Add");
		btnAdd.addActionListener(e -> controller.openAddCustomerForm());
		btnUpdate = new JButton("Update");
		btnUpdate.addActionListener(e -> controller.openUpdateCustomerForm());
		btnDelete = new JButton("Delete");
		btnDelete.addActionListener(e -> controller.deleteCustomer());

		JPanel btnPanel = new JPanel();
		btnPanel.setLayout(new BoxLayout(btnPanel, BoxLayout.LINE_AXIS));
		btnPanel.add(btnAdd);
		btnPanel.add(Box.createHorizontalStrut(10));
		btnPanel.add(btnUpdate);
		btnPanel.add(Box.createHorizontalStrut(10));
		btnPanel.add(btnDelete);
		
		this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		this.add(scrollPane);
		this.add(Box.createVerticalStrut(10));
		this.add(btnPanel);
		this.setBorder(new EmptyBorder(10, 10, 10, 10));
	}

	/** 
	 * Method Name: setCustomerList
	 * Purpose: To populate the CustomerListView.
	 * Accepts: A TableViewModel object of CustomerViewModel objects.
	 * Return: Nothing.
	 */
	public void setCustomerList(TableViewModel<CustomerViewModel> model) {
		this.model = model;
		customerTable.setModel(model);
	}

	/** 
	 * Method Name: getSelectedCustomer
	 * Purpose: To get the user selected row of the list. 
	 * 					Used in CustomerController's deleteCustomer() & openUpdateCustomerForm().
	 * Accepts: Nothing.
	 * Return: The selected row's CustomerViewModel.
	 */
	public CustomerViewModel getSelectedCustomer() {
		if (customerTable.getSelectedRow() == -1) {
			JOptionPane.showMessageDialog(this, "Please select a customer.", "Selection Failed!",
					JOptionPane.WARNING_MESSAGE);
			return null;
		}
		return model.getAtRow(customerTable.getSelectedRow());
	}
}
