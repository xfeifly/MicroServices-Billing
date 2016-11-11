package io.pivotal.microservices.eBusiness;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "E_ACCOUNT")
public class eBusAccount implements Serializable{
	
	private static final long serialVersionUID = 2L;

	public static Long nextId = 3L;
	
	@Id
	protected Long id;

	protected String number;
	
	@Column(name = "name")
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

	public eBusAccount(String number, String owner) {
		id = getNextId();
		this.number = number;
		this.owner = owner;
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
		return number;
	}

	protected void setNumber(String accountNumber) {
		this.number = accountNumber;
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

	public void withdraw(BigDecimal amount) {
		balance.subtract(amount);
	}

	public void deposit(BigDecimal amount) {
		balance.add(amount);
	}


	@Override
	public String toString() {
		return number + " [" + owner + "]: $" + balance;
	}
}
