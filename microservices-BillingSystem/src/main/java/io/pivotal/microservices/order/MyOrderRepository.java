package io.pivotal.microservices.order;


import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;

import io.pivotal.microservices.accounts.Account;


public interface MyOrderRepository extends Repository<MyOrder, Long>{
	
	@Query("SELECT count(*) from MyOrder")
	public int countOrders();
	
	/**
	 * Find accounts whose owner name contains the specified string
	 * 
	 * @param partialName
	 *            Any alphabetic string.
	 * @return The list of matching accounts - always non-null, but may be
	 *         empty.
	 */
	public List<Account> findByUsrIdContainingIgnoreCase(String partialName);
	
	

}
