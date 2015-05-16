package edu.rosehulman.rhitter.models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

public class Snippet {

	private static Map<String, Integer> ColumnIndices = new HashMap<String, Integer>();
	static {
		ColumnIndices.put("id", 1);
		ColumnIndices.put("publisher_id", 2);
		ColumnIndices.put("text", 3);
		ColumnIndices.put("timestamp", 4);
	}

	int id;
	int publisherId;

	String text;
	Date timestamp;

	public Snippet() {
	}

	public Snippet(ResultSet row) throws SQLException {
		this.id = row.getInt(ColumnIndices.get("id"));
		this.publisherId = row.getInt(ColumnIndices.get("publisher_id"));
		this.text = row.getString(ColumnIndices.get("text"));
		this.timestamp = row.getDate(ColumnIndices.get("timestamp"));
	}

	public Snippet(int id, int pubId, String text, Date timestamp) {
		this.id = id;
		this.publisherId = pubId;
		this.text = text;
		this.timestamp = timestamp;
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
	 * @return the publisherId
	 */
	public int getPublisherId() {
		return publisherId;
	}

	/**
	 * @param publisherId
	 *            the publisherId to set
	 */
	public void setPublisherId(int publisherId) {
		this.publisherId = publisherId;
	}

	/**
	 * @return the text
	 */
	public String getText() {
		return text;
	}

	/**
	 * @param text
	 *            the text to set
	 */
	public void setText(String text) {
		this.text = text;
	}

	/**
	 * @return the timestamp
	 */
	public Date getTimestamp() {
		return timestamp;
	}

	/**
	 * @param timestamp
	 *            the timestamp to set
	 */
	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}

	public void update(DataSource dataSource) throws SQLException {
		Connection conn = dataSource.getConnection();
		if (id > 0) { // update
			// TODO implement update operation
		} else { // insert
			PreparedStatement insert = conn
					.prepareStatement(
							"INSERT INTO Snippet(publisher_id, text, timestamp) values(?, ?, ?);",
							Statement.RETURN_GENERATED_KEYS);
			insert.setInt(1, publisherId);
			insert.setString(2, text);
			insert.setTimestamp(3, new java.sql.Timestamp(timestamp.getTime()));

			insert.executeUpdate();
			ResultSet inserted = insert.getGeneratedKeys();
			if (inserted.next()) {
				id = inserted.getInt(1);
			}
		}
	}
}
