package tests;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;

import interfaces.HttpResponseBase;
import interfaces.IHttpRequest;
import interfaces.RequestTaskBase;

import org.junit.Test;

import configuration.ResourceStrategyRoute;
import configuration.ResourceStrategyRouteOptions;
import edu.rosehulman.rhitter.LoginResourceStrategy;

public class LoginStrategyTests {

	@Test
	public void testStrategyParsesContent() {
		String serialized = "{\"username\": \"sturgedl\", \"password\": \"password\"}";
		Map<String, String> options = new HashMap<String, String>();
		options.put(ResourceStrategyRouteOptions.PluginDatabaseName, "rhitter");
		options.put(ResourceStrategyRouteOptions.PluginDatabasePassword,
				"Password1@");
		options.put(ResourceStrategyRouteOptions.PluginDatabaseUsername, "java");
		options.put(ResourceStrategyRouteOptions.PluginDatabaseServerPort,
				"3306");
		options.put(ResourceStrategyRouteOptions.PluginDatabaseServerName,
				"dylans-pc.rose-hulman.edu");

		LoginResourceStrategy loginStrategy = new LoginResourceStrategy();
		RequestTaskBase task = loginStrategy.prepareEvaluation(
				new FakeHttpRequest(serialized, "POST", ""),
				new ResourceStrategyRoute(null, null, null, options));

		task.run();

		HttpResponseBase response = task.getResponse();
		assertNotNull(response);
	}

	public static class FakeHttpRequest implements IHttpRequest {

		private String content;
		private String method;
		private String path;

		public FakeHttpRequest(String content, String method, String path) {
			this.content = content;
			this.method = method;
			this.path = path;
		}

		@Override
		public String getContent() {
			return content;
		}

		@Override
		public String getMethod() {
			return method;
		}

		@Override
		public String getPath() {
			return path;
		}

		@Override
		public String getHeader(String key) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Map<String, String> getQueryStrings() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public String getQueryString(String key) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public void readHeadersAndBody() throws Exception {
			// TODO Auto-generated method stub

		}

		@Override
		public void checkRequest() throws Exception {
			// TODO Auto-generated method stub

		}
	}
}
