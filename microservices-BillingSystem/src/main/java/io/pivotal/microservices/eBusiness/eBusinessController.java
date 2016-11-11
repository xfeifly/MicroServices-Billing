package io.pivotal.microservices.eBusiness;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Random;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.netflix.discovery.converters.Auto;

import io.pivotal.microservices.exceptions.AccountNotFoundException;


@RestController
public class eBusinessController {
	protected Logger logger = Logger.getLogger(eBusinessController.class
			.getName());
	protected HashMap<Integer, Integer> testMap;
	
	protected EbusAccountRepository ebusAccountRepository; 
	
	@Autowired
	public eBusinessController(EbusAccountRepository ebusAccountRepository) {
		this.testMap = new HashMap<Integer, Integer>();
		this.ebusAccountRepository = ebusAccountRepository;
		
		Random rand = new Random();
		for(int i  = 0; i < 10; i++){
			testMap.put(1230 + i, rand.nextInt(1000));
		}

		logger.info("eBusinessController says system has "
				+ testMap.size() + " eBusiness accounts");
	}
	
	@RequestMapping("/eBusinessAccount/{eBusAccountNumber}")
	public eBusAccount byNumber(@PathVariable("eBusAccountNumber") String accountNumber) {

		logger.info("eBusinessAccounts-service byNumber() invoked: " + accountNumber);
		
		eBusAccount ebtest = ebusAccountRepository.findByNumber(accountNumber);
		
//		eBusAccount ebtest = new eBusAccount();
//		ebtest.setId((long)1);
//		ebtest.setOwner("Jerry");
//		ebtest.setNumber("1231");
//		Random rand = new Random();
//		ebtest.balance = new BigDecimal(rand.nextInt(10000000) / 100.0).setScale(2, BigDecimal.ROUND_HALF_UP);
		
//		int balance = testMap.get(Integer.valueOf(accountNumber));
		
		logger.info("eBusinessAccounts-service byNumber() found: " + ebtest);
		if (ebtest == null)
			throw new AccountNotFoundException(accountNumber);
		else {
			return ebtest;
		}
	}
}
