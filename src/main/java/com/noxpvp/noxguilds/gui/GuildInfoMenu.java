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

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;

import com.noxpvp.noxguilds.gui.internal.CoreBox;
import com.noxpvp.noxguilds.gui.internal.CoreBoxItem;
import com.noxpvp.noxguilds.guild.Guild;
import com.noxpvp.noxguilds.kingdom.Kingdom;
import com.noxpvp.noxguilds.manager.GuildPlayerManager;

/**
 * @author ConnorStone
 * 
 */
public class GuildInfoMenu extends CoreBox {
	
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	// Static fields
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	
	public static final String	MENU_NAME	= "Guild Menu";
	public static final int	   size	      = 27;
	
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	// Instance Fields
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	
	private Guild	           g;
	
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	// Constructors
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	
	public GuildInfoMenu(Player p, Guild guild) {
		this(p, guild, null);
	}
	
	public GuildInfoMenu(Player p, Guild guild, CoreBox backbutton) {
		super(p, MENU_NAME, size, backbutton);
		
		g = guild;
		final boolean inGuild = g.hasMember(GuildPlayerManager
		        .getInstance().getFromPlayer(p));
		
		if (inGuild) {
			setBox(Bukkit.getServer().createInventory(null, 4 * 9,
			        MENU_NAME));
		}
		
		// Main item
		addMenuItem(4, new CoreBoxItem(this, g.getIdentifiableItem()) {
			
			public boolean onClick(InventoryClickEvent click) {
				return false;
			}
		});
		
		// Bank
		addMenuItem(18, new CoreBoxItem(this, g.getAccountItem()) {
			
			public boolean onClick(InventoryClickEvent click) {
				new AccountManager(getPlayer(), g.getAccount(),
				        GuildInfoMenu.this).show();
				return true;
			}
		});
		
		// Members
		addMenuItem(19, new CoreBoxItem(this, g.getMembersItem()) {
			
			public boolean onClick(InventoryClickEvent click) {
				// TODO guild members info menu
				return true;
			}
		});
		
		// Territory
		addMenuItem(22, new CoreBoxItem(this, g.getTerritoryItem()) {
			
			public boolean onClick(InventoryClickEvent click) {
				// TODO territory menu
				return true;
			}
		});
		
		// Kingdom
		addMenuItem(23, new CoreBoxItem(this, g.getKingdomsItem()) {
			
			public boolean onClick(InventoryClickEvent click) {
				if (!g.hasKingdom())
					return false;
				
				new KingdomSelectMenu(getPlayer(), g.getKingdoms(),
				        GuildInfoMenu.this) {
					
					@Override
					public void onSelect(Kingdom selected) {
						new KingdomInfoMenu(getPlayer(), selected,
						        GuildInfoMenu.this).show();
					}
				};
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
		return new GuildInfoMenu(getPlayer(), g);
	}
	
}
