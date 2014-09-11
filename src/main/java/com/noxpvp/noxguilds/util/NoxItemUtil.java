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
package com.noxpvp.noxguilds.util;

import java.util.List;

import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.bergerkiller.bukkit.common.utils.LogicUtil;

/**
 * @author ConnorStone
 * 
 */
public class NoxItemUtil {
	
	// ~~~~~~~~~~~~~~~~~ ~~~~~~~~~~~~~~
	// Static fields
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	// Instance Fields
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	// Constructors
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	// Static Methods
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	
	public static ItemStack addLore(ItemStack item, int startingIndex, String... lore) {
		final ItemMeta meta = item.getItemMeta();
		
		if (!LogicUtil.nullOrEmpty(lore)) {
			final List<String> curLore = meta.getLore();
			for (final String s : lore) {
				curLore.addAll(startingIndex++, MessageUtil.convertStringForLore(MessageUtil.parseColor(s)));
			}
			
			meta.setLore(curLore);
			item.setItemMeta(meta);
		}
		
		return item;
	}
	
	public static String getDisplayName(ItemStack identifiableItem) {
		return identifiableItem.getItemMeta().getDisplayName();
	}
	
	public static List<String> getLore(ItemStack item) {
		return item.getItemMeta().getLore();
	}
	
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	// Instance Methods
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
}
