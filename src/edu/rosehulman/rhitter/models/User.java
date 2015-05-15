package edu.rosehulman.rhitter.models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

public class User {

	private static Map<String, Integer> ColumnIndices = new HashMap<String, Integer>();
	static {
		ColumnIndices.put("id", 1);
		ColumnIndices.put("username", 2);
		ColumnIndices.put("password", 3);
	}

	int id;
	String username;
	String password;

	public User() {
	}

	public User(ResultSet row) throws SQLException {
		this.id = row.getInt(ColumnIndices.get("id"));
		this.username = row.getString(ColumnIndices.get("username"));
		this.password = row.getString(ColumnIndices.get("password"));
	}

	public User(String username, String password) {
		this.username = username;
		this.password = password;
	}

	public void update(DataSource source) throws SQLException {
		if (username == null || username.isEmpty()) {
			throw new IllegalStateException(
					"Attempt to save User object without a username is not allowed.");
		}

		if (password == null || password.isEmpty()) {
			throw new IllegalStateException(
					"Attempt to save User object without a password is not allowed.");
		}

		Connection conn = source.getConnection();
		if (id > 0) { // update
			PreparedStatement update = conn
					.prepareStatement("UPDATE User SET password = ? WHERE id = ? AND username = ?");
			update.setString(1, password);
			update.setInt(2, id);
			update.setString(3, username);

			update.executeUpdate();

		} else { // insert

			PreparedStatement existingCheck = conn
					.prepareStatement("SELECT * FROM User where username = ?");
			existingCheck.setString(1, username);

			ResultSet queryResults = existingCheck.executeQuery();
			if (queryResults.next()) {
				throw new UserExistsException(
						"Attempt to create duplicate username - " + username);
			}

			PreparedStatement insert = conn.prepareStatement(
					"INSERT INTO User(username, password) values(?, ?);",
					Statement.RETURN_GENERATED_KEYS);
			insert.setString(1, username);
			insert.setString(2, password);
			insert.executeUpdate();

			ResultSet result = insert.getGeneratedKeys();
			if (result.next()) {
				id = result.getInt(1);
			}
		}
	}

	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * @param username
	 *            the username to set
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password
	 *            the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	public static class UserExistsException extends IllegalArgumentException {
		/**
		 * 
		 */
		private static final long serialVersionUID = -6271677055497310605L;

		public UserExistsException(String msg, Throwable e) {
			super(msg, e);
		}

		public UserExistsException(String msg) {
			super(msg);
		}

		public UserExistsException() {
			super();
		}
	}
}
