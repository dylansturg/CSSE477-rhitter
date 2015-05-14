package edu.rosehulman.rhitter.tasks;

import javax.sql.DataSource;

import interfaces.IHttpRequest;
import interfaces.RequestTaskBase;

public class RhitterTask extends RequestTaskBase {

	protected DataSource dataSource;

	public RhitterTask(IHttpRequest request, DataSource dataSource) {
		super(request);
		this.dataSource = dataSource;
	}

}
