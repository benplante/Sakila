package com.blockbuster.sakila.ui;

import java.awt.GridLayout;
import java.math.BigDecimal;
import java.util.List;


import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import com.blockbuster.sakila.controllers.RentalController;
import com.blockbuster.sakila.viewmodels.CustomerViewModel;
import com.blockbuster.sakila.viewmodels.InventoryViewModel;
import com.blockbuster.sakila.viewmodels.RentalViewModel;

public class RentalForm extends JFrame {
	private JTextField txtAmountPaid;
	private JComboBox<InventoryViewModel> cmbInventories;
	private JComboBox<CustomerViewModel> cmbCustomers;
	private JButton btnConfirm, btnCancel;
	
	private RentalViewModel rental;
	private InventoryViewModel[] inventories;
	private CustomerViewModel[] customers;
	
	public RentalForm(RentalController controller) {
		super();
		
		rental = new RentalViewModel();
		
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		JPanel wrapper = new JPanel();
		txtAmountPaid = new JTextField();
		cmbInventories = new JComboBox<>();
		cmbCustomers = new JComboBox<>();
		
		btnConfirm = new JButton("Confirm");
		btnConfirm.addActionListener(e -> controller.confirmAddRental());

		btnCancel = new JButton("Cancel");
		btnCancel.addActionListener(e -> controller.closeCustomerForm());
		
		JPanel txtPanel = new JPanel();
		txtPanel.setBorder(new EmptyBorder(10, 5, 10, 5));
		txtPanel.setLayout(new GridLayout(0, 1, 0, 5));
		txtPanel.add(new JLabel("Film:"));
		txtPanel.add(cmbInventories);
		txtPanel.add(new JLabel("Customer:"));
		txtPanel.add(cmbCustomers);
		txtPanel.add(new JLabel("Amount ($):"));
		txtPanel.add(txtAmountPaid);
		
		JPanel btnPanel = new JPanel();
		btnPanel.setBorder(new EmptyBorder(10, 5, 10, 5));
		btnPanel.setLayout(new BoxLayout(btnPanel, BoxLayout.LINE_AXIS));
		btnPanel.add(btnConfirm);
		btnPanel.add(Box.createHorizontalStrut(10));
		btnPanel.add(btnCancel);
		
		wrapper.setLayout(new BoxLayout(wrapper, BoxLayout.PAGE_AXIS));
		wrapper.add(txtPanel);
		wrapper.add(btnPanel);
		this.setContentPane(wrapper);
		this.pack();
		this.setResizable(false);
		this.setLocationRelativeTo(null);
	}
	
	public RentalViewModel getRental() {
		rental.setIventoryId(inventories[cmbInventories.getSelectedIndex()].getInventoryId());
		rental.setCustomerId(customers[cmbCustomers.getSelectedIndex()].customerId);
		rental.setPaymentAmount(new BigDecimal(txtAmountPaid.getText()));
		rental.setFilmRentalDuration(inventories[cmbInventories.getSelectedIndex()].getFilmRentalDuration());
		return rental;
	}
	
	public void setRental(RentalViewModel rental) {
		this.rental = rental;
		if (rental != null) {
			int inventoryIdx = 1;
			for(int i = 0; i < inventories.length; ++i) {
				if(inventories[i].getInventoryId() == rental.getInventoryId()) {
					inventoryIdx = i;
					break;
				}
			}
			cmbInventories.setSelectedIndex(inventoryIdx);
			
			int customerIdx = 1;
			for(int i = 0; i < customers.length; ++i) {
				if(customers[i].customerId == rental.getCustomerId()) {
					customerIdx = i;
					break;
				}
			}
			cmbCustomers.setSelectedIndex(customerIdx);
			txtAmountPaid.setText(rental.getPaymentAmount().toString());
		} else {
			this.rental = new RentalViewModel();
			cmbInventories.setSelectedIndex(-1);
			cmbCustomers.setSelectedIndex(-1);
			txtAmountPaid.setText("");
		}
	}

	public void setInventories(List<InventoryViewModel> list)
	{
		InventoryViewModel[] arr = new InventoryViewModel[list.size()];
		list.toArray(arr);
		this.inventories = arr;
		cmbInventories.setModel(new DefaultComboBoxModel<InventoryViewModel>(arr));
	}

	public void setCustomers(List<CustomerViewModel> customers)
	{
		CustomerViewModel[] arr = new CustomerViewModel[customers.size()];
		customers.toArray(arr);
		this.customers = arr;
		cmbCustomers.setModel(new DefaultComboBoxModel<CustomerViewModel>(arr));
	}
}