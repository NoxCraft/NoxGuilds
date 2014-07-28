package com.noxpvp.noxguilds.land;

import org.bukkit.Chunk;

import com.noxpvp.noxguilds.util.StringEncoder;

public class TerritoryID {
	
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	// Static fields
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	
	private static final String	WORLD_KEY	= "world";
	private static final String	X_COORD_KEY	= "x";
	private static final String	Z_COORD_KEY	= "z";
	
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	// Instance Fields
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	
	private final String		id;
	
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	// Constructors
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	
	public TerritoryID(Chunk territoryChunk) {
		final StringEncoder se = new StringEncoder();
		
		se.put(WORLD_KEY, territoryChunk.getWorld().getUID().toString());
		se.put(X_COORD_KEY, territoryChunk.getX());
		se.put(Z_COORD_KEY, territoryChunk.getZ());
		
		id = se.encode();
	}
	
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	// Static Methods
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	// Instance Methods
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	
	public String getID() {
		return id;
	}
}
