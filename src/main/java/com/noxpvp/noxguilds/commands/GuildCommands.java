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
package com.noxpvp.noxguilds.commands;

import java.util.ArrayList;
import java.util.List;

import mkremins.fanciful.FancyMessage;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import se.ranzdo.bukkit.methodcommand.Arg;
import se.ranzdo.bukkit.methodcommand.Command;
import se.ranzdo.bukkit.methodcommand.Wildcard;

import com.noxpvp.noxguilds.NoxGuilds;
import com.noxpvp.noxguilds.chat.FanceeMessage;
import com.noxpvp.noxguilds.gui.GuildInfoMenu;
import com.noxpvp.noxguilds.gui.GuildSelectMenu;
import com.noxpvp.noxguilds.guild.Guild;
import com.noxpvp.noxguilds.guildplayer.GuildPlayer;
import com.noxpvp.noxguilds.internal.FutureReturn;
import com.noxpvp.noxguilds.land.GuildPlot;
import com.noxpvp.noxguilds.land.TerritoryCoord;
import com.noxpvp.noxguilds.land.TerritoryID;
import com.noxpvp.noxguilds.locale.NoxGuildLocale;
import com.noxpvp.noxguilds.manager.GuildManager;
import com.noxpvp.noxguilds.manager.GuildPlayerManager;
import com.noxpvp.noxguilds.manager.PlotManager;
import com.noxpvp.noxguilds.util.GuildPlayerUtils;
import com.noxpvp.noxguilds.util.GuildUtil;
import com.noxpvp.noxguilds.util.MessageUtil;
import com.noxpvp.noxguilds.util.TerritoryUtils;

public class GuildCommands {
	
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
	
	@Command(
		identifier = "guild",
		description = "Shows the player their guild info, if they belong to one",
		onlyPlayers = true,
		permissions = "noxguilds.guild.show")
	public void guild(final Player sender) {
		final GuildPlayer gp = GuildPlayerManager.getInstance().getFromPlayer(sender);
		
		new GuildGetter(gp) {
			
			@Override
			public void onReturn(Guild returned) {
				new GuildInfoMenu(sender, returned).show();
			}
		}.body();
		
	}
	
	@Command(identifier = "guild claim", description = "Claim land for your guild", onlyPlayers = true)
	public void guildClaim(final Player sender) {
		final TerritoryCoord coord = new TerritoryCoord(sender.getLocation());
		final GuildPlayer gp = GuildPlayerManager.getInstance().getFromPlayer(sender);
		
		new GuildGetter(gp) {
			
			@Override
			protected void onReturn(Guild returned) {
				
				if (returned.hasTerritory(coord.getBlockAt())) {
					NoxGuildLocale.GUILD_ERROR_CLAIM_ALREADYOWNED.send(sender, returned);
					return;
				}
				
				if (TerritoryUtils.isClaimed(coord)) {
					final GuildPlot plot = PlotManager.getInstance().getPlot(new TerritoryID(coord.getChunk()));
					
					NoxGuildLocale.GUILD_ERROR_CLAIM_ALREADYCLAIMED.send(sender, plot != null ? plot.getGuildOwner()
						: null);
					return;
				}
				
				returned.addPlot(new GuildPlot(returned, coord));
				
			}
		}.body();
		
	}
	
	@Command(
		identifier = "guild create",
		description = "creates a new guild with the given name",
		onlyPlayers = true,
		permissions = "noxguilds.guild.create")
	public void guildCreate(Player sender, @Arg(name = "guild name", description = "the name of the guild") String name) {
		final GuildPlayer gp = GuildPlayerManager.getInstance().getFromPlayer(sender);
		
		if (gp.isGuildMaster()) {
			NoxGuildLocale.PLAYER_ERROR_CREATEGUILD_ALREADYOWNER.send(sender);
			return;
		}
		
		if (!name.matches("[a-zA-Z0-9]+")) {
			NoxGuildLocale.GUILD_ERROR_INVALID_NAMEFASHION.send(sender);
			return;
		}
		
		final Guild newGuild = new Guild(name, gp);
		newGuild.addMember(gp);
		
		NoxGuildLocale.PLAYER_CREATED_GUILD.send(sender, newGuild);
		
		NoxGuilds.getInstance().getChatChannel()
			.broadcast(new FanceeMessage(NoxGuildLocale.GUILD_CREATED_BROADCAST.get(gp, newGuild)));
	}
	
	@Command(identifier = "guild invite", description = "Invite a outsider into the guild", onlyPlayers = true)
	public void guildInvite(final Player sender,
		@Arg(name = "Player Name", description = "Name of a player to invite") final Player invited) {
		
		new GuildGetter(GuildPlayerManager.getInstance().getFromPlayer(sender)) {
			
			@Override
			public void onReturn(Guild returned) {
				final GuildPlayer gpInvited = GuildPlayerManager.getInstance().getFromPlayer(invited);
				
				if (returned.hasMember(gpInvited)) {
					NoxGuildLocale.ERROR_GENERIC.send(sender, "That player is already apart of the guild!");
					return;
				}
				
				if (returned.isInvited(gpInvited)) {
					NoxGuildLocale.ERROR_GENERIC.send(sender, "That player has already been invited to the guild!");
					return;
				}
				
				if (GuildUtil.isEnemyOf(returned, gpInvited)) {
					NoxGuildLocale.ERROR_GENERIC.send(sender, "That player is an enemy of this guild!");
					return;
				}
				
				returned.invite(gpInvited);
			}
		}.body();
	}
	
	@Command(
		identifier = "guild join",
		description = "Join an open guild, or one you have been recently invited to join",
		onlyPlayers = true,
		permissions = "noxguilds.guild.join")
	public void guildJoin(Player sender, @Arg(name = "Guild name", description = "Guild to join") String guildName) {
		
		final Guild guild = GuildManager.getInstance().getByName(guildName);
		if (guild == null) {
			NoxGuildLocale.GUILD_ERROR_NOT_FOUND_NAMED.send(sender, guildName);
			return;
		}
		
		final GuildPlayer gp = GuildPlayerManager.getInstance().getFromPlayer(sender);
		
		if (guild.hasMember(gp)) {
			NoxGuildLocale.PLAYER_ERROR_JOINGUILD_ALREADYJOINED.send(sender);
			return;
		}
		
		if (GuildUtil.isEnemyOf(guild, gp)) {
			NoxGuildLocale.PLAYER_ERROR_JOINGUILD_CURRENTLYENEMY.get(guild).send(sender);
			return;
		}
		
		if (!guild.isOpen() && !guild.isInvited(gp)) {
			NoxGuildLocale.PLAYER_ERROR_JOINGUILD_NOTINVITEDOROPEN.send(sender);
			return;
		}
		
		guild.addMember(gp);
		guild.getChatChannel().broadcast(new FanceeMessage(NoxGuildLocale.GUILD_PLAYER_JOINED.get(gp)));
	}
	
	@Command(identifier = "guild kick", description = "Kicks a player from the guild", onlyPlayers = true)
	public void guildKick(final Player sender,
		@Arg(name = "player name", description = "The guild member to kick") final Player kicked) {
		
		new GuildGetter(GuildPlayerManager.getInstance().getFromPlayer(sender)) {
			
			@Override
			public void onReturn(Guild returned) {
				
				if (!returned.hasMember(kicked.getUniqueId())) {
					NoxGuildLocale.GUILD_ERROR_MEMBER_NOTFOUND.send(sender, kicked.getName());
					return;
				}
				
				returned.removeMember(GuildPlayerManager.getInstance().getFromPlayer(kicked));
				
				returned.getChatChannel().broadcast(
					new FanceeMessage(NoxGuildLocale.GUILD_PLAYER_KICKED.get(kicked, returned)));
				
				NoxGuildLocale.PLAYER_KICKED_GUILD.send(kicked, returned);
			}
		}.body();
		
	}
	
	@Command(
		identifier = "guild list",
		description = "Shows all the guilds on the server",
		onlyPlayers = true,
		permissions = "noxguilds.guild.show-all")
	public void guildList(final Player sender) {
		new GuildSelectMenu(sender, new ArrayList<Guild>(GuildManager.getInstance().getLoadedMap().values()), null) {
			
			@Override
			public void onSelect(Guild selected) {
				new GuildInfoMenu(sender, selected, null).show();
			}
		}.show();
		
	}
	
	@Command(
		identifier = "guild set badge",
		description = "Sets your guild badge item to the item you are holding",
		onlyPlayers = true,
		permissions = "noxguilds.guild.set.badge")
	public void guildSetBadge(final Player sender) {
		
		final ItemStack held;
		final GuildPlayer gp = GuildPlayerManager.getInstance().getFromPlayer(sender);
		
		if (!validateHasGuild(gp))
			return;
		
		if ((held = sender.getItemInHand()) == null || held.getType() == Material.AIR) {
			NoxGuildLocale.ERROR_GENERIC.send(sender, "You must be holding an item first!");
			return;
		}
		
		new GuildGetter(gp) {
			
			@Override
			protected void onReturn(Guild returned) {
				returned.setItemBadge(held);
				// TODO message
			}
		}.get();
	}
	
	// Guild set taxes [amount]
	
	// Guild set tag [tag / description]
	@Command(
		identifier = "guild set tag",
		description = "Sets your guild tag",
		onlyPlayers = true,
		permissions = "noxguilds.guild.set.tag")
	public void guildSetTag(final Player sender, @Wildcard @Arg(
		name = "tag",
		description = "New description for the guild") final String tag) {
		
		final GuildPlayer gp = GuildPlayerManager.getInstance().getFromPlayer(sender);
		
		if (!validateHasGuild(gp))
			return;
		
		if (tag.length() > 128)
			// NoxGuildLocale..send(sender,
			// "Guild tags have a max length of 128, yours is " +
			// tag.length());TODO finish
			return;
		
		new GuildGetter(gp) {
			
			@Override
			public void onReturn(Guild returned) {
				returned.setTag(tag);
				MessageUtil.sendMessage(sender, "&6You set the tag of \"&b" + returned.getName() + "&6\" to &b" + tag);
				
			}
		}.get();
	}
	
	// testing
	@Command(identifier = "test")
	public void test(final Player sender) {
		new FanceeMessage(new FancyMessage("").then("[").color(ChatColor.GRAY).then("Celsius").color(ChatColor.GREEN)
			.then("] ").color(ChatColor.GRAY).then("playername ").color(ChatColor.YELLOW).then("test test test test")
			.color(ChatColor.AQUA).then(" guildname").color(ChatColor.GREEN).then(" kingdomname")
			.color(ChatColor.LIGHT_PURPLE)).send(Bukkit.getOnlinePlayers());
		
	}
	
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	// Helpers
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	
	private boolean validateHasGuild(GuildPlayer gp) {
		if (!GuildPlayerUtils.hasGuild(gp)) {
			NoxGuildLocale.PLAYER_ERROR_NO_GUILD.send(gp.getPlayer());
			return false;
		}
		
		return true;
	}
	
	private boolean validateHasGuild(GuildPlayer gp, String guild) {
		if (!validateHasGuild(gp))
			return false;
		
		if (GuildPlayerUtils.hasGuild(gp, guild))
			return true;
		
		NoxGuildLocale.GUILD_ERROR_NO_GUILD_NAMED.send(gp.getPlayer(), guild);
		return false;
	}
	
	private abstract class GuildGetter extends FutureReturn<Guild> {
		
		private final GuildPlayer	gp;
		
		public GuildGetter(GuildPlayer gp) {
			this.gp = gp;
		}
		
		@Override
		public void body() {
			final List<Guild> guilds = gp.getGuilds();
			
			if (guilds.size() > 1) {
				new GuildSelectMenu(gp.getPlayer(), guilds, null) {
					
					@Override
					public void onSelect(Guild selected) {
						onReturn(selected);
					}
				}.show();
			} else if (guilds.size() == 1) {
				onReturn(guilds.get(0));
			} else {
				final Player p = gp.getPlayer();
				NoxGuildLocale.PLAYER_ERROR_NO_GUILD.send(p);
			}
		}
		
	}
	
}
