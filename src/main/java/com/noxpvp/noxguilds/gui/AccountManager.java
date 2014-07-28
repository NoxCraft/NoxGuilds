/*
 * Copyright (c) 2014. NoxPVP.com
 *
 * All rights are reserved.
 *
 * You are not permitted to
 * 	Modify
 * 	Redistribute nor distribute
 * 	Sublicense
 *
 * You are required to keep this license header intact
 *
 * You are allowed to use this for non commercial purpose only. This does not allow any ad.fly type links.
 *
 * When using this you are required to
 * 	Display a visible link to noxpvp.com
 * 	For crediting purpose.
 *
 * For more information please refer to the license.md file in the root directory of repo.
 *
 * To use this software with any different license terms you must get prior explicit written permission from the copyright holders.
 */
package com.noxpvp.noxguilds.gui;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.ItemStack;

import com.bergerkiller.bukkit.common.utils.LogicUtil;
import com.bergerkiller.bukkit.common.utils.StringUtil;
import com.noxpvp.noxguilds.economy.AccountBase;
import com.noxpvp.noxguilds.economy.PlayerAccount;
import com.noxpvp.noxguilds.gui.internal.CoreBox;
import com.noxpvp.noxguilds.gui.internal.CoreBoxItem;
import com.noxpvp.noxguilds.gui.internal.TextPrompt;
import com.noxpvp.noxguilds.internal.Result;
import com.noxpvp.noxguilds.util.ItemBuilder;

/**
 * @author ConnorStone
 * 
 */
public class AccountManager extends CoreBox {
	
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	// Static fields
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	
	public static final String	MENU_NAME	= "Account Manager";
	
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	// Instance Fields
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	
	private AccountBase			givenAccount;
	
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	// Constructors
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	
	/**
	 * @param p
	 */
	public AccountManager(Player p, AccountBase bank) {
		this(p, bank, null);
	}
	
	/**
	 * @param p
	 * @param backButton
	 */
	public AccountManager(Player p, AccountBase bank, CoreBox backButton) {
		super(p, MENU_NAME, InventoryType.DISPENSER,
				backButton);
		
		final Player player = p;
		givenAccount = bank;
		final PlayerAccount playerAccount = new PlayerAccount(player);
		
		final ItemStack deposit = new ItemBuilder(Material.GOLD_BLOCK, 1)
				.setName(ChatColor.AQUA + "Click to deposit")
				.build();
		final ItemStack withdraw = new ItemBuilder(Material.IRON_BLOCK, 1)
				.setName(ChatColor.AQUA + "Click to withdraw")
				.build();
		
		addMenuItem(3, new CoreBoxItem(this, withdraw) {
			
			public boolean onClick(InventoryClickEvent click) {
				new TextPrompt(player) {
					
					@Override
					public void onReturn(String[] lines) {
						if (LogicUtil.nullOrEmpty(lines))
							return;
						// NoxGuildLocale.ERROR_INVALID_INPUT.send(player,
						// "You didn't put anything!");TODO FINISH
						
						double amount = 0;
						final String number = StringUtil.join("", lines);
						
						try {
							amount = Double.parseDouble(number);
						} catch (final NumberFormatException e) {
							
						} finally {
							if (amount <= 0)
								// NoxGuildLocale.ERROR_INVALID_INPUT.send(player,
								// "Must be a number");TODO finish
								return;
							else {
								final Result r = playerAccount
										.depositFrom(givenAccount, amount);
								r.send(player);
								
								if (r.isResult()) {
									player.playSound(player.getLocation(),
											Sound.ARROW_HIT, 1, 0);
								} else {
									player.playSound(player.getLocation(),
											Sound.ANVIL_LAND, 1, 0);
								}
							}
						}
						
					}
				}.show();
				
				return true;
			}
		});
		
		addMenuItem(5, new CoreBoxItem(this, deposit) {
			
			public boolean onClick(InventoryClickEvent click) {
				new TextPrompt(player) {
					
					@Override
					public void onReturn(String[] lines) {
						double amount = 0;
						final String number = StringUtil.join("", lines);
						
						try {
							amount = Double.parseDouble(number);
						} catch (final NumberFormatException e) {
							
						} finally {
							if (amount <= 0)
								// NoxGuildLocale.ERROR_INVALID_INPUT.send(player,
								// "Must be a number");TODO finish
								return;
							else {
								final Result r = givenAccount.depositFrom(
										playerAccount, amount);
								r.send(player);
								
								if (r.isResult()) {
									player.playSound(player.getLocation(),
											Sound.ARROW_HIT, 1, 0);
								} else {
									player.playSound(player.getLocation(),
											Sound.ANVIL_LAND, 1, 0);
								}
							}
						}
					}
				}.show();
				
				return true;
			}
		});
	}
	
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	// Static Methods
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	// Instance Methods
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	
	@Override
	protected Object clone() throws CloneNotSupportedException {
		return new AccountManager(getPlayer(), givenAccount);
	}
	
}
