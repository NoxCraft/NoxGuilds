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
package com.noxpvp.noxguilds.locale;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import mkremins.fanciful.FancyMessage;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import com.noxpvp.noxguilds.guild.BaseGuild;
import com.noxpvp.noxguilds.guild.Guild;
import com.noxpvp.noxguilds.guildplayer.GuildPlayer;
import com.noxpvp.noxguilds.kingdom.Kingdom;
import com.noxpvp.noxguilds.land.GuildPlot;
import com.noxpvp.noxguilds.manager.GuildPlayerManager;

/**
 * @author ConnorStone
 * 
 */
public enum FancyLocaleArg {
	
	GUILD("GUILD", Guild.class) {
		
		@Override
		public FancyMessage parsePart(FancyMessage message, Object... args) {
		
			for (final Object arg : args) {
				if (arg instanceof Guild || arg instanceof BaseGuild) {
					final BaseGuild g = (BaseGuild) arg;
					message.then(g.getName()).color(ChatColor.GREEN).itemTooltip(g.getIdentifiableItem());
					break;
				}
			}
			
			return message;
		}
		
	},
	KINGDOM("KINGDOM", Kingdom.class) {
		
		@Override
		public FancyMessage parsePart(FancyMessage message, Object... args) {
		
			for (final Object arg : args) {
				if (arg instanceof Kingdom) {
					final Kingdom k = (Kingdom) arg;
					message.then(k.getName()).color(ChatColor.LIGHT_PURPLE).itemTooltip(k.getIdentifiableItem());
					break;
				}
			}
			
			return message;
		}
		
	},
	PLOT_INFO("PLOT_INFO", GuildPlot.class) {
		
		@Override
		public FancyMessage parsePart(FancyMessage message, Object... args) {
		
			for (final Object arg : args) {
				if (arg instanceof GuildPlot) {
					final GuildPlot plot = (GuildPlot) arg;
					final String name =
						plot.getPlayerOwner() != null ? plot.getPlayerOwner().getOffline().getName() : "Unowned";
					
					message.then(name).color(ChatColor.YELLOW).itemTooltip(plot.getIdentifiableItem());
					break;
				}
			}
			
			return message;
		}
		
	},
	PLAYER_INFO("PLAYER_INFO", GuildPlayer.class, Player.class) {
		
		@Override
		public FancyMessage parsePart(FancyMessage message, Object... args) {
		
			for (final Object arg : args) {
				if (arg instanceof GuildPlayer) {
					final GuildPlayer p = (GuildPlayer) arg;
					message.then(p.getPlayer().getName()).color(ChatColor.YELLOW).itemTooltip(p.getIdentifiableItem());
					break;
				} else if (arg instanceof Player) {
					message = parsePart(message, GuildPlayerManager.getInstance().getFromPlayer((Player) arg));
					break;
				}
			}
			
			return message;
			
		}
		
	},
	CLICK_COMMAND("CLICK_COMMAND", String.class) {
		
		@Override
		public FancyMessage parsePart(FancyMessage message, Object... args) {
		
			for (final Object arg : args) {
				if (arg instanceof String) {
					message.command((String) arg);
					break;
				}
			}
			
			return message;
		}
		
	};
	
	private String varName;
	private Set<Class<?>> argTypes;
	
	private FancyLocaleArg(String varName, Class<?>... argTypes) {
	
		this.varName = varName;
		this.argTypes = new HashSet<Class<?>>();
		
		this.argTypes.addAll(Arrays.asList(argTypes));
	}
	
	public static char getParseChar() {
	
		return '%';
	}
	
	public Set<Class<?>> getArgTypes() {
	
		return argTypes;
	}
	
	public String getVar(int number) {
	
		return getParseChar() + varName + number + getParseChar();
	}
	
	public String getVarName() {
	
		return varName;
	}
	
	public abstract FancyMessage parsePart(FancyMessage message, Object... args);
	
}
