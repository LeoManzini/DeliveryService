package br.com.leomanzini.delivery.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import br.com.leomanzini.delivery.utils.PropertiesLoader;

public class Connector {
	
	private static final Logger LOG = LogManager.getLogger(Connector.class);
	private static Connection connection;

	public static Connection getConnection() {
		if (connection == null) {
			try {
				connection = DriverManager.getConnection(PropertiesLoader.url, PropertiesLoader.username, PropertiesLoader.password);
			} catch (SQLException e) {
				LOG.error(e.getMessage(), e);
				System.exit(-1);
			}
		}
		return connection;
	}
	
	public static void closeConnection(Connection connection) {
		if (connection != null) {
			try {
				connection.close();
			} catch (SQLException e) {
				LOG.error(e.getMessage(), e);
				System.exit(-1);
			}
		}
	}
}
