package edu.rosehulman.rhitter;

import java.util.Map;

import javax.sql.DataSource;

import com.google.gson.Gson;

import protocol.HttpStatusCode;
import edu.rosehulman.rhitter.models.Snippet;
import edu.rosehulman.rhitter.tasks.DeleteSnippetTask;
import edu.rosehulman.rhitter.tasks.ErrorTask;
import edu.rosehulman.rhitter.tasks.GetSpecificSnippetTask;
import edu.rosehulman.rhitter.tasks.PutSnippetTask;
import interfaces.IHttpRequest;
import interfaces.IResourceRoute;
import interfaces.RequestTaskBase;

public class PutResourceStrategy extends RhitterStrategy {
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
			String token = request.getQueryString("token");
			if (token == null || token.isEmpty()) {
				throw new IllegalArgumentException(
						"rhitter snippet PUT requires an auth token");
			}

			DataSource dataSource = createDataSourceForRoute(fromRoute);
			
			String content = request.getContent();

			Gson serializer = new Gson();
			Snippet data = serializer.fromJson(content, Snippet.class);

			return new PutSnippetTask(request, dataSource, token, id, data.getText());

		} catch (Exception exp) {
			return new ErrorTask(request, HttpStatusCode.TEAPOT);
		}
	}

}
