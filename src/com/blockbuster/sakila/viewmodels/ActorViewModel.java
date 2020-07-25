package com.blockbuster.sakila.viewmodels;

import com.blockbuster.sakila.database.ColumnName;

/**
 * @author Ben Plante
 *
 * View Model for an Actor.
 * All fields are public and annotated with a column name.
 */
public class ActorViewModel {
	@ColumnName(columnName = "Actor #")
	public int actorId;
	
	@ColumnName(columnName = "First Name")
	public String firstName;
	
	@ColumnName(columnName = "Last Name")
	public String lastName;
	
	// new() sets Id to -1 for the controller to know if this is an
	// actor to be added or updated
	public ActorViewModel() {
		actorId = -1;
	}
}
