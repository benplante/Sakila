package com.blockbuster.sakila.viewmodels;

import java.math.BigDecimal;

import com.blockbuster.sakila.ui.utils.ColumnName;

public class StoreReportViewModel {

	@ColumnName(columnName = "Store #")
	public int storeId;
	
	@ColumnName(columnName = "Location")
	public String storeLocation;
	
	@ColumnName(columnName = "Total Sales")
	public BigDecimal salesAmount;
}
