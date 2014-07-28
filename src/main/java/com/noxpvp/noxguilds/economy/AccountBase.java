package com.noxpvp.noxguilds.economy;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import com.noxpvp.noxguilds.VaultAdapter;
import com.noxpvp.noxguilds.gui.internal.ItemRepresentable;
import com.noxpvp.noxguilds.internal.Result;
import com.noxpvp.noxguilds.locale.NoxGuildLocale;
import com.noxpvp.noxguilds.util.ItemBuilder;

public abstract class AccountBase implements Account, ItemRepresentable {
	
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	// Instance Methods
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	
	/**
	 * Returns if the account <b>can</b> pay the given amount
	 * 
	 * @param amount
	 * @return
	 */
	public boolean canPay(double amount) {
		return getBalance() >= amount;
	}
	
	/**
	 * Handles a transaction between 2 accounts, returning the success
	 * 
	 * @param receiver
	 * @param amount
	 * @return true if successful, otherwise false
	 */
	public Result depositFrom(AccountBase giver, double amount) {
		return giver.withdrawTo(this, amount);
	}
	
	// Menu item
	public ItemStack getIdentifiableItem() {
		return new ItemBuilder(Material.GOLD_INGOT, 1)
				.setName(
						ChatColor.GOLD
								+ "Balance: "
								+ ChatColor.AQUA
								+ VaultAdapter.economy
										.format(getBalance()))
				.setLore(" ", ChatColor.AQUA + "Click here to deposit",
						ChatColor.AQUA + "or withdraw funds")
				.build();
		
	}
	
	/**
	 * Handles a transaction between 2 accounts, returning the success
	 * 
	 * @param receiver
	 * @param amount
	 * @return true if successful, otherwise false
	 */
	public Result withdrawTo(AccountBase receiver, double amount) {
		if (!canPay(amount))
			return new Result(false, NoxGuildLocale.TRANSFER_FAILED
					.get(getAccountName() + " can't afford to pay "
							+ amount));
		
		if (!pay(amount))
			return new Result(false, NoxGuildLocale.TRANSFER_FAILED
					.get(getAccountName() + " could not pay " + amount));
		
		if (!receiver.deposit(amount)) {
			// refund
			deposit(amount);
			return new Result(false, NoxGuildLocale.TRANSFER_FAILED
					.get(receiver.getAccountName() + " could not deposit "
							+ amount));
		}
		
		return new Result(true, NoxGuildLocale.TRANSFER_SUCCESS
				.get(receiver.getAccountName() + " Recieved " + amount)
				+ " from " + getAccountName());
	}
	
}
