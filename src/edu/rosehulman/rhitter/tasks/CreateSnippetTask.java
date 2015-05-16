package edu.rosehulman.rhitter.tasks;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;

import interfaces.IHttpRequest;

import javax.sql.DataSource;

import protocol.HttpStatusCode;
import edu.rosehulman.rhitter.RhitterResponse;
import edu.rosehulman.rhitter.models.Snippet;
import edu.rosehulman.rhitter.models.User;

public class CreateSnippetTask extends RhitterTask {

	private String token;
	private Snippet snippet;

	public CreateSnippetTask(IHttpRequest request, DataSource dataSource,
			String authToken, Snippet snippet) {
		super(request, dataSource);
		token = authToken;
		this.snippet = snippet;
	}

	@Override
	public void run() {

		try {
			User user = validateUserToken();
			this.snippet.setPublisherId(user.getId());
			this.snippet.setTimestamp(new Date());

			this.snippet.update(dataSource);

			response = new RhitterResponse(HttpStatusCode.CREATED, snippet);

		} catch (IllegalArgumentException e) {
			response = new ErrorTask.BasicResponse(HttpStatusCode.USER_ERROR,
					new HashMap<String, String>());
		} catch (SQLException e) {
			response = new ErrorTask.BasicResponse(
					HttpStatusCode.INTERNAL_ERROR,
					new HashMap<String, String>());
		}

		completed = true;
		super.run();
	}

	private User validateUserToken() throws SQLException {
		if (token == null || token.isEmpty()) {
			throw new IllegalArgumentException("Token does not exist");
		}

		try {
			Connection conn = dataSource.getConnection();
			PreparedStatement findUser = conn
					.prepareStatement("SELECT User.id, User.username, User.password FROM auth_tokens JOIN User on auth_tokens.user_id = User.id WHERE token = ?");
			findUser.setString(1, token);

			ResultSet userResult = findUser.executeQuery();
			if (!userResult.next()) {
				throw new IllegalArgumentException(
						"Provided token did not map to a token.");
			}

			User user = new User(userResult);
			return user;

		} catch (SQLException e) {
			throw e;
		}

	}
}
