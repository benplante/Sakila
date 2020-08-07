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
	// ReportController members
	private SakilaDatabase db;
	private ReportListView reportListViewPanel;

	/**
	 * Method Name: ReportController
	 * Purpose: ReportController is instantiated in SuperController with a Singleton instance of MySqlSakilaDatabase.
	 * 					RentalListView is instantiated and passed this controller for listener events.
	 * Accepts: A SakilaDatabase object.
	 * Returns: A RentalController object.
	 */
	public ReportController(SakilaDatabase db) {
		this.db = db;
		reportListViewPanel = new ReportListView(this);
	}
	
	/**
	 *  Method Name: refreshDB
	 *  Purpose: Refreshes ReportListView data on every tab change.
	 *  				 Is essential if data from other tabs is deleted.
	 *  Accepts: Nothing.
	 *  Returns: Nothing.
	 */
	public void refreshDB() {
		reportListViewPanel.setStores(getStoresFromDB());
		reportListViewPanel.setCategories(getCategoriesFromDB());
		reportListViewPanel.setCustomers(getCustomersFromDB());
	}
	
	/** 
	 * Method Name: getPanel
	 * Purpose: Gets ReportController's ReportListView.
	 * 					Is used when adding a tab to the SuperController.
	 * Accepts: Nothing.
	 * Return: A RentalListView object.
	 */
	public JPanel getPanel() {
		return reportListViewPanel;
	}
	
	/** 
	 * Method Name: getStoresFromDB
	 * Purpose: Gets the stores from the database.
	 * 					Used to refresh ReportListView and used in ReportListView's setStores.
	 * Accepts: Nothing.
	 * Return: A list of StoreViewModel objects.
	 */
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
	
	/** 
	 * Method Name: getCategoriesFromDB
	 * Purpose: Gets the categories from the database.
	 * 					Used to refresh ReportListView and used in ReportListView's setCategories.
	 * Accepts: Nothing.
	 * Return: A list of CategoryViewModel objects.
	 */
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
	
	/** 
	 * Method Name: getCustomersFromDB
	 * Purpose: Gets the customers from the database.
	 * 					Used to refresh ReportListView and used in ReportListView's setCustomer.
	 * Accepts: Nothing.
	 * Return: A list of CustomerViewModel objects.
	 */
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
	
	/** 
	 * Method Name: generateStoreReport
	 * Purpose: Actions to be performed when user selects 'Generate' on ReportListView
	 * 					when 'Rental Income by Store' is selected
	 * Accepts: Nothing.
	 * Return: Nothing.
	 */
	public void generateStoreReport() {
		var stores = reportListViewPanel.getSelectedStores();
		try {
			reportListViewPanel.setStoreReport(
					db.getSalesByStore(stores.stream().map(s -> s.getStoreId()).collect(Collectors.toList())));
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(reportListViewPanel, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
		}
	}

	/** 
	 * Method Name: generateCategoryReport
	 * Purpose: Actions to be performed when user selects 'Generate' on ReportListView
	 * 					when 'Rental Income by Category' is selected
	 * Accepts: Nothing.
	 * Return: Nothing.
	 */
	public void generateCategoryReport() {
		var categories = reportListViewPanel.getSelectedCategories();
		try {
			reportListViewPanel.setCategoriesReport(
					db.getSalesByCategory(categories.stream().map(s -> s.getCategoryId()).collect(Collectors.toList())));
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(reportListViewPanel, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	/** 
	 * Method Name: generateCustomerReport
	 * Purpose: Actions to be performed when user selects 'Generate' on ReportListView
	 * 					when 'Top Customers' is selected
	 * Accepts: Nothing.
	 * Return: Nothing.
	 */
	public void generateCustomerReport() {
		var customers = reportListViewPanel.getSelectedCustomers();
		try {
			reportListViewPanel.setCustomerReport(db.getSalesByCustomer(
					customers.stream().map(c -> c.customerId).collect(Collectors.toList())));
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(reportListViewPanel, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	/** 
	 * Method Name: generateDateReport
	 * Purpose: Actions to be performed when user selects 'Generate' on ReportListView
	 * 					when 'Rental Income to Date' is selected
	 * Accepts: Nothing.
	 * Return: Nothing.
	 */
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
