package com.blockbuster.sakila.controllers;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

import com.blockbuster.sakila.database.SakilaDatabase;
import com.blockbuster.sakila.database.TableViewModel;
import com.blockbuster.sakila.ui.RentalForm;
import com.blockbuster.sakila.ui.RentalListView;
import com.blockbuster.sakila.viewmodels.RentalViewModel;

public class RentalController
{

	private SakilaDatabase db;
	private RentalListView rentalListViewPanel;
	private RentalForm rentalFormFrame;

	private TableViewModel<RentalViewModel> model;

	public RentalController(SakilaDatabase db) {
		this.db = db;

		rentalListViewPanel = new RentalListView(this);
		rentalFormFrame = new RentalForm(this);
		model = new TableViewModel<>(getRentalsFromDB(), RentalViewModel.class);
		rentalListViewPanel.setRentalList(model);
	}
	
	private List<RentalViewModel> getRentalsFromDB() {
		try {
			return db.selectRentals();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(rentalListViewPanel, 
					"Error loading rentals: " + e.getMessage(), 
					"Error loading rentals", JOptionPane.ERROR_MESSAGE);
			return new ArrayList<>();
		}
	}

	public JPanel getPanel() {
		return rentalListViewPanel;
	}
	
}
