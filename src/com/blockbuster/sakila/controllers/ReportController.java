package com.blockbuster.sakila.controllers;

import java.sql.SQLException;
import java.util.stream.Collectors;

import javax.swing.JPanel;

import com.blockbuster.sakila.database.SakilaDatabase;
import com.blockbuster.sakila.ui.ReportListView;
import com.blockbuster.sakila.ui.utils.TableViewModel;

/**
 * @author Saja Alhadeethi, Colin Manliclic, Dahye Min, Ben Plante
 *
 *         Report controller. Interacts with ReportForm, ReportListView and SakilaDatabase.
 */


public class ReportController
{

	private SakilaDatabase db;
	private ReportListView reportListViewPanel;

	public ReportController(SakilaDatabase db) {
		this.db = db;

		reportListViewPanel = new ReportListView(this);
		try {
			reportListViewPanel.setStores(db.selectStores());
			reportListViewPanel.setCategories(db.selectCategories());
			reportListViewPanel.setCustomers(db.selectCustomers());
		} catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	public JPanel getPanel() {
		return reportListViewPanel;
	}
	
	public void generateStoreReport() {
		var stores = reportListViewPanel.getSelectedStores();
		try {
			reportListViewPanel.setSalesReport(
					db.getSalesByStore(stores.stream().map(s -> s.getStoreId()).collect(Collectors.toList())));
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void generateCategoryReport() {
		var categories = reportListViewPanel.getSelectedCategories();
		try {
			reportListViewPanel.setCategoriesReport(
					db.getSalesByCategory(categories.stream().map(s -> s.getCategoryId()).collect(Collectors.toList())));
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void generateCustomerReport() {
		var customers = reportListViewPanel.getSelectedCustomers();
		try {
			reportListViewPanel.setCustomerReport(db.getSalesByCustomer(
					customers.stream().map(c -> c.customerId).collect(Collectors.toList())));
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
