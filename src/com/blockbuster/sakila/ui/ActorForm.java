package com.blockbuster.sakila.ui;

import java.awt.GridLayout;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import com.blockbuster.sakila.controllers.ActorController;
import com.blockbuster.sakila.viewmodels.ActorViewModel;

/**
 *  @author Saja Alhadeethi, Colin Manliclic, Dahye Min, Ben Plante
 *
 *         Actor Add/Update form in a JFrame contains text fields for an actors
 *         first and last name fields of actor table
 */
public class ActorForm extends JFrame {
	// ActorForm members
	private JTextField txtFirstName, txtLastName;
	private JButton btnConfirm, btnCancel;
	private ActorViewModel actor;

	/** 
	 * Method Name: ActorForm
	 * Purpose: ActorForm is a JFrame for user input.
	 * Accepts: ActorController to handle for listener events. 
	 * Return: An ActorForm object.
	 */
	public ActorForm(ActorController controller) {
		super();
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		
		JPanel wrapper = new JPanel();
		txtFirstName = new JTextField();
		txtLastName = new JTextField();

		btnConfirm = new JButton("Confirm");
		btnConfirm.addActionListener(e -> controller.confirmAddActor());

		btnCancel = new JButton("Cancel");
		btnCancel.addActionListener(e -> controller.closeActorForm());

		JPanel txtPanel = new JPanel();
		txtPanel.setBorder(new EmptyBorder(10, 5, 10, 5));
		txtPanel.setLayout(new GridLayout(0, 1, 0, 5));
		txtPanel.add(new JLabel("First Name:"));
		txtPanel.add(txtFirstName);
		txtPanel.add(new JLabel("Last Name:"));
		txtPanel.add(txtLastName);

		JPanel btnPanel = new JPanel();
		btnPanel.setBorder(new EmptyBorder(10, 5, 10, 5));
		btnPanel.setLayout(new BoxLayout(btnPanel, BoxLayout.LINE_AXIS));
		btnPanel.add(btnConfirm);
		btnPanel.add(Box.createHorizontalStrut(70));
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
	 * Method Name: getActor
	 * Purpose: To validate input and get ActorViewModel when user selects 'Confirm' in ActorForm.
	 * Accepts: Nothing.
	 * Returns: ActorViewModel object populated with user input.
	 */
	public ActorViewModel getActor() {
		if (txtFirstName.getText().isBlank()) {
			JOptionPane.showMessageDialog(this, "Please enter a first name.", "First Name Failed!",
					JOptionPane.WARNING_MESSAGE);
			txtFirstName.requestFocus();
			return null;
		} else if (txtLastName.getText().isBlank()) {
			JOptionPane.showMessageDialog(this, "Please enter a last name.", "Last Name Failed!",
					JOptionPane.WARNING_MESSAGE);
			txtLastName.requestFocus();
			return null;
		}

		actor.firstName = txtFirstName.getText();
		actor.lastName = txtLastName.getText();

		return actor;
	}

	/** 
	 * Method Name: setActor
	 * Purpose: Instantiates ActorForm's ActorViewModel and populates fields 
	 * 					based on user selecting 'Add' or user selecting a row and selecting 'Update' from ActorListView.
	 * Accepts: An ActorViewModel object.
	 * Returns: Nothing.
	 */
	public void setActor(ActorViewModel actor) {
		if (actor != null) {
			this.actor = actor;
			txtFirstName.setText(actor.firstName);
			txtLastName.setText(actor.lastName);
		} else {
			this.actor = new ActorViewModel();
			txtFirstName.setText("");
			txtLastName.setText("");
		}
	}
}
