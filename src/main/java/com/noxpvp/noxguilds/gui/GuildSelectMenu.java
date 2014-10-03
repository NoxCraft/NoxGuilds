/*
 * Copyright (c) 2014. NoxPVP.com
 * 
 * All rights are reserved.
 * 
 * You are not permitted to Modify Redistribute nor distribute Sublicense
 * 
 * You are required to keep this license header intact
 * 
 * You are allowed to use this for non commercial purpose only. This does not allow any ad.fly type links.
 * 
 * When using this you are required to Display a visible link to noxpvp.com For crediting purpose.
 * 
 * For more information please refer to the license.md file in the root directory of repo.
 * 
 * To use this software with any different license terms you must get prior explicit written permission from the
 * copyright holders.
 */
package com.noxpvp.noxguilds.gui;

import java.util.List;

import org.bukkit.entity.Player;

import com.noxpvp.noxguilds.gui.internal.CoreBox;
import com.noxpvp.noxguilds.guild.Guild;

/**
 * @author ConnorStone
 * 
 */
public abstract class GuildSelectMenu extends SelectionMenu<Guild> {
	
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	// Static fields
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	
	public static final String MENU_NAME = "Select a Guild to continue";
	
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	// Instance Fields
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	
	private final List<Guild> guilds;
	
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	// Constructors
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	
	public GuildSelectMenu(Player p, List<Guild> guilds, CoreBox backbutton) {
	
		super(p, MENU_NAME, guilds, backbutton);
		
		this.guilds = guilds;
	}
	
	public GuildSelectMenu(Player p, String menuName, List<Guild> guilds, CoreBox backbutton) {
	
		super(p, menuName, guilds, backbutton);
		
		this.guilds = guilds;
	}
	
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	// Static Methods
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	// Instance Methods
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	
	@Override
	protected GuildSelectMenu clone() throws CloneNotSupportedException {
	
		return new GuildSelectMenu(getPlayer(), getBox().getName(), guilds, getBackButton()) {
			
			@Override
			public void onSelect(Guild selected) {
			
				onSelect(selected);
			}
		};
	}
	
}
