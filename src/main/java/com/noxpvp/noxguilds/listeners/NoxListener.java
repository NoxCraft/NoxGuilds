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

package com.noxpvp.noxguilds.listeners;

import org.bukkit.Bukkit;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;

import com.noxpvp.noxguilds.internal.NoxPlugin;

public abstract class NoxListener<T extends NoxPlugin> implements Listener {
	
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	// Instance Fields
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	
	private final T plugin;
	private boolean isRegistered;
	private final PluginManager pm;
	
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	// Constructors
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	
	public NoxListener(T plugin) {
	
		this.plugin = plugin;
		this.pm = Bukkit.getPluginManager();
	}
	
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	// Instance Methods
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	
	public T getPlugin() {
	
		return this.plugin;
	}
	
	public boolean isRegistered() {
	
		return isRegistered;
	}
	
	public void register() {
	
		if (isRegistered)
			return;
		
		pm.registerEvents(this, plugin);
		isRegistered = true;
	}
	
	public void unregister() {
	
		HandlerList.unregisterAll(this);
		isRegistered = false;
	}
}
