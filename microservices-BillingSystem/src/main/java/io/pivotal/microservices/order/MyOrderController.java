package io.pivotal.microservices.order;


import java.util.List;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.pivotal.microservices.exceptions.MyOrderNotFoundException;




@RestController//BUG
public class MyOrderController {
	
	protected Logger logger = Logger.getLogger(MyOrderController.class
			.getName());
	protected MyOrderRepository myOrderRepository;
	
	
	@Autowired
	public MyOrderController(MyOrderRepository myOrderRepository) {
		this.myOrderRepository = myOrderRepository;

		logger.info("MyOrderRepository says system has "
				+ myOrderRepository.countOrders() + " orders");
	}
/**
 * fetch order by orderId
 * @param orderId
 * @return
 */
	@RequestMapping("/orders/{orderId}")
	public MyOrder byOrderId(@PathVariable("orderId") Long orderId) {

		logger.info("order-service byOrderId() invoked: " + orderId);
		MyOrder myorder = myOrderRepository.findByid(orderId);
		logger.info("order-service byOrderId() found: " + myorder);

		if (myorder == null)
			throw new MyOrderNotFoundException(orderId);
		else {
			return myorder;
		}
	}
	
	/**
	 * fetch order by usrId
	 * @param usrId
	 * @return
	 */
	@RequestMapping("/orders/uers/{ursId}")
	public List<MyOrder> byUsrId(@PathVariable("usrId") String usrId) {

		logger.info("order-service byUsrId() invoked: " + usrId);
		List<MyOrder> myorderList = myOrderRepository.findByusrId(usrId);
		logger.info("order-service byUsrId() found: " + myorderList);

		if (myorderList == null)
			throw new MyOrderNotFoundException(usrId);
		else {
			return myorderList;
		}
	}
	
	@RequestMapping("/orders/all")
	public List<MyOrder> allOrders() {
		

		logger.info("order-service allOrders() invoked: ");
		List<MyOrder> myorderList = myOrderRepository.findAll();
		logger.info("order-service byUsrId() found: " + myorderList);
		if (myorderList == null)
			throw new MyOrderNotFoundException();
		else {
			return myorderList;
		}
	}

//	/**
//	 * Fetch accounts with the specified name. A partial case-insensitive match
//	 * is supported. So <code>http://.../accounts/owner/a</code> will find any
//	 * accounts with upper or lower case 'a' in their name.
//	 * 
//	 * @param partialName
//	 * @return A non-null, non-empty set of accounts.
//	 * @throws AccountNotFoundException
//	 *             If there are no matches at all.
//	 */
//	@RequestMapping("/orders/names/{partialName}")
//	public List<MyOrder> byName(@PathVariable("partialName") String partialName) {
//		logger.info("order-service byName() invoked: "
//				+ myOrderRepository.getClass().getName() + " for "
//				+ partialName);
//
//		List<MyOrder> orders = myOrderRepository
//				.findByNameContainingIgnoreCase(partialName);
//		logger.info("accounts-service byOwner() found: " + accounts);
//
//		if (accounts == null || accounts.size() == 0)
//			throw new AccountNotFoundException(partialName);
//		else {
//			return accounts;
//		}
//	}
//
}
