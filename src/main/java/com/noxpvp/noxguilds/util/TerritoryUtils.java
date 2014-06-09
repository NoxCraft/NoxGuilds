package com.noxpvp.noxguilds.util;

import com.noxpvp.noxguilds.guild.Guild;
import com.noxpvp.noxguilds.manager.GuildManager;
import com.noxpvp.noxguilds.territory.TerritoryBlock;

public class TerritoryUtils {
	
	public static boolean canClaim(TerritoryBlock block) {
		return false;
	}
	
	public static boolean isClaimed(TerritoryBlock block) {
		for (final Guild guild : GuildManager.getInstance().getLoadeds()
		        .values()) {
			if (guild.hasTerritory(block))
				return true;
		}
		
		return false;
	}
	
}
