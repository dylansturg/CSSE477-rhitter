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
import edu.rosehulman.rhitter.tasks.RhitterSecuredTask.SnippetNotFoundException;
import edu.rosehulman.rhitter.viewmodels.SnippetViewModel;
import interfaces.IHttpRequest;
import interfaces.RequestTaskBase;

public class GetSpecificSnippetTask extends RequestTaskBase {

	private Integer id;
	private DataSource source;

	public GetSpecificSnippetTask(IHttpRequest request, DataSource dataSource, Integer snippetId) {
		super(request);
		id = snippetId;
		source = dataSource;
	}

	@Override
	public void run() {

		try {
			Connection conn = source.getConnection();
			Statement statement = conn.createStatement();
			
			PreparedStatement query = conn
					.prepareStatement("SELECT * FROM Snippet WHERE id = ?");
			query.setInt(1, id);

			ResultSet results = query.executeQuery();			

			List<SnippetViewModel> snippets = new ArrayList<SnippetViewModel>();
			while (results.next()) {
				snippets.add(new SnippetViewModel(new Snippet(results), new User(results)));
			}
			if(snippets.size() < 1){
				throw new SnippetNotFoundException();
			}
			response = new RhitterResponse(HttpStatusCode.OK, snippets.get(0));

			results.close();
			statement.close();
			conn.close();

		} catch (SQLException e) {
			response = new ErrorTask.BasicResponse(
					HttpStatusCode.INTERNAL_ERROR,
					new HashMap<String, String>());

		} catch (SnippetNotFoundException e) {
			response = new ErrorTask.BasicResponse(HttpStatusCode.NOT_FOUND,
					new HashMap<String, String>());
		}
		completed = true;
		super.run();
	}

}
