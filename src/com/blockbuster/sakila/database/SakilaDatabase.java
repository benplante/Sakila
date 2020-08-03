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
	public void insertActor(ActorViewModel actor) throws SQLException;

	public void updateActor(ActorViewModel actor) throws SQLException;

	public void deleteActor(ActorViewModel actor) throws SQLException;

	public List<ActorViewModel> selectActors() throws SQLException;

	public void insertCustomer(CustomerViewModel customer) throws SQLException;

	public void updateCustomer(CustomerViewModel customer) throws SQLException;

	public void deleteCustomer(CustomerViewModel customer) throws SQLException;

	public List<CustomerViewModel> selectCustomers() throws SQLException;
	
	public List<CityViewModel> selectCities() throws SQLException;
	
	public void insertFilm(FilmViewModel film) throws SQLException;

    public List<CategoryViewModel> selectCategories() throws SQLException;
}
