package com.blockbuster.sakila.database;

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
	public void insertActor(ActorViewModel actor);

	public void updateActor(ActorViewModel actor);

	public void deleteActor(ActorViewModel actor);

	public List<ActorViewModel> selectActors();

	public void insertCustomer(CustomerViewModel customer);

	public void updateCustomer(CustomerViewModel customer);

	public void deleteCustomer(CustomerViewModel customer);

	public List<CustomerViewModel> selectCustomers();
}
