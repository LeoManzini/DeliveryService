package br.com.leomanzini.delivery;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import br.com.leomanzini.delivery.db.Connector;
import br.com.leomanzini.delivery.utils.PropertiesLoader;

public class App {

	private static final Logger LOG = LogManager.getLogger(App.class);
	private static Statement stm;
	private static ResultSet rs;
	private static Connection connection;

	public static String QUERY = "select * from tb_product";

	public static void main(String[] args) {

		String propsPath = (args.length > 0) ? args[0] : "src/main/resources/database.properties";
		PropertiesLoader.load(propsPath);

		try {
			connection = Connector.getConnection();
			stm = connection.createStatement();
			rs = stm.executeQuery(QUERY);

			while (rs.next()) {
				LOG.info(rs.getLong("Id") + ", " + rs.getString("Name"));
			}
		} catch (SQLException e) {
			LOG.error(e.getMessage(), e);
			System.exit(-1);
		} finally {
			Connector.closeConnection(connection);
		}
	}

}
