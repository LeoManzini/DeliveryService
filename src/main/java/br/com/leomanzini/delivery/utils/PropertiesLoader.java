package br.com.leomanzini.delivery.utils;

import java.io.FileInputStream;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class PropertiesLoader {
	
	private static final Logger LOG = LogManager.getLogger(PropertiesLoader.class);
	
	private static final String DB_URL = "db.url";
	private static final String DB_USER = "db.user";
	private static final String DB_PASSWORD = "db.password";

	public static String url;
	public static String username;
	public static String password;

	public static Properties load(String path) {

		Properties props = new Properties();

		try {
			props.load(new FileInputStream(path));

			url = props.getProperty(DB_URL);
			username = props.getProperty(DB_USER);
			password = props.getProperty(DB_PASSWORD);

		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
			System.exit(-1);
		}
		return props;
	}
}