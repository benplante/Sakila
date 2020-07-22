package com.blockbuster.sakila.database;

import java.util.List;
import com.blockbuster.sakila.viewmodels.*;

public interface Database {	
	public void insertActor(ActorViewModel actor);
	public void updateActor(ActorViewModel actor);
	public void deleteActor(ActorViewModel actor);
	public List<ActorViewModel> selectActors();
}
