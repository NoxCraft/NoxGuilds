/*
 * Copyright (c) 2014. NoxPVP.com
 *
 * All rights are reserved.
 *
 * You are not permitted to
 * 	Modify
 * 	Redistribute nor distribute
 * 	Sublicense
 *
 * You are required to keep this license header intact
 *
 * You are allowed to use this for non commercial purpose only. This does not allow any ad.fly type links.
 *
 * When using this you are required to
 * 	Display a visible link to noxpvp.com
 * 	For crediting purpose.
 *
 * For more information please refer to the license.md file in the root directory of repo.
 *
 * To use this software with any different license terms you must get prior explicit written permission from the copyright holders.
 */
package com.noxpvp.noxguilds.chat;

import java.util.List;

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
	
	private final String	tag;
	
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	// Constructors
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	
	public BaseChannel(String tag) {
		this.tag = tag;
	}
	
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
		message.send(getReceivers().toArray(
		        new CommandSender[getReceivers().size()]));
	}
	
	/**
	 * Send a text based message to all receivers of the channel
	 * 
	 * @param text
	 */
	public void broadcast(TextMessage message) {
		message.send(getReceivers().toArray(
		        new CommandSender[getReceivers().size()]));
	}
	
	public abstract List<? extends CommandSender> getReceivers();
	
	/**
	 * @return the channel tag
	 */
	public String getTag() {
		return tag;
	}
	
	/**
	 * Send a fancy message to a specific player
	 * 
	 * @param message
	 */
	public void send(CommandSender reciever, FanceeMessage message) {
		message.send(reciever);
	}
	
	/**
	 * Send a text based message to specific player
	 * 
	 * @param message
	 */
	public void send(CommandSender reciever, TextMessage message) {
		message.send(reciever);
	}
	
}
