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

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.logging.Level;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import com.bergerkiller.bukkit.common.ModuleLogger;
import com.noxpvp.noxguilds.internal.Persistant;

public abstract class BaseManager<T extends Persistant> implements IManager<T> {
	
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	// Instance Fields
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	
	private final ModuleLogger logger;
	private final Class<T> typeClass;
	private final String saveFolder;
	private File folder;
	private final Map<String, T> loadedCache;
	
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	// Constructors
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	
	public BaseManager(Class<T> type, String saveFolderPath) {
	
		this.typeClass = type;
		this.saveFolder = saveFolderPath;
		this.loadedCache = new HashMap<String, T>();
		
		logger = new ModuleLogger(getPlugin(), getClass().getName());
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
		
		final FileConfiguration configRet = YamlConfiguration.loadConfiguration(configFile);
		
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
	
	public Map<String, T> getLoadedMap() {
	
		return Collections.unmodifiableMap(loadedCache);
	}
	
	public List<T> getLoadedValues() {
	
		return Collections.unmodifiableList(new ArrayList<T>(loadedCache.values()));
	}
	
	public boolean isLoaded(T object) {
	
		return loadedCache.containsValue(object);
	}
	
	public void loadObject(T object) {
	
		if (!loadedCache.containsKey(object.getPersistentID())) {
			loadedCache.put(object.getPersistentStringID(), object);
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
	
		// final Iterator<T> iterate = loadedCache.values().iterator();
		// while (iterate.hasNext()) {
		// save(iterate.next());
		// iterate.remove();
		// }
		
		final Collection<T> values = new ArrayList<T>(loadedCache.values());
		for (final T v : values) {
			unloadAndSave(v);
		}
	}
	
	protected T get(String arg) {
	
		T it;
		if ((it = loadedCache.get(arg)) != null)
			return it;
		else
			return load(arg);
	}
	
	protected T get(UUID arg) {
	
		return get(arg.toString());
	}
	
	protected T getIfLoaded(String id) {
	
		T object;
		if ((object = loadedCache.get(id)) != null)
			return object;
		
		return null;
	}
	
	protected T getIfLoaded(UUID id) {
	
		return getIfLoaded(id.toString());
	}
	
	protected boolean isLoaded(String key) {
	
		return loadedCache.containsKey(key);
	}
	
	@SuppressWarnings("unchecked")
	protected T load(String path) {
	
		T created = null;
		
		try {
			created = (T) getConfig(path + ".yml").get(path);
		} catch (final ClassCastException e) {
		}
		
		if (created != null) {
			load(created);
		}
		
		return created;
	}
	
	protected T load(T object) {
	
		return load(object.getPersistentID());
	}
	
	protected T load(UUID path) {
	
		return load(path.toString());
	}
	
	protected void save(T object, String id) {
	
		final String ymlName = id + ".yml";
		final File file = new File(getFile(), ymlName);
		
		final FileConfiguration datafile = getConfig(ymlName);
		datafile.set(id, object);
		
		try {
			datafile.save(file);
		} catch (final IOException e) {
		}
	}
	
	protected void save(T object, UUID id) {
	
		save(object, id.toString());
	}
	
	protected void unload(String arg) {
	
		T object;
		if ((object = getIfLoaded(arg)) != null) {
			unload(object);
		}
	}
	
	protected void unload(UUID arg) {
	
		unload(arg.toString());
	}
	
}
