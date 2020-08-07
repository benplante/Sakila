package com.blockbuster.sakila.viewmodels;

import java.math.BigDecimal;

import com.blockbuster.sakila.ui.utils.ColumnName;

public class CategoryReportViewModel {
	
	@ColumnName(columnName = "Category #")
	public int categoryId;
	
	@ColumnName(columnName = "Name")
	public String categoryName;
	
	@ColumnName(columnName = "Total Sales")
	public BigDecimal salesAmount;
}
