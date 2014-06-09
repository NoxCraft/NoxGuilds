package com.noxpvp.noxguilds.territory;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.bukkit.Location;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.util.Vector;

public class Area implements ConfigurationSerializable {
	
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	// Instance Fields
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	
	private Vector	min, max;
	
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	// Constructors
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	
	public Area(Location a, Location b) {
		this(a.toVector(), b.toVector());
	}
	
	// Deserialize
	public Area(Map<String, Object> data) {
		try {
			min = (Vector) data.get("min");
			max = (Vector) data.get("max");
		} catch (final Exception e) {
			e.printStackTrace();
		}
	}
	
	public Area(Vector min, Vector max) {
		setMinMaxPoints(Arrays.asList(min, max));
	}
	
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	// Instance Methods
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	
	public boolean contains(Location loc) {
		return contains(loc.toVector());
	}
	
	public boolean contains(Vector loc) {
		final double x = loc.getX(), y = loc.getY(), z = loc.getZ();
		
		return x >= min.getX() && x <= max.getX() &&
		        y >= min.getY() && y <= max.getY() &&
		        z >= min.getZ() && z <= max.getZ();
	}
	
	public LinkedList<Vector> getCorners() {
		final LinkedList<Vector> ret = new LinkedList<Vector>();
		
		final double minX = min.getX(), minZ = min.getZ(), maxX = max
		        .getX(), maxZ = max.getZ();
		
		// Top corners
		ret.add(max);
		ret.add(max.clone().add(new Vector(-minX, 0, 0)));
		ret.add(max.clone().add(new Vector(0, 0, -minZ)));
		ret.add(max.clone().add(new Vector(-minX, 0, -minZ)));
		
		// Bottom corners
		ret.add(min);
		ret.add(min.clone().add(new Vector(maxX, 0, 0)));
		ret.add(min.clone().add(new Vector(0, 0, maxZ)));
		ret.add(min.clone().add(new Vector(maxX, 0, maxZ)));
		
		return ret;
	}
	
	public Vector getMax() {
		return max;
	}
	
	public Vector getMin() {
		return min;
	}
	
	public LinkedList<Vector> getOutline() {
		return null;// TODO
	}
	
	public Map<String, Object> serialize() {
		final Map<String, Object> data = new HashMap<String, Object>();
		
		data.put("min", min);
		data.put("max", max);
		
		return data;
	}
	
	public void setMinMaxPoints(List<Vector> points) {
		int minX = points.get(0).getBlockX();
		int minY = points.get(0).getBlockY();
		int minZ = points.get(0).getBlockZ();
		int maxX = minX;
		int maxY = minY;
		int maxZ = minZ;
		
		for (final Vector v : points) {
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
		
		min = new Vector(minX, minY, minZ);
		max = new Vector(maxX, maxY, maxZ);
	}
}