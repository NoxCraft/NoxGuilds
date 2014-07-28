package com.noxpvp.noxguilds.land;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.configuration.serialization.ConfigurationSerializable;

public class TerritoryCoord implements ConfigurationSerializable {
	
	protected Location	loc;
	
	public TerritoryCoord(Block blockLocation) {
		this(blockLocation.getLocation());
	}
	
	public TerritoryCoord(Chunk chunkLocation) {
		this(new Location(chunkLocation.getWorld(), chunkLocation.getX() * 16, 0,
				chunkLocation.getZ() * 16));
	}
	
	public TerritoryCoord(Location location) {
		loc = location;
	}
	
	public TerritoryCoord(Map<String, Object> data) {
		Object getter;
		if ((getter = data.get("location")) != null
				&& getter instanceof Location) {
			loc = (Location) getter;
		}
		
	}
	
	public TerritoryBlock getBlockAt() {
		return new TerritoryBlock(this);
	}
	
	public Chunk getChunk() {
		return loc.getChunk();
	}
	
	public Location getLocation() {
		return loc.clone();
	}
	
	public World getWorld() {
		return loc.getWorld();
	}
	
	public Map<String, Object> serialize() {
		final Map<String, Object> data = new HashMap<String, Object>();
		
		data.put("location", loc);
		
		return data;
	}
	
}
