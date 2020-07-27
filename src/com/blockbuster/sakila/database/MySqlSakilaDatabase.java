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
import com.blockbuster.sakila.viewmodels.CustomerViewModel;

/**
 * @author Ben Plante
 *
 *         Sakila Database implentation using JDBC and MySql. Uses the singleton
 *         pattern to avoid instancing problems
 */
public class MySqlSakilaDatabase implements SakilaDatabase {
	private static final String CONNECTION_STRING = "jdbc:mysql://localhost:3306/sakila?useSSL=false&allowPublicKeyRetrieval=true";

	private static SakilaDatabase instance;

	public MySqlSakilaDatabase() {

	}

	public static SakilaDatabase getInstance() {
		if (instance == null) {
			instance = new MySqlSakilaDatabase();
		}
		return instance;
	}

	@Override
	public void insertActor(ActorViewModel actor) {
		try (Connection conn = DriverManager.getConnection(CONNECTION_STRING, "root", "password")) {
			PreparedStatement stmt = conn.prepareStatement("INSERT INTO actor(first_name, last_name) VALUES(?, ?)");
			stmt.setString(1, actor.firstName);
			stmt.setString(2, actor.lastName);
			stmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println(e.toString());
		}
	}

	@Override
	public void updateActor(ActorViewModel actor) {
		try (Connection conn = DriverManager.getConnection(CONNECTION_STRING, "root", "password")) {
			PreparedStatement stmt = conn
					.prepareStatement("UPDATE actor SET first_name = ?, last_name = ? WHERE actor_id = ?");
			stmt.setString(1, actor.firstName);
			stmt.setString(2, actor.lastName);
			stmt.setInt(3, actor.actorId);
			stmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println(e.toString());
		}
	}

	@Override
	public void deleteActor(ActorViewModel actor) {
		try (Connection conn = DriverManager.getConnection(CONNECTION_STRING, "root", "password")) {
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
		try (Connection conn = DriverManager.getConnection(CONNECTION_STRING, "root", "password")) {
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT actor_id, first_name, last_name FROM actor ORDER BY actor_id");

			while (rs.next()) {
				ActorViewModel vm = new ActorViewModel();
				vm.actorId = rs.getInt(1);
				vm.firstName = rs.getString(2);
				vm.lastName = rs.getString(3);
				li.add(vm);
			}
			rs.close();
			return li;
		} catch (SQLException e) {
			System.out.println(e.toString());
		}
		return li;
	}

	@Override
	public void insertCustomer(CustomerViewModel customer) {
		// insert into customer(store_id,first_name,last_name,email,address_id)
		// values(1,"Maroon","Adam","adam@yahoo.com",9);
		try (Connection conn = DriverManager.getConnection(CONNECTION_STRING, "root", "password")) {
			PreparedStatement stmt = conn
					.prepareStatement("INSERT INTO customer(store_id,first_name,last_name,email) VALUES(?,?,?,?)");
			stmt.setString(1, "1");
			stmt.setString(2, customer.firstName);
			stmt.setString(3, customer.lastName);
			stmt.setString(4, customer.email);
			// stmt.setString(5, customer.setAddressId(9));
			stmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println(e.toString());
		}
	}

	@Override
	public void updateCustomer(CustomerViewModel customer) {
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteCustomer(CustomerViewModel customer) {
		// TODO Auto-generated method stub

	}

	@Override
	public List<CustomerViewModel> selectCustomers() {
		ArrayList<CustomerViewModel> li = new ArrayList<>();
		try (Connection conn = DriverManager.getConnection(CONNECTION_STRING, "root", "password")) {
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(
					"SELECT customer_id, first_name, last_name,email,address_id FROM customer ORDER BY customer_id");

			while (rs.next()) {
				CustomerViewModel vm = new CustomerViewModel();
				vm.customerId = rs.getInt(1);
				vm.firstName = rs.getString(2);
				vm.lastName = rs.getString(3);
				vm.email = rs.getString(4);
				vm.addressLine1 = rs.getString(5);

				li.add(vm);
			}
			rs.close();
			return li;
		} catch (SQLException e) {
			System.out.println(e.toString());
		}
		return li;
	}

}
