package io.pivotal.microservices.order;


import java.math.BigDecimal;
import java.util.List;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import io.pivotal.microservices.exceptions.MyOrderNotFoundException;




@RestController//BUG
public class MyOrderController {
	
	protected Logger logger = Logger.getLogger(MyOrderController.class
			.getName());
	protected MyOrderRepository myOrderRepository;
	@Autowired
	protected MyOrderService myOrderService;
	
	
	@Autowired
	public MyOrderController(MyOrderRepository myOrderRepository, MyOrderService myOrderService) {
		this.myOrderRepository = myOrderRepository;
		this.myOrderService = myOrderService;
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
	@RequestMapping("/orders/users/{usrId}")
	public List<MyOrder> byUsrId(@PathVariable("usrId") String usrId) {

		logger.info("order-service byUsrId() invoked: " + usrId);
		List<MyOrder> myorderList = myOrderRepository.findByUsrId(usrId);
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
		logger.info("order-service allOrders() found: " + myorderList);
		if (myorderList == null)
			throw new MyOrderNotFoundException();
		else {
			return myorderList;
		}
	}
	
	
//	@RequestMapping(value = "/orders/User/{usrId}+{productName}", method = RequestMethod.PUT)
//	public void createNewOrder(@PathVariable(value = "usrId") String Id, @PathVariable(value = "productName") String productName) {
//		
//	}
	
	@RequestMapping(value = "/orders/users/{usrId}", method = RequestMethod.PUT)
	public void updateOrderbyUsrId(@PathVariable("usrId") String usrId) {

		logger.info("order-service updatedbyUsrId() invoked: " + usrId);
		int result = myOrderRepository.setFixedUserIdFor(usrId);
		logger.info("order-service updateUsrId() result : " + result);	
	}

	@RequestMapping(value = "/orders/pay/{orderId} ", method = RequestMethod.PUT) 
	public void payOrderByOrderId(@PathVariable("orderId") Long orderId) {
		
		//find orderId
		logger.info("order-service payOrderByOrderId() invoked: " + orderId);
		MyOrder myorder = myOrderRepository.findByid(orderId);
		logger.info("order-service payOrderByOrderId() found: " + myorder);
		if (myorder == null) {
			throw new MyOrderNotFoundException(orderId);
		}
		String usrId = myorder.getUsrId();
		BigDecimal price = myorder.getPrice();
		
		//sending http put request;
		boolean result = myOrderService.payOrder(usrId, price);
		if (result) {
			logger.info("order-service payOrder() succeed: " + orderId);
			
		} else {
			logger.info("order-service payOrder() failed: " + orderId);
		}
		
	}
		
	
}
