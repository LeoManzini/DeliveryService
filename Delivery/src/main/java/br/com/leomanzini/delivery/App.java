package br.com.leomanzini.delivery;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import br.com.leomanzini.delivery.db.Connector;
import br.com.leomanzini.delivery.utils.PropertiesLoader;
import br.com.leomanzini.entities.Order;
import br.com.leomanzini.entities.OrderStatus;
import br.com.leomanzini.entities.Product;

public class App {

	private static final Logger LOG = LogManager.getLogger(App.class);
	private static Statement stm;
	private static ResultSet rs;
	private static Connection connection;

	public static String QUERY = "SELECT * FROM tb_order";
	
	public static Product instantiateProduct(ResultSet rs) {

		Product prod = new Product();

		try {
			prod.setId(rs.getLong("id"));
			prod.setName(rs.getString("name"));
			prod.setPrice(rs.getDouble("price"));
			prod.setDescription(rs.getString("description"));
			prod.setImageUrl(rs.getString("image_uri"));
		} catch (SQLException e) {
			LOG.error(e.getMessage(), e);
			System.exit(-1);
		}
		return prod;
	}
	
	public static Order instantiateOrder(ResultSet rs) {
		Order order = new Order();

		try {
			order.setId(rs.getLong("id"));
			order.setLatitude(rs.getDouble("latitude"));
			order.setLongitude(rs.getDouble("longitude"));
			order.setMoment(rs.getTimestamp("moment").toInstant());
			order.setStatus(OrderStatus.values()[rs.getInt("status")]);
		} catch (SQLException e) {
			LOG.error(e.getMessage(), e);
			System.exit(-1);
		}
		return order;
	}

	public static void main(String[] args) {

		String propsPath = (args.length > 0) ? args[0] : "src/main/resources/database.properties";
		PropertiesLoader.load(propsPath);

		try {
			connection = Connector.getConnection();
			stm = connection.createStatement();
			rs = stm.executeQuery(QUERY);

			while (rs.next()) {
				//Product product = instantiateProduct(rs);
				//LOG.info(product);
				
				Order order = instantiateOrder(rs);
				LOG.info(order);
			}
		} catch (SQLException e) {
			LOG.error(e.getMessage(), e);
			System.exit(-1);
		} finally {
			Connector.closeConnection(connection);
		}
	}

}
