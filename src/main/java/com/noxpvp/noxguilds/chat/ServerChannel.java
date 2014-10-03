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

import java.util.Arrays;
import java.util.List;

import mkremins.fanciful.FancyMessage;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import com.noxpvp.noxguilds.locale.NoxGuildLocale;

/**
 * @author ConnorStone
 * 
 */
public class ServerChannel extends BaseChannel {
	
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	// Static fields
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	// Instance Fields
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	// Constructors
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	
	public ServerChannel() {
	
		super();
	}
	
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	// Static Methods
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	// Instance Methods
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	
	@Override
	public List<Player> getReceivers() {
	
		return Arrays.asList(Bukkit.getOnlinePlayers());
	}
	
	@Override
	public FancyMessage getTag() {
	
		return new FancyMessage(NoxGuildLocale.CHAT_CHANNEL_SERVER_TAG.get());
	}
	
}
