package edu.rosehulman.rhitter;

import protocol.HttpStatusCode;

import com.google.gson.Gson;

import edu.rosehulman.rhitter.models.Snippet;
import edu.rosehulman.rhitter.tasks.CreateSnippetTask;
import edu.rosehulman.rhitter.tasks.ErrorTask;
import interfaces.IHttpRequest;
import interfaces.IResourceRoute;
import interfaces.RequestTaskBase;

public class CreateResourceStrategy extends RhitterStrategy {
	@Override
	public RequestTaskBase prepareEvaluation(IHttpRequest request,
			IResourceRoute fromRoute) {

		if (request.getMethod() == null
				|| !request.getMethod().equalsIgnoreCase("post")) {
			throw new UnsupportedOperationException(
					"RHITTER CreateResourceStrategy only supports POST operations.");
		}

		String content = request.getContent();
		Gson serializer = new Gson();
		Snippet submitted = serializer.fromJson(content, Snippet.class);

		String authToken = request.getQueryString("token");
		if (authToken == null || authToken.isEmpty()) {
			return new ErrorTask(request, HttpStatusCode.USER_ERROR);
		}

		return new CreateSnippetTask(request,
				createDataSourceForRoute(fromRoute), authToken, submitted);

	}
}
