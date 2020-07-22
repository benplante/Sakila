package com.blockbuster.sakila.ui;

import javax.swing.*;

import com.blockbuster.sakila.controllers.ActorController;
import com.blockbuster.sakila.viewmodels.ActorViewModel;

public class ActorForm extends JPanel {
	
	public static final String BTN_ADD = "add";

	public ActorForm(ActorController controller) {
		JButton button = new JButton();
		button.setActionCommand(BTN_ADD);
		button.addActionListener(controller);
	}
	
	public ActorViewModel getActor() {
		return new ActorViewModel();
	}
}
