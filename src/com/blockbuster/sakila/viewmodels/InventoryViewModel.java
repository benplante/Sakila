package com.blockbuster.sakila.viewmodels;

import java.math.BigDecimal;

public class InventoryViewModel
{
	private int inventoryId; 
	
	private String filmTitle;
	
	private int filmRentalDuration;
	
	private BigDecimal rentalRate;

	public int getInventoryId() {
		return inventoryId;
	}

	public void setInventoryId(int inventoryId) {
		this.inventoryId = inventoryId;
	}

	public String getFilmTitle() {
		return filmTitle;
	}

	public void setFilmTitle(String filmTitle) {
		this.filmTitle = filmTitle;
	}
	
	public int getFilmRentalDuration() {
		return filmRentalDuration;
	}

	public void setFilmRentalDuration(int filmRentalDuration) {
		this.filmRentalDuration = filmRentalDuration;
	}
	
	public String toString() {
		return this.filmTitle;
	}

	public BigDecimal getRentalRate() {
		return rentalRate;
	}

	public void setRentalRate(BigDecimal rentalRate) {
		this.rentalRate = rentalRate;
	}
	
}
