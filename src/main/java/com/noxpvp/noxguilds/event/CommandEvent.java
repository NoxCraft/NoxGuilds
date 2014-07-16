package com.noxpvp.noxguilds.event;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class CommandEvent extends Event {
	
	//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	//Static fields
	//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

	public static final HandlerList handlers = new HandlerList();
	
	//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	//Instance Fields
	//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

	protected CommandSender sender;
	protected Command cmd;
	protected String label;
	protected String[] args;
	
	//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	//Constructors
	//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

	public CommandEvent(CommandSender sender, Command cmd, String label, String[] args) {
		this.sender = sender;
		this.cmd = cmd;
		this.label = label;
		this.args = args;
		
	}
	
	//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	//Static Methods
	//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	
	public static HandlerList getHandlerList() {
		return handlers;
	}

	//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	//Instance Methods
	//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	
	public CommandSender getSender() {
		return sender;
	}
	
	public Command getCmd() {
		return cmd;
	}
	
	public String getLabel() {
		return label;
	}
	
	public String[] getArgs() {
		return args;
	}
	
	public HandlerList getHandlers() {
		return handlers;
	}
	
}
