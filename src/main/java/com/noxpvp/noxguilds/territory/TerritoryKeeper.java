package com.noxpvp.noxguilds.territory;

import java.util.List;

import org.bukkit.Location;

import com.noxpvp.noxguilds.guildplayer.GuildPlayer;
import com.noxpvp.noxguilds.internal.Result;

public interface TerritoryKeeper {
	
	public Result addTerritory(TerritoryBlock block);
	
	public Result buyTerritory(GuildPlayer buyer, TerritoryBlock block);
	
	public List<TerritoryBlock> getTerritory();
	
	public boolean hasTerritory(Location loc);
	
	public boolean hasTerritory(TerritoryBlock block);
	
	public boolean hasTerritory(TerritoryCoord coord);
	
	public Result removeTerritory(TerritoryBlock block);
	
}
