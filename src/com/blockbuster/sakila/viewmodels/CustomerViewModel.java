package com.blockbuster.sakila.viewmodels;

import com.blockbuster.sakila.ui.utils.ColumnName;

/**
 * @author Saja Alhadeethi, Colin Manliclic, Dahye Min, Ben Plante
 *
 *         View Model for a Customer.
 */

public class CustomerViewModel {
	private int addressId;

	private int cityId;
	
	private int storeId;

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
	public String isActiveStr;

	@ColumnName(columnName = "City")
	public String city;

	@ColumnName(columnName = "District")
	public String district;

	@ColumnName(columnName = "Country")
	public String country;

	@ColumnName(columnName = "Address")
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
		this.isActiveStr = this.isActive ? "Yes" : "No";
	}
	
	public CustomerViewModel() {
		this.customerId = -1;
	}
	
	public String toString() {
		return this.firstName + " " +  this.lastName;
	}

	public int getStoreId() {
		return storeId;
	}

	public void setStoreId(int storeId) {
		this.storeId = storeId;
	}
}
