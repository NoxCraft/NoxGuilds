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
	
	GUILD("GUILD", Guild.class) {
		@Override
		public FancyMessage parsePart(FancyMessage message, Object arg) {
			if (arg instanceof Guild) {
				final Guild g = (Guild) arg;
				message.then(g.getName())
				        .itemTooltip(g.getIdentifiableItem());
			}
			
			return message;
		}
		
	}, KINGDOM("KINGDOM", Kingdom.class) {
		@Override
		public FancyMessage parsePart(FancyMessage message, Object arg) {
			if (arg instanceof Kingdom) {
				final Kingdom k = (Kingdom) arg;
				message.then(k.getName())
				        .itemTooltip(k.getIdentifiableItem());
			}
			
			return message;
		}
		
	}, PLAYER_INFO("PLAYER_INFO", GuildPlayer.class) {
		@Override
		public FancyMessage parsePart(FancyMessage message, Object arg) {
			if (arg instanceof GuildPlayer) {
				final GuildPlayer p = (GuildPlayer) arg;
				message.then(p.getFormatedName())
				        .itemTooltip(p.getIdentifiableItem());
			} else if (arg instanceof Player) {
				message = parsePart(message, GuildPlayerManager
				        .getInstance().getFromPlayer((Player) arg));
			}
			
			return message;
			
		}

	};
	
	private String	 varName;
	private Class<?>	typeClass;
	
	private FancyLocaleArg(String varName, Class<?> type) {
		this.varName = varName;
		typeClass = type;
	}
	
	public abstract FancyMessage parsePart(FancyMessage message, Object arg);
	
	public static String getParseString() {
		return "%";
	}
	
	public Class<?> getTypeClass() {
		return typeClass;
	}
	
	public String getVar(int number) {
		return getParseString() + varName + number + getParseString();
	}
	
	public String getVarName() {
		return varName;
	}
	
}
