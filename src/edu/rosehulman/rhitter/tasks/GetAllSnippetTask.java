package edu.rosehulman.rhitter.tasks;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.sql.DataSource;

import protocol.HttpStatusCode;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;

import edu.rosehulman.rhitter.RhitterResponse;
import edu.rosehulman.rhitter.models.Snippet;
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
			Statement statement = conn.createStatement();
			ResultSet results = statement.executeQuery("SELECT * FROM Snippet");

			List<Snippet> snippets = new ArrayList<Snippet>();
			while (results.next()) {
				snippets.add(new Snippet(results));
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
