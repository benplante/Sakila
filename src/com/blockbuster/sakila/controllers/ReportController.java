package com.blockbuster.sakila.controllers;

import java.sql.SQLException;

import javax.swing.JPanel;

import com.blockbuster.sakila.database.SakilaDatabase;
import com.blockbuster.sakila.ui.ReportForm;
import com.blockbuster.sakila.ui.ReportListView;
import com.blockbuster.sakila.ui.utils.TableViewModel;
import com.blockbuster.sakila.viewmodels.ReportViewModel;

public class ReportController
{

	private SakilaDatabase db;
	private ReportListView reportListViewPanel;
	private ReportForm reportFormFrame;

	private TableViewModel<ReportViewModel> model;

	public ReportController(SakilaDatabase db) {
		this.db = db;

		reportListViewPanel = new ReportListView(this);
		reportFormFrame = new ReportForm(this);
		try {
			reportListViewPanel.setCities(db.selectCities());
		} catch(SQLException e) {
			e.printStackTrace();
		}
	}

	public JPanel getPanel() {
		return reportListViewPanel;
	}
}
