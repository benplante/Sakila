package com.blockbuster.sakila.viewmodels;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

import com.blockbuster.sakila.database.ColumnName;

public class RentalViewModel {
	private int inventoryId;
	
	private int customerId;
	
	private int staffId;
	
	private int paymentId;
	
	private Timestamp rentalDate;
	
	private Timestamp returnDate;
	
	private BigDecimal paymentAmount;
	
	private BigDecimal rentalRate;
	
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
	public String paymentAmountStr;
	
	@ColumnName(columnName = "Price")
	public String rentalRateStr;
	
	@ColumnName(columnName = "Staff First Name")
	public String staffFirstName;
	
	@ColumnName(columnName = "Staff Last Name")
	public String staffLastName;
	
	@ColumnName(columnName = "Rental Date")
	public String rentalDateStr;
	
	@ColumnName(columnName = "Return Date")
	public String returnDateStr;
	

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
	
	public Timestamp getRentalDate()
	{
		return rentalDate;
	}

	public void setRentalDate(Timestamp rentalDate) 
	{
		this.rentalDate = rentalDate;
		this.rentalDateStr = rentalDate != null ? new SimpleDateFormat("MMM dd, ''yy").format(rentalDate) : "N/A";
	}

	public Timestamp getReturnDate()
	{
		return returnDate;
	}

	public void setReturnDate(Timestamp returnDate)
	{
		this.returnDate = returnDate;
		this.returnDateStr = returnDate != null ? new SimpleDateFormat("MMM dd, ''yy").format(returnDate) : "N/A";
	}

	public BigDecimal getPaymentAmount()
	{
		return paymentAmount;
	}

	public void setPaymentAmount(BigDecimal paymentAmount)
	{
		this.paymentAmount = paymentAmount;
		this.paymentAmountStr = "$" + paymentAmount.toString();
	}

	public BigDecimal getRentalRate()
	{
		return rentalRate;
	}

	public void setRentalRate(BigDecimal rentalRate)
	{
		this.rentalRate = rentalRate;
		this.rentalRateStr = "$" + rentalRate.toString();
	}
	
	
	public RentalViewModel() {
		this.rentalId = -1;
	}
}
