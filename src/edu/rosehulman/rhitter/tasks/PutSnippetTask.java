package edu.rosehulman.rhitter.tasks;

import interfaces.IHttpRequest;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.sql.DataSource;

import protocol.HttpStatusCode;
import edu.rosehulman.rhitter.RhitterResponse;
import edu.rosehulman.rhitter.models.Snippet;
import edu.rosehulman.rhitter.models.User;
import edu.rosehulman.rhitter.viewmodels.SnippetViewModel;

public class PutSnippetTask extends RhitterSecuredTask {
	private String text;

	public PutSnippetTask(IHttpRequest request, DataSource dataSource,
			String authToken, int snippetId, String snippetText) {
		super(request, dataSource, authToken, snippetId);
		this.text = snippetText;
	}

	@Override
	public void run() {

		try {
			Date date = new Date();
			validateUser();
			Connection conn = dataSource.getConnection();

			PreparedStatement statement = conn
					.prepareStatement("UPDATE Snippet SET text = ?, timestamp = ? WHERE id = ?");
			statement.setString(1, text);
			statement.setTimestamp(2, new Timestamp(date.getTime()));
			statement.setInt(3, this.snippetId);
			statement.executeUpdate();

			PreparedStatement selectUpdated = conn
					.prepareStatement("SELECT * FROM Snippet WHERE id = ?;");
			selectUpdated.setInt(1, snippetId);
			ResultSet results = selectUpdated.executeQuery();

			List<SnippetViewModel> snippets = new ArrayList<SnippetViewModel>();
			if (results.next()) {
				Snippet updated = new Snippet(results);
				snippets.add(new SnippetViewModel(updated, user));
			} else {
				throw new SnippetNotFoundException();
			}

			response = new RhitterResponse(HttpStatusCode.OK, snippets.get(0));

			statement.close();
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
