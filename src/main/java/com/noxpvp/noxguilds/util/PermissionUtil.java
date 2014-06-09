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

import com.noxpvp.noxguilds.access.GuildAccessLevel;
import com.noxpvp.noxguilds.access.GuildPermissionType;
import com.noxpvp.noxguilds.guild.Guild;
import com.noxpvp.noxguilds.guild.GuildRank;
import com.noxpvp.noxguilds.guildplayer.GuildPlayer;

/**
 * @author ConnorStone
 * 
 */
public class PermissionUtil {
	
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	// Static fields
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	// Instance Fields
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	// Constructors
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	// Static Methods
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	
	public static boolean hasPermission(Guild g, GuildPlayer player,
	        GuildPermissionType perm) {
		
		if (g.getOwner().equals(player))
			return true;
		
		final GuildAccessLevel level = GuildAccessLevel.getAccessLevel(g,
		        player);
		
		if (g.getPermissions().get(level, perm))
			return true;
		
		for (final GuildRank r : g.getRanksFor(player)) {
			if (r.getPermissions().get(level, perm))
				return true;
		}
		
		return false;
	}
	
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	// Instance Methods
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
}
