package edu.rosehulman.rhitter.models;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

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
}
