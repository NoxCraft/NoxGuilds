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

package com.noxpvp.noxguilds.gui.internal;

import org.bukkit.inventory.ItemStack;

import com.noxpvp.noxguilds.gui.internal.ICoreBox.ICoreBoxItem;

public abstract class CoreBoxItem implements ICoreBoxItem {
	
	private final CoreBox	box;
	private final ItemStack	item;
	
	public CoreBoxItem(CoreBox parent, ItemStack item) {
		this.item = item;
		box = parent;
	}
	
	public ItemStack getItem() {
		return item;
	}
	
	public CoreBox getParentBox() {
		return box;
	}
	
}
