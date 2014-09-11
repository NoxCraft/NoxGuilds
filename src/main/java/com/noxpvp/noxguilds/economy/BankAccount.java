package com.noxpvp.noxguilds.economy;

import net.milkbowl.vault.economy.EconomyResponse;

import com.noxpvp.noxguilds.VaultAdapter;

public class BankAccount extends AccountBase {
	
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	// Instance Fields
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	
	private final String	name;
	
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	// Constructors
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	
	public BankAccount(String name) {
		this.name = name;
	}
	
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	// Instance Methods
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	
	public boolean deposit(double amount) {
		return VaultAdapter.economy.bankDeposit(name, amount).transactionSuccess();
	}
	
	public String getAccountName() {
		return name;
	}
	
	public double getBalance() {
		EconomyResponse r;
		return (r = VaultAdapter.economy.bankBalance(name)) != null ? r.balance : 0D;
	}
	
	public boolean pay(double amount) {
		return VaultAdapter.economy.bankWithdraw(name, amount).transactionSuccess();
	}
}
