package edu.rosehulman.rhitter.models;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

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

	public User(int id, String username, String password) {

	}
}
