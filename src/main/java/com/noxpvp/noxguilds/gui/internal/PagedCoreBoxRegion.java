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

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

import com.noxpvp.noxguilds.internal.Cycler;
import com.noxpvp.noxguilds.util.ItemBuilder;

/**
 * @author ConnorStone
 * 
 */
public class PagedCoreBoxRegion extends CoreBoxRegion {
	
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	// Static fields
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	// Instance Fields
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	
	private Cycler<CoreBoxItem>	items;
	private int					size;
	private CoreBoxItem			back, next;
	
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	// Constructors
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	
	/**
	 * @param parent
	 * @param topLeft
	 * @param height
	 * @param width
	 */
	public PagedCoreBoxRegion(CoreBox parent, Vector topLeft, int height, int width) {
		super(parent, topLeft, height, width);
		
		size = height * width;
		items = new Cycler<CoreBoxItem>();
		
		final ItemStack arrowBack = new ItemBuilder(Material.ARROW, 1).setName(ChatColor.AQUA + "Previous Page")
			.build();
		final ItemStack arrowNext = new ItemBuilder(Material.ARROW, 1).setName(ChatColor.AQUA + "Next Page").build();
		
		next = new CoreBoxItem(parent, arrowNext) {
			
			public boolean onClick(InventoryClickEvent click) {
				next();
				return true;
			}
		};
		
		back = new CoreBoxItem(parent, arrowBack) {
			
			public boolean onClick(InventoryClickEvent click) {
				previous();
				return true;
			}
		};
		
	}
	
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	// Static Methods
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	// Instance Methods
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	
	@Override
	public boolean add(CoreBoxItem item) {
		items.add(item);
		
		return true;
	};
	
	public CoreBoxItem getBackArrow() {
		return back;
	}
	
	public CoreBoxItem getNextArrow() {
		return next;
	}
	
	public void next() {
		clearRegion();
		
		super.add(items.next());
		for (int i = 0; i < size - 1 && !items.isAtEnd(); i++) {
			super.add(items.next());
		}
		
	}
	
	public void previous() {
		clearRegion();
		
		super.add(items.previous());
		for (int i = 0; i < size - 1 && !items.isAtStart(); i++) {
			super.add(items.previous());
		}
		
	}
	
}
