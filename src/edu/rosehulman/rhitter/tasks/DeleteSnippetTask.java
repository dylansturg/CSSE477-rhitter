package edu.rosehulman.rhitter.tasks;

import interfaces.IHttpRequest;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;

import javax.sql.DataSource;

import protocol.HttpStatusCode;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;

import edu.rosehulman.rhitter.RhitterResponse;
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
			validateUser();
			Connection conn = dataSource.getConnection();
			deleted = this.snippet;
			if (deleted != null) {
				PreparedStatement deleteStatement = conn
						.prepareStatement("DELETE FROM Snippet WHERE id = ?");
				deleteStatement.setString(1, "" + snippetId);
				deleteStatement.execute();
				deleteStatement.close();

				response = new RhitterResponse(HttpStatusCode.OK, deleted);
			}

			conn.close();

		} catch (SQLException e) {
			response = new ErrorTask.BasicResponse(
					HttpStatusCode.INTERNAL_ERROR,
					new HashMap<String, String>());
		} catch (UnauthorizedRequestException e) {
			response = new ErrorTask.BasicResponse(HttpStatusCode.USER_ERROR,
					new HashMap<String, String>());
		} catch (SnippetNotFoundException e) {
			response = new ErrorTask.BasicResponse(HttpStatusCode.NOT_FOUND,
					new HashMap<String, String>());
		}

		completed = true;
		super.run();
	}
}
