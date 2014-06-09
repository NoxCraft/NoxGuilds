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

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.util.Vector;

import com.noxpvp.noxguilds.gui.internal.CoreBox;
import com.noxpvp.noxguilds.gui.internal.CoreBoxItem;
import com.noxpvp.noxguilds.gui.internal.ItemRepresentable;
import com.noxpvp.noxguilds.gui.internal.PagedCoreBoxRegion;

/**
 * @author ConnorStone
 * 
 */
public abstract class SelectionMenu<T extends ItemRepresentable> extends
        CoreBox {
	
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	// Static fields
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	
	public static final String	MENU_NAME	= "Selection Menu";
	
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	// Instance Fields
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	
	private List<T>	           items;
	
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	// Constructors
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	
	public SelectionMenu(Player p, List<T> items) {
		this(p, MENU_NAME, items);
	}
	
	public SelectionMenu(Player p, String menuName, List<T> items) {
		this(p, menuName, items, null);
	}
	
	public SelectionMenu(Player p, String menuName, List<T> items,
	        CoreBox backbutton) {
		super(p, menuName, 9, backbutton);
		
		this.items = items;
		
		int size = Math.min(45, Math.max(9,
		        (int) (this.items.size() / 9.0) * 9));
		setBox(Bukkit.getServer()
		        .createInventory(null, size + 9, menuName));
		
		final PagedCoreBoxRegion itemRg = new PagedCoreBoxRegion(this,
		        new Vector(0, 0, 0), size / 9, 9);
		
		size = getBox().getSize();
		addMenuItem(size - 9, itemRg.getBackArrow());
		addMenuItem(getBackButton() != null ? size - 2 : size - 1, itemRg
		        .getNextArrow());
		
		for (final T item : this.items) {
			itemRg.add(new CoreBoxItem(this, item.getIdentifiableItem()) {
				
				public boolean onClick(InventoryClickEvent click) {
					onSelect(item);
					
					return true;
				}
			});
		}
		
		itemRg.next();
		
	}
	
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	// Static Methods
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	// Instance Methods
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	
	public abstract void onSelect(T selected);
	
	@Override
	protected SelectionMenu<T> clone() throws CloneNotSupportedException {
		return new SelectionMenu<T>(getPlayer(), getBox().getName(), items) {
			
			@Override
			public void onSelect(T selected) {
				this.onSelect(selected);
			}
		};
	}
	
}
