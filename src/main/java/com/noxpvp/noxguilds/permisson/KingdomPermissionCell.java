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
package com.noxpvp.noxguilds.permisson;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import com.noxpvp.noxguilds.access.KingdomAccessLevel;
import com.noxpvp.noxguilds.access.KingdomPermissionType;
import com.noxpvp.noxguilds.util.ItemBuilder;
import com.noxpvp.noxguilds.util.NoxEnumUtil;

/**
 * @author ConnorStone
 * 
 */
public class KingdomPermissionCell extends PermissionCell<KingdomAccessLevel, KingdomPermissionType> {
	
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	// Static fields
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	// Instance Fields
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	// Constructors
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	
	public KingdomPermissionCell() {
	
		super();
	}
	
	// Deserialize
	public KingdomPermissionCell(Map<String, Object> data) {
	
		super(data);
	}
	
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	// Static Methods
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	// Instance Methods
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	
	public ItemStack getIdentifiableItem() {
	
		final List<String> lore = new ArrayList<String>();
		
		for (final KingdomAccessLevel level : KingdomAccessLevel.values()) {
			lore.add(ChatColor.GOLD + NoxEnumUtil.getFriendlyName(level) + ": ");
			for (final KingdomPermissionType perm : KingdomPermissionType.values()) {
				if (get(level, perm)) {
					lore.add("   " + ChatColor.GOLD + NoxEnumUtil.getFriendlyName(perm) + ": " + ChatColor.GREEN
						+ "TRUE");
				}
			}
		}
		
		return new ItemBuilder(Material.IRON_DOOR, 1).setName(ChatColor.GOLD + "Permissions").setLore(lore).build();
	}
	
}
