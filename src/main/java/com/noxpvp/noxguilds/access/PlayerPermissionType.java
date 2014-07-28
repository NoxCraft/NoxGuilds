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
package com.noxpvp.noxguilds.access;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import com.noxpvp.noxguilds.gui.internal.ItemRepresentable;
import com.noxpvp.noxguilds.util.ItemBuilder;
import com.noxpvp.noxguilds.util.NoxEnumUtil;

/**
 * @author ConnorStone
 * 
 */
public enum PlayerPermissionType
		implements
		IPermissionType<PlayerPermissionType>,
		ItemRepresentable {
	
	ALL(null, "All permissions for your plots"),
	EDIT(ALL, "All permissions to break and build"),
	BREAK(EDIT, "Specific permission to break"),
	BUILD(EDIT, "Specific permission to build"),
	ACCESS(
			ALL,
			"All permission to access containers, open doors and switch"),
	CONTAINER(ACCESS, "All permissions to open containers"),
	CHEST(CONTAINER, "Specific permission to open chests"),
	DROPPER(CONTAINER, "Specific permission to open droppers"),
	DISPENSER(CONTAINER, "Specific permission to open dispensers"),
	BREWINGSTAND(CONTAINER, "Specific permission to open brewingstands"),
	FURNACE(CONTAINER, "Specific permission to open furnaces"),
	HOPPER(CONTAINER, "Specific permission to open hoppers"),
	ANVIL(CONTAINER, "Specific permission to open anvils"),
	SWITCH(ACCESS, "All permissions to use buttons and levers"),
	BUTTON(SWITCH, "Specific permission to use buttons"),
	LEVER(SWITCH, "Specific permission to use levers"),
	DOOR(ACCESS, "All permissions to open doors"),
	WOODEN_TRAP_DOOR(DOOR, "Specific permission to open wooden trap doors"),
	WOODEN_DOOR(DOOR, "Specific permission to open wooden doors");
	
	private PlayerPermissionType	parent;
	private String					desc;
	
	private PlayerPermissionType(PlayerPermissionType parent,
			String description) {
		this.parent = parent;
		desc = description;
	}
	
	public String getDescription() {
		return desc;
	}
	
	public ItemStack getIdentifiableItem() {
		return new ItemBuilder(Material.BOOK, 1)
				.setName(
						ChatColor.AQUA + NoxEnumUtil.getFriendlyName(this))
				.setLore(ChatColor.GOLD + getDescription())
				.build();
	}
	
	public PlayerPermissionType getParent() {
		return parent;
	}
	
	public boolean isChildOf(PlayerPermissionType parent) {
		return NoxEnumUtil.isChildOf(this, parent);
	}
	
	public boolean isParentOf(PlayerPermissionType child) {
		return NoxEnumUtil.isParentOf(this, child);
	}
}
