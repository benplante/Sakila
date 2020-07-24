package com.blockbuster.sakila.ui;

import java.awt.FlowLayout;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import com.blockbuster.sakila.controllers.ActorController;
import com.blockbuster.sakila.viewmodels.ActorViewModel;

public class ActorForm extends JPanel {
	
	public static final String BTN_CONFIRM = "confirm";
	public static final String BTN_CANCEL = "cancel";
	
	private JTextField txtFirstName, txtLastName;
	private JButton btnConfirm, btnCancel;

	public ActorForm(ActorController controller) {
		super();
		txtFirstName = new JTextField();
		txtLastName = new JTextField();
		
		btnConfirm = new JButton("Confirm");
		btnCancel = new JButton("Cancel");
		
		btnConfirm.setActionCommand(BTN_CONFIRM);
		btnConfirm.addActionListener(controller);
		
		btnCancel.setActionCommand(BTN_CANCEL);
		btnCancel.addActionListener(controller);
		
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
		
		
		this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		this.add(txtPanel);
		this.add(btnPanel);
	}
	
	public ActorViewModel getActor() {
		ActorViewModel vm = new ActorViewModel();
		vm.setFirstName(txtFirstName.getText());
		vm.setLastName(txtLastName.getText());
		return vm;
	}
}
