package com.blockbuster.sakila.viewmodels;

public class StoreViewModel {
	private int storeId;
	private String city;
	private String country;
	
	public int getStoreId() {
		return storeId;
	}
	
	public void setStoreId(int storeId) {
		this.storeId = storeId;
	}
	
	public String getCity() {
		return this.city;
	}
	
	public void setCity(String city) {
		this.city = city;
	}
	
	public String getCountry() {
		return this.country;
	}
	
	public void setCountry(String country) {
		this.country = country;
	}
	
	@Override
	public String toString() {
		return storeId + " - " + city + ", " + country;
	}
}
