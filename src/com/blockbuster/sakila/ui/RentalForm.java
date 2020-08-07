package com.blockbuster.sakila.ui;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.util.List;
import java.util.regex.Pattern;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import com.blockbuster.sakila.controllers.RentalController;
import com.blockbuster.sakila.viewmodels.CustomerViewModel;
import com.blockbuster.sakila.viewmodels.InventoryViewModel;
import com.blockbuster.sakila.viewmodels.RentalViewModel;

public class RentalForm extends JFrame {
	private JTextField txtAmountPaid, txtRentalRate, txtRentalDuration;
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
		txtRentalRate = new JTextField();
		txtRentalRate.setEditable(false);
		txtRentalDuration = new JTextField();
		txtRentalDuration.setEditable(false);
		cmbInventories = new JComboBox<>();
		cmbCustomers = new JComboBox<>();	
		
		cmbInventories.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (cmbInventories.getSelectedIndex() > -1) {
					txtRentalRate.setText("$" + inventories[cmbInventories.getSelectedIndex()].getRentalRate().toString());
					txtRentalDuration.setText(inventories[cmbInventories.getSelectedIndex()].getFilmRentalDuration() + " day(s)");
				}
			}
		});
		
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
		txtPanel.add(new JLabel("Price:"));
		txtPanel.add(txtRentalRate);
		txtPanel.add(new JLabel("Rental Duration:"));
		txtPanel.add(txtRentalDuration);
		txtPanel.add(new JLabel("Payment ($):"));
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
		int inventoryIdx = cmbInventories.getSelectedIndex();		
		int customerIdx = cmbCustomers.getSelectedIndex();
		BigDecimal rentalRate = inventories[inventoryIdx].getRentalRate();
		
		if(inventoryIdx < 0) {
			JOptionPane.showMessageDialog(this, "Please select a film.", "Actor Selection Failed!",
					JOptionPane.WARNING_MESSAGE);
			cmbInventories.requestFocus();
			return null;
		}
		else if(customerIdx < 0) {
			JOptionPane.showMessageDialog(this, "Please select a customer.", "Customer Selection Failed!",
					JOptionPane.WARNING_MESSAGE);
			cmbCustomers.requestFocus();
			return null;
		}
		else if(!Pattern.matches("^[0-9]{1,3}(\\.[0-9]{1,2})?$", txtAmountPaid.getText())) {
			JOptionPane.showMessageDialog(this, "Please enter a valid amount.\nAmount can not exceed $999.99", "Payment Failed!",
					JOptionPane.WARNING_MESSAGE);
			txtAmountPaid.requestFocus();
			return null;
		}
		
		BigDecimal amountPaid = new BigDecimal(txtAmountPaid.getText());
		if(amountPaid.compareTo(rentalRate) < 0) {
			JOptionPane.showMessageDialog(this, "Amount must be greater than price.", "Payment Failed!",
					JOptionPane.WARNING_MESSAGE);
			txtAmountPaid.requestFocus();
			return null;
		}

		rental.setIventoryId(inventories[inventoryIdx].getInventoryId());
		rental.setCustomerId(customers[customerIdx].customerId);
		rental.setFilmRentalDuration(inventories[inventoryIdx].getFilmRentalDuration());
		rental.setPaymentAmount(amountPaid);		
		return rental;
	}
	
	public void setRental() {
			this.rental = new RentalViewModel();
			cmbInventories.setSelectedIndex(-1);
			cmbCustomers.setSelectedIndex(-1);
			txtRentalRate.setText("Please select a film.");	
			txtRentalDuration.setText("Please select a film.");
			txtAmountPaid.setText("");
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