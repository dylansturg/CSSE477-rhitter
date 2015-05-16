package edu.rosehulman.rhitter.tasks;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.sql.DataSource;

import protocol.HttpStatusCode;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;

import edu.rosehulman.rhitter.RhitterResponse;
import edu.rosehulman.rhitter.models.Snippet;
import edu.rosehulman.rhitter.tasks.RhitterSecuredTask.SnippetNotFoundException;
import edu.rosehulman.rhitter.tasks.RhitterSecuredTask.UnauthorizedRequestException;
import interfaces.IHttpRequest;
import interfaces.RequestTaskBase;

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
			validateUser();
			Connection conn = dataSource.getConnection();
			Statement statement = conn.createStatement();
			ResultSet results = statement.executeQuery("UPDATE Snippet SET text = " + this.text + ", timestamp = " + new Date() + " WHERE id = " + this.snippetId);

			List<Snippet> snippets = new ArrayList<Snippet>();
			while (results.next()) {
				snippets.add(new Snippet(results));
			}
			
			response = new RhitterResponse(HttpStatusCode.OK, snippets.get(0));

			results.close();
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
