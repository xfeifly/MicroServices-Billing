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

	@Column(name = "enumber")
	protected String enumber;
	
	@Column(name = "idnumber")
	protected String idnumber;
	
	@Column(name = "name")
	protected String owner;
	
	@Column(name = "balance")
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
