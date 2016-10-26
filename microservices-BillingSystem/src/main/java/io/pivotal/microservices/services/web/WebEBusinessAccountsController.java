package io.pivotal.microservices.services.web;

import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
public class WebEBusinessAccountsController {
	
	
	@Autowired
	protected WebEBusinessAccountsService EBaccountsService;
	
	protected Logger logger = Logger.getLogger(WebEBusinessAccountsController.class
			.getName());
	
	public WebEBusinessAccountsController(WebEBusinessAccountsService EBaccountsService) {
		this.EBaccountsService = EBaccountsService;
		
	}
	
//	@InitBinder
//	public void initBinder(WebDataBinder binder) {
//		binder.setAllowedFields("accountNumber", "searchText");
//	}
	
	///eBusinessService Controller
	@RequestMapping(value = "/eBusinessAccount/{eBusinessAccountNumber}")
	public String ebusbyNumber(Model model,
			@PathVariable("eBusinessAccountNumber") String eBusinessAccountNumber) {

		logger.info("webEBusinessAccountController findByNumber() invoked: " + eBusinessAccountNumber);

		eBusAccount account = EBaccountsService.findByNumber(eBusinessAccountNumber);
//		eBusAccount EBaccount = EBaccountsService.findByNumber(eBusinessAccountNumber);
		logger.info("webEBusinessAccountController findByNumber() found: " + account);
		model.addAttribute("eBusAccount", account);
		return "eBusAccount";
	}
}
