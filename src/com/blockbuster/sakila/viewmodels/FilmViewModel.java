package com.blockbuster.sakila.viewmodels;

public class FilmViewModel {
	private int categoryId;
  
	public String title;
	public String description;
	public String categoryName;
	public String release_year;
	public int rental_duration;
	public double rental_rate;
	public double replacement_cost;
	public String rating;
    public int getCategoryId() {
      return categoryId;
    }

    public void setCategoryId(int categoryId) {
      this.categoryId = categoryId;
    }
}
