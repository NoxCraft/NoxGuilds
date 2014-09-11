package com.noxpvp.noxguilds.event;

import org.bukkit.command.CommandSender;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class CommandEvent extends Event {
	
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	// Static fields
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	
	public static final HandlerList	handlers	= new HandlerList();
	
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	// Instance Fields
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	
	protected CommandSender			sender;
	protected String				label;
	protected String[]				args;
	
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	// Constructors
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	
	public CommandEvent(CommandSender sender, String label, String[] args) {
		this.sender = sender;
		this.label = label;
		this.args = args;
		
	}
	
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	// Static Methods
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	
	public static HandlerList getHandlerList() {
		return handlers;
	}
	
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	// Instance Methods
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	
	public String[] getArgs() {
		return args;
	}
	
	@Override
	public HandlerList getHandlers() {
		return handlers;
	}
	
	public String getLabel() {
		return label;
	}
	
	public CommandSender getSender() {
		return sender;
	}
	
}
