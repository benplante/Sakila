package com.blockbuster.sakila.viewmodels;

import java.math.BigDecimal;
import java.sql.Timestamp;

import com.blockbuster.sakila.database.ColumnName;

public class RentalViewModel {
	private int inventoryId;
	
	private int customerId;
	
	private int staffId;
	
	private int paymentId;
	
	@ColumnName(columnName = "Rental #")
	public int rentalId;
	
	@ColumnName(columnName = "Store")
	public String storeAddress;
	
	@ColumnName(columnName = "Title")
	public String filmTitle;
	
	@ColumnName(columnName = "Customer First Name")
	public String customerFirstName;
	
	@ColumnName(columnName = "Customer Last Name")
	public String customerLastName;
	
	@ColumnName(columnName = "Amount Paid")
	public BigDecimal paymentAmount;
	
	@ColumnName(columnName = "Price")
	public BigDecimal rentalRate;
	
	@ColumnName(columnName = "Staff First Name")
	public String staffFirstName;
	
	@ColumnName(columnName = "Staff Last Name")
	public String staffLastName;
	
	@ColumnName(columnName = "Rental Date")
	public Timestamp rentalDate;

	@ColumnName(columnName = "Return Date")
	public Timestamp returnDate;
	

	public int getInventoryId() {
		return this.inventoryId;
	}

	public void setIventoryId(int inventoryId) {
		this.inventoryId = inventoryId;
	}

	public int getCustomerId() {
		return customerId;
	}

	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}

	public int getStaffId() {
		return staffId;
	}

	public void setStaffId(int staffId) {
		this.staffId = staffId;
	}
	
	public int getPaymentId() {
		return paymentId;
	}

	public void setPaymentId(int paymentId) {
		this.paymentId = paymentId;
	}
	
	
	public RentalViewModel() {
		this.rentalId = -1;
	}
}
