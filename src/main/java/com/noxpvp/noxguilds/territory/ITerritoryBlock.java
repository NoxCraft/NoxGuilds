package com.noxpvp.noxguilds.territory;

import java.util.UUID;

import org.bukkit.Location;
import org.bukkit.World;

public interface ITerritoryBlock {
	
	public TerritoryCoord getCoord();
	
	public Location getLocation();
	
	// Main Data
	public UUID getOwner();
	
	public double getPrice();
	
	public World getWorld();
	
	public boolean hasOwner();
	
	// Plot selling
	public boolean isForSale();
	
	public boolean isOwner(UUID id);
	
	public void setOwner(UUID newOwnerID);
	
	public void setPrice(double price);
	
}
