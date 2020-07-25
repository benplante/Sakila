package com.blockbuster.sakila.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
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
			stmt.setString(1, actor.firstName);
			stmt.setString(2,  actor.lastName);
			stmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println(e.toString());
		}
	}

	@Override
	public void updateActor(ActorViewModel actor) {
		try (Connection conn = DriverManager.getConnection(CONNECTION_STRING, "sakila", "alikas")){
			PreparedStatement stmt = conn.prepareStatement("UPDATE actor SET first_name = ?, last_name = ? WHERE actor_id = ?");
			stmt.setString(1, actor.firstName);
			stmt.setString(2,  actor.lastName);
			stmt.setInt(3, actor.actorId);
			stmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println(e.toString());
		}
	}

	@Override
	public void deleteActor(ActorViewModel actor) {
		try (Connection conn = DriverManager.getConnection(CONNECTION_STRING, "sakila", "alikas")){
			PreparedStatement stmt = conn.prepareStatement("DELETE FROM actor WHERE actor_id = ?");
			stmt.setInt(1, actor.actorId);
			stmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println(e.toString());
		}
	}

	@Override
	public List<ActorViewModel> selectActors() {
		ArrayList<ActorViewModel> li = new ArrayList<>();
		try (Connection conn = DriverManager.getConnection(CONNECTION_STRING, "sakila", "alikas")){
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT actor_id, first_name, last_name FROM actor ORDER BY actor_id");
			
			while (rs.next()) {
				ActorViewModel vm = new ActorViewModel();
				vm.actorId = rs.getInt(1);
				vm.firstName = rs.getString(2);
				vm.lastName = rs.getString(3);
				li.add(vm);
			}
			return li;
		} catch (SQLException e) {
			System.out.println(e.toString());
		}
		return li;
	}
	
	
}
