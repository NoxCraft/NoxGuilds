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

import java.util.Collection;

import org.bukkit.entity.Player;

import com.noxpvp.noxguilds.gui.internal.CoreBox;
import com.noxpvp.noxguilds.kingdom.Kingdom;

/**
 * @author ConnorStone
 * 
 */
public abstract class KingdomSelectMenu extends SelectionMenu<Kingdom> {
	
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	// Static fields
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	
	public static final String			MENU_NAME	= "Select a Kingdom to continue";
	
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	// Instance Fields
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	
	private final Collection<Kingdom>	kingdoms;
	
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	// Constructors
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	
	public KingdomSelectMenu(Player p, Collection<Kingdom> kingdoms, CoreBox backbutton) {
		super(p, MENU_NAME, kingdoms, backbutton);
		
		this.kingdoms = kingdoms;
	}
	
	public KingdomSelectMenu(Player p, String menuName, Collection<Kingdom> kingdoms, CoreBox backbutton) {
		super(p, menuName, kingdoms, backbutton);
		
		this.kingdoms = kingdoms;
	}
	
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	// Static Methods
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	// Instance Methods
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	
	@Override
	protected KingdomSelectMenu clone() throws CloneNotSupportedException {
		return new KingdomSelectMenu(getPlayer(), getBox().getName(), kingdoms, null) {
			
			@Override
			public void onSelect(Kingdom selected) {
				onSelect(selected);
			}
		};
	}
	
}
