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

package com.noxpvp.noxguilds.util;

import java.util.ArrayList;
import java.util.List;

import mkremins.fanciful.FancyMessage;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import com.bergerkiller.bukkit.common.utils.LogicUtil;
import com.bergerkiller.bukkit.common.utils.StringUtil;
import com.noxpvp.noxguilds.VaultAdapter;
import com.noxpvp.noxguilds.locale.FancyLocaleArg;
import com.noxpvp.noxguilds.locale.NoxLocale;

public class MessageUtil {
	
	public static void broadcast(String message) {
		sendMessage(Bukkit.getOnlinePlayers(), message);
	}
	
	public static void broadcast(String... messages) {
		sendMessages(Bukkit.getOnlinePlayers(), messages);
	}
	
	public static void broadcast(final String permission, String message) {
		sendPermMessages(Bukkit.getOnlinePlayers(), permission, message);
	}
	
	public static void broadcast(final String permission,
	        String... messages) {
		sendPermMessages(Bukkit.getOnlinePlayers(), permission, messages);
	}
	
	public static void broadcast(World world, String message) {
		for (final Player player : world.getPlayers()) {
			sendMessage(player, message);
		}
	}
	
	public static void broadcast(World world, String... messages) {
		for (final Player player : world.getPlayers()) {
			sendMessages(player, messages);
		}
	}
	
	public static void broadcast(World world, final String permission,
	        String message) {
		sendPermMessages(world.getPlayers().toArray(new Player[0]),
		        permission, message);
	}
	
	public static void broadcast(World world, final String permission,
	        String... messages) {
		sendPermMessages(world.getPlayers().toArray(new Player[0]),
		        permission, messages);
	}
	
	/**
	 * Converts a string message into 28~ish length strings for use as item
	 * lore, or anything else
	 * 
	 * @param lore
	 * @return List<String> converted string
	 */
	public static List<String> convertStringForLore(String lore) {
		final List<String> ret = new ArrayList<String>();
		lore = parseColor(lore);
		final int lineLength = 30;
		
		int one = 0, two = 0;
		boolean ending = false;
		String lastColor = "";
		
		for (final char cur : lore.toCharArray()) {
			if (two - one >= lineLength && !ending) {
				ending = true;
			}
			
			if (ending && cur == ' ' || two - one > lineLength + 5) {
				ret.add(lastColor + lore.substring(one, two));
				
				lastColor = getLastColors(lore.substring(0, two));
				ending = false;
				one = two;
			}
			
			two++;
		}
		
		final String leftOver = lore.substring(one, two);
		if (leftOver != "" && !ret.contains(leftOver)) {
			ret.add(lastColor + leftOver);
		}
		
		return ret;
		
	}
	
	public static String getLastColors(String message) {
		final char[] chars = message.toCharArray();
		
		int i = 0;
		
		String ret = "";
		boolean c = false, f = false;
		while (i < chars.length) {
			if (!c) {
				if (chars[i] == ChatColor.COLOR_CHAR) {
					c = true;
				}
				i++;
				continue;
			} else {
				final ChatColor color = ChatColor.getByChar(chars[i]);
				if (color != null) {
					if (!f) {
						ret = color.toString();
						f = true;
					} else {
						if (color.isColor()) {
							ret = color.toString();
						}
						ret += color.toString();
					}
				}
				c = false;
				i++;
				continue;
			}
		}
		if (LogicUtil.nullOrEmpty(ret))
			return ChatColor.WHITE.toString();
		
		return ret;
	}
	
	public static String parseArguments(String message, String... args) {
		final StringBuilder msg = new StringBuilder(message);
		if (args.length > 0) {
			for (int i = 0; i < args.length; i++) {
				StringUtil.replaceAll(msg, "%" + i + "%", LogicUtil
				        .fixNull(args[i], "null"));
			}
		}
		return msg.toString();
	}
	
	public static String parseColor(String message) {
		return ChatColor.translateAlternateColorCodes('&', message);
	}
	
	public static FancyMessage parseFancyArguments(String message,
	        Object... args) {
		FancyMessage ret = new FancyMessage();
		boolean started = false;
		final StringBuilder string = new StringBuilder(message);
		
		if (args.length > 0) {
			// For each argument...
			for (int i = 0; i < args.length; i++) {
				// get all possible fancy parts
				final String[] posFancys = string.toString().split(
				        FancyLocaleArg.getParseString());
				
				// For each possible fancy part
				for (final String posFancy : posFancys) {
					// Null? continue
					if (LogicUtil.nullOrEmpty(posFancy)) {
						continue;
					}
					
					// check for having a number, since a fancy part
					// requires it
					String numberGet;
					if (LogicUtil.nullOrEmpty(numberGet = posFancy
					        .replaceAll("[^0-9]", ""))) {
						
						// if no number, add as not fancy and continue
						if (!started) {
							ret.text(posFancy);
							started = true;
						} else {
							ret.then(posFancy);
						}
						
						int ind;
						string.delete(ind = string.indexOf(posFancy), ind
						        + posFancy.length());
						continue;
					}
					
					// get the number
					int number;
					try {
						number = Integer.valueOf(numberGet);
					} catch (final NumberFormatException e) {
						
						// Couldn't find it? add as not fancy and continue
						if (!started) {
							ret.text(posFancy);
							started = true;
						} else {
							ret.then(posFancy);
						}
						
						int ind;
						string.delete(ind = string.indexOf(posFancy), ind
						        + posFancy.length());
						continue;
					}
					
					// get the part with no number, to match a enum arg
					// type
					final String matcher = posFancy
					        .replaceAll("[0-9]", "");
					FancyLocaleArg type;
					
					// if number is same arg cycle, type was found, and
					// type is same as arg type...
					final Object curArg = args[i];
					if (number == i
					        && (type = FancyLocaleArg.valueOf(matcher)) != null
					        && type.getTypeClass().equals(
					                curArg.getClass())) {
						
						// add fancy part
						ret = type.parseFancyPart(ret, curArg);
						
						int ind;
						string.delete(ind = string.indexOf(posFancy) - 1,
						        ind + posFancy.length() + 1);
					}
				}
			}
		} else {
			ret.text(string.toString());
		}
		
		return ret;
	}
	
	public static void sendFilteredMessage(CommandSender[] senders,
	        NoxFilter<CommandSender> filter, String message) {
		for (final CommandSender sender : senders)
			if (filter.isFiltered(sender)) {
				sendMessage(sender, message);
			}
	}
	
	public static void sendFilteredMessage(Player[] players,
	        NoxFilter<Player> filter, String message) {
		for (final Player player : players)
			if (filter.isFiltered(player)) {
				sendMessage(player, message);
			}
	}
	
	public static void sendFilteredMessages(CommandSender[] senders,
	        NoxFilter<CommandSender> filter, String... messages) {
		for (final CommandSender sender : senders)
			if (filter.isFiltered(sender)) {
				sendMessages(sender, messages);
			}
	}
	
	public static void sendFilteredMessages(Player[] players,
	        NoxFilter<Player> filter, String... messages) {
		for (final Player player : players)
			if (filter.isFiltered(player)) {
				sendMessages(player, messages);
			}
	}
	
	public static void sendLocale(CommandSender sender, NoxLocale locale,
	        String... args) {
		locale.send(sender, args);
	}
	
	public static void sendMessage(CommandSender sender, String message) {
		sender.sendMessage(parseColor(message));
	}
	
	public static void sendMessage(CommandSender[] senders, String message) {
		for (final CommandSender sender : senders) {
			sender.sendMessage(message);
		}
	}
	
	public static void sendMessageNearby(Entity entity, double radX,
	        double radY, double radZ, String message) {
		for (final Entity e : entity.getNearbyEntities(radX, radY, radZ))
			if (e instanceof CommandSender) {
				((CommandSender) e).sendMessage(message);
			}
	}
	
	public static void sendMessageNearby(Entity entity, double radius,
	        String message) {
		sendMessageNearby(entity, radius, radius, radius, message);
	}
	
	public static void sendMessages(CommandSender sender,
	        String... messages) {
		if (!LogicUtil.nullOrEmpty(messages)) {
			for (final String message : messages) {
				sendMessage(sender, message);
			}
		}
	}
	
	public static void sendMessages(CommandSender[] senders,
	        String... messages) {
		for (final CommandSender sender : senders) {
			sendMessages(sender, messages);
		}
	}
	
	public static void sendMessagesToGroup(final String groupName,
	        String... messages) {
		sendFilteredMessages(Bukkit.getOnlinePlayers(),
		        new NoxFilter<Player>() {
			        
			        @Override
			        public boolean isFiltered(Player player) {
				        if (VaultAdapter.isPermissionsLoaded()
				                && VaultAdapter.permission
				                        .hasGroupSupport()
				                && VaultAdapter.permission.playerInGroup(
				                        player, groupName))
					        return true;
				        else if (VaultAdapter.isPermissionsLoaded()
				                && !VaultAdapter.permission
				                        .hasGroupSupport())
					        return VaultAdapter.permission.has(player,
					                "group." + groupName);
				        return false;
			        }
		        }, messages);
	}
	
	public static void sendMessageToGroup(final String groupName,
	        String message) {
		sendFilteredMessage(Bukkit.getOnlinePlayers(),
		        new NoxFilter<Player>() {
			        
			        @Override
			        public boolean isFiltered(Player player) {
				        if (VaultAdapter.isPermissionsLoaded()
				                && VaultAdapter.permission
				                        .hasGroupSupport()
				                && VaultAdapter.permission.playerInGroup(
				                        player, groupName))
					        return true;
				        else if (VaultAdapter.isPermissionsLoaded()
				                && !VaultAdapter.permission
				                        .hasGroupSupport())
					        return VaultAdapter.permission.has(player,
					                "group." + groupName);
				        return false;
			        }
		        }, message);
	}
	
	public static void sendPermMessages(CommandSender[] senders,
	        final String permission, String... messages) {
		sendFilteredMessages(senders, new NoxFilter<CommandSender>() {
			
			@Override
			public boolean isFiltered(CommandSender sender) {
				if (VaultAdapter.isPermissionsLoaded()
				        && VaultAdapter.permission.has(sender, permission))
					return true;
				else if (sender.hasPermission(permission))
					return true;
				return false;
			}
		}, messages);
	}
	
}
