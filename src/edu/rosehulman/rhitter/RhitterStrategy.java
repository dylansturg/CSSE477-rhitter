package edu.rosehulman.rhitter;

import interfaces.IResourceRoute;

import javax.sql.DataSource;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;

import configuration.ResourceStrategyRouteOptions;
import strategy.ResourceStrategyBase;

public class RhitterStrategy extends ResourceStrategyBase {
	protected DataSource createDataSourceForRoute(IResourceRoute fromRoute) {

		String serverName = fromRoute
				.getStrategyOption(ResourceStrategyRouteOptions.PluginDatabaseServerName);
		String serverPort = fromRoute
				.getStrategyOption(ResourceStrategyRouteOptions.PluginDatabaseServerPort);
		String databaseUser = fromRoute
				.getStrategyOption(ResourceStrategyRouteOptions.PluginDatabaseUsername);
		String databasePass = fromRoute
				.getStrategyOption(ResourceStrategyRouteOptions.PluginDatabasePassword);
		String dbName = fromRoute
				.getStrategyOption(ResourceStrategyRouteOptions.PluginDatabaseName);

		MysqlDataSource dataSource = new MysqlDataSource();
		dataSource.setServerName(serverName);
		dataSource.setUser(databaseUser);
		dataSource.setPassword(databasePass);
		dataSource.setDatabaseName(dbName);

		if (serverPort != null && !serverPort.isEmpty()) {
			int port = Integer.parseInt(serverPort);
			dataSource.setPort(port);
		}

		return dataSource;
	}
}
