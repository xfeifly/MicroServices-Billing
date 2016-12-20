package cmu18980;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;

@Controller
public class HelloController {
	@Autowired
	protected RestTemplate restTemplate;
	
	
	@RequestMapping("/")
	public String hi() {
		return "hello1";
	}
	
	@RequestMapping("/d3")
	public String testD3() {
		return "testd3";
	}
	
	@RequestMapping("/d3jquery")
	public String testD3_Jquery() {
		return "d3jquery";
	}
	
	@RequestMapping("/d3fdg")
	public String forceDirectedGrph() {
		return "d3fdg";
	}
	
	@RequestMapping("/d3processmap")
	public String processMap() {
		return "d3processmap";
	}
	
	/*
	 * send http call to get all traces, and put in the frontend
	 * and use single api to show  process map
	 */
	@RequestMapping("/d3alltraces")
	public String processMaAllTraces() {
		
		
		String url = "http://localhost:9411/api/v1/traces";      //URL 
		HashMap<String, String> requestBody1 = new HashMap<String, String>();
//		requestBody1.put("userId", usrId);
//		requestBody1.put("prices", price.toString());
	    HttpHeaders headers = new HttpHeaders();
	    headers.setContentType(MediaType.APPLICATION_JSON); 
	    HttpEntity<HashMap<String, String>> entity = new HttpEntity<HashMap<String, String>>(requestBody1, headers); 
	    ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.PUT, entity, String.class, 0 );//make the compiler happy
	    // check the response, e.g. Location header,  Status, and body
	    response.getHeaders().getLocation();
	    String responseBody = response.getBody();
	    HttpStatus status = response.getStatusCode();
//	    if (status.toString().equals("200")) {
//	    	logger.info("payOrder() in MyOrderService is successful" + ": BODY: " + responseBody + "status: " + status);
//	    	return true;
//	    } else {
//	    	logger.info("staus code in payOrder is:" + status.toString());
//	    	return false;
//	    }
		
		
		
		
		return "d3alltraces";
	}
	
	@RequestMapping("/d3processmaporiginal")
	public String processMapOriginal() {
		return "d3processmapOriginal";
	}
	
	@RequestMapping("/d3testmultilanes")
	public String testD3multilanes() {
		return "d3testmultilanes";
	}
}