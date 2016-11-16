package io.pivotal.microservices.services.web;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonRootName;

@JsonRootName("eBusAccount")
public class eBusAccount {
	
	protected Long id;
	protected String enumber;
	protected String idnumber;
	protected String owner;
	protected BigDecimal balance;
	
	protected eBusAccount() {
		balance = BigDecimal.ZERO;
	}

	public long getId() {
		return id;
	}

	/**
	 * Set JPA id - for testing and JPA only. Not intended for normal use.
	 * 
	 * @param id
	 *            The new id.
	 */
	protected void setId(long id) {
		this.id = id;
	}
	public String getNumber() {
		return enumber;
	}

	protected void setNumber(String accountNumber) {
		this.enumber = accountNumber;
	}

	public String getOwner() {
		return owner;
	}

	protected void setOwner(String owner) {
		this.owner = owner;
	}

	public BigDecimal getBalance() {
		return balance.setScale(2, BigDecimal.ROUND_HALF_EVEN);
	}

	protected void setBalance(BigDecimal value) {
		balance = value;
		balance.setScale(2, BigDecimal.ROUND_HALF_EVEN);
	}

	@Override
	public String toString() {
		return enumber + " [" + owner + "]: $" + balance;
	}

}
