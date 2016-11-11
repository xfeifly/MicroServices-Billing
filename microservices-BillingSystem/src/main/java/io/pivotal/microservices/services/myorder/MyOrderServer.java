package io.pivotal.microservices.services.myorder;


import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Import;


import io.pivotal.microservices.order.MyOrderConfiguration;
import io.pivotal.microservices.order.MyOrderRepository;

/**
 * Run as a micro-service, registering with the Discovery Server (Eureka).
 * <p>
 * Note that the configuration for this application is imported from
 * {@link AccountsConfiguration}. This is a deliberate separation of concerns.
 * 
 * @author Paul Chapman
 */
@EnableAutoConfiguration
@EnableDiscoveryClient
@Import(MyOrderConfiguration.class)//BUG
public class MyOrderServer {

	@Autowired
	protected MyOrderRepository orderRepository;//BUG

	protected Logger logger = Logger.getLogger(MyOrderServer.class.getName());

	/**
	 * Run the application using Spring Boot and an embedded servlet engine.
	 * 
	 * @param args
	 *            Program arguments - ignored.
	 */
	public static void main(String[] args) {
		// Tell server to look for order-server.properties or
		// order-server.yml
		System.setProperty("spring.config.name", "order-server");

		SpringApplication.run(MyOrderServer.class, args);
	}
}
