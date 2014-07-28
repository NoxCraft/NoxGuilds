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

import java.util.ArrayList;
import java.util.List;

import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.util.Vector;

import com.noxpvp.noxguilds.gui.internal.CoreBox;
import com.noxpvp.noxguilds.gui.internal.CoreBoxItem;
import com.noxpvp.noxguilds.gui.internal.PagedCoreBoxRegion;
import com.noxpvp.noxguilds.guild.Guild;
import com.noxpvp.noxguilds.manager.GuildManager;

/**
 * @author ConnorStone
 * 
 */
public class GuildListMenu extends CoreBox {
	
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	// Static fields
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	
	public static final String	MENU_NAME	= "Guild list";
	private static final int	size		= 9;
	
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	// Instance Fields
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	// Constructors
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	
	public GuildListMenu(Player p) {
		super(p, MENU_NAME, size);
		
		final List<Guild> guilds = new ArrayList<Guild>(GuildManager
				.getInstance().getLoadedMap().values());
		final PagedCoreBoxRegion guildsRegion = new PagedCoreBoxRegion(
				this, new Vector(0, 0, 1), 1, 7);
		
		for (final Guild g : guilds) {
			guildsRegion
					.add(new CoreBoxItem(this, g.getIdentifiableItem()) {
						
						public boolean onClick(InventoryClickEvent click) {
							new GuildInfoMenu(getPlayer(), g,
									GuildListMenu.this).show();
							return true;
						}
					});
		}
		
		addMenuItem(0, guildsRegion.getBackArrow());
		addMenuItem(8, guildsRegion.getNextArrow());
		
		guildsRegion.next();
		
	}
	
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	// Static Methods
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	// Instance Methods
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	
	@Override
	protected Object clone() throws CloneNotSupportedException {
		return new GuildListMenu(getPlayer());
	}
	
}
