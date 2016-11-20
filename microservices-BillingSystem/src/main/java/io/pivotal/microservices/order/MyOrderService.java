package io.pivotal.microservices.order;

import java.math.BigDecimal;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import io.pivotal.microservices.services.web.Account;
import io.pivotal.microservices.services.web.WebAccountsService;

@Service
public class MyOrderService {
	@Autowired
	@LoadBalanced
	protected RestTemplate restTemplate;

	protected String serviceUrl;

	protected Logger logger = Logger.getLogger(WebAccountsService.class
			.getName());

	public MyOrderService(String serviceUrl) {
		this.serviceUrl = serviceUrl.startsWith("http") ? serviceUrl
				: "http://" + serviceUrl;
	}
	
	public MyOrderService() {
		logger.info("MyOrderService() in MyOrderService invoked: for usrId:");
	}
	
	//contatenating a url to make a httprequest
	public boolean payOrder(String usrId, BigDecimal price ) {
		
		logger.info("payOrder() in MyOrderService invoked: for usrId:" +
				usrId + " price:" + price);
		
		String url = "http://localhost:2223/payorder/{usrId}";      //URL 
//	    String accountId = 2l;
//	    String requestBody = "{\"status\":\"testStatus2\"}";
	    String requestBody1 = "{\"usrId\":" + usrId + "," + "\"price\":" + price + "}";
	    HttpHeaders headers = new HttpHeaders();
	    headers.setContentType(MediaType.APPLICATION_JSON); 
	    HttpEntity<String> entity = new HttpEntity<String>(requestBody1, headers); 
	    ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.PUT, entity, String.class, usrId);
	    // check the response, e.g. Location header,  Status, and body
	    response.getHeaders().getLocation();
	    String responseBody = response.getBody();
	    HttpStatus status = response.getStatusCode();
	    if (status.toString().equals("OK")) {
	    	logger.info("payOrder() in MyOrderService is successful");
	    	return true;
	    } else {
	    	logger.info("staus code in payOrder is:" + status.toString());
	    	return false;
	    }
	}
	
	

}
