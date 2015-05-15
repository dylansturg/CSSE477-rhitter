package edu.rosehulman.rhitter;

import interfaces.HttpResponseBase;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import protocol.HttpStatusCode;
import protocol.Protocol;

import com.google.gson.Gson;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.json.JsonHierarchicalStreamDriver;

public class RhitterResponse extends HttpResponseBase {

	protected String serializedContent;

	public RhitterResponse(HttpStatusCode status, Map<String, String> headers,
			Object content) {
		super(Protocol.VERSION, status, headers);
		if (content != null) {
			appendContent(content);
		}
	}

	public RhitterResponse(HttpStatusCode status, Object content) {
		super(Protocol.VERSION, status, new HashMap<String, String>());
		if (content != null) {
			appendContent(content);
		}
	}

	private void appendContent(Object content) {
		Gson serializer = new Gson();
		serializedContent = serializer.toJson(content);

		if (serializedContent != null) {
			putHeader(Protocol.CONTENT_LENGTH, serializedContent.length() + "");
		}
	}

	@Override
	protected void writeContent(BufferedOutputStream outStream)
			throws IOException {
		if (serializedContent != null) {
			outStream.write(serializedContent.getBytes(Protocol.CHARSET));
		}
	}

}
