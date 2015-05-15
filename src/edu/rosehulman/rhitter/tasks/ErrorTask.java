package edu.rosehulman.rhitter.tasks;

import interfaces.HttpResponseBase;
import interfaces.IHttpRequest;
import interfaces.RequestTaskBase;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import protocol.HttpStatusCode;
import protocol.Protocol;

public class ErrorTask extends RequestTaskBase {

	private HttpStatusCode status;

	public ErrorTask(IHttpRequest request, HttpStatusCode status) {
		super(request);
		this.status = status;
	}

	@Override
	public void run() {
		response = new BasicResponse(status, new HashMap<String, String>());
		completed = true;
		super.run();
	}

	public static class BasicResponse extends HttpResponseBase {

		public BasicResponse(HttpStatusCode status, Map<String, String> headers) {
			super(Protocol.VERSION, status, headers);
		}

		@Override
		protected void writeContent(BufferedOutputStream outStream)
				throws IOException {
		}
	}
}
