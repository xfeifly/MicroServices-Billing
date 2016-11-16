package io.pivotal.microservices.eBusiness;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Random;
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

@Configuration
@ComponentScan
@EnableJpaRepositories("io.pivotal.microservices.eBusiness")
@EntityScan("io.pivotal.microservices.eBusiness")
@PropertySource("classpath:db-config.properties")
public class eBusinessConfiguration {
	protected Logger logger;

	public eBusinessConfiguration() {
		logger = Logger.getLogger(getClass().getName());
	}
	
	@Bean
	public DataSource dataSource() {
		logger.info("dataSource() in Ebusiness invoked");

		// Create an in-memory H2 relational database containing some demo
		// accounts.
		DataSource dataSource = (new EmbeddedDatabaseBuilder()).addScript("classpath:testdbForEbus/schemaEbus.sql")
				.addScript("classpath:testdbForEbus/dataEbus.sql").build();

		logger.info("Ebusiness dataSource = " + dataSource);

		// Sanity check
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		List<Map<String, Object>> accounts = jdbcTemplate.queryForList("SELECT enumber FROM E_ACCOUNT");
		logger.info("System has " + accounts.size() + " Ebusiness accounts");

		// Populate with random balances
		Random rand = new Random();

		for (Map<String, Object> item : accounts) {
			String number = (String) item.get("enumber");
			BigDecimal balance = new BigDecimal(10000).setScale(2, BigDecimal.ROUND_HALF_UP);
			jdbcTemplate.update("UPDATE E_ACCOUNT SET balance = ? WHERE enumber = ?", balance, number);
		}

		return dataSource;
	}

}
