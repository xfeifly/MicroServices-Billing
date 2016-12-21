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
	
	
	@RequestMapping("/d3testmultilanes")
	public String testD3multilanes() {
		return "d3testmultilanes";
	}
}