package com.blockbuster.sakila.viewmodels;

public class InventoryViewModel
{
	private int inventoryId; 
	
	private String filmTitle;
	
	private int filmRentalDuration;

	public int getInventoryId()
	{
		return inventoryId;
	}

	public void setInventoryId(int inventoryId)
	{
		this.inventoryId = inventoryId;
	}

	public String getFilmTitle()
	{
		return filmTitle;
	}

	public void setFilmTitle(String filmTitle)
	{
		this.filmTitle = filmTitle;
	}
	
	public int getFilmRentalDuration()
	{
		return filmRentalDuration;
	}

	public void setFilmRentalDuration(int filmRentalDuration)
	{
		this.filmRentalDuration = filmRentalDuration;
	}
	
	public String toString()
	{
		return this.filmTitle;
	}
	
}
