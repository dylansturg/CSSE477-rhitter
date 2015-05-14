package edu.rosehulman.rhitter;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.json.JsonHierarchicalStreamDriver;

import protocol.HttpStatusCode;
import protocol.Protocol;
import interfaces.HttpResponseBase;

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
		XStream streamer = new XStream(new JsonHierarchicalStreamDriver());

		streamer.setMode(XStream.NO_REFERENCES);
		serializedContent = streamer.toXML(content);

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
