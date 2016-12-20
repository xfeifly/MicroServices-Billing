package io.pivotal.microservices.exceptions;

import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.http.HttpStatus;

/**
 * Allow the controller to return a 404 if an account is not found by simply
 * throwing this exception. The @ResponseStatus causes Spring MVC to return a
 * 404 instead of the usual 500.
 * 
 * @author Ethan
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class MyOrderNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public MyOrderNotFoundException(long usrId) {
		super("No such order by orderId: " + usrId);
	}
	
	public MyOrderNotFoundException(String usrId) {
		super("No such order by usrId: " + usrId);
	}
	
	public MyOrderNotFoundException() {
		super("No orders found");
	}
}