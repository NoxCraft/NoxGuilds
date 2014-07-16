package com.noxpvp.noxguilds.listeners;

import com.noxpvp.noxguilds.event.CommandEvent;
import com.noxpvp.noxguilds.internal.NoxPlugin;

public abstract class NoxCommandListener<T extends NoxPlugin> extends NoxListener<T> {
	
	//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	//Static fields
	//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

	//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	//Instance Fields
	//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

	//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	//Constructors
	//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	
	public NoxCommandListener(T plugin) {
		super(plugin);
		
	}

	//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	//Static Methods
	//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

	//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	//Instance Methods
	//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	
	public abstract boolean onCommand(CommandEvent event);
	
}
