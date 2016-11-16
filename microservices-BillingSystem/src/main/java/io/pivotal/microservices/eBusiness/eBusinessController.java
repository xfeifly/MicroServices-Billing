package io.pivotal.microservices.eBusiness;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.logging.Logger;

import org.bouncycastle.asn1.crmf.PKIPublicationInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.netflix.discovery.converters.Auto;

import antlr.collections.List;
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
		
//		Random rand = new Random();
//		for(int i  = 0; i < 10; i++){
//			testMap.put(1230 + i, rand.nextInt(1000));
//		}
//
//		logger.info("eBusinessController says system has "
//				+ testMap.size() + " eBusiness accounts");
	}
	
	@RequestMapping("/eBusinessAccount/{eBusAccountNumber}")
	public eBusAccount byNumber(@PathVariable("eBusAccountNumber") String accountNumber) {

		logger.info("eBusinessAccounts-service byNumber() invoked: " + accountNumber);
		
		eBusAccount ebtest = ebusAccountRepository.findByEnumber(accountNumber);
		
//		eBusAccount ebtest = new eBusAccount();
//		ebtest.setId((long)1);
//		ebtest.setOwner("Jerry");
//		ebtest.setNumber("1231");
//		Random rand = new Random();
//		ebtest.balance = new BigDecimal(rand.nextInt(10000000) / 100.0).setScale(2, BigDecimal.ROUND_HALF_UP);				
		logger.info("eBusinessAccounts-service byNumber() found: " + ebtest);
		if (ebtest == null)
			throw new AccountNotFoundException(accountNumber);
		else {
			return ebtest;
		}
	}
	
	@RequestMapping("/eBusinessAccount/findById/{IdNumber}")
	public eBusAccount byIdNumber(@PathVariable("IdNumber") String IdNumber) {

		logger.info("eBusinessAccounts-service byNumber() invoked: " + IdNumber);
		
		eBusAccount ebtest = ebusAccountRepository.findByIdnumber(IdNumber);
		
//		eBusAccount ebtest = new eBusAccount();
//		ebtest.setId((long)1);
//		ebtest.setOwner("Jerry");
//		ebtest.setNumber("1231");
//		Random rand = new Random();
//		ebtest.balance = new BigDecimal(rand.nextInt(10000000) / 100.0).setScale(2, BigDecimal.ROUND_HALF_UP);				
		logger.info("eBusinessAccounts-service byENumber() found: " + ebtest);
		if (ebtest == null)
			throw new AccountNotFoundException(IdNumber);
		else {
			return ebtest;
		}
	}
	
	@RequestMapping("/eBusinessAccount/findAll")
	public ArrayList<eBusAccount> findAll() throws Exception {

		logger.info("eBusinessAccounts-service findAll() invoked: ");
		
		ArrayList<eBusAccount> ebtest = ebusAccountRepository.findAll();
		
//		eBusAccount ebtest = new eBusAccount();
//		ebtest.setId((long)1);
//		ebtest.setOwner("Jerry");
//		ebtest.setNumber("1231");
//		Random rand = new Random();
//		ebtest.balance = new BigDecimal(rand.nextInt(10000000) / 100.0).setScale(2, BigDecimal.ROUND_HALF_UP);				
		logger.info("eBusinessAccounts-service byENumber() found: " + ebtest);
		if (ebtest == null)
			throw new Exception();
		else {
			return ebtest;
		}
	}
	
	@RequestMapping("/eBusinessAccount/updata/{eBusinessNumber}")
	public eBusAccount updataDB(@PathVariable("eBusinessNumber") String EBNumber){
		BigDecimal newBalance = new BigDecimal(5);
		ebusAccountRepository.updateEbusAccount(EBNumber, newBalance);
		
		eBusAccount ebtest = ebusAccountRepository.findByIdnumber(EBNumber); 
		return ebtest;
	}
	
}
