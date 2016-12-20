package io.pivotal.microservices.services.collector;

import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.annotation.EnableAsync;
import io.pivotal.microservices.collector.collectorConfiguration;

@EnableAutoConfiguration
@Import(collectorConfiguration.class)
@EnableAsync
public class collectorServer {

	protected Logger logger = Logger.getLogger(collectorServer.class.getName());
	
	
	public static void main(String[] args) {
		// Tell server to look for accounts-server.properties or
		// accounts-server.yml
		

		System.setProperty("spring.config.name", "collector-server");

		SpringApplication.run(collectorServer.class, args);
	}
	

}
