package edu.rosehulman.rhitter.tasks;

import interfaces.IHttpRequest;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;

import edu.rosehulman.rhitter.models.Snippet;

public class DeleteSnippetTask extends RhitterSecuredTask {

	public DeleteSnippetTask(IHttpRequest request, DataSource dataSource,
			String authToken, int snippetId) {
		super(request, dataSource, authToken, snippetId);
	}

	@Override
	public void run() {

		MysqlDataSource dataSource = new MysqlDataSource();
		Snippet deleted = null;

		try {
			Connection conn = dataSource.getConnection();
			PreparedStatement statement = conn
					.prepareStatement("SELECT * FROM Snippet WHERE id = ?");
			statement.setString(1, "" + snippetId);

			ResultSet results = statement.executeQuery();

			if (results.next()) {
				deleted = new Snippet(results);
			}

			results.close();
			statement.close();

			if (deleted != null) {
				PreparedStatement deleteStatement = conn
						.prepareStatement("DELETE FROM Snippet WHERE id = ?");
				deleteStatement.setString(1, "" + snippetId);
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
