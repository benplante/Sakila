package com.blockbuster.sakila.database;

import java.sql.SQLException;
import java.util.List;
import com.blockbuster.sakila.viewmodels.*;

/**
 * @author Ben Plante
 *
 *         Database interface for inversion of control and unit testing
 * 
 *         Interacts with the Sakila database model
 */
public interface SakilaDatabase {
	// Actor
	public void insertActor(ActorViewModel actor) throws SQLException;

	public void updateActor(ActorViewModel actor) throws SQLException;

	public void deleteActor(ActorViewModel actor) throws SQLException;

	public List<ActorViewModel> selectActors() throws SQLException;

	// Customer
	public void insertCustomer(CustomerViewModel customer) throws SQLException;

	public void updateCustomer(CustomerViewModel customer) throws SQLException;

	public void deleteCustomer(CustomerViewModel customer) throws SQLException;

	public List<CustomerViewModel> selectCustomers() throws SQLException;
	
	public List<CityViewModel> selectCities() throws SQLException;
	
	public List<FilmViewModel> selectFilms() throws SQLException;
	
	// Film
	public void insertFilm(FilmViewModel film) throws SQLException;

  public List<CategoryViewModel> selectCategories() throws SQLException;
  
  // Rental
  public List<RentalViewModel> selectRentals() throws SQLException;
  
  public List<InventoryViewModel> selectInventories() throws SQLException;

	public void insertRental(RentalViewModel rental) throws SQLException;

	public void deleteRental(RentalViewModel rental) throws SQLException;
  
  
}
