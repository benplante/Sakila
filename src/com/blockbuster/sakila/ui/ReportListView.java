package com.blockbuster.sakila.ui;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Dimension;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import com.blockbuster.sakila.controllers.ReportController;
import com.blockbuster.sakila.ui.utils.ComboCheckBox;
import com.blockbuster.sakila.ui.utils.ComboCheckBoxModel;
import com.blockbuster.sakila.ui.utils.TableViewModel;
import com.blockbuster.sakila.viewmodels.CategoryReportViewModel;
import com.blockbuster.sakila.viewmodels.CategoryViewModel;
import com.blockbuster.sakila.viewmodels.CityViewModel;
import com.blockbuster.sakila.viewmodels.CustomerViewModel;
import com.blockbuster.sakila.viewmodels.DateReportViewModel;
import com.blockbuster.sakila.viewmodels.CustomerReportViewModel;
import com.blockbuster.sakila.viewmodels.StoreReportViewModel;
import com.blockbuster.sakila.viewmodels.StoreViewModel;

/**
 * @author Saja Alhadeethi, Colin Manliclic, Dahye Min, Ben Plante
 *     
 * Table view for displaying reports  
 */


public class ReportListView extends JPanel {
	private enum ReportType {
		BY_STORE("Rental Income by Store"), 
		BY_CATEOGRY("Rental Income by Category"),
		TOP_CUSTOMERS("Top Customers"),
		TO_DATE("Rental Income to Date");

		
		private final String name;
		ReportType(String name) {
			this.name = name;
		}
		
		@Override
		public String toString() {
			return name;
		}
	}
	
	private JTable reportTable;
	private JPanel parametersCard;
	private JComboBox<ReportType> cmbReportType;
	private DefaultComboBoxModel<ReportType> reportTypes;
	
	private ComboCheckBoxModel<StoreViewModel> storeModel;
	private ComboCheckBoxModel<CategoryViewModel> categoriesModel;
	private ComboCheckBoxModel<CustomerViewModel> customersModel;
	
	private JTextField txtStartDate, txtEndDate;
	
	public ReportListView(ReportController controller) {
		super();

		reportTypes = new DefaultComboBoxModel<>(ReportType.values());
		cmbReportType = new JComboBox<>(reportTypes);
		
		storeModel = new ComboCheckBoxModel<>();
		categoriesModel = new ComboCheckBoxModel<>();
		customersModel = new ComboCheckBoxModel<>();
		
		txtStartDate = new JTextField(12);
		txtStartDate.setToolTipText("Date as YYYY-MM-DD");
		txtEndDate = new JTextField(12);
		txtEndDate.setToolTipText("Date as YYYY-MM-DD");
		
		reportTable = new JTable();
		JScrollPane scrollPane = new JScrollPane(reportTable);
						
		CardLayout layout = new CardLayout();
		
		parametersCard = new JPanel();
		parametersCard.setLayout(layout);
		
		JPanel byStorePanel = new JPanel();
		byStorePanel.add(new ComboCheckBox<StoreViewModel>(storeModel, "Stores"));
		JButton btn1 = new JButton("Generate");
		btn1.addActionListener(e -> controller.generateStoreReport());
		byStorePanel.add(btn1);
		
		JPanel byCategoryPanel = new JPanel();
		byCategoryPanel.add(new ComboCheckBox<CategoryViewModel>(categoriesModel, "Categories"));
		JButton btn2 = new JButton("Generate");
		btn2.addActionListener(e -> controller.generateCategoryReport());
		byCategoryPanel.add(btn2);
		
		JPanel topCustomersPanel = new JPanel();
		topCustomersPanel.add(new ComboCheckBox<CustomerViewModel>(customersModel, "Customers"));
		JButton btn3 = new JButton("Generate");
		btn3.addActionListener(e -> controller.generateCustomerReport());
		topCustomersPanel.add(btn3);
		
		JPanel toDatePanel = new JPanel();
		JPanel startDate = new JPanel();
		startDate.setLayout(new BoxLayout(startDate, BoxLayout.PAGE_AXIS));
		startDate.add(new JLabel("Start Date:"));
		startDate.add(txtStartDate);
		
		JPanel endDate = new JPanel();
		endDate.setLayout(new BoxLayout(endDate, BoxLayout.PAGE_AXIS));
		endDate.add(new JLabel("End Date:"));
		endDate.add(txtEndDate);
		
		toDatePanel.add(startDate);
		toDatePanel.add(endDate);
		JButton btn4 = new JButton("Generate");
		btn4.addActionListener(e -> controller.generateDateReport());
		toDatePanel.add(btn4);
		
		parametersCard.add(byStorePanel, ReportType.BY_STORE.toString());
		parametersCard.add(byCategoryPanel, ReportType.BY_CATEOGRY.toString());
		parametersCard.add(topCustomersPanel, ReportType.TOP_CUSTOMERS.toString());
		parametersCard.add(toDatePanel, ReportType.TO_DATE.toString());
		
		cmbReportType.addItemListener(i -> {
			layout.show(parametersCard, i.getItem().toString());
		});

		this.setLayout(new BorderLayout(5, 5));
		this.add(cmbReportType, BorderLayout.NORTH);
		this.add(scrollPane, BorderLayout.CENTER);
		this.add(parametersCard, BorderLayout.SOUTH);
		this.setBorder(new EmptyBorder(10, 10, 10, 10));
	}
	
	public void setSalesReport(List<StoreReportViewModel> report) {
		reportTable.setModel(new TableViewModel<>(report, StoreReportViewModel.class));
	}
	
	public void setCategoriesReport(List<CategoryReportViewModel> report) {
		reportTable.setModel(new TableViewModel<>(report, CategoryReportViewModel.class));
	}
	
	public void setCustomerReport(List<CustomerReportViewModel> report) {
		reportTable.setModel(new TableViewModel<>(report, CustomerReportViewModel.class));
	}
	
	public void setDateReport(List<DateReportViewModel> report) {
		reportTable.setModel(new TableViewModel<>(report, DateReportViewModel.class));
	}
	
	public void setStores(List<StoreViewModel> stores) {
		storeModel.setItems(stores);
	}
	
	public List<StoreViewModel> getSelectedStores() {
		return storeModel.getAllSelected();
	}
	
	public void setCategories(List<CategoryViewModel> categories) {
		categoriesModel.setItems(categories);
	}
	
	public List<CategoryViewModel> getSelectedCategories() {
		return categoriesModel.getAllSelected();
	}
	
	public void setCustomers(List<CustomerViewModel> customers) {
		customersModel.setItems(customers);
	}
	
	public List<CustomerViewModel> getSelectedCustomers() {
		return customersModel.getAllSelected();
	}
	
	public Date getStartDate() throws ParseException {
		return new SimpleDateFormat("yyyy-MM-dd").parse(txtStartDate.getText());
	}
	
	public Date getEndDate() throws ParseException {
		return new SimpleDateFormat("yyyy-MM-dd").parse(txtEndDate.getText());
	}
}
