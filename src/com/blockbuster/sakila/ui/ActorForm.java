package com.blockbuster.sakila.ui;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import com.blockbuster.sakila.controllers.ActorController;
import com.blockbuster.sakila.viewmodels.ActorViewModel;

/**
 * @author Ben Plante
 *
 *         Actor Add/Update form in a JFrame contains text fields for an actors
 *         first and last name
 */
public class ActorForm extends JFrame {

	private JTextField txtFirstName, txtLastName;
	private JButton btnConfirm, btnCancel;

	private ActorViewModel actor;

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
		txtPanel.setLayout(new BoxLayout(txtPanel, BoxLayout.PAGE_AXIS));
		txtPanel.add(new JLabel("First Name:"));
		txtPanel.add(txtFirstName);
		txtPanel.add(new JLabel("Last Name:"));
		txtPanel.add(txtLastName);

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

	public ActorViewModel getActor() {
		actor.firstName = txtFirstName.getText();
		actor.lastName = txtLastName.getText();
		return actor;
	}

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
