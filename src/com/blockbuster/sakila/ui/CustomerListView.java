package com.blockbuster.sakila.ui;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;

import com.blockbuster.sakila.controllers.CustomerController;
import com.blockbuster.sakila.database.TableViewModel;
import com.blockbuster.sakila.viewmodels.CustomerViewModel;

/**
 * @author Ben Plante
 *
 *         Panel for viewing a list of all Customers in the database Displays
 *         actors in a JTable and contains buttons to add, update and delete a
 *         customer
 */
public class CustomerListView extends JPanel {
	private JTable customerTable;
	private JButton btnAdd;
	private JButton btnUpdate;

	private TableViewModel<CustomerViewModel> model;

	public CustomerListView(CustomerController controller) {
		super();

		customerTable = new JTable();
		JScrollPane scrollPane = new JScrollPane(customerTable);

		btnAdd = new JButton("Add");
		btnAdd.addActionListener(e -> controller.openAddCustomerForm());
		btnUpdate = new JButton("Update");
		btnUpdate.addActionListener(e -> controller.openUpdateCustomerForm());

		this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));

		JPanel btnPanel = new JPanel();
		btnPanel.setLayout(new BoxLayout(btnPanel, BoxLayout.LINE_AXIS));
		btnPanel.add(btnAdd);
		btnPanel.add(Box.createHorizontalStrut(10));
		btnPanel.add(btnUpdate);
		btnPanel.add(Box.createHorizontalStrut(10));

		this.add(scrollPane);
		this.add(Box.createVerticalStrut(10));
		this.add(btnPanel);
		this.setBorder(new EmptyBorder(10, 10, 10, 10));
	}

	public void setCustomerList(TableViewModel<CustomerViewModel> model) {
		this.model = model;
		customerTable.setModel(model);
	}

	public CustomerViewModel getSelectedCustomer() {
		return model.getAtRow(customerTable.getSelectedRow());
	}
}
