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

/**
 * @author Saja Alhadeethi, Colin Manliclic, Dahye Min, Ben Plante
 *
 *         Rental add form in a JFrame contains fields for a rental's
 *         film, customer, and payment amount
 */

 @SuppressWarnings("serial")
public class RentalForm extends JFrame {
	// RentalForm members
	private JTextField txtAmountPaid, txtRentalRate, txtRentalDuration;
	private JComboBox<InventoryViewModel> cmbInventories;
	private JComboBox<CustomerViewModel> cmbCustomers;
	private JButton btnConfirm, btnCancel;
	private RentalViewModel rental;
	private InventoryViewModel[] inventories;
	private CustomerViewModel[] customers;
	
	/** 
	 * Method Name: RentalForm
	 * Purpose: RentalForm is a JFrame for user input.
	 * Accepts: RentalController to handle for listener events. 
	 * Return: A RentalForm object.
	 */
	public RentalForm(RentalController controller) {
		super();
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
					// Will output the rental rate and rental duration of the selected film in the inventory combo box
					txtRentalRate.setText("$" + inventories[cmbInventories.getSelectedIndex()].getRentalRate().toString());
					txtRentalDuration.setText(inventories[cmbInventories.getSelectedIndex()].getFilmRentalDuration() + " day(s)");
				}
			}
		});
		
		btnConfirm = new JButton("Confirm");
		btnConfirm.addActionListener(e -> controller.confirmAddRental());

		btnCancel = new JButton("Cancel");
		btnCancel.addActionListener(e -> controller.closeRentalForm());
		
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
		btnPanel.add(Box.createHorizontalStrut(80));
		btnPanel.add(btnCancel);
		
		wrapper.setLayout(new BoxLayout(wrapper, BoxLayout.PAGE_AXIS));
		wrapper.add(txtPanel);
		wrapper.add(btnPanel);
		this.setContentPane(wrapper);
		this.pack();
		this.setResizable(false);
		this.setLocationRelativeTo(null);
	}
	
	/** 
	 * Method Name: getRental
	 * Purpose: To validate input and get RentalViewModel when user selects 'Confirm' in FilmForm.
	 * Accepts: Nothing.
	 * Returns: RentalViewModel object populated with user input.
	 */
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
		else if(!Pattern.matches("^[0-9]{1,3}(\\.[0-9]{1,2})?$", txtAmountPaid.getText())) { // Regex for 0-999
			JOptionPane.showMessageDialog(this, "Please enter a valid amount.\nAmount can not exceed $999.99", "Payment Failed!",
					JOptionPane.WARNING_MESSAGE);
			txtAmountPaid.requestFocus();
			return null;
		}
		
		BigDecimal amountPaid = new BigDecimal(txtAmountPaid.getText());
		// BigDecimal comparison equivalent to amountPaid < rentalRate
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
	
	/** 
	 * Method Name: setRental
	 * Purpose: Instantiates RentalForm's RentalViewModel and clears all fields 
	 * 					when user selects 'Add' from RentalListView.
	 * Accepts: Nothing.
	 * Returns: Nothing.
	 */
	public void setRental() {
			this.rental = new RentalViewModel();
			cmbInventories.setSelectedIndex(-1);
			cmbCustomers.setSelectedIndex(-1);
			txtRentalRate.setText("Please select a film.");	
			txtRentalDuration.setText("Please select a film.");
			txtAmountPaid.setText("");
	}

	/** 
	 * Method Name: setInventories
	 * Purpose: Instantiates InventoryViewModel array
	 * 					and sets the array to the JComboBox.
	 * Accepts: A list of InventoryViewModel objects.
	 * Returns: Nothing.
	 */
	public void setInventories(List<InventoryViewModel> list)
	{
		InventoryViewModel[] arr = new InventoryViewModel[list.size()];
		list.toArray(arr);
		this.inventories = arr;
		cmbInventories.setModel(new DefaultComboBoxModel<InventoryViewModel>(arr));
	}

	/** 
	 * Method Name: setCustomers
	 * Purpose: Instantiates CustomerViewModel array 
	 * 					and sets the array to the JComboBox.
	 * Accepts: A list of CustomerViewModel objects.
	 * Returns: Nothing.
	 */
	public void setCustomers(List<CustomerViewModel> customers)
	{
		CustomerViewModel[] arr = new CustomerViewModel[customers.size()];
		customers.toArray(arr);
		this.customers = arr;
		cmbCustomers.setModel(new DefaultComboBoxModel<CustomerViewModel>(arr));
	}
}