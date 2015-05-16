package edu.rosehulman.rhitter;

import javax.sql.DataSource;

import protocol.HttpStatusCode;
import edu.rosehulman.rhitter.tasks.ErrorTask;
import edu.rosehulman.rhitter.tasks.GetSpecificSnippetTask;
import interfaces.IHttpRequest;
import interfaces.IResourceRoute;
import interfaces.RequestTaskBase;

public class GetSnippetResourceStrategy extends RhitterStrategy {
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
			
			return new GetSpecificSnippetTask(request, dataSource, id);

		} catch (NumberFormatException exp) {
			return new ErrorTask(request, HttpStatusCode.TEAPOT);
		}

	}

}
