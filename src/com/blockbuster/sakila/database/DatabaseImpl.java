package com.blockbuster.sakila.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import com.blockbuster.sakila.viewmodels.ActorViewModel;

public class DatabaseImpl implements Database {
	private static final String CONNECTION_STRING = "jdbc:mysql://localhost:3306/sakila?useSSL=false&allowPublicKeyRetrieval=true";
	
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
		try (Connection conn = DriverManager.getConnection(CONNECTION_STRING, "sakila", "alikas")){
			PreparedStatement stmt = conn.prepareStatement("INSERT INTO actor(first_name, last_name) VALUES(?, ?)");
			stmt.setString(1, actor.getFirstName());
			stmt.setNString(2,  actor.getLastName());
			stmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println(e.toString());
		}
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
