package edu.rosehulman.rhitter;

import protocol.HttpStatusCode;

import com.google.gson.Gson;

import edu.rosehulman.rhitter.models.User;
import edu.rosehulman.rhitter.tasks.ErrorTask;
import edu.rosehulman.rhitter.tasks.RegisterUserTask;
import interfaces.IHttpRequest;
import interfaces.IResourceRoute;
import interfaces.RequestTaskBase;

public class RegisterResourceStrategy extends RhitterStrategy {

	@Override
	public RequestTaskBase prepareEvaluation(IHttpRequest request,
			IResourceRoute fromRoute) {

		if (request.getMethod() == null
				|| !request.getMethod().equalsIgnoreCase("post")) {
			throw new UnsupportedOperationException(
					"RegisterResourceStrategy only supports HTTP POST method.");
		}

		String content = request.getContent();
		Gson serializer = new Gson();
		User user = serializer.fromJson(content, User.class);

		if (user == null) {
			return new ErrorTask(request, HttpStatusCode.USER_ERROR);
		}

		return new RegisterUserTask(request,
				createDataSourceForRoute(fromRoute), user);
	}
}
