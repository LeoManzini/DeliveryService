package br.com.leomanzini.delivery;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class App {
	
	private static final Logger LOG = LogManager.getLogger(App.class);

	public static void main(String[] args) {
	
		String propsPath = (args.length > 0) ? args[0] : "src/main/resources/database.properties";
		
	}

}
