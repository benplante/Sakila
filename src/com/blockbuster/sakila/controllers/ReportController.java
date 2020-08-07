package com.blockbuster.sakila.controllers;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

import com.blockbuster.sakila.database.SakilaDatabase;
import com.blockbuster.sakila.ui.ReportListView;
import com.blockbuster.sakila.ui.utils.TableViewModel;
import com.blockbuster.sakila.viewmodels.CategoryViewModel;
import com.blockbuster.sakila.viewmodels.CustomerViewModel;
import com.blockbuster.sakila.viewmodels.StoreViewModel;

/**
 * @author Saja Alhadeethi, Colin Manliclic, Dahye Min, Ben Plante
 *
 *         Report controller
 */


public class ReportController
{

	private SakilaDatabase db;
	private ReportListView reportListViewPanel;

	public ReportController(SakilaDatabase db) {
		this.db = db;

		reportListViewPanel = new ReportListView(this);
	}
	
	public void refreshDB() {
		reportListViewPanel.setStores(getStoresFromDB());
		reportListViewPanel.setCategories(getCategoriesFromDB());
		reportListViewPanel.setCustomers(getCustomersFromDB());
	}
	
	public JPanel getPanel() {
		return reportListViewPanel;
	}
	
	private List<StoreViewModel> getStoresFromDB() {
		try {
			return db.selectStores();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(reportListViewPanel,
					"Error loading Films",
					"Error loading Films", 
					JOptionPane.ERROR_MESSAGE);
			return new ArrayList<>();
		}
	}
	
	private List<CategoryViewModel> getCategoriesFromDB() {
		try {
			return db.selectCategories();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(reportListViewPanel,
					"Error loading Categories",
					"Error loading Categories", 
					JOptionPane.ERROR_MESSAGE);
			return new ArrayList<>();
		}
	}
	
	private List<CustomerViewModel> getCustomersFromDB() {
		try {
			return db.selectCustomers();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(reportListViewPanel,
					"Error loading Customers",
					"Error loading Customers", 
					JOptionPane.ERROR_MESSAGE);
			return new ArrayList<>();
		}
	}
	
	public void generateStoreReport() {
		var stores = reportListViewPanel.getSelectedStores();
		try {
			reportListViewPanel.setStoreReport(
					db.getSalesByStore(stores.stream().map(s -> s.getStoreId()).collect(Collectors.toList())));
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(reportListViewPanel, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
		}
	}

	public void generateCategoryReport() {
		var categories = reportListViewPanel.getSelectedCategories();
		try {
			reportListViewPanel.setCategoriesReport(
					db.getSalesByCategory(categories.stream().map(s -> s.getCategoryId()).collect(Collectors.toList())));
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(reportListViewPanel, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	public void generateCustomerReport() {
		var customers = reportListViewPanel.getSelectedCustomers();
		try {
			reportListViewPanel.setCustomerReport(db.getSalesByCustomer(
					customers.stream().map(c -> c.customerId).collect(Collectors.toList())));
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(reportListViewPanel, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	public void generateDateReport() {
		try {
			var startDate = new Timestamp(reportListViewPanel.getStartDate().getTime());
			var endDate = new Timestamp(reportListViewPanel.getEndDate().getTime());
			reportListViewPanel.setDateReport(db.getSalesByDate(startDate, endDate));
		} catch (ParseException e) {
			JOptionPane.showMessageDialog(reportListViewPanel, 
					"Dates must be formatted as YYYY-MM-DD", 
					"Date Format Error", 
					JOptionPane.ERROR_MESSAGE
				);
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(reportListViewPanel, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
		}
	}
}
