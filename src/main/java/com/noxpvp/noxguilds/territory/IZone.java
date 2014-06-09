package com.noxpvp.noxguilds.territory;

import java.util.UUID;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;

public interface IZone {
	
	/**
	 * Gets the area object associated with this zone
	 * 
	 * @return {@link Area} the area
	 */
	public Area getArea();
	
	/**
	 * Gets the unique id for this zone
	 * 
	 * @return {@link UUID} this zones id
	 */
	public UUID getID();
	
	/**
	 * Gets the name of this zone, this is very different from the id and
	 * should only be used for display
	 * 
	 * @return {@link String} this zones name
	 */
	public String getName();
	
	public boolean isInZone(Block b);
	
	public boolean isInZone(Entity e);
	
	public boolean isInZone(Location loc);
	
	/**
	 * Sets the area of this zone
	 * 
	 * @param newRegion
	 */
	public void setArea(Area newRegion);
	
	/**
	 * Sets the name of this zone.
	 * 
	 * Warning, the name must be at least 2 in length and less than 17
	 * 
	 * @param name
	 * @return true if the change was successful, otherwise false
	 */
	public boolean setName(String name);
	
}
