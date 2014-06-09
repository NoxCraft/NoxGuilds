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

package com.noxpvp.noxguilds.manager;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.UUID;
import java.util.logging.Level;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import com.bergerkiller.bukkit.common.ModuleLogger;
import com.noxpvp.noxguilds.internal.Persistant;

public abstract class BaseManager<T extends Persistant> implements
        IManager<T> {
	
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	// Instance Fields
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	
	private final ModuleLogger	logger;
	private final Class<T>	   typeClass;
	private final String	   saveFolder;
	private File	           folder;
	private final Map<UUID, T>	loadedCache;
	
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	// Constructors
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	
	public BaseManager(Class<T> type, String saveFolderPath) {
		this.typeClass = type;
		this.saveFolder = saveFolderPath;
		this.loadedCache = new HashMap<UUID, T>();
		
		logger = new ModuleLogger(getPlugin(), typeClass.getName()
		        + "Manager");
	}
	
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	// Instance Methods
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	
	public FileConfiguration getConfig(String name) {
		final File configFile = new File(getFile(), name);
		if (!configFile.exists()) {
			try {
				configFile.createNewFile();
			} catch (final IOException e) {
			}
		}
		
		final FileConfiguration configRet = YamlConfiguration
		        .loadConfiguration(configFile);
		
		return configRet;
	}
	
	public FileConfiguration getConfig(T object) {
		return getConfig(object.getPersistentID().toString() + ".yml");
	}
	
	public File getFile() {
		if (folder == null) {
			folder = new File(getPlugin().getDataFolder(), saveFolder);
			if (!folder.exists()) {
				getPlugin().getDataFolder().mkdirs();
			}
			
			folder.mkdirs();
		}
		return folder;
	}
	
	public Map<UUID, T> getLoadeds() {
		return Collections.unmodifiableMap(loadedCache);
	}
	
	public boolean isLoaded(T object) {
		return loadedCache.containsValue(object);
	}
	
	public void loadObject(T object) {
		if (!loadedCache.containsKey(object.getPersistentID())) {
			loadedCache.put(object.getPersistentID(), object);
		}
	}
	
	public void log(Level lv, String msg) {
		logger.log(lv, msg);
	}
	
	public void save() {
		for (final T loaded : loadedCache.values()) {
			save(loaded);
		}
	}
	
	public void save(T object) {
		save(object, object.getPersistentID());
		
	}
	
	public void unload() {
		loadedCache.clear();
	}
	
	public void unload(T object) {
		loadedCache.remove(object.getPersistentID());
		
	}
	
	public void unloadAndSave(T object) {
		if (isLoaded(object)) {
			save(object);
			unload(object);
		}
	}
	
	public void unloadAndSaveAll() {
		final Iterator<T> iterate = loadedCache.values().iterator();
		while (iterate.hasNext()) {
			save(iterate.next());
			iterate.remove();
		}
	}
	
	protected T get(UUID arg) {
		T it;
		if ((it = loadedCache.get(arg)) != null)
			return it;
		else
			return load(arg);
	}
	
	protected T getIfLoaded(UUID id) {
		T object;
		if ((object = loadedCache.get(id)) != null)
			return object;
		
		return null;
	}
	
	protected boolean isLoaded(String key) {
		return loadedCache.containsKey(key);
	}
	
	protected T load(T object) {
		return load(object.getPersistentID());
	}
	
	@SuppressWarnings("unchecked")
	protected T load(UUID path) {
		T created = null;
		
		try {
			created = (T) getConfig(path.toString() + ".yml").get(
			        path.toString());
		} catch (final ClassCastException e) {
		}
		
		if (created != null) {
			loadedCache.put(created.getPersistentID(), created);
		}
		
		return created;
	}
	
	protected void save(T object, UUID id) {
		final String ymlName = id.toString() + ".yml";
		final File file = new File(getFile(), ymlName);
		
		final FileConfiguration datafile = getConfig(ymlName);
		datafile.set(id.toString(), object);
		
		try {
			datafile.save(file);
		} catch (final IOException e) {
		}
	}
	
	protected void unload(UUID arg) {
		T object;
		if ((object = getIfLoaded(arg)) != null) {
			unload(object);
		}
	}
	
}
