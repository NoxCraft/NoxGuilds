package com.noxpvp.noxguilds.listeners;

import java.util.Arrays;
import java.util.List;

import org.bukkit.command.CommandSender;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;

import com.noxpvp.noxguilds.event.CommandEvent;
import com.noxpvp.noxguilds.internal.NoxPlugin;
import com.noxpvp.noxguilds.internal.Result;

public abstract class NoxCommandListener<T extends NoxPlugin> extends NoxListener<T> {
	
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	// Static fields
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	// Instance Fields
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	
	private final List<String> commands;
	
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	// Constructors
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	
	public NoxCommandListener(T plugin, String... commands) {
	
		super(plugin);
		
		this.commands = Arrays.asList(commands);
	}
	
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	// Static Methods
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	// Instance Methods
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	
	@EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = true)
	public boolean onCommand(CommandEvent event) {
	
		if (!commands.contains(event.getLabel()))
			return true;
		
		final Result ret = onCommand(event.getSender(), event.getArgs());
		ret.send(event.getSender());
		
		return ret.isResult();
	}
	
	public abstract Result onCommand(CommandSender sender, String[] args);
	
}
