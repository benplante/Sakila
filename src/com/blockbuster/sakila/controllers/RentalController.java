package com.blockbuster.sakila.controllers;

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
	}

	public JPanel getPanel() {
		return rentalListViewPanel;
	}
}
