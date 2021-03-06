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

package com.noxpvp.noxguilds.manager;

import java.util.List;
import java.util.Map;
import java.util.logging.Level;

import org.bukkit.configuration.file.FileConfiguration;

import com.noxpvp.noxguilds.internal.NoxPlugin;
import com.noxpvp.noxguilds.internal.Persistant;

public interface IManager<T extends Persistant> {
	
	public FileConfiguration getConfig(String name);
	
	public Map<String, T> getLoadedMap();
	
	public List<T> getLoadedValues();
	
	public NoxPlugin getPlugin();
	
	public boolean isLoaded(T object);
	
	public void load();
	
	public void log(Level lv, String msg);
	
	public void save();
	
	public void save(T object);
	
	public void unload(T object);
	
}
