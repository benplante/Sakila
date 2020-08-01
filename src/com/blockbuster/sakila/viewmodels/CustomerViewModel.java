package com.blockbuster.sakila.viewmodels;

import com.blockbuster.sakila.database.ColumnName;

public class CustomerViewModel {
	private int addressId;

	private int cityId;

	private boolean isActive;

	@ColumnName(columnName = "Customer #")
	public int customerId;

	@ColumnName(columnName = "First Name")
	public String firstName;

	@ColumnName(columnName = "Last Name")
	public String lastName;

	@ColumnName(columnName = "Email")
	public String email;

	@ColumnName(columnName = "Is Active")
	public String isActiveStr = isActive ? "Yes" : "No";

	@ColumnName(columnName = "City")
	public String city;

	@ColumnName(columnName = "District")
	public String district;

	@ColumnName(columnName = "Country")
	public String country;

	@ColumnName(columnName = "Address Line 1")
	public String addressLine1;

	@ColumnName(columnName = "Post Code")
	public String postalCode;

	@ColumnName(columnName = "Phone #")
	public String phone;

	public int getAddressId() {
		return this.addressId;
	}

	public void setAddressId(int addressId) {
		this.addressId = addressId;
	}

	public int getCityId() {
		return cityId;
	}

	public void setCityId(int cityId) {
		this.cityId = cityId;
	}

	public boolean getIsActive() {
		return this.isActive;
	}

	public void setIsActive(boolean isActive) {
		this.isActive = isActive;
	}

	public CustomerViewModel() {
		this.customerId = -1;
	}
}
