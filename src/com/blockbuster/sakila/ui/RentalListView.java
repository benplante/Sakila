package com.blockbuster.sakila.ui;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;

import com.blockbuster.sakila.controllers.RentalController;
import com.blockbuster.sakila.ui.utils.TableViewModel;
import com.blockbuster.sakila.viewmodels.RentalViewModel;

/**
 * @author Saja Alhadeethi, Colin Manliclic, Dahye Min, Ben Plante
 *
 *         Panel for viewing a list of all rentals in the database displays
 *         rentals in a JTable and contains buttons to add
 */
@SuppressWarnings("serial")
public class RentalListView extends JPanel {
	// RentalListView members
	private JTable rentalTable;
	private JButton btnAdd;
	private JButton btnDelete;
	private TableViewModel<RentalViewModel> model;

	/** 
	 * Method Name: RentalListView
	 * Purpose: RentalListView is a JPanel to show list of rentals and its attributes.
	 * Accepts: RentalController to handle for listener events.
	 * Return: A RentalListView object.
	 */
	public RentalListView(RentalController controller) {
		super();

		rentalTable = new JTable();
		JScrollPane scrollPane = new JScrollPane(rentalTable);

		btnAdd = new JButton("Add");
		btnAdd.addActionListener(e -> controller.openAddRentalForm());
		btnDelete = new JButton("Delete");
		btnDelete.addActionListener(e -> controller.deleteRental());


		this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));

		JPanel btnPanel = new JPanel();
		btnPanel.setLayout(new BoxLayout(btnPanel, BoxLayout.LINE_AXIS));
		btnPanel.add(btnAdd);
		btnPanel.add(Box.createHorizontalStrut(10));
		btnPanel.add(btnDelete);

		this.add(scrollPane);
		this.add(Box.createVerticalStrut(10));
		this.add(btnPanel);
		this.setBorder(new EmptyBorder(10, 10, 10, 10));
	}

	/** 
	 * Method Name: setRentalList
	 * Purpose: To populate the RentalListView.
	 * Accepts: A TableViewModel object of RentalViewModel objects. 
	 * Return: Nothing.
	 */
	public void setRentalList(TableViewModel<RentalViewModel> model) {
		this.model = model;
		rentalTable.setModel(model);
	}

	/** 
	 * Method Name: getSelectedRental
	 * Purpose: To get the user selected row of the list. 
	 * 					Used in RentalController's deleteRental().
	 * Accepts: Nothing.
	 * Return: The selected row's RentalViewModel.
	 */
	public RentalViewModel getSelectedRental() { 
		if (rentalTable.getSelectedRow() == -1) {
			JOptionPane.showMessageDialog(this, "Please select a rental.", "Selection Failed!",
					JOptionPane.WARNING_MESSAGE);
			return null;
		}
		return model.getAtRow(rentalTable.getSelectedRow());
	}
}
