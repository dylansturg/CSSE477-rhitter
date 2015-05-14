package edu.rosehulman.rhitter.tasks;

import interfaces.IHttpRequest;
import interfaces.RequestTaskBase;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;

public class DeleteSnippetTask extends RequestTaskBase {

	private int id;

	public DeleteSnippetTask(IHttpRequest request, int snippetId) {
		super(request);
		snippetId = id;
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
