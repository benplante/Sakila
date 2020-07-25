package com.blockbuster.sakila.viewmodels;

import com.blockbuster.sakila.database.ColumnName;

public class ActorViewModel {
	@ColumnName(columnName = "Actor #")
	public int actorId;
	
	@ColumnName(columnName = "First Name")
	public String firstName;
	
	@ColumnName(columnName = "Last Name")
	public String lastName;
	
	public ActorViewModel() {
		actorId = -1;
	}
}
