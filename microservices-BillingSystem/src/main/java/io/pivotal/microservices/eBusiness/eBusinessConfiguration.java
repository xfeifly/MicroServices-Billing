package io.pivotal.microservices.eBusiness;

import java.util.logging.Logger;

import org.springframework.boot.orm.jpa.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@ComponentScan
@EnableJpaRepositories("io.pivotal.microservices.eBusiness")
@EntityScan("io.pivotal.microservices.eBusiness")
public class eBusinessConfiguration {
	protected Logger logger;

	public eBusinessConfiguration() {
		logger = Logger.getLogger(getClass().getName());
	}

}
