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
package com.noxpvp.noxguilds.locale;

import mkremins.fanciful.FancyMessage;

import org.bukkit.entity.Player;

import com.noxpvp.noxguilds.guild.Guild;
import com.noxpvp.noxguilds.guildplayer.GuildPlayer;
import com.noxpvp.noxguilds.kingdom.Kingdom;
import com.noxpvp.noxguilds.manager.GuildPlayerManager;

/**
 * @author ConnorStone
 * 
 */
public enum FancyLocaleArg {
	
	GUILD("GUILD", Guild.class),
	KINGDOM("KINGDOM", Kingdom.class),
	PLAYER_INFO("PLAYER_INFO", GuildPlayer.class);
	
	private String	 varName;
	private Class<?>	typeClass;
	
	private FancyLocaleArg(String varName, Class<?> type) {
		this.varName = varName;
		typeClass = type;
	}
	
	public static String getParseString() {
		return "%";
	}
	
	/**
	 * @return the typeClass
	 */
	public Class<?> getTypeClass() {
		return typeClass;
	}
	
	public String getVar(int number) {
		return getParseString() + varName + number + getParseString();
	}
	
	/**
	 * @return the name
	 */
	public String getVarName() {
		return varName;
	}
	
	public FancyMessage parseFancyPart(FancyMessage message, Object arg) {
		if (arg instanceof Guild) {
			final Guild g = (Guild) arg;
			message.then(g.getName())
			        .itemTooltip(g.getIdentifiableItem());
		} else if (arg instanceof Kingdom) {
			final Kingdom k = (Kingdom) arg;
			message.then(k.getName())
			        .itemTooltip(k.getIdentifiableItem());
		} else if (arg instanceof GuildPlayer) {
			final GuildPlayer p = (GuildPlayer) arg;
			message.then(p.getFormatedName())
			        .itemTooltip(p.getIdentifiableItem());
		} else if (arg instanceof Player) {
			message = parseFancyPart(message, GuildPlayerManager
			        .getInstance().getFromPlayer((Player) arg));
		}
		
		return message;
	}
	
	public String toString(int index) {
		return getVar(index);
	}
	
}
