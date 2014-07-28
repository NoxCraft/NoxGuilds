package com.noxpvp.noxguilds.util;

import com.noxpvp.noxguilds.guild.Guild;
import com.noxpvp.noxguilds.land.GuildPlot;
import com.noxpvp.noxguilds.land.TerritoryCoord;
import com.noxpvp.noxguilds.land.TerritoryID;
import com.noxpvp.noxguilds.manager.PlotManager;

public class TerritoryUtils {
	
	public static Guild getOwner(TerritoryCoord coord) {
		final GuildPlot plot = PlotManager.getInstance().getPlot(
				new TerritoryID(coord.getChunk()));
		
		if (plot != null)
			return plot.getGuildOwner();
		
		return null;
	}
	
	public static boolean isClaimed(TerritoryCoord coord) {
		return getOwner(coord) != null;
	}
	
}
