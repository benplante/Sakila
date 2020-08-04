package com.blockbuster.sakila.viewmodels;

public class InventoryViewModel
{
	private int inventoryId; 
	
	private String filmTitle;

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
	
	public String toString()
	{
		return this.filmTitle;
	}
	
}
