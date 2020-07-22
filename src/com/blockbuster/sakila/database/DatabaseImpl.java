package com.blockbuster.sakila.database;

import java.util.List;

import com.blockbuster.sakila.viewmodels.ActorViewModel;

public class DatabaseImpl implements Database {
	
	private static Database instance;
	
	public DatabaseImpl() {
		
	}
	
	public static Database getInstance() {
		if (instance == null) {
			instance = new DatabaseImpl();
		}
		return instance;
	}

	@Override
	public void insertActor(ActorViewModel actor) {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateActor(ActorViewModel actor) {
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteActor(ActorViewModel actor) {
		// TODO Auto-generated method stub

	}

	@Override
	public List<ActorViewModel> selectActors() {
		// TODO Auto-generated method stub
		return null;
	}

}
