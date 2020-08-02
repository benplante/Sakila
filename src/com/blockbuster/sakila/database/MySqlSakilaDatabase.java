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
import com.blockbuster.sakila.viewmodels.CityViewModel;
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

	public MySqlSakilaDatabase() {}

	public static SakilaDatabase getInstance() {
		if (instance == null) {
			instance = new MySqlSakilaDatabase();
		}
		return instance;
	}

	@Override
	public void insertActor(ActorViewModel actor) throws SQLException {
		Connection conn = null;
		PreparedStatement stmt = null;
		try {
			conn = DriverManager.getConnection(CONNECTION_STRING, "root", "password");
			stmt = conn.prepareStatement("INSERT INTO actor(first_name, last_name) VALUES(?, ?)");
			stmt.setString(1, actor.firstName);
			stmt.setString(2, actor.lastName);
			stmt.executeUpdate();
		} finally {
			try {
				if (conn != null) conn.close();
				if (stmt != null) stmt.close();
			} catch (SQLException e) {
				System.out.println("Error closing DB resources");
				e.printStackTrace();
			}
		}
	}

	@Override
	public void updateActor(ActorViewModel actor) throws SQLException {
		Connection conn = null;
		PreparedStatement stmt = null;
		try {
			conn = DriverManager.getConnection(CONNECTION_STRING, "root", "password");
			stmt = conn.prepareStatement("UPDATE actor SET first_name = ?, last_name = ? WHERE actor_id = ?");
			stmt.setString(1, actor.firstName);
			stmt.setString(2, actor.lastName);
			stmt.setInt(3, actor.actorId);
			stmt.executeUpdate();
		} finally {
			try {
				if (conn != null) conn.close();
				if (stmt != null) stmt.close();
			} catch (SQLException e) {
				System.out.println("Error closing DB resources");
				e.printStackTrace();
			}
		}
	}

	@Override
	public void deleteActor(ActorViewModel actor) throws SQLException {
		Connection conn = null;
		PreparedStatement stmt = null;
		try {
			conn = DriverManager.getConnection(CONNECTION_STRING, "root", "password");
			stmt = conn.prepareStatement("DELETE FROM actor WHERE actor_id = ?");
			stmt.setInt(1, actor.actorId);
			stmt.executeUpdate();
		} finally {
			try {
				if (conn != null) conn.close();
				if (stmt != null) stmt.close();
			} catch (SQLException e) {
				System.out.println("Error closing DB resources");
				e.printStackTrace();
			}
		}
	}

	@Override
	public List<ActorViewModel> selectActors() throws SQLException {
		ArrayList<ActorViewModel> li = new ArrayList<>();
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		try {
			conn = DriverManager.getConnection(CONNECTION_STRING, "root", "password");
			stmt = conn.createStatement();
			rs = stmt.executeQuery("SELECT actor_id, first_name, last_name FROM actor ORDER BY actor_id");

			while (rs.next()) {
				ActorViewModel vm = new ActorViewModel();
				vm.actorId = rs.getInt(1);
				vm.firstName = rs.getString(2);
				vm.lastName = rs.getString(3);
				li.add(vm);
			}
			rs.close();
		} finally {
			try {
				if (conn != null) conn.close();
				if (stmt != null) stmt.close();
				if (rs != null) rs.close();
			} catch (SQLException e) {
				System.out.println("Error closing DB resources");
				e.printStackTrace();
			}
		}
		return li;
	}

	@Override
	public void insertCustomer(CustomerViewModel customer) throws SQLException {

		Connection conn = null;
		PreparedStatement stmtAddress = null, stmtCustomer = null;
		ResultSet rsId = null;
		try {
			conn = DriverManager.getConnection(CONNECTION_STRING, "root", "password");
			conn.setAutoCommit(false);

			// use location for 47 MySakila Drive record
			String sqlAddress = "INSERT INTO address(address, district, city_id, postal_code, phone, location) " +
				"VALUES(?,?,?,?,?,ST_GeomFromText('POINT(-112.8185647 49.6999986)'))";

			stmtAddress = conn.prepareStatement(sqlAddress, Statement.RETURN_GENERATED_KEYS);
			stmtAddress.setString(1, customer.addressLine1);
			stmtAddress.setString(2, customer.district);
			stmtAddress.setInt(3, customer.getCityId());
			stmtAddress.setString(4, customer.postalCode);
			stmtAddress.setString(5, customer.phone);
			stmtAddress.executeUpdate();

			rsId = stmtAddress.getGeneratedKeys();
			if (rsId.next()) {
				int addressId = rsId.getInt(1);

				String sqlCustomer = "INSERT INTO customer(store_id, first_name, last_name, email, address_id, active, create_date)" +
					"VALUES(1,?,?,?,?,?,NOW())";
				stmtCustomer = conn.prepareStatement(sqlCustomer);
				stmtCustomer.setString(1, customer.firstName);
				stmtCustomer.setString(2, customer.lastName);
				stmtCustomer.setString(3, customer.email);
				stmtCustomer.setInt(4, addressId);
				stmtCustomer.setBoolean(5, customer.getIsActive());
				if (stmtCustomer.executeUpdate() == 1) {
					conn.commit();
				}
				else {
					throw new SQLException("Customer was not added - invalid number of rows affected!");
				}
			}
			else {
				throw new SQLException("Address was not added - invalid number of rows affected!");
			}
		} catch (SQLException e) {
			if (conn != null) conn.rollback();
			throw e;
		} finally {
			try {
				if (conn != null) conn.close();
				if (stmtAddress != null) stmtAddress.close();
				if (stmtCustomer != null) stmtCustomer.close();
				if (rsId != null) rsId.close();
			} catch (SQLException e) {
				System.out.println("Error closing DB resources");
				e.printStackTrace();
			}
		}
	}

	@Override
	public void updateCustomer(CustomerViewModel customer) throws SQLException {
		Connection conn = null;
		PreparedStatement stmtAddress = null, stmtCustomer = null;
		ResultSet rsId = null;
		try {
			conn = DriverManager.getConnection(CONNECTION_STRING, "root", "password");
			conn.setAutoCommit(false);

			String sqlAddress = "UPDATE address SET address = ?, district = ?, city_id = ?, postal_code = ?, phone = ? WHERE address_id = ?";

			stmtAddress = conn.prepareStatement(sqlAddress);
			stmtAddress.setString(1, customer.addressLine1);
			stmtAddress.setString(2, customer.district);
			stmtAddress.setInt(3, customer.getCityId());
			stmtAddress.setString(4, customer.postalCode);
			stmtAddress.setString(5, customer.phone);
			stmtAddress.setInt(6, customer.getAddressId());
			if (stmtAddress.executeUpdate() == 1) {
				String sqlCustomer = "UPDATE customer SET first_name = ?, last_name = ?, email = ?, active = ? WHERE customer_id = ?";
				stmtCustomer = conn.prepareStatement(sqlCustomer);
				stmtCustomer.setString(1, customer.firstName);
				stmtCustomer.setString(2, customer.lastName);
				stmtCustomer.setString(3, customer.email);
				stmtCustomer.setBoolean(4, customer.getIsActive());
				stmtCustomer.setInt(5, customer.customerId);
				if (stmtCustomer.executeUpdate() == 1) {
					conn.commit();
				}
				else {
					throw new SQLException("Customer was not updated - invalid number of rows affected!");
				}
			}
			else {
				throw new SQLException("Address was not updated - invalid number of rows affected!");
			}
		} catch (SQLException e) {
			if (conn != null) conn.rollback();
			throw e;
		} finally {
			try {
				if (conn != null) conn.close();
				if (stmtAddress != null) stmtAddress.close();
				if (stmtCustomer != null) stmtCustomer.close();
				if (rsId != null) rsId.close();
			} catch (SQLException e) {
				System.out.println("Error closing DB resources");
				e.printStackTrace();
			}
		}

	}

	@Override
	public void deleteCustomer(CustomerViewModel customer) throws SQLException {
		Connection conn = null;
		PreparedStatement stmt = null;
		try {
			conn = DriverManager.getConnection(CONNECTION_STRING, "root", "password");
			stmt = conn.prepareStatement("DELETE FROM customer WHERE customer_id = ?");
			stmt.setInt(1, customer.customerId);
		} finally {
			try {
				if (conn != null) conn.close();
				if (stmt != null) stmt.close();
			} catch (SQLException e) {
				System.out.println("Error closing DB resources");
				e.printStackTrace();
			}
		}
	}

	@Override
	public List<CustomerViewModel> selectCustomers() throws SQLException {
		ArrayList<CustomerViewModel> li = new ArrayList<>();
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		try  {
			conn = DriverManager.getConnection(CONNECTION_STRING, "root", "password");
			stmt = conn.createStatement();
			String sql = "SELECT customer_id, first_name, last_name, email, active, city, district, "
					+ "country, address, postal_code, phone, cu.address_id, ad.city_id "
					+ "FROM customer cu "
					+ "LEFT JOIN address ad ON cu.address_id = ad.address_id "
					+ "LEFT JOIN city ct ON ad.city_id = ct.city_id "
					+ "LEFT JOIN country co ON ct.country_id = co.country_id "
					+ "ORDER BY customer_id";
			rs = stmt.executeQuery(sql);

			while (rs.next()) {
				CustomerViewModel vm = new CustomerViewModel();
				vm.customerId = rs.getInt(1);
				vm.firstName = rs.getString(2);
				vm.lastName = rs.getString(3);
				vm.email = rs.getString(4);
				vm.setIsActive(rs.getBoolean(5));
				vm.city = rs.getString(6);
				vm.district = rs.getString(7);
				vm.country = rs.getString(8);
				vm.addressLine1 = rs.getString(9);
				vm.postalCode = rs.getString(10);
				vm.phone = rs.getString(11);
				vm.setAddressId(rs.getInt(12));
				vm.setCityId(rs.getInt(13));

				li.add(vm);
			}
			rs.close();
		} finally {
			try {
				if (conn != null) conn.close();
				if (stmt != null) stmt.close();
				if (rs != null) rs.close();
			} catch (SQLException e) {
				System.out.println("Error closing DB resources");
				e.printStackTrace();
			}
		}
		return li;
	}
	
	@Override
	public List<CityViewModel> selectCities() throws SQLException {
		ArrayList<CityViewModel> li = new ArrayList<>();
		
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		try {
			conn = DriverManager.getConnection(CONNECTION_STRING, "root", "password");
			String sql = "SELECT ci.city_id, ci.city, co.country "
					+ "FROM city ci "
					+ "INNER JOIN country co ON ci.country_id = co.country_id "
					+ "ORDER BY ci.city, co.country";
			stmt = conn.createStatement();
			
			rs = stmt.executeQuery(sql);
			
			while (rs.next()) {
				CityViewModel vm = new CityViewModel();
				vm.setCityId(rs.getInt(1));
				vm.setCityName(rs.getString(2));
				vm.setCountryName(rs.getString(3));
				
				li.add(vm);
			}
		} finally {
			try {
				if (conn != null) conn.close();
				if (stmt != null) stmt.close();
				if (rs != null) rs.close();
			} catch (SQLException e) {
				System.out.println("Error closing DB resources");
				e.printStackTrace();
			}
		}
		
		return li;
	}

}
