package io.pivotal.microservices.order;

import java.math.BigDecimal;
import java.util.HashMap;
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


@Service
public class MyOrderService {
	@Autowired
	@LoadBalanced
	protected RestTemplate restTemplate;

	protected String serviceUrl;

	protected Logger logger = Logger.getLogger(MyOrderService.class
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
		
		String url = "http://EBUSINESS-SERVICE/eBusinessAccount/payorder/{usrId}";      //URL 
		HashMap<String, String> requestBody1 = new HashMap<String, String>();
//		String requestBody1 = usrId + ";" + price.toString();
		requestBody1.put("userId", usrId);
		requestBody1.put("prices", price.toString());
	    HttpHeaders headers = new HttpHeaders();
	    headers.setContentType(MediaType.APPLICATION_JSON); 
	    HttpEntity<HashMap<String, String>> entity = new HttpEntity<HashMap<String, String>>(requestBody1, headers); 
	    ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.PUT, entity, String.class, usrId);
	    // check the response, e.g. Location header,  Status, and body
	    response.getHeaders().getLocation();
	    String responseBody = response.getBody();
	    HttpStatus status = response.getStatusCode();
	    if (status.toString().equals("200")) {
	    	logger.info("payOrder() in MyOrderService is successful" + ": BODY: " + responseBody + "status: " + status);
	    	return true;
	    } else {
	    	logger.info("staus code in payOrder is:" + status.toString());
	    	return false;
	    }
	}
	
	

}
