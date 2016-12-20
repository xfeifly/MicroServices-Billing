package cmu18980;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

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
	
	@RequestMapping("/d3processmaporiginal")
	public String processMapOriginal() {
		return "d3processmapOriginal";
	}
	
	@RequestMapping("/d3testmultilanes")
	public String testD3multilanes() {
		return "d3testmultilanes";
	}
}