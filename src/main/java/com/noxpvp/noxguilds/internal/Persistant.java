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

package com.noxpvp.noxguilds.internal;

import java.util.UUID;

import org.bukkit.configuration.serialization.ConfigurationSerializable;

public interface Persistant extends ConfigurationSerializable {
	
	/**
	 * Gets the persistent id of this object
	 * 
	 * @return
	 */
	public UUID getPersistentID();
	
	/**
	 * Lets the object know when it attempt to load any data from file that
	 * isn't already loaded with deserialization
	 * 
	 */
	public void load();
	
	/**
	 * Lets the object know when it should attempt to save data from file
	 * that is not already done with serialization
	 * 
	 */
	public void save();
	
}
