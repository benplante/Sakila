package com.blockbuster.sakila.viewmodels;

import com.blockbuster.sakila.ui.utils.ColumnName;

public class FilmViewModel {

	private int categoryId;
	private int actorId;
	private int languageId;
	
	@ColumnName(columnName = "Film #")
  public int filmId;
	@ColumnName(columnName = "Title")
	public String title;
	@ColumnName(columnName = "Description")
	public String description;
	@ColumnName(columnName = "Category Name")
	public String categoryName;
	@ColumnName(columnName = "Release year")
	public int releaseYear;
	@ColumnName(columnName = "Rental Duration")
	public int rentalDuration;
	@ColumnName(columnName = "Rental Rate")
	public double rentalRate;
	@ColumnName(columnName = "Length")
  public int length;
  @ColumnName(columnName = "Replacement Cost")
	public double replacementCost;
	@ColumnName(columnName = "Rating")
	public String rating;
	@ColumnName(columnName = "Special Features")
  public String specialFeatures;
	
	public int getFilmId() {
		return filmId;
	}

	public void setFilmId(int filmId) {
		this.filmId = filmId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public int getReleaseYear() {
		return releaseYear;
	}

	public void setReleaseYear(int releaseYear) {
		this.releaseYear = releaseYear;
	}

	public int getRentalDuration() {
		return rentalDuration;
	}

	public void setRentalDuration(int rentalDuration) {
		this.rentalDuration = rentalDuration;
	}

	public double getRentalRate() {
		return rentalRate;
	}

	public void setRentalRate(double rentalRate) {
		this.rentalRate = rentalRate;
	}

	public double getReplacementCost() {
		return replacementCost;
	}

	public void setReplacementCost(double replacementCost) {
		this.replacementCost = replacementCost;
	}

	public String getRating() {
		return rating;
	}

	public void setRating(String rating) {
		this.rating = rating;
	}

  public int getCategoryId() {
      return categoryId;
  }
  
  public int getLength() {
  	return length;
  }

  public void setLength(int length) {
    this.length = length;
  }

  public String getSpecialFeatures() {
    return specialFeatures;
  }

  public void setSpecialFeatures(String specialFeatures) {
    this.specialFeatures = specialFeatures;
  }

  public void setCategoryId(int categoryId) {
  	this.categoryId = categoryId;
  }
  
	public int getActorId() {
		return actorId;
	}

	public void setActorId(int actorId) {
		this.actorId = actorId;
	}
	
	public void setLanguageId(int languageId) {
	  this.languageId = languageId;
	}
	
	public int getLanguageId() {
	  return  languageId;
	}
	public String toString() {
		return this.title;
	}
}
