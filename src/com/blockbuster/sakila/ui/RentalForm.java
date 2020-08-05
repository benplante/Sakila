package com.blockbuster.sakila.ui;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.util.List;


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
	private JTextField txtAmountPaid, txtRentalRate;
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
		cmbInventories = new JComboBox<>();
		cmbCustomers = new JComboBox<>();	
		
		cmbInventories.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e)
			{
				if (cmbInventories.getSelectedIndex() > -1) {
					txtRentalRate.setText("$" + inventories[cmbInventories.getSelectedIndex()].getRentalRate().toString());
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
		int inventoryIdx = cmbInventories.getSelectedIndex();		
		int customerIdx = cmbCustomers.getSelectedIndex();
		BigDecimal amountPaid = null;
		BigDecimal rentalRate = null;
		
		if(inventoryIdx < 0) {
			JOptionPane.showMessageDialog(new JFrame(), "Please select a film.", "Invalid",
					JOptionPane.WARNING_MESSAGE);
			cmbInventories.requestFocus();
			return null;
		}
		else if(customerIdx < 0) {
			JOptionPane.showMessageDialog(new JFrame(), "Please select a customer.", "Invalid",
					JOptionPane.WARNING_MESSAGE);
			cmbCustomers.requestFocus();
			return null;
		}
		
		try {
			amountPaid = new BigDecimal(txtAmountPaid.getText());
			rentalRate = inventories[inventoryIdx].getRentalRate();
			
			if (amountPaid.compareTo(rentalRate) < 0 || amountPaid.compareTo(new BigDecimal(999.99)) > 0)
			{
				throw new Exception();
			}
			
		}
		catch (NumberFormatException ex) {
			JOptionPane.showMessageDialog(new JFrame(), "Please enter a valid amount.", "Invalid",
					JOptionPane.WARNING_MESSAGE);
			txtAmountPaid.requestFocus();
			return null;
		}
		catch (Exception ex) {
			JOptionPane.showMessageDialog(new JFrame(), "Amount must be greater than price \nand can not exceed 999.99.", "Invalid",
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
			txtRentalRate.setText("");
			
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