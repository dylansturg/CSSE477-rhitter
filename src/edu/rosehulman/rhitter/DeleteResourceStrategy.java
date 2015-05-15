package edu.rosehulman.rhitter;

import interfaces.IHttpRequest;
import interfaces.IResourceRoute;
import interfaces.RequestTaskBase;

import javax.sql.DataSource;

import protocol.HttpStatusCode;
import edu.rosehulman.rhitter.tasks.DeleteSnippetTask;
import edu.rosehulman.rhitter.tasks.ErrorTask;

public class DeleteResourceStrategy extends RhitterStrategy {
	@Override
	public RequestTaskBase prepareEvaluation(IHttpRequest request,
			IResourceRoute fromRoute) {

		String path = request.getPath();
		String[] parts = path.split("/");

		if (parts.length < 1) {
			return new ErrorTask(request, HttpStatusCode.BAD_REQUEST);
		}

		String expectedId = parts[parts.length - 1];

		try {
			int id = Integer.parseInt(expectedId);

			DataSource dataSource = createDataSourceForRoute(fromRoute);

			return new DeleteSnippetTask(request, dataSource, id);

		} catch (NumberFormatException exp) {
			return new ErrorTask(request, HttpStatusCode.TEAPOT);
		}

	}

}