package io.pivotal.microservices.order;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Home page controller.
 * 
 * @author Ethan Lin
 */
@Controller
public class HomeController {

	@RequestMapping("/")
	public String home() {
		return "index";
	}

}
