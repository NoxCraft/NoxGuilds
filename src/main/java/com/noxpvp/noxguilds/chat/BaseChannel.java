/*
 * Copyright (c) 2014. NoxPVP.com
 * 
 * All rights are reserved.
 * 
 * You are not permitted to Modify Redistribute nor distribute Sublicense
 * 
 * You are required to keep this license header intact
 * 
 * You are allowed to use this for non commercial purpose only. This does not allow any ad.fly type links.
 * 
 * When using this you are required to Display a visible link to noxpvp.com For crediting purpose.
 * 
 * For more information please refer to the license.md file in the root directory of repo.
 * 
 * To use this software with any different license terms you must get prior explicit written permission from the
 * copyright holders.
 */
package com.noxpvp.noxguilds.chat;

import java.util.List;

import mkremins.fanciful.FancyMessage;

import org.bukkit.command.CommandSender;

/**
 * @author ConnorStone
 * 
 */
public abstract class BaseChannel {
	
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	// Static fields
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	
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
	
	/**
	 * Send a fancy message to all receivers of the channel
	 * 
	 * @param message
	 */
	public void broadcast(FanceeMessage message) {
	
		for (final CommandSender receiver : getReceivers()) {
			send(receiver, message);
		}
	}
	
	/**
	 * Send a text based message to all receivers of the channel
	 * 
	 * @param text
	 */
	public void broadcast(TextMessage message) {
	
		for (final CommandSender receiver : getReceivers()) {
			send(receiver, message);
		}
	}
	
	public abstract List<? extends CommandSender> getReceivers();
	
	/**
	 * @return the channel tag in fancy form
	 */
	public abstract FancyMessage getTag();
	
	/**
	 * Send a fancy message to a specific player from this channel
	 * 
	 * @param message
	 */
	public void send(CommandSender receiver, FanceeMessage message) {
	
		getTag().then(message.getMessage().toOldMessageFormat()).send(receiver);// TODO
																				// work
																				// around
	}
	
	/**
	 * Send a text based message to specific player from this channel
	 * 
	 * @param message
	 */
	public void send(CommandSender receiver, TextMessage message) {
	
		getTag().then(message.getMessage()).send(receiver);
	}
	
}
