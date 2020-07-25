package com.blockbuster.sakila.ui;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import com.blockbuster.sakila.controllers.ActorController;
import com.blockbuster.sakila.viewmodels.ActorViewModel;

public class ActorForm extends JFrame {
	
	private JTextField txtFirstName, txtLastName;
	private JButton btnConfirm, btnCancel;

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
		txtPanel.add(txtFirstName);
		txtPanel.add(Box.createVerticalStrut(10));
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
		ActorViewModel vm = new ActorViewModel();
		vm.firstName = txtFirstName.getText();
		vm.lastName = txtLastName.getText();
		return vm;
	}
	
	public void setActor(ActorViewModel vm) {
		if (vm != null) {
			txtFirstName.setText(vm.firstName);
			txtLastName.setText(vm.lastName);
		}
	}
}
