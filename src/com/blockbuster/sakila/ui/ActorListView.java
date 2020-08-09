package com.blockbuster.sakila.ui;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;

import com.blockbuster.sakila.controllers.ActorController;
import com.blockbuster.sakila.ui.utils.TableViewModel;
import com.blockbuster.sakila.viewmodels.ActorViewModel;

/**
 * @author Saja Alhadeethi, Colin Manliclic, Dahye Min, Ben Plante
 *
 *         Panel for viewing a list of all Actors in the database Displays
 *         actors in a JTable and contains buttons to add, update and delete an
 *         actor
 */

@SuppressWarnings("serial")
public class ActorListView extends JPanel {
	// ActorListView members
	private JTable tblActors;
	private JButton btnAdd;
	private JButton btnUpdate;
	private JButton btnDelete;
	private TableViewModel<ActorViewModel> model;

	/** 
	 * Method Name: ActorListView
	 * Purpose: ActorListView is a JPanel to show list of actors and its attributes.
	 * Accepts: ActorController to handle for listener events.
	 * Return: An ActorListView object.
	 */
	public ActorListView(ActorController controller) {
		super();

		tblActors = new JTable();
		JScrollPane scrollPane = new JScrollPane(tblActors);

		btnAdd = new JButton("Add");
		btnAdd.addActionListener(e -> controller.openAddActorForm());
		btnUpdate = new JButton("Update");
		btnUpdate.addActionListener(e -> controller.openUpdateActorForm());
		btnDelete = new JButton("Delete");
		btnDelete.addActionListener(e -> controller.deleteActor());

		JPanel btnPanel = new JPanel();
		btnPanel.setLayout(new BoxLayout(btnPanel, BoxLayout.LINE_AXIS));
		btnPanel.add(btnAdd);
		btnPanel.add(Box.createHorizontalStrut(10));
		btnPanel.add(btnUpdate);
		btnPanel.add(Box.createHorizontalStrut(10));
		btnPanel.add(btnDelete);
		
		this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		this.add(scrollPane);
		this.add(Box.createVerticalStrut(10));
		this.add(btnPanel);
		this.setBorder(new EmptyBorder(10, 10, 10, 10));
	}
	
	/** 
	 * Method Name: setActorList
	 * Purpose: To populate the ActorListView.
	 * Accepts: A TableViewModel object of ActorViewModel objects. 
	 * Return: Nothing.
	 */
	public void setActorList(TableViewModel<ActorViewModel> model) {
		this.model = model;
		tblActors.setModel(model);
	}

	/** 
	 * Method Name: getSelectedActor
	 * Purpose: To get the user selected row of the list.
	 * 					Used in ActorController's deleteActor() & openUpdateActorForm().
	 * Accepts: Nothing.
	 * Return: The selected row's ActorViewModel.
	 */
	public ActorViewModel getSelectedActor() {
		if (tblActors.getSelectedRow() == -1) {
			JOptionPane.showMessageDialog(this, "Please select an actor.", "Selection Failed!",
					JOptionPane.WARNING_MESSAGE);
			return null;
		}
		return model.getAtRow(tblActors.getSelectedRow());
	}
}
