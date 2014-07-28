package com.noxpvp.noxguilds.land;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.entity.Entity;

import com.noxpvp.noxguilds.internal.Persistant;

public abstract class Zone implements IZone, Persistant, ConfigurationSerializable {
	
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	// Static Fields
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	
	public static final String	DEFAULT_ZONE_NAME	= "Unnamed Zone";
	public static final String	SERIALIZE_ID		= "id";
	public static final String	SERIALIZE_NAME		= "name";
	public static final String	SERIALIZE_AREA		= "area";
	
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	// Instance Fields
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	
	private UUID				id;
	private String				zoneName;
	private Area				area;
	
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	// Constructors
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	
	// Deserialize
	public Zone(Map<String, Object> data) {
		Object getter;
		if ((getter = data.get(SERIALIZE_ID)) != null && getter instanceof String) {
			id = UUID.fromString((String) getter);
		} else {
			id = UUID.randomUUID();
		}
		
		if ((getter = data.get(SERIALIZE_NAME)) != null && getter instanceof Zone) {
			zoneName = (String) getter;
		} else {
			zoneName = DEFAULT_ZONE_NAME;
		}
		
		if ((getter = data.get(SERIALIZE_AREA)) != null && getter instanceof Area) {
			area = (Area) getter;
		}
	}
	
	public Zone(String name, Area region) {
		id = UUID.randomUUID();
		zoneName = checkZoneName(name);
		area = region;
	}
	
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	// Instance Methods
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	
	public Area getArea() {
		return area;
	}
	
	public UUID getID() {
		return id;
	}
	
	public String getName() {
		return zoneName;
	}
	
	public UUID getPersistentID() {
		return getID();
	}
	
	public boolean isInArea(Area a) {
		return a.contains(area.getMax()) && a.contains(area.getMin());
	}
	
	public boolean isInZone(Zone z) {
		return isInArea(z.getArea());
	}
	
	public boolean overlaps(Block b) {
		return area.contains(b.getLocation());
	}
	
	public boolean overlaps(Entity e) {
		return area.contains(e.getLocation());
	}
	
	public boolean overlaps(Location loc) {
		return area.contains(loc);
	}
	
	public Map<String, Object> serialize() {
		final Map<String, Object> data = new HashMap<String, Object>();
		
		data.put(SERIALIZE_ID, id.toString());
		data.put(SERIALIZE_NAME, zoneName);
		data.put(SERIALIZE_AREA, area);
		
		return data;
	}
	
	public void setArea(Area newRegion) {
		area = newRegion;
	}
	
	public boolean setName(String name) {
		zoneName = checkZoneName(name);
		
		return zoneName.equals(name);
	}
	
	private String checkZoneName(String name) {
		if (name.length() > 1 && name.length() < 17)
			return name;
		
		return zoneName != null ? zoneName : DEFAULT_ZONE_NAME;
	}
	
}
