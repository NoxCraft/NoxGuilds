package com.noxpvp.noxguilds.territory;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.entity.Entity;
import org.bukkit.util.Vector;

public abstract class Zone implements IZone, ConfigurationSerializable {
	
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	// Static Fields
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	
	public static final String	DEFAULT_ZONE_NAME	= "Unnamed Zone";
	
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	// Instance Fields
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	
	private UUID	           id;
	private String	           zoneName;
	private Area	           area;
	
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	// Constructors
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	
	// Deserialize
	public Zone(Map<String, Object> data) {
		Object getter;
		if ((getter = data.get("id")) != null && getter instanceof String) {
			id = UUID.fromString((String) getter);
		}
		
		if ((getter = data.get("area")) != null && getter instanceof Area) {
			area = (Area) getter;
		}
		
		if ((getter = data.get("name")) != null && getter instanceof Zone) {
			zoneName = (String) getter;
		}
	}
	
	public Zone(String name, Area region) {
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
	
	public boolean inInZone(Vector loc) {
		return area.contains(loc);
	}
	
	public boolean isInZone(Block b) {
		return area.contains(b.getLocation());
	}
	
	public boolean isInZone(Entity e) {
		return area.contains(e.getLocation());
	}
	
	public boolean isInZone(Location loc) {
		return area.contains(loc);
	}
	
	public Map<String, Object> serialize() {
		final Map<String, Object> data = new HashMap<String, Object>();
		
		data.put("id", id.toString());
		data.put("area", area);
		data.put("name", zoneName);
		
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
