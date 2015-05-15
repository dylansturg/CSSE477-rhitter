package edu.rosehulman.rhitter.tasks;

import interfaces.IHttpRequest;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import protocol.HttpStatusCode;
import edu.rosehulman.rhitter.RhitterResponse;
import edu.rosehulman.rhitter.models.User;
import edu.rosehulman.rhitter.models.User.UserExistsException;

public class RegisterUserTask extends RhitterTask {

	private User userModel;

	public RegisterUserTask(IHttpRequest request, DataSource source, User model) {
		super(request, source);

		userModel = model;
	}

	@Override
	public void run() {

		if (userModel == null) {
			response = new ErrorTask.BasicResponse(HttpStatusCode.BAD_REQUEST,
					new HashMap<String, String>());
		} else {
			try {
				userModel.update(dataSource);
				response = new RhitterResponse(HttpStatusCode.CREATED,
						userModel);
			} catch (SQLException exp) {
				response = new ErrorTask.BasicResponse(
						HttpStatusCode.INTERNAL_ERROR,
						new HashMap<String, String>());
			} catch (UserExistsException exp) {
				Map<String, String> responseContent = new HashMap<String, String>();
				responseContent.put("status", "fail");
				responseContent
						.put("reason",
								"That username already exists.  Please select a different username.");
				response = new RhitterResponse(HttpStatusCode.TEAPOT,
						responseContent);
			}
		}

		completed = true;
		super.run();
	}
}
