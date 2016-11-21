package io.pivotal.microservices.order;


import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import java.util.logging.Logger;

import javax.sql.DataSource;
import org.springframework.boot.orm.jpa.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;

/**
 * The s Spring configuration.
 * 
 * @author Ethan 
 */
@Configuration
@ComponentScan
@EntityScan("io.pivotal.microservices.order")
@EnableJpaRepositories("io.pivotal.microservices.order")
@PropertySource("classpath:db-configformyorder.properties")
public class MyOrderConfiguration {
	protected Logger logger;

	public MyOrderConfiguration() {
		logger = Logger.getLogger(getClass().getName());
	}
	
	/**
	 * Creates an in-memory "rewards" database populated with test data for fast
	 * testing
	 */
	@Bean
	public DataSource dataSource() {
		logger.info("dataSource for my order() invoked");

		// Create an in-memory H2 relational database containing some demo
		DataSource dataSource = (new EmbeddedDatabaseBuilder()).addScript("classpath:testdbformyorder/schema.sql")
				.addScript("classpath:testdbformyorder/data.sql").build();

		logger.info("dataSource = " + dataSource);

		// Sanity check
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		List<Map<String, Object>> s = jdbcTemplate.queryForList("SELECT USERID FROM T_MYORDER");
		logger.info("System has " + s.size() + " s");



		return dataSource;
	}
	
	
	

}
