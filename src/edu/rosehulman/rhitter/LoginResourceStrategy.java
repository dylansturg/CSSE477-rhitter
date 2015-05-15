package edu.rosehulman.rhitter;

import interfaces.IHttpRequest;
import interfaces.IResourceRoute;
import interfaces.RequestTaskBase;

import java.util.Map;

import com.google.gson.Gson;

import edu.rosehulman.rhitter.tasks.LoginUserTask;

public class LoginResourceStrategy extends RhitterStrategy {

	@Override
	public RequestTaskBase prepareEvaluation(IHttpRequest request,
			IResourceRoute fromRoute) {

		if (!request.getMethod().equalsIgnoreCase("post")) {
			throw new UnsupportedOperationException(
					"LoginResourceStrategy only supports the POST method.");
		}

		String content = request.getContent();

		Gson serializer = new Gson();
		Map<String, String> data = serializer.fromJson(content, Map.class);

		String username = data.get("username");
		String password = data.get("password");

		return new LoginUserTask(request, createDataSourceForRoute(fromRoute),
				username, password);
	}
}
