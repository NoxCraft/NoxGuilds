package com.noxpvp.noxguilds.land;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.inventory.ItemStack;

import com.noxpvp.noxguilds.gui.internal.ItemRepresentable;
import com.noxpvp.noxguilds.internal.Persistant;

public class TerritoryBlock implements Persistant, ItemRepresentable {
	
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	// Static Fields
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	
	// Serializers start
	private static final String	NODE_UID		= "uuid";
	private static final String	NODE_LOCATION	= "location";
	// Serializers end
	
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	// Instance Fields
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	
	private TerritoryCoord		coord;
	private final TerritoryID	id;
	
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	// Constructors
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	
	// Deserialize
	public TerritoryBlock(Map<String, Object> data) {
		Object getter;
		
		if ((getter = data.get(NODE_LOCATION)) != null
				&& getter instanceof Location) {
			coord = new TerritoryCoord((Location) getter);
		}
		
		id = new TerritoryID(getChunk());
		
	}
	
	public TerritoryBlock(TerritoryCoord coord) {
		this.coord = coord;
		id = new TerritoryID(getChunk());
	}
	
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	// Instance Methods
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	
	public Chunk getChunk() {
		return coord.getChunk();
	}
	
	public Area getChunkArea() {
		final Chunk c = coord.getChunk();
		
		final Location max = new Location(c.getWorld(), c.getX(), 0, c.getZ());
		final Location min = new Location(c.getWorld(), c.getX() + 16, 256,
				c.getZ() + 16);
		
		return new Area(max, min);
	}
	
	public TerritoryCoord getCoord() {
		return coord;
	}
	
	public ItemStack getIdentifiableItem() {
		return null;
	};
	
	public Location getLocation() {
		return coord.getLocation();
	}
	
	@Deprecated
	public UUID getPersistentID() {
		return null;
	}
	
	public String getPersistentStringID() {
		return id.getID();
	}
	
	public World getWorld() {
		return coord.getWorld();
	}
	
	public void load() {
		return;
		// nothing yet
	}
	
	public void save() {
		return;
		// Nothing yet
	}
	
	public Map<String, Object> serialize() {
		final Map<String, Object> data = new HashMap<String, Object>();
		
		data.put(NODE_LOCATION, coord);
		
		return data;
	}
	
}
