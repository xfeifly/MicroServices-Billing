package io.pivotal.microservices.eBusiness;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.logging.Logger;
import org.springframework.http.*;
//import org.apache.commons.collections.keyvalue.TiedMapEntry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.springframework.cloud.sleuth.Span;
import org.springframework.cloud.sleuth.SpanAccessor;
import org.springframework.cloud.sleuth.Tracer;
import org.springframework.cloud.sleuth.sampler.AlwaysSampler;
import io.pivotal.microservices.exceptions.AccountNotFoundException;


@RestController
public class eBusinessController {
//	protected Logger logger = Logger.getLogger(eBusinessController.class
//			.getName());
	private static final Log logger = LogFactory.getLog(eBusinessController.class);
	protected HashMap<Integer, Integer> testMap;
	
	protected EbusAccountRepository ebusAccountRepository; 
	private Random random = new Random();

	@Autowired
	private Tracer tracer;
	@Autowired
	private SpanAccessor accessor;
	
	
	@Autowired
	public eBusinessController(EbusAccountRepository ebusAccountRepository) {
		this.testMap = new HashMap<Integer, Integer>();
		this.ebusAccountRepository = ebusAccountRepository;
	}
	
	
	//find eBaccount by eB number
	@RequestMapping("/eBusinessAccount/{eBusAccountNumber}")
	public eBusAccount byNumber(@PathVariable("eBusAccountNumber") String accountNumber) throws InterruptedException {
		Span span = this.tracer.createSpan("http:customTraceEndpoint",
				new AlwaysSampler());
		int millis = this.random.nextInt(1000);
		logger.info(String.format("Sleeping for [%d] millis at byNumber", millis));
		Thread.sleep(millis);
		this.tracer.addTag("random-sleep-millis", String.valueOf(millis));
		logger.info("eBusinessAccounts-service byNumber() invoked: " + accountNumber);
		eBusAccount ebtest = ebusAccountRepository.findByEnumber(accountNumber);
		logger.info("eBusinessAccounts-service byNumber() found: " + ebtest);
		this.tracer.addTag("random-sleep-millis", String.valueOf(1000));

		this.tracer.close(span);

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
		logger.info("eBusinessAccounts-service byENumber() found: " + ebtest);
		if (ebtest == null)
			throw new AccountNotFoundException(IdNumber);
		else {
			return ebtest;
		}
	}
	
	
//	@RequestMapping("/eBusinessAccount/fetchEaccountInfo")
//	public String EBInfoByIdNumber(@PathVariable("IdNumber") String IdNumber){
//		logger.info("ebusiness account EBInfoByIdNumber is invoked");
//		
//	}
	
	//find all eB account
	@RequestMapping(value = "/eBusinessAccount/findAll", method=RequestMethod.GET)
	public @ResponseBody ArrayList<eBusAccount> findAll() throws Exception {

		logger.info("eBusinessAccounts-service findAll() invoked: ");
		
		ArrayList<eBusAccount> ebtest = ebusAccountRepository.findAll();
		
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
				logger.info("Balance is enough! order will be paid!");
				MathContext mc = new MathContext(7);
				BigDecimal newBalance = ebtest.getBalance().subtract(orderprice, mc);
				logger.info(newBalance.toString());
				ebusAccountRepository.updateEbusAccountById(id, newBalance);
			}
		}else{
				return new ResponseEntity<String>("User ID does not exists!", HttpStatus.BAD_REQUEST);
		}
		
		return 	new ResponseEntity<String>("order payment success!", HttpStatus.OK);
	}
	
}
