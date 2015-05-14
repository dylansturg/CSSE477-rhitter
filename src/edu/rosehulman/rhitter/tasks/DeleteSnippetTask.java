package edu.rosehulman.rhitter.tasks;

import interfaces.IHttpRequest;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;

import edu.rosehulman.rhitter.models.Snippet;

public class DeleteSnippetTask extends RhitterTask {

	private int id;

	public DeleteSnippetTask(IHttpRequest request, DataSource dataSource,
			int snippetId) {
		super(request, dataSource);
		snippetId = id;
	}

	@Override
	public void run() {

		MysqlDataSource dataSource = new MysqlDataSource();
		Snippet deleted = null;

		try {
			Connection conn = dataSource.getConnection();
			PreparedStatement statement = conn
					.prepareStatement("SELECT * FROM Snippet WHERE id = ?");
			statement.setString(1, "" + id);

			ResultSet results = statement.executeQuery();

			if (results.next()) {
				deleted = new Snippet(results);
			}

			results.close();
			statement.close();

			if (deleted != null) {
				PreparedStatement deleteStatement = conn
						.prepareStatement("DELETE FROM Snippet WHERE id = ?");
				deleteStatement.setString(1, "" + id);
				statement.execute();
			}

			conn.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}

		completed = true;
		super.run();
	}
}
