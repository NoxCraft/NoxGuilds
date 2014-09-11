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
public enum KingdomPermissionType implements IPermissionType<KingdomPermissionType>, ItemRepresentable {
	
	ALL(null, "All permissions to the kingdom"),
	MANAGE(ALL, "All permissions for managing important stuff, recommended for trusted members only"),
	SET(MANAGE, "All permissions for settings"),
	TAXES(SET, "All permission for setting taxes"),
	TAX_AMOUNT(TAXES, "Specific permission to set the tax amount"),
	TAX_PERCENT(TAXES, "Specific permission to set tax percent on / off"),
	NAME(SET, "Specific permission to set the Kingdom name"),
	BADGE(SET, "Specific permission to set the Kingdom item-badge");
	
	private KingdomPermissionType	parent;
	private String					desc;
	
	private KingdomPermissionType(KingdomPermissionType parent, String description) {
		this.parent = parent;
		desc = description;
	}
	
	public String getDescription() {
		return desc;
	}
	
	public ItemStack getIdentifiableItem() {
		return new ItemBuilder(Material.BOOK, 1).setName(ChatColor.AQUA + NoxEnumUtil.getFriendlyName(this))
			.setLore(ChatColor.GOLD + getDescription()).build();
	}
	
	public KingdomPermissionType getParent() {
		return parent;
	}
	
	public boolean isChildOf(KingdomPermissionType parent) {
		return NoxEnumUtil.isChildOf(this, parent);
	}
	
	public boolean isParentOf(KingdomPermissionType child) {
		return NoxEnumUtil.isParentOf(this, child);
	}
	
}
