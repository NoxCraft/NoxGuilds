package com.noxpvp.noxguilds.kingdom;

import java.util.Map;

import com.noxpvp.noxguilds.guild.Guild;

public class Kingdom extends BaseKingdom {
	
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	// Static fields
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	
	public Kingdom(Guild capital, String name) {
		super(capital.getID(), capital.getOwner().getUID(), name);
		
	}
	
	// Deserialize
	public Kingdom(Map<String, Object> data) {
		super(data);
	}
	
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	// Instance Fields
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	// Constructors
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	// Static Methods
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	// Instance Methods
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
}
