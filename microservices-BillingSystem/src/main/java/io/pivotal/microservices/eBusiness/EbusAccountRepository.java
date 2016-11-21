package io.pivotal.microservices.eBusiness;

import java.math.BigDecimal;
import java.util.ArrayList;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;



public interface EbusAccountRepository extends Repository<eBusAccount, Long>{
	
	/**
	 * Find an account with the specified account number.
	 *
	 * @param eBusiness accountNumber
	 * @return The eBusiness account if found, null otherwise.
	 */
	public eBusAccount findByEnumber(String enumber);
	
	public eBusAccount findByIdnumber(String idnumber);
	
	public ArrayList<eBusAccount> findAll();
	
	@Modifying
	@Transactional
    @Query("UPDATE eBusAccount eb SET eb.balance = :balance WHERE eb.enumber = :enumber")
    public int updateEbusAccount(@Param("enumber") String enumber, @Param("balance") BigDecimal balance);
	
	@Modifying
	@Transactional
    @Query("UPDATE eBusAccount eb SET eb.balance = :balance WHERE eb.idnumber = :idnumber")
    public int updateEbusAccountById(@Param("idnumber") String enumber, @Param("balance") BigDecimal balance);
	
	

}
