package com.blockbuster.sakila.viewmodels;

import com.blockbuster.sakila.database.ColumnName;

public class CustomerViewModel {
	@ColumnName(columnName = "Customer #")
	public int customerId;
	
	@ColumnName(columnName = "First Name")
	public String firstName;
	
	@ColumnName(columnName = "Last Name")
	public String lastName;
	
	@ColumnName(columnName = "Email")
	public String email;
	
	@ColumnName(columnName = "Is Active")
	public boolean isActive;
	
	@ColumnName(columnName = "City")
	public String city;
	
	@ColumnName(columnName = "Province")
	public String province;
	
	@ColumnName(columnName = "Country")
	public String country;
	
	@ColumnName(columnName = "Address Line 1")
	public String addressLine1;
	
	@ColumnName(columnName = "Address Line 2")
	public String addressLine2;
	
	@ColumnName(columnName = "Post Code")
	public String postalCode;
	
	@ColumnName(columnName = "Phone #")
	public String phone;
	
	private int cityId;

	public int getCityId() {
		return cityId;
	}
	
	public void setCityId(int cityId) {
		this.cityId = cityId;
	}
	
	 private int addressId;
       
	 public void setAddressId(int addressId) {
	      this.addressId = addressId;
	 }
	public CustomerViewModel() {
	  this.customerId = -1;
  }
}
