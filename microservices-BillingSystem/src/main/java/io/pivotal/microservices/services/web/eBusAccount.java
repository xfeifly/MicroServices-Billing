package io.pivotal.microservices.services.web;

import java.math.BigDecimal;



import com.fasterxml.jackson.annotation.JsonRootName;


@JsonRootName("eBusAccount")
public class eBusAccount {
	
	private static final long serialVersionUID = 2L;

	public static Long nextId = 3L;
	
	
	protected Long id;

	protected String enumber;
	
	protected String idnumber;
	
	protected String owner;
	
	protected BigDecimal balance;
	
	
	protected static Long getNextId() {
		synchronized (nextId) {
			return nextId++;
		}
	}
	
	protected eBusAccount() {
		balance = BigDecimal.ZERO;
	}

	public eBusAccount(String enumber, String idnumber, String owner) {
		id = getNextId();
		this.enumber = enumber;
		this.idnumber = idnumber;
		this.owner = owner;
		balance = BigDecimal.ZERO;
	}
	


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getEnumber() {
		return enumber;
	}

	public void setEnumber(String enumber) {
		this.enumber = enumber;
	}

	public String getIdnumber() {
		return idnumber;
	}

	public void setIdnumber(String idnumber) {
		this.idnumber = idnumber;
	}

	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public static void setNextId(Long nextId) {
		eBusAccount.nextId = nextId;
	}

	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}

	/**
	 * Set JPA id - for testing and JPA only. Not intended for normal use.
	 * 
	 * @param id
	 *            The new id.
	 */

	public BigDecimal getBalance() {
		return balance.setScale(2, BigDecimal.ROUND_HALF_EVEN);
	}

	public void withdraw(BigDecimal amount) {
		balance.subtract(amount);
	}

	public void deposit(BigDecimal amount) {
		balance.add(amount);
	}


	@Override
	public String toString() {
		return "ID: " + idnumber + " " + enumber + " [" + owner + "]: $" + balance;
	}
}
