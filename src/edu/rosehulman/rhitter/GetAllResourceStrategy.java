package edu.rosehulman.rhitter;

import javax.sql.DataSource;

import protocol.HttpStatusCode;
import edu.rosehulman.rhitter.tasks.ErrorTask;
import edu.rosehulman.rhitter.tasks.GetAllSnippetTask;
import interfaces.IHttpRequest;
import interfaces.IResourceRoute;
import interfaces.RequestTaskBase;

public class GetAllResourceStrategy extends RhitterStrategy {
	@Override
	public RequestTaskBase prepareEvaluation(IHttpRequest request,
			IResourceRoute fromRoute) {

		String path = request.getPath();
		String[] parts = path.split("/");

		if (parts.length < 1) {
			return new ErrorTask(request, HttpStatusCode.BAD_REQUEST);
		}

		try {

			DataSource dataSource = createDataSourceForRoute(fromRoute);
			
			return new GetAllSnippetTask(request, dataSource);

		} catch (NumberFormatException exp) {
			return new ErrorTask(request, HttpStatusCode.TEAPOT);
		}

	}

}
