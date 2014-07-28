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

package com.noxpvp.noxguilds;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import net.milkbowl.vault.chat.Chat;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.permission.Permission;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.RegisteredServiceProvider;

import com.bergerkiller.bukkit.common.ModuleLogger;
import com.noxpvp.noxguilds.util.MessageUtil;

public class VaultAdapter {
	
	public static Chat			chat		= null;
	public static Economy		economy		= null;
	public static Permission	permission	= null;
	
	public static boolean isChatLoaded() {
		return chat != null;
	}
	
	public static boolean isEconomyLoaded() {
		return economy != null;
	}
	
	public static boolean isPermissionsLoaded() {
		return permission != null;
	}
	
	public static void load() {
		GroupUtils.log = new ModuleLogger(NoxGuilds.getInstance(),
				"VaultAdapter.GroupUtils");
		setupChat();
		setupEconomy();
		setupPermission();
	}
	
	public static boolean setupChat() {
		final RegisteredServiceProvider<Chat> service = Bukkit.getServer()
				.getServicesManager().getRegistration(
						net.milkbowl.vault.chat.Chat.class);
		if (service != null) {
			chat = service.getProvider();
		}
		return chat != null;
	}
	
	public static boolean setupEconomy() {
		final RegisteredServiceProvider<Economy> service = Bukkit
				.getServer().getServicesManager().getRegistration(
						net.milkbowl.vault.economy.Economy.class);
		if (service != null) {
			economy = service.getProvider();
		}
		return economy != null;
	}
	
	public static boolean setupPermission() {
		final RegisteredServiceProvider<Permission> service = Bukkit
				.getServer().getServicesManager().getRegistration(
						net.milkbowl.vault.permission.Permission.class);
		if (service != null) {
			permission = service.getProvider();
		}
		return permission != null;
	}
	
	public static class ChatUtil {
		
		public static String getFormatedPlayerName(Player p) {
			final StringBuilder name = new StringBuilder(p.getName());
			if (isChatLoaded()) {
				name.insert(0, chat.getPlayerPrefix(p));
				name.append(chat.getPlayerSuffix(p));
			}
			
			return MessageUtil.parseColor(name.toString());
		}
		
	}
	
	public static class GroupUtils {
		
		static ModuleLogger	log;
		
		public static List<String> getGroupList() {
			if (isPermissionsLoaded() && permission.hasGroupSupport())
				return Arrays.asList(VaultAdapter.permission.getGroups());
			else if (log != null) {
				log.warning("Could not get group list... "
						+ (!isPermissionsLoaded() ? "Permissions not loaded."
								: permission.hasGroupSupport() ? ""
										: "No Group Support"));
			}
			
			return Collections.emptyList();
		}
		
		public static String getPlayerGroup(Player p) {
			if (isPermissionsLoaded())
				return VaultAdapter.permission.getPrimaryGroup(p);
			
			return null;
		}
		
	}
	
	public static class PermUtils {
		
		public static boolean hasPermission(Player p, String perm) {
			if (isPermissionsLoaded())
				return permission.has(p, perm);
			
			return p.hasPermission(perm);
		}
		
	}
}
