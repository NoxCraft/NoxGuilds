package com.noxpvp.noxguilds.util;

import com.noxpvp.noxguilds.guild.Guild;
import com.noxpvp.noxguilds.guildplayer.GuildPlayer;
import com.noxpvp.noxguilds.kingdom.Kingdom;

public class GuildUtil {
	
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
	
	public static boolean isAllyOf(Guild of, Guild ally) {
		if (of.hasKingdom()) {
			for (final Kingdom k : of.getKingdoms())
				if (k.getGuilds().contains(ally))
					return true;
		}
		
		return false;
	}
	
	public static boolean isAllyOf(Guild of, GuildPlayer ally) {
		if (ally.hasGuild()) {
			for (final Guild g : ally.getGuilds())
				return isAllyOf(of, g);
		}
		
		return false;
	}
	
	public static boolean isEnemyOf(Guild of, Guild enemy) {
		if (enemy.hasKingdom()) {
			for (final Kingdom k : of.getKingdoms()) {
				for (final Kingdom e : k.getEnemies())
					if (e.getGuilds().contains(enemy))
						return true;
			}
		}
		
		return false;
	}
	
	public static boolean isEnemyOf(Guild of, GuildPlayer enemy) {
		if (enemy.hasGuild()) {
			for (final Guild g : enemy.getGuilds())
				if (isAllyOf(of, g))
					return true;
		}
		
		return false;
	}
	
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	// Instance Methods
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
}
