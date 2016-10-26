package io.pivotal.microservices.services.eBusiness;

import java.util.logging.Logger;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Import;

import io.pivotal.microservices.eBusiness.eBusinessConfiguration;
import io.pivotal.microservices.services.accounts.AccountsServer;

@EnableAutoConfiguration
@EnableDiscoveryClient
@Import(eBusinessConfiguration.class)
public class eBusinessServer {

	protected Logger logger = Logger.getLogger(AccountsServer.class.getName());

	
	public static void main(String[] args) {
		// Tell server to look for accounts-server.properties or
		// accounts-server.yml
		

		System.setProperty("spring.config.name", "ebusiness-server");

		SpringApplication.run(eBusinessServer.class, args);
	}
}
