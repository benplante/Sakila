package com.blockbuster.sakila.ui;

import java.awt.Dimension;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;

import com.blockbuster.sakila.controllers.ActorController;
import com.blockbuster.sakila.database.TableViewModel;
import com.blockbuster.sakila.viewmodels.ActorViewModel;

/**
 * @author Ben Plante
 *
 *         Panel for viewing a list of all Actors in the database Displays
 *         actors in a JTable and contains buttons to add, update and delete an
 *         actor
 */
public class ActorListView extends JPanel {
	private JTable tblActors;
	private JButton btnAdd;
	private JButton btnUpdate;
	private JButton btnDelete;

	private TableViewModel<ActorViewModel> model;

	public ActorListView(ActorController controller) {
		super();

		tblActors = new JTable();
		tblActors.setMaximumSize(new Dimension(500, 500));
		JScrollPane scrollPane = new JScrollPane(tblActors);

		btnAdd = new JButton("Add");
		btnAdd.addActionListener(e -> controller.openAddActorForm());
		btnUpdate = new JButton("Update");
		btnUpdate.addActionListener(e -> controller.openUpdateActorForm());
		btnDelete = new JButton("Delete");
		btnDelete.addActionListener(e -> controller.deleteActor());

		this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));

		JPanel btnPanel = new JPanel();
		btnPanel.setLayout(new BoxLayout(btnPanel, BoxLayout.LINE_AXIS));
		btnPanel.add(btnAdd);
		btnPanel.add(Box.createHorizontalStrut(10));
		btnPanel.add(btnUpdate);
		btnPanel.add(Box.createHorizontalStrut(10));
		btnPanel.add(btnDelete);

		this.add(scrollPane);
		this.add(Box.createVerticalStrut(10));
		this.add(btnPanel);
		this.setBorder(new EmptyBorder(10, 10, 10, 10));
	}

	public void setActorList(TableViewModel<ActorViewModel> model) {
		this.model = model;
		tblActors.setModel(model);
	}

	public ActorViewModel getSelectedActor() {
		return model.getAtRow(tblActors.getSelectedRow());
	}
}
