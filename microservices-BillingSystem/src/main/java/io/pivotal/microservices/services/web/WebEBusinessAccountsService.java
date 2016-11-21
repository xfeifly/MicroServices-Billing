package io.pivotal.microservices.services.web;

import java.math.BigDecimal;
import java.util.Random;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class WebEBusinessAccountsService {
	
	@Autowired
	@LoadBalanced
	protected RestTemplate restTemplate;

	protected String serviceUrl;

	protected Logger logger = Logger.getLogger(WebEBusinessAccountsService.class
			.getName());

	public WebEBusinessAccountsService(String serviceUrl) {
		this.serviceUrl = serviceUrl.startsWith("http") ? serviceUrl
				: "http://" + serviceUrl;
	}
	
	@PostConstruct
	public void demoOnly() {
		// Can't do this in the constructor because the RestTemplate injection
		// happens afterwards.
		logger.warning("WebEBusinessAccountService says: The RestTemplate request factory is "
				+ restTemplate.getRequestFactory().getClass());
	}
	
	
	public eBusAccount findByNumber(String IdNumber) {
		logger.info("findByNumber() invoked: for " + IdNumber);
		logger.info("WebEBusinessAccountService  calling: " + "http://EBUSINESS-SERVICE" + "/eBusinessAccount/findById/{IdNumber}");
		return restTemplate.getForObject("http://EBUSINESS-SERVICE" + "/eBusinessAccount/findById/{IdNumber}",
				eBusAccount.class, IdNumber);
	}
	
	public eBusAccount findByEAccountNumber(String eBNumber){
		logger.info("findByEAccountNumber() invoked: for " + eBNumber);
		logger.info("WebEBusinessAccountService  calling: " + "http://EBUSINESS-SERVICE" + "/eBusinessAccount/{eBNumber}");
		return restTemplate.getForObject("http://EBUSINESS-SERVICE" + "/eBusinessAccount/{eBNumber}",
				eBusAccount.class, eBNumber);
	}

}
