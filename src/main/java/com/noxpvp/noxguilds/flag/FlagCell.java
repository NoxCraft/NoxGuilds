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
package com.noxpvp.noxguilds.flag;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.configuration.serialization.ConfigurationSerializable;

/**
 * @author ConnorStone
 * 
 */
public class FlagCell implements ConfigurationSerializable {
	
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	// Static fields
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	
	public static final String				NODE_FLAGS	= "flags";
	
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	// Instance Fields
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	
	private final Map<FlagType, Boolean>	flags;
	
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	// Constructors
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	
	public FlagCell() {
		flags = new HashMap<FlagType, Boolean>();
		
	}
	
	public FlagCell(Map<FlagType, Boolean> data) {
		flags = new HashMap<FlagType, Boolean>(data);
	}
	
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	// Static Methods
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	// Instance Methods
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	
	/**
	 * Clears all settings for all flags in the flag cell
	 * 
	 */
	public void clearSettings() {
		flags.clear();
	}
	
	/**
	 * Gets the boolean value for the given flag stored in the flagcell if
	 * there is no value set for the flag already, it will return false
	 * 
	 * @param flag
	 * @return value of flag setting if contained in the settings,
	 *         otherwise false if not set already
	 */
	public boolean getFlag(FlagType flag) {
		return flags.containsValue(flag) ? flags.get(flag) : false;
	}
	
	/**
	 * Removes any setting in the cell for the given flag
	 * 
	 * @param type
	 * @return true if the flags were changed is a result of the removal,
	 *         otherwise false
	 */
	public boolean removeFlag(FlagType type) {
		return flags.remove(type);
	}
	
	public Map<String, Object> serialize() {
		final Map<String, Object> data = new HashMap<String, Object>();
		
		data.put(NODE_FLAGS, flags);
		
		return data;
	}
	
	/**
	 * Sets a boolean value for a given flag in the flagcell
	 * 
	 * @param type
	 * @param value
	 * @return If the flags were changed as a result of the setting
	 */
	public boolean setFlag(FlagType type, boolean value) {
		return flags.put(type, value);
	}
}
