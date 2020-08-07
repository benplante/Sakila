package com.blockbuster.sakila.viewmodels;

import java.math.BigDecimal;

import com.blockbuster.sakila.ui.utils.ColumnName;

/**
 * @author Saja Alhadeethi, Colin Manliclic, Dahye Min, Ben Plante
 * 
 * View Model for Top Customers report
 */
public class CustomerReportViewModel {
	@ColumnName(columnName = "Customer #")
	public int customerId;
	
	@ColumnName(columnName = "Name")
	public String customerName;
	
	@ColumnName(columnName = "Total Sales")
	public BigDecimal salesAmount;	
}
