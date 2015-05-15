package edu.rosehulman.rhitter.tasks;

import interfaces.IHttpRequest;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import edu.rosehulman.rhitter.models.Snippet;
import edu.rosehulman.rhitter.models.User;

public class RhitterSecuredTask extends RhitterTask {

	int snippetId;
	String authToken;

	User user;
	Snippet snippet;

	public RhitterSecuredTask(IHttpRequest request, DataSource dataSource,
			String authToken, int snippetId) {
		super(request, dataSource);

		this.authToken = authToken;
		this.snippetId = snippetId;
	}

	protected void validateUser() throws SQLException {
		Connection conn = dataSource.getConnection();

		PreparedStatement userCheck = conn
				.prepareStatement("SELECT User.id, User.username, User.password FROM auth_tokens JOIN User ON auth_tokens.user_id = User.id WHERE token = ?;");
		userCheck.setString(1, authToken);

		ResultSet results = userCheck.executeQuery();
		if (results.next()) {
			user = new User(results);
		} else {
			throw new UnauthorizedRequestException();
		}
		userCheck.close();

		PreparedStatement fetchSnippet = conn
				.prepareStatement("SELECT * FROM Snippet WHERE id = ?");
		fetchSnippet.setInt(1, snippetId);

		ResultSet snippetResults = fetchSnippet.executeQuery();
		if (snippetResults.next()) {
			snippet = new Snippet(snippetResults);
		} else {
			throw new SnippetNotFoundException();
		}
		fetchSnippet.close();

		if (snippet.getPublisherId() != user.getId()) {
			throw new UnauthorizedRequestException(
					"User does not own the specified Snippet.");
		}
		conn.close();
	}

	public static class UnauthorizedRequestException extends
			IllegalStateException {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1555241584290622400L;

		public UnauthorizedRequestException(String msg, Throwable e) {
			super(msg, e);
		}

		public UnauthorizedRequestException(String msg) {
			super(msg);
		}

		public UnauthorizedRequestException() {
			super();
		}

	}

	public static class SnippetNotFoundException extends IllegalStateException {
		/**
 * 
 */
		private static final long serialVersionUID = 1555241584290622400L;

		public SnippetNotFoundException(String msg, Throwable e) {
			super(msg, e);
		}

		public SnippetNotFoundException(String msg) {
			super(msg);
		}

		public SnippetNotFoundException() {
			super();
		}

	}

}
