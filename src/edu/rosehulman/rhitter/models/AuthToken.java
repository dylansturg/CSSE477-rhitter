package edu.rosehulman.rhitter.models;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.sql.DataSource;

public class AuthToken {

	private static SecureRandom randomGen = new SecureRandom();

	private static Map<String, Integer> ColumnIndices = new HashMap<String, Integer>();
	static {
		ColumnIndices.put("id", 1);
		ColumnIndices.put("user_id", 2);
		ColumnIndices.put("token", 3);
		ColumnIndices.put("expiration", 4);
	}

	int id;
	int userId;

	String token;
	Date expiration;

	public AuthToken() {
	}

	public AuthToken(ResultSet row) throws SQLException {
		this.id = row.getInt(ColumnIndices.get("id"));
		this.userId = row.getInt(ColumnIndices.get("user_id"));
		this.token = row.getString(ColumnIndices.get("token"));
		this.expiration = row.getDate(ColumnIndices.get("expiration"));
	}

	public AuthToken(int userId) {
		this.userId = userId;
	}

	public void update(DataSource source, long secondsTilExpiration)
			throws SQLException {
		Connection conn = source.getConnection();
		if (id > 0) {
			throw new UnsupportedOperationException(
					"AuthToken only supports INSERT operation - attempt to update AuthToken instance with id set.");
		} else {
			token = new BigInteger(130, randomGen).toString(32);
			expiration = new Date(new Date().getTime()
					+ TimeUnit.MILLISECONDS.convert(secondsTilExpiration,
							TimeUnit.SECONDS));

			PreparedStatement insert = conn
					.prepareStatement(
							"INSERT INTO auth_tokens(user_id, token, expiration) values(?, ?, ?);",
							Statement.RETURN_GENERATED_KEYS);
			insert.setInt(1, userId);
			insert.setString(2, token);
			insert.setTimestamp(3, new java.sql.Timestamp(expiration.getTime()));
			insert.executeUpdate();

			ResultSet keys = insert.getGeneratedKeys();
			if (keys.next()) {
				id = (int) keys.getLong(1);
			}
		}
	}

	public void update(DataSource source) throws SQLException {
		update(source, TimeUnit.SECONDS.convert(7, TimeUnit.DAYS));
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
	 * @return the userId
	 */
	public int getUserId() {
		return userId;
	}

	/**
	 * @param userId
	 *            the userId to set
	 */
	public void setUserId(int userId) {
		this.userId = userId;
	}

	/**
	 * @return the token
	 */
	public String getToken() {
		return token;
	}

	/**
	 * @param token
	 *            the token to set
	 */
	public void setToken(String token) {
		this.token = token;
	}

	/**
	 * @return the expiration
	 */
	public Date getExpiration() {
		return expiration;
	}

	/**
	 * @param expiration
	 *            the expiration to set
	 */
	public void setExpiration(Date expiration) {
		this.expiration = expiration;
	}

}
