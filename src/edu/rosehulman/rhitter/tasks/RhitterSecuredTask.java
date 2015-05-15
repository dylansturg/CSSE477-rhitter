package edu.rosehulman.rhitter.tasks;

import interfaces.IHttpRequest;

import javax.sql.DataSource;

public class RhitterSecuredTask extends RhitterTask {

	int snippetId;
	String authToken;

	public RhitterSecuredTask(IHttpRequest request, DataSource dataSource,
			String authToken, int snippetId) {
		super(request, dataSource);

		this.authToken = authToken;
		this.snippetId = snippetId;
	}

}
