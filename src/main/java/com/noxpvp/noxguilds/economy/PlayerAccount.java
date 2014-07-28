package com.noxpvp.noxguilds.economy;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;

import com.noxpvp.noxguilds.VaultAdapter;

public class PlayerAccount extends AccountBase {
	
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	// Instance Fields
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	
	private final OfflinePlayer	holder;
	
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	// Constructors
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	
	public PlayerAccount(OfflinePlayer player) {
		holder = player;
	}
	
	public PlayerAccount(UUID player) {
		holder = Bukkit.getOfflinePlayer(player);
	}
	
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	// Instance Methods
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	
	public boolean deposit(double amount) {
		return VaultAdapter.economy.depositPlayer(holder, amount)
				.transactionSuccess();
	}
	
	public String getAccountName() {
		String name;
		if ((name = holder.getName()) == null) {
			name = holder.getUniqueId().toString();
		}
		
		return name;
	}
	
	public double getBalance() {
		return VaultAdapter.economy.getBalance(holder);
	}
	
	public boolean pay(double amount) {
		return VaultAdapter.economy.withdrawPlayer(holder, amount)
				.transactionSuccess();
	}
}
