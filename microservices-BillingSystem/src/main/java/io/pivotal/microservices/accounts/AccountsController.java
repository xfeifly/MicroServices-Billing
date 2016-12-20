package io.pivotal.microservices.accounts;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import org.springframework.aop.ThrowsAdvice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import io.pivotal.microservices.eBusiness.EbusAccountRepository;
import io.pivotal.microservices.eBusiness.eBusAccount;
import io.pivotal.microservices.exceptions.*;

@RestController
public class AccountsController {

	@Autowired
	@LoadBalanced
	protected RestTemplate restTemplate;
	
	protected Logger logger = Logger.getLogger(AccountsController.class
			.getName());
	protected AccountRepository accountRepository;
	//protected EbusAccountRepository EbusAccountRepository;

	/**
	 * Create an instance plugging in the respository of Accounts.
	 * 
	 * @param accountRepository
	 *            An account repository implementation.
	 */
	@Autowired
	public AccountsController(AccountRepository accountRepository) {
		this.accountRepository = accountRepository;

		logger.info("AccountRepository says system has "
				+ accountRepository.countAccounts() + " accounts");
	}

	/**
	 * Fetch an account with the specified account number.
	 * 
	 * @param accountNumber
	 *            A numeric, 9 digit account number.
	 * @return The account if found.
	 * @throws AccountNotFoundException
	 *             If the number is not recognised.
	 */
	
	@RequestMapping("/accounts/{accountNumber}")
	public Account byNumber(@PathVariable("accountNumber") String accountNumber) {

		logger.info("accounts-service byNumber() invoked: " + accountNumber);
		Account account = accountRepository.findByIdnumber(accountNumber);
		logger.info("accounts-service byNumber() found: " + account);

		if (account == null)
			throw new AccountNotFoundException(accountNumber);
		else {
			return account;
		}
	}
	
	
	@RequestMapping("/accounts/showEBusiness/{accountNumber}")
	public eBusAccount showEBAccount(@PathVariable("accountNumber") String accountNumber) {

		logger.info("accounts-service showEBAccountbyNumber() invoked: " + accountNumber);
		eBusAccount account = restTemplate.getForObject("http://EBUSINESS-SERVICE" + "/eBusinessAccount/findById/{accountNumber}",
				eBusAccount.class, accountNumber);

		if (account == null)
			throw new AccountNotFoundException(accountNumber);
		else {
			return account;
		}
	}
	
	
	
	@RequestMapping("/accounts/showAll")
	public eBusAccount[] showAllEBAccounts() throws Exception{
		logger.info("showAllEBAccounts() method in AccountController is invoked!");
		
		String ebusFindAllURL = "http://EBUSINESS-SERVICE" + "/eBusinessAccount/findAll";
		ResponseEntity<eBusAccount[]> responseEntity = restTemplate.getForEntity(ebusFindAllURL, eBusAccount[].class);
		eBusAccount[] eBAccounts = responseEntity.getBody();
		
		logger.info("try to find all the eBusAccounts from eBusiness Service" + eBAccounts);
		
		MediaType contentType = responseEntity.getHeaders().getContentType();
		HttpStatus statusCode = responseEntity.getStatusCode();
		
		if (eBAccounts == null)
			throw new Exception();
		else {
			return eBAccounts;
		}
 	}
	
	@RequestMapping("/accounts/showAllOrders")
	public MyOrder[] showAllOrderInfo() throws Exception{
		logger.info("showAllOrderInfo() method in AccountController is invoked!");
		String ebusFindAllURL = "http://ORDER-SERVICE" + "/orders/all";
		ResponseEntity<MyOrder[]> responseEntity = restTemplate.getForEntity(ebusFindAllURL, MyOrder[].class);
		MyOrder[] orderInfo = responseEntity.getBody();
		logger.info("try to find all the order history from MyOrder Service" + orderInfo);
		
		MediaType contentType = responseEntity.getHeaders().getContentType();
		HttpStatus statusCode = responseEntity.getStatusCode();
		if (orderInfo == null)
			throw new Exception();
		else {
			return orderInfo;
		}
	}
	
	/**
	 * Fetch accounts with the specified name. A partial case-insensitive match
	 * is supported. So <code>http://.../accounts/owner/a</code> will find any
	 * accounts with upper or lower case 'a' in their name.
	 * 
	 * @param partialName
	 * @return A non-null, non-empty set of accounts.
	 * @throws AccountNotFoundException
	 *             If there are no matches at all.
	 */
	@RequestMapping("/accounts/owner/{name}")
	public List<Account> byOwner(@PathVariable("name") String partialName) {
		logger.info("accounts-service byOwner() invoked: "
				+ accountRepository.getClass().getName() + " for "
				+ partialName);

		List<Account> accounts = accountRepository
				.findByOwnerContainingIgnoreCase(partialName);
		logger.info("accounts-service byOwner() found: " + accounts);

		if (accounts == null || accounts.size() == 0)
			throw new AccountNotFoundException(partialName);
		else {
			return accounts;
		}
	}
}
