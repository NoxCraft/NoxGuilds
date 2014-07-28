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
public enum GuildPermissionType
		implements
		IPermissionType<GuildPermissionType>,
		ItemRepresentable {
	
	ALL(null, "All permissions to the guild"),
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
	BREWINGSTAND(CONTAINER, "Specific permission to open brewing stands"),
	FURNACE(CONTAINER, "Specific permission to open furnaces"),
	HOPPER(CONTAINER, "Specific permission to open hoppers"),
	ANVIL(CONTAINER, "Specific permission to open anvils"),
	SWITCH(ACCESS, "All permissions to use buttons and levers"),
	BUTTON(SWITCH, "Specific permission to use buttons"),
	LEVER(SWITCH, "Specific permission to use levers"),
	DOOR(ACCESS, "All permissions to open doors"),
	WOODEN_TRAP_DOOR(DOOR, "Specific permission to open wooden trap doors"),
	WOODEN_DOOR(DOOR, "Specific permission to open wooden doors"),
	TELEPORT(
			ACCESS,
			"All permission for teleporting in and out of the guild"),
	GUILD_SPAWN(
			TELEPORT,
			"Specific permission to use the guild spawn command to spawn to teleport to the guild spawn point"),
	TP_INTO(
			TELEPORT,
			"Specific permission to teleport into the guild in anyway besides guild spawn"),
	TP_OUTOF(
			TELEPORT,
			"Specific permission to teleport out of the guild in anyway"),
	MANAGE(
			ALL,
			"All permissions for managing important stuff, recommended for trusted members only"),
	BANK(MANAGE, "All permission to manage the guild bank"),
	DEPOSIT(
			BANK,
			"Specific permission to deposit money into the guild bank"),
	WITHDRAW(
			BANK,
			"Specific permission to withdraw money out of the guild bank"),
	TERRITORY(MANAGE, "All permissions for editing guild territory"),
	CLAIM(
			TERRITORY,
			"Specific permission to claim territory for the guild"),
	UNCLAIM(TERRITORY, "Specific permission to unclaim guild territory"),
	RECRUIT(MANAGE, "All permissions for managing guild members"),
	ADD_MEMBER(RECRUIT, "Specific permission to add members to the guild"),
	KICK_MEMBER(
			RECRUIT,
			"Specific permission to kick members from the guild"),
	PERMS(
			MANAGE,
			"All permissions to edit guild permissions, HANDLE WITH CARE"),
	ADD_PERM(PERMS, "Specific permission to add permissions"),
	REMOVE_PERM(PERMS, "Specific permission to remove permissions"),
	ZONING(MANAGE, "All permissions for editing guild zones"),
	ADD_ZONE(ZONING, "Specific permission for adding guild zones"),
	REMOVE_ZONE(ZONING, "Specific permission for removing guild zones"),
	EDIT_ZONE(
			ZONING,
			"Specific permission for editing any zone, with the exception of removing any"),
	SET(MANAGE, "All permissions for settings"),
	TAXES(SET, "All permission for setting taxes"),
	TAX_AMOUNT(TAXES, "Specific permission to set the tax amount"),
	TAX_PERCENT(TAXES, "Specific permission to set tax percent on / off"),
	NAME(SET, "Specific permission to set the Kingdom name"),
	BADGE(SET, "Specific permission to set the Kingdom item-badge");
	
	private GuildPermissionType	parent;
	private String				desc;
	
	private GuildPermissionType(GuildPermissionType parent,
			String description) {
		this.parent = parent;
		desc = description;
	}
	
	public String getDescription() {
		return desc;
	}
	
	public ItemStack getIdentifiableItem() {
		return new ItemBuilder(
				Material.BOOK, 1)
				.setName(
						ChatColor.AQUA + NoxEnumUtil.getFriendlyName(this))
				.setLore(ChatColor.GOLD + getDescription())
				.build();
	}
	
	public GuildPermissionType getParent() {
		return parent;
	}
	
	public boolean isChildOf(GuildPermissionType parent) {
		return NoxEnumUtil.isChildOf(this, parent);
	}
	
	public boolean isParentOf(GuildPermissionType child) {
		return NoxEnumUtil.isParentOf(this, child);
	}
	
}
