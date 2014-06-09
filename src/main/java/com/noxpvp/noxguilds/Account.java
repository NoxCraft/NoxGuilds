package com.noxpvp.noxguilds;

public interface Account {
	
	/**
	 * Adds money to this account
	 * 
	 * @param amount
	 * @return success
	 */
	public abstract boolean deposit(double amount);
	
	/**
	 * Gets the name of this account
	 * 
	 * @return
	 */
	public abstract String getAccountName();
	
	/**
	 * Gets the balance for this economy object
	 * 
	 * @return the balance
	 */
	public abstract double getBalance();
	
	/**
	 * Take money from this account
	 * 
	 * @param amount
	 * @return success
	 */
	public abstract boolean pay(double amount);
	
}
