package com.blockbuster.sakila.viewmodels;

import java.math.BigDecimal;

import com.blockbuster.sakila.ui.utils.ColumnName;

/**
 * @author Saja Alhadeethi, Colin Manliclic, Dahye Min, Ben Plante
 *
 * View Model for Date ranged report
 */
public class DateReportViewModel {
	@ColumnName(columnName = "# of Rentals")
	public int rentalsCount;
	
	@ColumnName(columnName = "Total Sales")
	public BigDecimal salesAmount;
	
	@ColumnName(columnName = "Top Customer")
	public String topCustomer;
	
	@ColumnName(columnName = "Top Category")
	public String topCategory;
	
	@ColumnName(columnName = "Top Film")
	public String topFilm;
}
