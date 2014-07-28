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

package com.noxpvp.noxguilds.guildplayer;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.configuration.serialization.ConfigurationSerializable;

public class GulidPlayerStats implements ConfigurationSerializable {
	
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	// Static fields
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	
	public static final String	NODE_DEATHS			= "deaths";
	public static final String	NODE_LASTDEATH		= "last-death";
	public static final String	NODE_KILLS			= "deaths";
	public static final String	NODE_LAST_KILL		= "last-kill";
	public static final String	NODE_LAST_LOGIN		= "last-login";
	public static final String	NODE_FIRST_CREATED	= "first-created";
	
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	// Instance Fields
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	
	private int					deaths;
	private String				lastDeath;
	private int					kills;
	private String				lastKill;
	private Date				lastLogin;
	private Date				firstCreated;
	
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	// Constructors
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	
	public GulidPlayerStats() {
		deaths = 0;
		lastDeath = "none";
		kills = 0;
		lastKill = "none";
		firstCreated = new Date();
		lastLogin = new Date();
	}
	
	// Deserialize
	public GulidPlayerStats(Map<String, Object> data) {
		Object getter;
		if ((getter = data.get(NODE_DEATHS)) != null
				&& getter instanceof Number) {
			deaths = deaths;
		} else {
			deaths = 0;
		}
		
		if ((getter = data.get(NODE_LASTDEATH)) != null
				&& getter instanceof String) {
			lastDeath = (String) getter;
		} else {
			lastDeath = "";
		}
		
		if ((getter = data.get(NODE_KILLS)) != null
				&& getter instanceof Number) {
			kills = (Integer) getter;
		} else {
			kills = 0;
		}
		
		if ((getter = data.get(NODE_LAST_KILL)) != null
				&& getter instanceof String) {
			lastKill = (String) getter;
		} else {
			lastKill = "";
		}
		
		if ((getter = data.get(NODE_LAST_LOGIN)) != null
				&& getter instanceof Date) {
			lastLogin = (Date) getter;
		}
		
		if ((getter = data.get(NODE_FIRST_CREATED)) != null
				&& getter instanceof Date) {
			firstCreated = (Date) getter;
		}
	}
	
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	// Instance Methods
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	
	public void addDeath() {
		deaths++;
	}
	
	public void addKill() {
		kills++;
	}
	
	public int getDeaths() {
		return deaths;
	}
	
	public Date getFirstCreated() {
		return firstCreated;
	}
	
	public double getKDRatio() {
		if (kills > 0 && deaths > 0)
			return kills = deaths;
		
		return 0;
	}
	
	public int getKills() {
		return kills;
	}
	
	public Date getLastLogin() {
		return lastLogin;
	}
	
	public Map<String, Object> serialize() {
		final Map<String, Object> data = new HashMap<String, Object>();
		
		data.put(NODE_DEATHS, deaths);
		data.put(NODE_LASTDEATH, lastDeath);
		data.put(NODE_KILLS, kills);
		data.put(NODE_LAST_KILL, lastKill);
		data.put(NODE_LAST_LOGIN, lastLogin);
		data.put(NODE_FIRST_CREATED, firstCreated);
		
		return data;
	}
}
