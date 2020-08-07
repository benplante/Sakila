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
import com.blockbuster.sakila.viewmodels.CategoryReportViewModel;
import com.blockbuster.sakila.viewmodels.CategoryViewModel;
import com.blockbuster.sakila.viewmodels.CityViewModel;
import com.blockbuster.sakila.viewmodels.CustomerViewModel;
import com.blockbuster.sakila.viewmodels.CustomerReportViewModel;
import com.blockbuster.sakila.viewmodels.FilmViewModel;
import com.blockbuster.sakila.viewmodels.InventoryViewModel;
import com.blockbuster.sakila.viewmodels.RentalViewModel;
import com.blockbuster.sakila.viewmodels.StoreReportViewModel;
import com.blockbuster.sakila.viewmodels.StoreViewModel;

/**
 * @author Saja Alhadeethi, Colin Manliclic, Dahye Min, Ben Plante
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
		PreparedStatement stmtActor = null, stmtFilm_Actor = null;
		try {
			conn = DriverManager.getConnection(CONNECTION_STRING, "root", "password");
			conn.setAutoCommit(false);
			
			stmtActor = conn.prepareStatement("DELETE FROM actor WHERE actor_id = ?");
			stmtActor.setInt(1, actor.actorId);
			
			if(stmtActor.executeUpdate() == 1) {
				stmtFilm_Actor = conn.prepareStatement("DELETE FROM film_actor WHERE actor_id = ?");
				stmtFilm_Actor.setInt(1, actor.actorId); 
				stmtFilm_Actor.executeUpdate();
				conn.commit();
			}
			else { 
				throw new SQLException("Actor was not deleted - invalid number of rows affected!");
			}
		} finally {
			try {
				if (conn != null) conn.close();
				if (stmtActor != null) stmtActor.close();
				if (stmtFilm_Actor != null) stmtFilm_Actor.close();
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
	public List<CustomerViewModel> selectCustomers() throws SQLException {
		ArrayList<CustomerViewModel> li = new ArrayList<>();
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		try  {
			conn = DriverManager.getConnection(CONNECTION_STRING, "root", "password");
			stmt = conn.createStatement();
			String sql = "SELECT customer_id, first_name, last_name, email, active, city, district, "
					+ "country, address, postal_code, phone, cu.address_id, ad.city_id , cu.store_id "
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
				vm.setStoreId(rs.getInt(14));

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
	
	@Override
	public List<CategoryViewModel> selectCategories() throws SQLException {
	 	ArrayList<CategoryViewModel> li = new ArrayList<>();
	     
	 	Connection conn = null;
	 	Statement stmt = null;
	 	ResultSet rs = null;
	 	try {
      conn = DriverManager.getConnection(CONNECTION_STRING, "root", "password");
      String sql = "SELECT Category_id, name "
                  + "FROM category ";
      stmt = conn.createStatement();
          
      rs = stmt.executeQuery(sql);
          
      while (rs.next()) {
        CategoryViewModel vm = new CategoryViewModel();
        vm.setCategoryId(rs.getInt(1));
        vm.setCategoryName(rs.getString(2));
              
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
	    
	@Override
	public List<FilmViewModel> selectFilms() throws SQLException {
		ArrayList<FilmViewModel> li = new ArrayList<>();
		
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		try {
			conn = DriverManager.getConnection(CONNECTION_STRING, "root", "password");
			String sql = "SELECT f.film_id, f.title, f.description, f.release_year,"
											 + " f.rental_duration, f.rental_rate,f.length, f.replacement_cost,"
											 + " f.rating, f.special_features , c.name"
											 + " FROM sakila.film f"
											 + " LEFT OUTER JOIN sakila.film_category fc ON f.film_id = fc.film_id"
											 + " LEFT OUTER JOIN sakila.category c ON fc.category_id = c.category_id;";
			stmt = conn.createStatement();
			
			rs = stmt.executeQuery(sql);
		
			while (rs.next()) {
				FilmViewModel vm = new FilmViewModel();
				vm.setFilmId(rs.getInt(1));
				vm.setTitle(rs.getString(2));
				vm.setDescription(rs.getString(3));
				vm.setReleaseYear(rs.getInt(4));
				vm.setRentalDuration(Integer.parseInt(rs.getString(5)));
				vm.setRentalRate(Double.parseDouble(rs.getString(6)));
        vm.setLength((rs.getInt(7)));
				vm.setReplacementCost(Double.parseDouble(rs.getString(8)));
				vm.setRating(rs.getString(9));
        vm.setSpecialFeatures(rs.getString(10));
				vm.setCategoryName(rs.getString(11));
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
	   
	@Override
	public void insertFilm(FilmViewModel film) throws SQLException {

		Connection conn = null;
		PreparedStatement stmtFilm = null,
											stmtFilmActor = null,
											stmtFilmCategory = null,
											stmtInventory = null;
		ResultSet rsId = null;
		try {
			conn = DriverManager.getConnection(CONNECTION_STRING, "root", "password");
			conn.setAutoCommit(false);

			// use location for 47 MySakila Drive record
			String sqlFilm = "INSERT INTO film(title, description, release_year, language_id, rental_duration, "
																			+ "rental_rate, length , replacement_cost, rating , special_features) " +
																				"VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

			stmtFilm = conn.prepareStatement(sqlFilm, Statement.RETURN_GENERATED_KEYS);
			stmtFilm.setString(1, film.title);
			stmtFilm.setString(2, film.description);
			stmtFilm.setInt(3, film.releaseYear);
			stmtFilm.setInt(4, film.getLanguageId());
			stmtFilm.setInt(5, film.rentalDuration);
			stmtFilm.setDouble(6, film.rentalRate);
      stmtFilm.setInt(7, film.length);
			stmtFilm.setDouble(8, film.replacementCost);
			stmtFilm.setString(9, film.rating);
			stmtFilm.setString(10, film.specialFeatures);
			stmtFilm.executeUpdate();
			
			rsId = stmtFilm.getGeneratedKeys();
			if (rsId.next()) {
				int filmId = rsId.getInt(1);
				String sqlFilmActor = "INSERT INTO film_actor(actor_id, film_id) VALUES(?, ?)";
				stmtFilmActor = conn.prepareStatement(sqlFilmActor, Statement.RETURN_GENERATED_KEYS);
				stmtFilmActor.setInt(1, film.getActorId());
				stmtFilmActor.setInt(2, filmId);
				stmtFilmActor.executeUpdate();
				
				String sqlFilmCategory = "INSERT INTO film_category(film_id, category_id) VALUES(?, ?)";
				stmtFilmCategory = conn.prepareStatement(sqlFilmCategory, Statement.RETURN_GENERATED_KEYS);
				stmtFilmCategory.setInt(1, filmId);
				stmtFilmCategory.setInt(2, film.getCategoryId());
				stmtFilmCategory.executeUpdate();
				
				String sqlInventory = "INSERT INTO inventory(film_id, store_id) VALUES(?, ?)";
				stmtInventory = conn.prepareStatement(sqlInventory, Statement.RETURN_GENERATED_KEYS);
				stmtInventory.setInt(1, filmId);
				stmtInventory.setInt(2, 1);
				stmtInventory.executeUpdate();
					
				conn.commit();
			}
		} catch (SQLException e) {
			if (conn != null) conn.rollback();
			throw e;
		} finally {
			try {
				if (conn != null) conn.close();
				if (stmtFilm != null) stmtFilm.close();
				if (stmtFilmActor != null) stmtFilmActor.close();
				if (stmtFilmCategory != null) stmtFilmCategory.close();
				if (stmtInventory != null) stmtInventory.close();
				if (rsId != null) rsId.close();
			} catch (SQLException e) {
				System.out.println("Error closing DB resources");
				e.printStackTrace();
			}
		}
	}
	
	@Override
	public void deleteFilm(FilmViewModel film) throws SQLException
	{
		Connection conn = null;
		PreparedStatement stmtFilm = null,
											stmtFilmActor = null,
											stmtFilmCategory = null,
											stmtInventory = null,
											stmtRental = null;
		try {
			conn = DriverManager.getConnection(CONNECTION_STRING, "root", "password");
			conn.setAutoCommit(false);
			
			stmtFilmActor = conn.prepareStatement("DELETE FROM film_actor WHERE film_id = ?");
			stmtFilmActor.setInt(1, film.filmId);
			stmtFilmActor.executeUpdate();
			
			stmtFilmCategory = conn.prepareStatement("DELETE FROM film_category WHERE film_id = ?");
			stmtFilmCategory.setInt(1, film.filmId);
			stmtFilmCategory.executeUpdate();
			
			stmtRental = conn.prepareStatement("DELETE r FROM rental r INNER JOIN inventory i ON r.inventory_id = i.inventory_id where i.film_id = ?");
			stmtRental.setInt(1, film.filmId);
			stmtRental.executeUpdate();
			
			stmtInventory = conn.prepareStatement("DELETE FROM inventory WHERE film_id = ?");
			stmtInventory.setInt(1, film.filmId);
			stmtInventory.executeUpdate();
			
			stmtFilm = conn.prepareStatement("DELETE FROM film WHERE film_id = ?");
			stmtFilm.setInt(1, film.filmId);
			if(stmtFilm.executeUpdate() == 1) {
				conn.commit();
			}
		} finally {
			try {
				if (conn != null) conn.close();
				if (stmtFilm != null) stmtFilm.close();
				if (stmtFilmActor != null) stmtFilmActor.close();
				if (stmtFilmCategory != null) stmtFilmCategory.close();
				if (stmtInventory != null) stmtInventory.close();
			} catch (SQLException e) {
				System.out.println("Error closing DB resources");
				e.printStackTrace();
			}
		}	
	}

	@Override
	public List<RentalViewModel> selectRentals() throws SQLException
	{
		ArrayList<RentalViewModel> li = new ArrayList<>();
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		try  {
			conn = DriverManager.getConnection(CONNECTION_STRING, "root", "password");
			stmt = conn.createStatement();
			String sql = "SELECT r.rental_id, a.address, f.title, c.first_name, c.last_name, "
					+ "p.amount, f.rental_rate, sta.first_name, sta.last_name, r.rental_date, r.return_date, "
					+ "p.payment_id, r.inventory_id, r.customer_id, r.staff_id, f.rental_duration "
					+ "FROM rental r  "
					+ "LEFT JOIN payment p on r.rental_id = p.rental_id  "
					+ "LEFT JOIN customer c on r.customer_id = c.customer_id "
					+ "LEFT JOIN staff sta ON r.staff_id = sta.staff_id "
					+ "LEFT JOIN inventory i ON r.inventory_id = i.inventory_id "
					+ "LEFT JOIN film f ON i.film_id = f.film_id "
					+ "LEFT JOIN store s ON i.store_id = s.store_id "
					+ "LEFT JOIN address a ON s.address_id = a.address_id "
					+ "ORDER BY r.rental_id";
			rs = stmt.executeQuery(sql);

			while (rs.next()) {
				RentalViewModel vm = new RentalViewModel();
				vm.rentalId = rs.getInt(1);
				vm.storeAddress = rs.getString(2);
				vm.filmTitle = rs.getString(3);
				vm.customerFirstName = rs.getString(4);
				vm.customerLastName = rs.getString(5);
				vm.setPaymentAmount(rs.getBigDecimal(6));
				vm.setRentalRate(rs.getBigDecimal(7));
				vm.staffFirstName = rs.getString(8);
				vm.staffLastName = rs.getString(9);
				vm.setRentalDate(rs.getTimestamp(10));
				vm.setReturnDate(rs.getTimestamp(11));
				vm.setPaymentId(rs.getInt(12));
				vm.setIventoryId(rs.getInt(13));
				vm.setCustomerId(rs.getInt(14));
				vm.setStaffId(rs.getInt(15));
				vm.setFilmRentalDuration(rs.getInt(16));

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
	public List<InventoryViewModel> selectInventories() throws SQLException
	{
		ArrayList<InventoryViewModel> li = new ArrayList<>();
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		try  {
			conn = DriverManager.getConnection(CONNECTION_STRING, "root", "password");
			stmt = conn.createStatement();
			String sql = "SELECT s.inventory_id, f.title, f.rental_duration, f.rental_rate FROM "
					+"( "
					+ "SELECT i.inventory_id, i.film_id, ROW_NUMBER() OVER (PARTITION BY i.film_id ORDER BY i.film_id) AS rn "
					+ "From inventory i " 
					+ ") s "
					+ "INNER JOIN film f "
					+ "ON s.film_id = f.film_id "
					+ "WHERE s.rn = 1";
			rs = stmt.executeQuery(sql);

			while (rs.next()) {
				InventoryViewModel vm = new InventoryViewModel();
				vm.setInventoryId(rs.getInt(1));
				vm.setFilmTitle(rs.getString(2));
				vm.setFilmRentalDuration(rs.getInt(3));
				vm.setRentalRate(rs.getBigDecimal(4));
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
	public void insertRental(RentalViewModel rental) throws SQLException
	{

		Connection conn = null;
		PreparedStatement stmtRental = null, stmtPayment = null;
		ResultSet rsId = null;
		try {
			conn = DriverManager.getConnection(CONNECTION_STRING, "root", "password");
			conn.setAutoCommit(false);

			// use location for 47 MySakila Drive record
			String sqlRental = "INSERT INTO rental(inventory_id, customer_id, staff_id, rental_date, return_date) " +
				"VALUES(?,?,1,NOW(), DATE_ADD(now(), INTERVAL ? DAY))";
			stmtRental = conn.prepareStatement(sqlRental, Statement.RETURN_GENERATED_KEYS);
			stmtRental.setInt(1, rental.getInventoryId());
			stmtRental.setInt(2, rental.getCustomerId());
			stmtRental.setInt(3, rental.getFilmRentalDuration());
			stmtRental.executeUpdate();
			
			rsId = stmtRental.getGeneratedKeys();
			if (rsId.next()) {
				int rentalId = rsId.getInt(1);
				String sqlPayment = "INSERT INTO payment(rental_id, amount, customer_id, staff_id, payment_date)" +
					"VALUES(?,?,?,1,NOW())";
				stmtPayment = conn.prepareStatement(sqlPayment);
				stmtPayment.setInt(1, rentalId);
				stmtPayment.setBigDecimal(2, rental.getPaymentAmount());
				stmtPayment.setInt(3, rental.getCustomerId());
				if (stmtPayment.executeUpdate() == 1) {
					conn.commit();
				}
				else {
					throw new SQLException("Payment was not added - invalid number of rows affected!");
				}
			}
			else {
				throw new SQLException("Rental was not added - invalid number of rows affected!");
			}
		} catch (SQLException e) {
			if (conn != null) conn.rollback();
			throw e;
		} finally {
			try {
				if (conn != null) conn.close();
				if (stmtRental != null) stmtRental.close();
				if (stmtPayment != null) stmtPayment.close();
				if (rsId != null) rsId.close();
			} catch (SQLException e) {
				System.out.println("Error closing DB resources");
				e.printStackTrace();
			}
		}
		
	}

	@Override
	public void deleteRental(RentalViewModel rental) throws SQLException
	{
		Connection conn = null;
		PreparedStatement stmtRental = null, stmtPayment = null;
		try {
			conn = DriverManager.getConnection(CONNECTION_STRING, "root", "password");
			conn.setAutoCommit(false);
			
			stmtRental = conn.prepareStatement("DELETE FROM rental WHERE rental_id = ?");
			stmtRental.setInt(1, rental.rentalId);
			
			if(stmtRental.executeUpdate() == 1) {
				stmtPayment = conn.prepareStatement("DELETE FROM payment WHERE payment_id = ?");
				stmtPayment.setInt(1, rental.getPaymentId());
				
				if (stmtPayment.executeUpdate() == 1) {
					conn.commit();
				}
				else {
					throw new SQLException("Payment was not deleted - invalid number of rows affected!");
				}
			}
			else { 
				throw new SQLException("Rental was not deleted - invalid number of rows affected!");
			}
		} finally {
			try {
				if (conn != null) conn.close();
				if (stmtRental != null) stmtRental.close();
				if (stmtPayment != null) stmtPayment.close();
			} catch (SQLException e) {
				System.out.println("Error closing DB resources");
				e.printStackTrace();
			}
		}
	}
	
	public List<StoreViewModel> selectStores() throws SQLException {
		ArrayList<StoreViewModel> list = new ArrayList<>();
		
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		try {
			conn = DriverManager.getConnection(CONNECTION_STRING, "root", "password");
			stmt = conn.createStatement();
			
			rs = stmt.executeQuery(
					"SELECT s.store_id, c.city, u.country "
					+ "FROM store s "
					+ "INNER JOIN address a ON s.address_id = a.address_id "
					+ "INNER JOIN city c ON a.city_id = c.city_id "
					+ "INNER JOIN country u ON c.country_id = u.country_id"
				);
			
			while (rs.next()) {
				StoreViewModel vm = new StoreViewModel();
				vm.setStoreId(rs.getInt(1));
				vm.setCity(rs.getString(2));
				vm.setCountry(rs.getString(3));
				list.add(vm);
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
		
		return list;
	}
	
	public List<StoreReportViewModel> getSalesByStore(List<Integer> stores) throws SQLException {
		ArrayList<StoreReportViewModel> list = new ArrayList<>();
		
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		String sql = 				
			"SELECT s.store_id, CONCAT(c.city, ', ', u.country), SUM(p.amount) "
			+ "FROM payment p "
			+ "INNER JOIN rental r ON p.rental_id = r.rental_id "
			+ "INNER JOIN inventory i ON r.inventory_id = i.inventory_id "
			+ "INNER JOIN store s ON i.store_id = s.store_id "
			+ "INNER JOIN address a ON s.address_id = a.address_id "
			+ "INNER JOIN city c ON a.city_id = c.city_id "
			+ "INNER JOIN country u ON c.country_id = u.country_id "
			+ "WHERE s.store_id IN (";

		if (stores.size() == 0) throw new SQLException("No Stores Selected");
		String paramList = "";
		for (int i = 0; i < stores.size(); ++i) {
			paramList += "?,";
		}
		sql += paramList.substring(0, paramList.length() - 1);
		sql += ") GROUP BY s.store_id, c.city, u.country "
				+ "ORDER BY 3 DESC";
		try {
			conn = DriverManager.getConnection(CONNECTION_STRING, "root", "password");
			stmt = conn.prepareStatement(sql);
			
			for (int i = 0; i < stores.size(); ++i) {
				stmt.setInt(i+1, stores.get(i));
			}
			
			rs = stmt.executeQuery();
			
			while (rs.next() ) {
				StoreReportViewModel vm = new StoreReportViewModel();
				vm.storeId = rs.getInt(1);
				vm.storeLocation = rs.getString(2);
				vm.salesAmount = rs.getBigDecimal(3);
				list.add(vm);
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
		return list;
	}
	
	public List<CategoryReportViewModel> getSalesByCategory(List<Integer> categories) throws SQLException {
		ArrayList<CategoryReportViewModel> list = new ArrayList<>();
		
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		String sql = 				
			"SELECT c.category_id, c.name, SUM(p.amount) "
			+ "FROM payment p "
			+ "INNER JOIN rental r ON p.rental_id = r.rental_id "
			+ "INNER JOIN inventory i ON r.inventory_id = i.inventory_id "
			+ "INNER JOIN film_category f ON i.film_id = f.film_id "
			+ "INNER JOIN category c ON f.category_id = c.category_id "
			+ "WHERE c.category_id IN (";

		if (categories.size() == 0) throw new SQLException("No Categories Selected");
		String paramList = "";
		for (int i = 0; i < categories.size(); ++i) {
			paramList += "?,";
		}
		sql += paramList.substring(0, paramList.length() - 1);
		sql += ") GROUP BY c.category_id, c.name "
				+ "ORDER BY 3 DESC";
		try {
			conn = DriverManager.getConnection(CONNECTION_STRING, "root", "password");
			stmt = conn.prepareStatement(sql);
			
			for (int i = 0; i < categories.size(); ++i) {
				stmt.setInt(i+1, categories.get(i));
			}
			
			rs = stmt.executeQuery();
			
			while (rs.next() ) {
				CategoryReportViewModel vm = new CategoryReportViewModel();
				vm.categoryId = rs.getInt(1);
				vm.categoryName = rs.getString(2);
				vm.salesAmount = rs.getBigDecimal(3);
				list.add(vm);
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
		return list;
	}
	
	public List<CustomerReportViewModel> getSalesByCustomer(List<Integer> customers) throws SQLException {
ArrayList<CustomerReportViewModel> list = new ArrayList<>();
		
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		String sql = 				
			"SELECT c.customer_id, CONCAT(c.first_name, ' ', c.last_name), SUM(p.amount) "
			+ "FROM payment p "
			+ "INNER JOIN rental r ON p.rental_id = r.rental_id "
			+ "INNER JOIN customer c ON r.customer_id = c.customer_id "
			+ "WHERE c.customer_id IN (";

		if (customers.size() == 0) throw new SQLException("No Categories Selected");
		String paramList = "";
		for (int i = 0; i < customers.size(); ++i) {
			paramList += "?,";
		}
		sql += paramList.substring(0, paramList.length() - 1);
		sql += ") GROUP BY c.customer_id, c.first_name, c.last_name "
				+ "ORDER BY 3 DESC";
		try {
			conn = DriverManager.getConnection(CONNECTION_STRING, "root", "password");
			stmt = conn.prepareStatement(sql);
			
			for (int i = 0; i < customers.size(); ++i) {
				stmt.setInt(i+1, customers.get(i));
			}
			
			rs = stmt.executeQuery();
			
			while (rs.next() ) {
				CustomerReportViewModel vm = new CustomerReportViewModel();
				vm.customerId = rs.getInt(1);
				vm.customerName = rs.getString(2);
				vm.salesAmount = rs.getBigDecimal(3);
				list.add(vm);
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
		return list;
	}
}
