package edu.rosehulman.rhitter.tasks;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

import interfaces.IHttpRequest;

import javax.sql.DataSource;

import protocol.HttpStatusCode;
import edu.rosehulman.rhitter.RhitterResponse;
import edu.rosehulman.rhitter.models.AuthToken;
import edu.rosehulman.rhitter.models.User;

public class LoginUserTask extends RhitterTask {

	private String user;
	private String pass;

	public LoginUserTask(IHttpRequest request, DataSource dataSource,
			String username, String password) {
		super(request, dataSource);

		this.user = username;
		this.pass = password;
	}

	@Override
	public void run() {

		try {
			Connection conn = dataSource.getConnection();
			PreparedStatement query = conn
					.prepareStatement("SELECT * FROM User WHERE username = ? AND password = ?");
			query.setString(1, user);
			query.setString(2, pass);

			ResultSet result = query.executeQuery();
			if (result.next()) {
				User authenticatedUser = new User(result);
				
				PreparedStatement deleteSession = conn.prepareStatement("DELETE FROM auth_tokens WHERE user_id = ?");
				deleteSession.setInt(1, authenticatedUser.getId());
				deleteSession.executeUpdate();
				
				AuthToken token = new AuthToken(authenticatedUser.getId());
				token.update(dataSource);

				response = new RhitterResponse(HttpStatusCode.OK, token);
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		if (response == null) {
			response = new ErrorTask.BasicResponse(HttpStatusCode.NOT_FOUND,
					new HashMap<String, String>());
		}

		completed = true;
		super.run();
	}

}
