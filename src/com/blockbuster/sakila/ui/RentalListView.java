package com.blockbuster.sakila.ui;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;

import com.blockbuster.sakila.controllers.RentalController;
import com.blockbuster.sakila.ui.utils.TableViewModel;
import com.blockbuster.sakila.viewmodels.CustomerViewModel;
import com.blockbuster.sakila.viewmodels.RentalViewModel;

public class RentalListView extends JPanel {
	private JTable rentalTable;
	private JButton btnAdd;
	private JButton btnDelete;

	private TableViewModel<RentalViewModel> model;

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

	public void setRentalList(TableViewModel<RentalViewModel> model) {
		this.model = model;
		rentalTable.setModel(model);
	}

	public RentalViewModel getSelectedRental() { 
		if (rentalTable.getSelectedRow() == -1) {
			JOptionPane.showMessageDialog(this, "Please select a rental.", "Selection Failed!",
					JOptionPane.WARNING_MESSAGE);
			return null;
		}
		return model.getAtRow(rentalTable.getSelectedRow());
	}
}
