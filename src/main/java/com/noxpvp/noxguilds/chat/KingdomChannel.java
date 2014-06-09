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

import java.util.ArrayList;
import java.util.List;

import org.bukkit.entity.Player;

import com.noxpvp.noxguilds.guild.Guild;
import com.noxpvp.noxguilds.kingdom.BaseKingdom;
import com.noxpvp.noxguilds.locale.NoxGuildLocale;

/**
 * @author ConnorStone
 * 
 */
public class KingdomChannel extends BaseChannel {
	
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	// Static fields
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	// Instance Fields
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	
	private final BaseKingdom	kingdom;
	
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	// Constructors
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	
	public KingdomChannel(BaseKingdom kingdom) {
		super(NoxGuildLocale.CHAT_CHANNEL_KINGDOM_TAG.get());
		
		this.kingdom = kingdom;
	}
	
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	// Static Methods
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	// Instance Methods
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	
	@Override
	public List<Player> getReceivers() {
		final List<Player> ret = new ArrayList<Player>();
		
		for (final Guild g : kingdom.getGuilds()) {
			for (final Player p : g.getOnlinePlayers()) {
				ret.add(p);
			}
		}
		
		return ret;
	}
}
