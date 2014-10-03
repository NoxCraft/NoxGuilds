package com.noxpvp.noxguilds.land;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.serialization.ConfigurationSerializable;

public class Area implements ConfigurationSerializable {
	
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	// Instance Fields
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	
	private Location min, max;
	
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	// Constructors
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	
	public Area(Location min, Location max) {
	
		setMinMaxPoints(Arrays.asList(min, max));
	}
	
	// Deserialize
	public Area(Map<String, Object> data) {
	
		try {
			min = (Location) data.get("min");
			max = (Location) data.get("max");
		} catch (final Exception e) {
			e.printStackTrace();
		}
	}
	
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	// Instance Methods
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	
	public boolean contains(Location loc) {
	
		final double x = loc.getX(), y = loc.getY(), z = loc.getZ();
		
		if (!loc.getWorld().equals(min.getWorld()))
			return false;
		
		return x >= min.getX() && x <= max.getX() && y >= min.getY() && y <= max.getY() && z >= min.getZ()
			&& z <= max.getZ();
	}
	
	public LinkedList<Location> getCorners() {
	
		final LinkedList<Location> ret = new LinkedList<Location>();
		
		final World w = ret.getFirst().getWorld();
		
		final double minX = min.getX(), minZ = min.getZ(), maxX = max.getX(), maxZ = max.getZ();
		
		// Top corners
		ret.add(max);
		ret.add(max.clone().add(new Location(w, -minX, 0, 0)));
		ret.add(max.clone().add(new Location(w, 0, 0, -minZ)));
		ret.add(max.clone().add(new Location(w, -minX, 0, -minZ)));
		
		// Bottom corners
		ret.add(min);
		ret.add(min.clone().add(new Location(w, maxX, 0, 0)));
		ret.add(min.clone().add(new Location(w, 0, 0, maxZ)));
		ret.add(min.clone().add(new Location(w, maxX, 0, maxZ)));
		
		return ret;
	}
	
	public Location getMax() {
	
		return max;
	}
	
	public Location getMin() {
	
		return min;
	}
	
	public LinkedList<Location> getOutline() {
	
		return null;// TODO
	}
	
	public Map<String, Object> serialize() {
	
		final Map<String, Object> data = new HashMap<String, Object>();
		
		data.put("min", min);
		data.put("max", max);
		
		return data;
	}
	
	public void setMinMaxPoints(List<Location> points) {
	
		final World w = points.get(0).getWorld();
		
		int minX = points.get(0).getBlockX();
		int minY = points.get(0).getBlockY();
		int minZ = points.get(0).getBlockZ();
		int maxX = minX;
		int maxY = minY;
		int maxZ = minZ;
		
		for (final Location v : points) {
			final int x = v.getBlockX();
			final int y = v.getBlockY();
			final int z = v.getBlockZ();
			
			if (x < minX) {
				minX = x;
			}
			if (y < minY) {
				minY = y;
			}
			if (z < minZ) {
				minZ = z;
			}
			
			if (x > maxX) {
				maxX = x;
			}
			if (y > maxY) {
				maxY = y;
			}
			if (z > maxZ) {
				maxZ = z;
			}
		}
		
		min = new Location(w, minX, minY, minZ);
		max = new Location(w, maxX, maxY, maxZ);
	}
}