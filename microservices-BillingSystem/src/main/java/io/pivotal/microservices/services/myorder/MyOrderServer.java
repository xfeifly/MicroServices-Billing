package io.pivotal.microservices.services.myorder;


import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.client.RestTemplate;

import io.pivotal.microservices.order.MyOrderConfiguration;
import io.pivotal.microservices.order.MyOrderController;
import io.pivotal.microservices.order.MyOrderRepository;
import io.pivotal.microservices.order.MyOrderService;


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
@Configuration
public class MyOrderServer {
	
	public static final String MYORDER_SERVICE_URL = "http://ORDER-SERVICE";

	@Autowired
	protected MyOrderRepository orderRepository;

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
	
	/**
	 * A customized RestTemplate that has the ribbon load balancer build in.
	 * Note that prior to the "Brixton" 
	 * 
	 * @return
	 */
	@LoadBalanced
	@Bean
	RestTemplate restTemplate() {
		return new RestTemplate();
	}
	
	/**
	 * The AccountService encapsulates the interaction with the micro-service.
	 * 
	 * @return A new service instance.
	 */
	@Bean
	public MyOrderService myOrderService() {
		return new MyOrderService(MYORDER_SERVICE_URL);
	}
	
//	@Bean
//	public MyOrderController myOrderController() {
//		return new MyOrderController(orderRepository, myOrderService());
//	}
}
