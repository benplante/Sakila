package com.blockbuster.sakila.ui;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import com.blockbuster.sakila.controllers.CustomerController;
import com.blockbuster.sakila.viewmodels.CustomerViewModel;

/**
 * @author Ben Plante
 *
 *         Actor Add/Update form in a JFrame contains text fields for an actors
 *         first and last name
 */
public class CustomerForm extends JFrame {

	private JTextField txtFirstName, txtLastName, txtEmail, txtAddress, txtDistrict, txtPostalCode, txtPhone;
	private JButton btnConfirm, btnCancel;

	private CustomerViewModel customer;

	public CustomerForm(CustomerController controller) {
		super();

		customer = new CustomerViewModel();

		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		JPanel wrapper = new JPanel();
		txtFirstName = new JTextField();
		txtLastName = new JTextField();
		txtEmail = new JTextField();
		txtAddress = new JTextField();
		txtDistrict = new JTextField();
		txtPostalCode = new JTextField();
		txtPhone = new JTextField();

		

		btnConfirm = new JButton("Confirm");
		btnConfirm.addActionListener(e -> controller.confirmAddCustomer());

		btnCancel = new JButton("Cancel");
		btnCancel.addActionListener(e -> controller.closeCustomerForm());

		JPanel txtPanel = new JPanel();
		txtPanel.setBorder(new EmptyBorder(10, 5, 10, 5));
		txtPanel.setLayout(new BoxLayout(txtPanel, BoxLayout.PAGE_AXIS));
		txtPanel.add(new JLabel("First Name:"));
		txtPanel.add(txtFirstName);
		txtPanel.add(new JLabel("Last Name"));
		txtPanel.add(txtLastName);
		txtPanel.add(new JLabel("Email:"));
		txtPanel.add(txtEmail);
		txtPanel.add(new JLabel("Address:"));
		txtPanel.add(txtAddress);
		txtPanel.add(new JLabel("District:"));
		txtPanel.add(txtDistrict);
		txtPanel.add(new JLabel("Postal Code:"));
		txtPanel.add(txtPostalCode);
		txtPanel.add(new JLabel("Phone #:"));
		txtPanel.add(txtPhone);
		txtPanel.add(new JLabel("Cities: "));
		//txtPanel.

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

	public CustomerViewModel getCustomer() {
		customer.firstName = txtFirstName.getText();
		customer.lastName = txtLastName.getText();
		customer.email = txtEmail.getText();

		return customer;
	}

	public void setCustomer(CustomerViewModel customer) {
		this.customer = customer;
		if (customer != null) {
			txtFirstName.setText(customer.firstName);
			txtLastName.setText(customer.lastName);
			txtEmail.setText(customer.email);
		} else {
			txtFirstName.setText("");
			txtLastName.setText("");
			txtEmail.setText("");
		}
	}
}
