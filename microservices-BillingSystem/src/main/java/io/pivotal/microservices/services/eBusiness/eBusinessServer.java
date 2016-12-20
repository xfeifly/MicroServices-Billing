package io.pivotal.microservices.services.eBusiness;

import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.sleuth.sampler.AlwaysSampler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.annotation.EnableAsync;

import io.pivotal.microservices.eBusiness.EbusAccountRepository;
import io.pivotal.microservices.eBusiness.eBusinessConfiguration;
import io.pivotal.microservices.services.accounts.AccountsServer;

@EnableAutoConfiguration
@EnableDiscoveryClient
@Import(eBusinessConfiguration.class)
@EnableAsync
public class eBusinessServer {

	protected Logger logger = Logger.getLogger(AccountsServer.class.getName());
	
	@Autowired
	protected EbusAccountRepository ebusAccountRepository;

	
	public static void main(String[] args) {
		// Tell server to look for accounts-server.properties or
		// accounts-server.yml
		

		System.setProperty("spring.config.name", "ebusiness-server");

		SpringApplication.run(eBusinessServer.class, args);
	}
	
	@Bean
	public AlwaysSampler defaultSampler() {
	  return new AlwaysSampler();
	}
}
