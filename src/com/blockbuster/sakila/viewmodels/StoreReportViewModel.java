package com.blockbuster.sakila.viewmodels;

import java.math.BigDecimal;

import com.blockbuster.sakila.ui.utils.ColumnName;

/**
 * @author Saja Alhadeethi, Colin Manliclic, Dahye Min, Ben Plante
 *
 *         View Model for a Store Report.
 */

public class StoreReportViewModel {

	@ColumnName(columnName = "Store #")
	public int storeId;
	
	@ColumnName(columnName = "Location")
	public String storeLocation;
	
	@ColumnName(columnName = "Total Sales")
	public BigDecimal salesAmount;
}
