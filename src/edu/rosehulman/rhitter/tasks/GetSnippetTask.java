package edu.rosehulman.rhitter.tasks;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;

import edu.rosehulman.rhitter.models.Snippet;
import interfaces.IHttpRequest;
import interfaces.RequestTaskBase;

public class GetSnippetTask extends RequestTaskBase {

	private Integer id;

	public GetSnippetTask(IHttpRequest request, Integer snippetId) {
		super(request);
		id = snippetId;
	}

	@Override
	public void run() {

		MysqlDataSource dataSource = new MysqlDataSource();
		dataSource.setUser("java");
		dataSource.setPassword("Password1@");
		dataSource.setDatabaseName("rhitter");
		dataSource.setPort(3306);
		dataSource.setServerName("dylans-pc.rose-hulman.edu");

		try {
			Connection conn = dataSource.getConnection("java", "Password1@");
			Statement statement = conn.createStatement();
			ResultSet results = statement.executeQuery("SELECT * FROM Snippet");

			List<Snippet> snippets = new ArrayList<Snippet>();
			while (results.next()) {
				snippets.add(new Snippet(results));
			}

			results.close();
			statement.close();
			conn.close();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		super.run();
	}

}
