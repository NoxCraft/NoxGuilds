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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

/**
 * @author ConnorStone
 * 
 */
public class ItemBuilder {
	
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	// Static fields
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	// Instance Fields
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	
	private final ItemStack	item;
	private ItemMeta		meta;
	
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	// Constructors
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	
	public ItemBuilder(ItemStack item) {
		this.item = item;
		meta = item.getItemMeta();
	}
	
	public ItemBuilder(Material type) {
		this(new ItemStack(type, 1));
	}
	
	public ItemBuilder(Material type, int amount) {
		this(new ItemStack(type, amount, (short) 0));
	}
	
	public ItemBuilder(Material type, int amount, short damageValue) {
		this(new ItemStack(type, amount, damageValue));
	}
	
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	// Static Methods
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	// Instance Methods
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	
	public ItemBuilder addLore(List<String> lore) {
		final List<String> addition = meta.getLore();
		addition.addAll(lore);
		
		meta.setLore(addition);
		return this;
	}
	
	public ItemBuilder addLore(String... lore) {
		addLore(Arrays.asList(lore));
		return this;
	}
	
	public ItemStack build() {
		item.setItemMeta(meta);
		return item.clone();
	}
	
	public ItemBuilder setLore(Collection<String> lore) {
		final List<String> formated = new ArrayList<String>();
		
		for (final String s : lore) {
			formated.addAll(MessageUtil.convertStringForLore(MessageUtil
					.parseColor(s)));
		}
		
		meta.setLore(formated);
		return this;
	}
	
	public ItemBuilder setLore(String... lore) {
		setLore(Arrays.asList(lore));
		return this;
	}
	
	public ItemBuilder setName(String name) {
		meta.setDisplayName(name);
		return this;
	}
	
	public ItemBuilder withMeta(ItemMeta meta) {
		this.meta = meta.clone();
		return this;
	}
	
}
