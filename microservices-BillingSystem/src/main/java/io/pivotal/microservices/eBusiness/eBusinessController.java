package io.pivotal.microservices.eBusiness;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.logging.Logger;
import org.springframework.http.*;
//import org.apache.commons.collections.keyvalue.TiedMapEntry;
import org.bouncycastle.asn1.crmf.PKIPublicationInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.object.UpdatableSqlQuery;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
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
	
	
	//find eBaccount by eB number
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
	
	//find aB account by Id number
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
	
	//find all eB account
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
		//logger.info("eBusinessAccounts-service byENumber() found: " + ebtest);
		if (ebtest == null)
			throw new Exception();
		else {
			return ebtest;
		}
	}
	 
	
	//updata ebusiness account balance
	@RequestMapping("/eBusinessAccount/updata/{eBusinessNumber}")
	public eBusAccount updataDB(@PathVariable("eBusinessNumber") String EBNumber){
		BigDecimal newBalance = new BigDecimal(5);
		ebusAccountRepository.updateEbusAccount(EBNumber, newBalance);
		eBusAccount ebtest = ebusAccountRepository.findByIdnumber(EBNumber); 
		return ebtest;
	}
	
	//API for myOrderService: pay the order
	@RequestMapping(value = "/eBusinessAccount/payorder/{userId}", method = RequestMethod.PUT)
	public ResponseEntity<String> tryToPay(@PathVariable("userId") String userId, @RequestBody HashMap<String, String> input){
		logger.info("eBusiness service received:" + "ID: "+ input.get("userId") + "price: " + input.get("prices"));
		String id = input.get("userId");
		String price = input.get("prices");
		eBusAccount ebtest = ebusAccountRepository.findByIdnumber(id);
		if(ebtest != null){
			BigDecimal orderprice = new BigDecimal(price);
			int ret = ebtest.getBalance().compareTo(orderprice); //first value: balance; second value: orderprice
			if(ret == 2){ // balance not enough
				logger.info("balance not enough!");
				return new ResponseEntity<String>("Balance is not enough!", HttpStatus.BAD_REQUEST);
			}else{ // updata database
				MathContext mc = new MathContext(2);
				BigDecimal newBalance = ebtest.getBalance().subtract(orderprice, mc);
				logger.info("");
				ebusAccountRepository.updateEbusAccountById(id, newBalance);
			}
		}else{
				return new ResponseEntity<String>("User ID does not exists!", HttpStatus.BAD_REQUEST);
		}
		
		return 	new ResponseEntity<String>("order payment success!", HttpStatus.OK);
	}
	
}
