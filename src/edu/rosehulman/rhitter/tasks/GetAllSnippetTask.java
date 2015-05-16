package edu.rosehulman.rhitter.tasks;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import protocol.HttpStatusCode;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;

import edu.rosehulman.rhitter.RhitterResponse;
import edu.rosehulman.rhitter.models.Snippet;
import edu.rosehulman.rhitter.models.User;
import edu.rosehulman.rhitter.viewmodels.SnippetViewModel;
import edu.rosehulman.rhitter.viewmodels.UserViewModel;
import interfaces.IHttpRequest;
import interfaces.RequestTaskBase;

public class GetAllSnippetTask extends RequestTaskBase {
	DataSource source;

	public GetAllSnippetTask(IHttpRequest request, DataSource datasource) {
		super(request);
		this.source = datasource;
	}

	@Override
	public void run() {

		try {
			Connection conn = source.getConnection();

			PreparedStatement getAllUsers = conn
					.prepareStatement("SELECT * FROM User where id in (SELECT publisher_id FROM Snippet);");
			ResultSet usersResults = getAllUsers.executeQuery();

			Map<Integer, User> users = new HashMap<Integer, User>();
			while (usersResults.next()) {
				User user = new User(usersResults);
				users.put(user.getId(), user);
			}

			Statement statement = conn.createStatement();
			ResultSet results = statement
					.executeQuery("SELECT * FROM Snippet ORDER BY timestamp DESC");

			List<SnippetViewModel> snippets = new ArrayList<SnippetViewModel>();
			while (results.next()) {
				Snippet snip = new Snippet(results);
				snippets.add(new SnippetViewModel(snip, users.get(snip
						.getPublisherId())));
			}

			response = new RhitterResponse(HttpStatusCode.OK, snippets);

			results.close();
			statement.close();
			conn.close();

		} catch (SQLException e) {
			response = new ErrorTask.BasicResponse(
					HttpStatusCode.INTERNAL_ERROR,
					new HashMap<String, String>());
		}

		completed = true;
		super.run();
	}
}
