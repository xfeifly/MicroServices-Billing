package io.pivotal.microservices.eBusiness;

import org.springframework.data.repository.Repository;


public interface EbusAccountRepository extends Repository<eBusAccount, Long>{
	
	/**
	 * Find an account with the specified account number.
	 *
	 * @param eBusiness accountNumber
	 * @return The eBusiness account if found, null otherwise.
	 */
	public eBusAccount findByNumber(String number);
	

}
