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

import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;

import com.noxpvp.noxguilds.gui.internal.CoreBox;
import com.noxpvp.noxguilds.gui.internal.CoreBoxItem;
import com.noxpvp.noxguilds.kingdom.Kingdom;

/**
 * @author ConnorStone
 * 
 */
public class KingdomInfoMenu extends CoreBox {
	
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	// Static fields
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	
	public static final String	MENU_NAME	= "Kingdom Info";
	
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	// Instance Fields
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	
	private Kingdom	           kingdom;
	
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	// Constructors
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	
	public KingdomInfoMenu(Player p, Kingdom k) {
		this(p, k, null);
	}
	
	public KingdomInfoMenu(Player p, Kingdom k, CoreBox backbutton) {
		super(p, MENU_NAME, 2);
		
		kingdom = k;
		
		addMenuItem(4, new CoreBoxItem(this, k.getIdentifiableItem()) {
			
			public boolean onClick(InventoryClickEvent click) {
				return false;
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
	protected KingdomInfoMenu clone() throws CloneNotSupportedException {
		return new KingdomInfoMenu(getPlayer(), kingdom);
	}
	
}
