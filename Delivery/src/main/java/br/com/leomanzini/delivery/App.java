package br.com.leomanzini.delivery;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

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
	private static Map<Long, Order> mapOrder = new HashMap<>();
	private static Map<Long, Product> mapProduct = new HashMap<>();

	public static String QUERY = " SELECT * FROM tb_order "
							   + " INNER JOIN tb_order_product ON tb_order.id = tb_order_product.order_id "
							   + " INNER JOIN tb_product ON tb_product.id = tb_order_product.product_id ";
	
	public static Product instantiateProduct(ResultSet rs) {

		Product prod = new Product();

		try {
			prod.setId(rs.getLong("product_id"));
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
			order.setId(rs.getLong("order_id"));
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
				
				Long orderId = rs.getLong("order_id");
				if(mapOrder.get(orderId) == null) {
					Order order = instantiateOrder(rs);
					mapOrder.put(orderId, order);
				}
				
				Long productId = rs.getLong("product_id");
				if(mapProduct.get(productId) == null) {
					Product product = instantiateProduct(rs);
					mapProduct.put(productId, product);
				}
				
				mapOrder.get(orderId).getProducts().add(mapProduct.get(productId));
			}
			
			for(Long orderId : mapOrder.keySet()) {
				LOG.info(mapOrder.get(orderId));
				for(Product product : mapOrder.get(orderId).getProducts()) {
					LOG.info(product);
				}
				System.out.println();
			}
			
		} catch (SQLException e) {
			LOG.error(e.getMessage(), e);
			System.exit(-1);
		} finally {
			Connector.closeConnection(connection);
		}
	}

}
