package com.blockbuster.sakila.ui;

import java.awt.GridLayout;
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

import com.blockbuster.sakila.controllers.CustomerController;
import com.blockbuster.sakila.viewmodels.CityViewModel;
import com.blockbuster.sakila.viewmodels.CustomerViewModel;

/**
 * @author Saja Alhadeethi, Colin Manliclic, Dahye Min, Ben Plante
 *
 *         Customer CRUD operation ,form in a JFrame contains text fields for a customers
 *         first and last name 
 */
public class CustomerForm extends JFrame {
	// CustomerForm members
	private JTextField txtFirstName, txtLastName, txtEmail, txtAddress, txtDistrict, txtPostalCode, txtPhone;
	private JComboBox<CityViewModel> cmbCities;
	private JButton btnConfirm, btnCancel;
	private CustomerViewModel customer;
	private CityViewModel[] cities;
	
	/** 
	 * Method Name: CustomerForm
	 * Purpose: CustomerForm is a JFrame for user input.
	 * Accepts: CustomerController to handle for listener events. 
	 * Return: An CustomerForm object.
	 */
	public CustomerForm(CustomerController controller) {
		super();
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		
		JPanel wrapper = new JPanel();
		txtFirstName = new JTextField();
		txtLastName = new JTextField();
		txtEmail = new JTextField();
		txtAddress = new JTextField();
		txtDistrict = new JTextField();
		txtPostalCode = new JTextField();
		txtPhone = new JTextField();
		cmbCities = new JComboBox<>();

		btnConfirm = new JButton("Confirm");
		btnConfirm.addActionListener(e -> controller.confirmAddCustomer());

		btnCancel = new JButton("Cancel");
		btnCancel.addActionListener(e -> controller.closeCustomerForm());

		JPanel txtPanel = new JPanel();
		txtPanel.setBorder(new EmptyBorder(10, 5, 10, 5));
		txtPanel.setLayout(new GridLayout(0, 1, 0, 5));
		txtPanel.add(new JLabel("First Name:"));
		txtPanel.add(txtFirstName);
		txtPanel.add(new JLabel("Last Name:"));
		txtPanel.add(txtLastName);
		txtPanel.add(new JLabel("Email:"));
		txtPanel.add(txtEmail);
		txtPanel.add(new JLabel("City:"));
		txtPanel.add(cmbCities);
		txtPanel.add(new JLabel("Address:"));
		txtPanel.add(txtAddress);
		txtPanel.add(new JLabel("District:"));
		txtPanel.add(txtDistrict);
		txtPanel.add(new JLabel("Postal Code:"));
		txtPanel.add(txtPostalCode);
		txtPanel.add(new JLabel("Phone #:"));
		txtPanel.add(txtPhone);

		JPanel btnPanel = new JPanel();
		btnPanel.setBorder(new EmptyBorder(10, 5, 10, 5));
		btnPanel.setLayout(new BoxLayout(btnPanel, BoxLayout.LINE_AXIS));
		btnPanel.add(btnConfirm);
		btnPanel.add(Box.createHorizontalStrut(50));
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
	 * Method Name: getCustomer
	 * Purpose: To validate input and get CustomerViewModel when user selects 'Confirm' in CustomerForm.
	 * Accepts: Nothing.
	 * Returns: CustomerViewModel object populated with user input.
	 */
	public CustomerViewModel getCustomer() {
		String firstName = txtFirstName.getText();
		String lastName = txtLastName.getText();
		String email = txtEmail.getText();
		int selectedIndex = cmbCities.getSelectedIndex();
		String address = txtAddress.getText();
		String district = txtDistrict.getText();
		String postalCode = txtPostalCode.getText();
		String phone = txtPhone.getText();
				
		if(firstName.isBlank()) {
			JOptionPane.showMessageDialog(this, "Please enter a first name.", "First Name Failed!",
					JOptionPane.WARNING_MESSAGE);
			txtFirstName.requestFocus();
			return null;
		}
		else if (lastName.isBlank()) {
			JOptionPane.showMessageDialog(this, "Please enter a last name.", "Last Name Failed!",
					JOptionPane.WARNING_MESSAGE);
			txtLastName.requestFocus();
			return null;
		}
		else if (email.isBlank()) {
			JOptionPane.showMessageDialog(this, "Please enter an email.", "Email Failed!",
					JOptionPane.WARNING_MESSAGE);
			txtEmail.requestFocus();
			return null;
		}
		else if(selectedIndex == -1) {
			JOptionPane.showMessageDialog(this, "Please select a city.", "City Failed!",
					JOptionPane.WARNING_MESSAGE);
			cmbCities.requestFocus();
			return null;
		}
		else if (address.isBlank()) {
			JOptionPane.showMessageDialog(this, "Please enter an address.", "Address Failed!",
					JOptionPane.WARNING_MESSAGE);
			txtAddress.requestFocus();
			return null;
		}
		else if (district.isBlank()) {
			JOptionPane.showMessageDialog(this, "Please enter a district.", "District Failed!",
					JOptionPane.WARNING_MESSAGE);
			txtDistrict.requestFocus();
			return null;
		}
		else if (postalCode.isBlank()) {
			JOptionPane.showMessageDialog(this, "Please enter a postal code.", "Postal Code Failed!",
					JOptionPane.WARNING_MESSAGE);
			txtPostalCode.requestFocus();
			return null;
		}
		else if (!Pattern.matches("^\\d{10,12}$", phone)) {
			JOptionPane.showMessageDialog(this, "Please enter a valid phone number.\nPhone Number must be a length of 10-12 characters and contains no spaces or hyphens (-).", "Phone Number Failed!",
					JOptionPane.WARNING_MESSAGE);
			txtPhone.requestFocus();
			return null;
		}
		
		customer.firstName = firstName;
		customer.lastName = lastName;
		customer.email = email;
		customer.setCityId(cities[selectedIndex].getCityId());
		customer.addressLine1 = address;
		customer.district = district;
		customer.postalCode = postalCode;
		customer.phone = phone;
		customer.setIsActive(true);
		return customer;
	}

	/** 
	 * Method Name: setCustomer
	 * Purpose: Instantiates CustomerViewModel and populates fields 
	 * 					based on user selecting 'Add' or user selecting a row and selecting 'Update' from CustomerListView.
	 * Accepts: A CustomerViewModel object.
	 * Returns: Nothing.
	 */
	public void setCustomer(CustomerViewModel customer) {
		this.customer = customer;
		if (customer != null) {
			txtAddress.setText(customer.addressLine1);
			txtDistrict.setText(customer.district);
			txtEmail.setText(customer.email);
			txtFirstName.setText(customer.firstName);
			txtLastName.setText(customer.lastName);
			txtPhone.setText(customer.phone);
			txtPostalCode.setText(customer.postalCode);
			
			int idx = -1;
			for (int i = 0; i < cities.length; ++i) {
				if (cities[i].getCityId() == customer.getCityId()) {
					idx = i;
					break;
				}
			}
			cmbCities.setSelectedIndex(idx);
		} else {
			this.customer = new CustomerViewModel();
			txtAddress.setText("");
			txtDistrict.setText("");
			txtEmail.setText("");
			txtFirstName.setText("");
			txtLastName.setText("");
			txtPhone.setText("");
			txtPostalCode.setText("");
			cmbCities.setSelectedIndex(-1);
		}
	}

	/** 
	 * Method Name: setCities
	 * Purpose: Instantiates CityViewModel array from database 
	 * 					and sets the array to the JComboBox.
	 * Accepts: A list of CityViewModel objects.
	 * Returns: Nothing.
	 */
	public void setCities(List<CityViewModel> cities) {
		CityViewModel[] arr = new CityViewModel[cities.size()];
		cities.toArray(arr);
		this.cities = arr;
		cmbCities.setModel(new DefaultComboBoxModel<CityViewModel>(arr));
	}
}
