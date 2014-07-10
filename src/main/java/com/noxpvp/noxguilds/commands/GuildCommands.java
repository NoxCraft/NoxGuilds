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

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import se.ranzdo.bukkit.methodcommand.Arg;
import se.ranzdo.bukkit.methodcommand.Command;
import se.ranzdo.bukkit.methodcommand.Wildcard;

import com.noxpvp.noxguilds.access.GuildPermissionType;
import com.noxpvp.noxguilds.gui.GuildInfoMenu;
import com.noxpvp.noxguilds.gui.GuildSelectMenu;
import com.noxpvp.noxguilds.guild.Guild;
import com.noxpvp.noxguilds.guildplayer.GuildPlayer;
import com.noxpvp.noxguilds.internal.FutureReturn;
import com.noxpvp.noxguilds.locale.FancyLocaleArg;
import com.noxpvp.noxguilds.locale.NoxGuildLocale;
import com.noxpvp.noxguilds.manager.GuildManager;
import com.noxpvp.noxguilds.manager.GuildPlayerManager;
import com.noxpvp.noxguilds.util.GuildPlayerUtils;
import com.noxpvp.noxguilds.util.GuildUtil;
import com.noxpvp.noxguilds.util.MessageUtil;
import com.noxpvp.noxguilds.util.NoxEnumUtil;
import com.noxpvp.noxguilds.util.PermissionUtil;

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
	
	// Guild
	@Command(
	        identifier = "guild",
	        description = "Shows the player their guild info, if they belong to one",
	        onlyPlayers = true,
	        permissions = "noxguilds.guild.show")
	public void guild(final Player sender) {
		final GuildPlayer gp = GuildPlayerManager.getInstance()
		        .getFromPlayer(sender);
		
		List<Guild> guilds;
		
		// Has less than 2
		if ((guilds = gp.getGuilds()).size() < 2) {
			if (guilds.size() == 1) {
				new GuildInfoMenu(sender, guilds.get(0)).show();
			} else {
				NoxGuildLocale.GUILD_ERROR_NO_GUILD.send(sender);
			}
			
			// Multiple guilds
		} else {
			new GuildSelectMenu(sender, guilds, null) {
				
				@Override
				public void onSelect(Guild selected) {
					new GuildInfoMenu(sender, selected).show();
				}
			};
		}
	}
	
	// Guild Create
	@Command(
	        identifier = "guild create",
	        description = "creates a new guild with the given name",
	        onlyPlayers = true,
	        permissions = "noxguilds.guild.create")
	public void guildCreate(Player sender, @Arg(
	        name = "guild name",
	        description = "the name of the guild") String name) {
		final GuildPlayer gp = GuildPlayerManager.getInstance()
		        .getFromPlayer(sender);
		
		if (gp.isGuildMaster()) {
			NoxGuildLocale.PLAYER_ERROR_CREATEGUILD_ALREADYOWNER
			        .send(sender);
			return;
		}
		
		if (!name.matches("[a-zA-Z0-9]+")) {
			NoxGuildLocale.GUILD_ERROR_INVALID_NAMEFASHION.send(sender);
			return;
		}
		
		gp.addToGuild(new Guild(name, gp.getUID()));
		MessageUtil.broadcast(NoxGuildLocale.GUILD_CREATED.get(name));
		
	}
	
	// Guild invite
	@Command(identifier = "guild invite",
	        description = "Invite a outsider into the guild",
	        onlyPlayers = true,
	        permissions = "noxguilds.guild.recruit.invite")
	public void guildInvite(
	        final Player sender,
	        @Arg(
	                name = "Player Name",
	                description = "Name of a player to invite") final Player invited) {
		
		new GuildGetter(GuildPlayerManager.getInstance().getFromPlayer(
		        sender)) {
			
			@Override
			public void onReturn(Guild returned) {
				final GuildPlayer gpInvited = GuildPlayerManager
				        .getInstance().getFromPlayer(invited);
				
				if (returned.hasMember(gpInvited)) {
					NoxGuildLocale.ERROR_GENERIC.send(sender,
					        "That player is already apart of the guild!");
					return;
				}
				
				if (returned.isInvited(gpInvited)) {
					NoxGuildLocale.ERROR_GENERIC
					        .send(sender,
					                "That player has already been invited to the guild!");
					return;
				}
				
				if (GuildUtil.isEnemyOf(returned, gpInvited)) {
					NoxGuildLocale.ERROR_GENERIC.send(sender,
					        "That player is an enemy of this guild!");
					return;
				}
				
				returned.invite(gpInvited);
			}
		};
	}
	
	// Guild join
	@Command(
	        identifier = "guild join",
	        description = "Join an open guild, or one you have been recently invited to join",
	        onlyPlayers = true,
	        permissions = "noxguilds.guild.join")
	public void guildJoin(
	        Player sender,
	        @Arg(name = "Guild name", description = "Guild to join") String guildName) {
		
		final Guild guild = GuildManager.getInstance()
		        .getByName(guildName);
		if (guild == null) {
			NoxGuildLocale.GUILD_ERROR_NOT_FOUND_NAMED.send(sender,
			        guildName);
			return;
		}
		
		final GuildPlayer gp = GuildPlayerManager.getInstance()
		        .getFromPlayer(sender);
		
		if (guild.isOpen()) {
			if (GuildUtil.isEnemyOf(guild, gp))
				return;// TODO finish
				
		}
		
	}
	
	// Guild list
	@Command(
	        identifier = "guild list",
	        description = "Shows all the guilds on the server",
	        onlyPlayers = true,
	        permissions = "noxguilds.guild.show-all")
	public void guildList(final Player sender) {
		new GuildSelectMenu(sender,
		        new ArrayList<Guild>(GuildManager.getInstance()
		                .getLoadeds().values()), null) {
			
			@Override
			public void onSelect(Guild selected) {
				new GuildInfoMenu(sender, selected, null).show();
			}
		}.show();
		
	}
	
	// Guild kick
	
	// Guild leave
	
	// Guild set badge
	@Command(
	        identifier = "guild set badge",
	        description = "Sets your guild badge item to the item you are holding",
	        onlyPlayers = true,
	        permissions = "noxguilds.guild.set.badge")
	public void guildSetBadge(final Player sender) {
		
		final ItemStack held;
		final GuildPlayer gp = GuildPlayerManager.getInstance()
		        .getFromPlayer(sender);
		
		if (!validateHasGuild(gp))
			return;
		
		if ((held = sender.getItemInHand()) == null
		        || held.getType() == Material.AIR)
			// NoxGuildLocale.ERROR_INVALID_INPUT.send(sender,
			// "You must be holding an item first!");TODO finish
			return;
		
		List<Guild> guilds;
		if ((guilds = gp.getGuilds()) != null) {
			if (guilds.size() > 1) {
				new GuildSelectMenu(sender, guilds, null) {
					
					@Override
					public void onSelect(Guild selected) {
						if (!PermissionUtil.hasPermission(selected, gp,
						        GuildPermissionType.BADGE)) {
							NoxGuildLocale.ERROR_NO_PERMISSION
							        .send(sender);
							return;
						}
						
						selected.setItemBadge(held);
						MessageUtil.sendMessage(sender,
						        "&6You set the badge of \"&b"
						                + selected.getName()
						                + "&6\" to &b"
						                + NoxEnumUtil.getFriendlyName(held
						                        .getType()));
					}
				}.show();
			} else if (guilds.size() == 1) {
				Guild g = null;
				for (final Guild guild : guilds) {
					if (guild != null) {
						g = guild;
						break;
					}
				}
				
				if (g != null) {
					if (!PermissionUtil.hasPermission(g, gp,
					        GuildPermissionType.BADGE)) {
						NoxGuildLocale.ERROR_NO_PERMISSION.send(sender);
						return;
					}
					
					g.setItemBadge(held);
					MessageUtil.sendMessage(sender,
					        "&6You set the badge of \"&b"
					                + g.getName()
					                + "&6\" to &b"
					                + NoxEnumUtil.getFriendlyName(held
					                        .getType()));
				}
			}
		} else {
			NoxGuildLocale.ERROR_NULL.send(sender,
			        "You have a guild, but we couldn't get to it!");
		}
	}
	
	// Guild set percenttaxes [on / off]
	
	// Guild set taxes [amount]
	
	// Guild set perm [type] [on / off]
	
	// Guild set tag [tag / description]
	@Command(
	        identifier = "guild set tag",
	        description = "Sets your guild tag",
	        onlyPlayers = true,
	        permissions = "noxguilds.guild.set.tag")
	public void guildSetTag(
	        final Player sender,
	        @Wildcard @Arg(
	                name = "tag",
	                description = "New description for the guild") final String tag) {
		
		final GuildPlayer gp = GuildPlayerManager.getInstance()
		        .getFromPlayer(sender);
		
		if (!validateHasGuild(gp))
			return;
		
		if (tag.length() > 128)
			// NoxGuildLocale..send(sender,
			// "Guild tags have a max length of 128, yours is " +
			// tag.length());TODO finish
			return;
		
		List<Guild> guilds;
		if ((guilds = gp.getGuilds()) != null) {
			if (guilds.size() > 1) {
				new GuildSelectMenu(sender, guilds, null) {
					
					@Override
					public void onSelect(Guild selected) {
						selected.setTag(tag);
						MessageUtil.sendMessage(sender,
						        "&6You set the tag of \"&b"
						                + selected.getName()
						                + "&6\" to &b" + tag);
					}
				}.show();
			} else if (guilds.size() == 1) {
				Guild g = null;
				for (final Guild guild : guilds) {
					if (guild != null) {
						g = guild;
						break;
					}
				}
				
				if (g != null) {
					g.setTag(tag);
					MessageUtil.sendMessage(sender,
					        "&6You set the tag of \"&b" + g.getName()
					                + "&6\" to &b" + tag);
				}
			}
		} else {
			NoxGuildLocale.ERROR_NULL.send(sender,
			        "You have a guild, but we couldn't get to it!");
		}
	}
	
	// testing
	@Command(
	        identifier = "test")
	public void test(final Player sender) {
		MessageUtil.parseFancyArguments(
		        MessageUtil.parseColor
		                (
		                "&ctest test &b" + FancyLocaleArg.GUILD.getVar(0)
		                ),
		        GuildManager.getInstance().getByName("celsius")).send(
		        sender);
		
	}
	
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	// Helpers
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	
	private boolean validateHasGuild(GuildPlayer gp) {
		if (!GuildPlayerUtils.hasGuild(gp)) {
			NoxGuildLocale.GUILD_ERROR_NO_GUILD.send(gp.getPlayer());
			return false;
		}
		
		return true;
	}
	
	private boolean validateHasGuild(GuildPlayer gp, String guild) {
		if (!validateHasGuild(gp))
			return false;
		
		if (GuildPlayerUtils.hasGuild(gp, guild))
			return true;
		
		NoxGuildLocale.GUILD_ERROR_NO_GUILD_NAMED.send(gp.getPlayer(),
		        guild);
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
				final Guild guild;
				new GuildSelectMenu(gp.getPlayer(), guilds, null) {
					
					@Override
					public void onSelect(Guild selected) {
						onReturn(selected);
					}
				};
			} else if (guilds.size() == 1) {
				onReturn(guilds.get(0));
			} else {
				NoxGuildLocale.ERROR_NULL.send(gp.getPlayer(),
				        "&cCould not find a guild...");
			}
		}
		
	}
	
}
