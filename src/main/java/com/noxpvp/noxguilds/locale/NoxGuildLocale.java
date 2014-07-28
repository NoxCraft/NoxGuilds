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

import org.bukkit.command.CommandSender;

import com.noxpvp.noxguilds.util.MessageUtil;

/**
 * @author ConnorStone
 * 
 */
public class NoxGuildLocale extends NoxLocale {
	
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	// Static fields
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	
	// Money
	
	public static final NoxGuildLocale	TRANSFER_SUCCESS;
	public static final NoxGuildLocale	TRANSFER_FAILED;
	
	// Questions
	
	public static final NoxGuildLocale	QUESTION_ASK_PLAYERJOINGUILD;
	
	// Guild
	
	public static final NoxGuildLocale	GUILD_CREATED_BROADCAST;
	public static final NoxGuildLocale	GUILD_LAND_CLAIMED;
	public static final NoxGuildLocale	GUILD_INVITED_PLAYER;
	public static final NoxGuildLocale	GUILD_PLAYER_JOINED;
	public static final NoxGuildLocale	GUILD_PLAYER_KICKED;
	public static final NoxGuildLocale	GUILD_KICKED_KINGDOM;
	public static final NoxGuildLocale	GUILD_ERROR_NO_GUILD_NAMED;
	public static final NoxGuildLocale	GUILD_ERROR_MEMBER_NOTFOUND;
	public static final NoxGuildLocale	GUILD_ERROR_NOT_FOUND;
	public static final NoxGuildLocale	GUILD_ERROR_NOT_FOUND_NAMED;
	public static final NoxGuildLocale	GUILD_ERROR_ALREADY_CREATED;
	public static final NoxGuildLocale	GUILD_ERROR_INVALID_NAMEFASHION;
	public static final NoxGuildLocale	GUILD_ERROR_CLAIM_TOO_CLOSE_GUILD;
	public static final NoxGuildLocale	GUILD_ERROR_CLAIM_ALREADYOWNED;
	public static final NoxGuildLocale	GUILD_ERROR_CLAIM_ALREADYCLAIMED;
	public static final NoxGuildLocale	GUILD_ERROR_CLAIM_TOO_FAR_GUILD;
	
	// Kingdom
	
	public static final NoxGuildLocale	KINGDOM_CREATED_BROADCAST;
	public static final NoxGuildLocale	KINGDOM_INVITED_GUILD;
	public static final NoxGuildLocale	KINGDOM_GUILD_KICKED;
	public static final NoxGuildLocale	KINGDOM_ERROR_NO_KINGDOM;
	public static final NoxGuildLocale	KINGDOM_ERROR_NO_KINGDOM_NAMED;
	public static final NoxGuildLocale	KINGDOM_ERROR_NOT_FOUND;
	public static final NoxGuildLocale	KINGDOM_ERROR_NOT_FOUND_NAMED;
	public static final NoxGuildLocale	KINGDOM_ERROR_ALREADY_CREATED;
	public static final NoxGuildLocale	KINGDOM_ERROR_INVALID_NAMEFASHION;
	
	// Player
	
	public static final NoxGuildLocale	PLAYER_CREATED_GUILD;
	public static final NoxGuildLocale	PLAYER_CREATED_KINGDOM;
	public static final NoxGuildLocale	PLAYER_INVITED_GUILD;
	public static final NoxGuildLocale	PLAYER_ERROR_JOINGUILD_CURRENTLYENEMY;
	public static final NoxGuildLocale	PLAYER_KICKED_GUILD;
	public static final NoxGuildLocale	PLAYER_MOVED_GUILD;
	public static final NoxGuildLocale	PLAYER_MOVED_UNOWNED;
	public static final NoxGuildLocale	PLAYER_MOVED_PLOT;
	public static final NoxGuildLocale	PLAYER_ERROR_JOINGUILD_NOTINVITEDOROPEN;
	public static final NoxGuildLocale	PLAYER_ERROR_JOINGUILD_ALREADYJOINED;
	public static final NoxGuildLocale	PLAYER_ERROR_CREATEGUILD_ALREADYOWNER;
	public static final NoxGuildLocale	PLAYER_ERROR_NO_GUILD;
	
	// Command
	
	public static final NoxGuildLocale	COMMAND_ACCEPTDENY_DENY_NOPENDING;
	public static final NoxGuildLocale	COMMAND_ACCEPTDENY_ACCEPT_NOPENDING;
	
	// Chat
	
	public static final NoxGuildLocale	CHAT_CHANNEL_GUILD_TAG;
	public static final NoxGuildLocale	CHAT_CHANNEL_KINGDOM_TAG;
	public static final NoxGuildLocale	CHAT_HEADER_START;
	public static final NoxGuildLocale	CHAT_HEADER_START_SPECIFIC;
	public static final NoxGuildLocale	CHAT_HEADER_END;
	public static final NoxGuildLocale	CHAT_CHANNEL_SERVER_TAG;
	
	// Error
	
	public static final NoxGuildLocale	ERROR_GENERIC;
	public static final NoxGuildLocale	ERROR_NO_PERMISSION;
	public static final NoxGuildLocale	ERROR_NO_PERMISSION_VERBOSE;
	public static final NoxGuildLocale	ERROR_NULL;
	public static final NoxGuildLocale	ERROR_EXCEPTION;
	
	static {
		// Money
		
		TRANSFER_FAILED = new NoxGuildLocale("money.transfer.fail",
				"&cTransfer Failed: &b%0%");
		
		TRANSFER_SUCCESS = new NoxGuildLocale("money.transfer.success",
				"&bTransfer Success: &b%0%");
		
		// Questions
		
		QUESTION_ASK_PLAYERJOINGUILD = new NoxGuildLocale(
				"question.ask.playerjoinguild",
				"&bYou have been invited to join the guild of "
						+ FancyLocaleArg.GUILD.getVar(1));
		
		// Guild
		
		GUILD_CREATED_BROADCAST = new NoxGuildLocale("guild.created.broadcast",
				FancyLocaleArg.PLAYER_INFO.getVar(0)
						+ " &bcreated the guild of "
						+ FancyLocaleArg.GUILD.getVar(1));
		
		GUILD_LAND_CLAIMED = new NoxGuildLocale("guild.land.claimed", "");// TODO
		
		GUILD_INVITED_PLAYER = new NoxGuildLocale(
				"guild.invited.player", FancyLocaleArg.PLAYER_INFO.getVar(0)
						+ " &bHas been invited to join the guild!");
		
		GUILD_PLAYER_JOINED = new NoxGuildLocale("guild.player.joined",
				FancyLocaleArg.PLAYER_INFO.getVar(0) + " &bJoined the guild");
		
		GUILD_PLAYER_KICKED = new NoxGuildLocale("guild.player.kicked",
				FancyLocaleArg.PLAYER_INFO.getVar(0)
						+ " &bWas kicked from the guild");
		
		GUILD_KICKED_KINGDOM = new NoxGuildLocale("guild.kicked.kingdom",
				FancyLocaleArg.GUILD.getVar(0)
						+ " &bWas kicked from the kingdom of "
						+ FancyLocaleArg.KINGDOM.getVar(1));
		
		GUILD_ERROR_NO_GUILD_NAMED = new NoxGuildLocale(
				"guild.no-guild-named",
				"&cYou don't belong to a guild by the name of \"&b%0%&c\"");
		
		GUILD_ERROR_NOT_FOUND = new NoxGuildLocale(
				"guild.error.not-found", "&cCould not find a guild");
		
		GUILD_ERROR_NOT_FOUND_NAMED = new NoxGuildLocale(
				"guild.not-found-named",
				"&cCould not find a guild by the name of \"&b%0%&c\"");
		
		GUILD_ERROR_ALREADY_CREATED = new NoxGuildLocale(
				"guild.already-created",
				"&cA Guild already exists with a similar name!");
		
		GUILD_ERROR_INVALID_NAMEFASHION = new NoxGuildLocale(
				"guild.invalid-name",
				"&cA guild name may only contains letters A - Z");
		
		GUILD_ERROR_MEMBER_NOTFOUND = new NoxGuildLocale(
				"guild.error.member.notfound",
				"&cCould not find a member by the name of &e%0%");
		
		GUILD_ERROR_CLAIM_ALREADYCLAIMED = new NoxGuildLocale(
				"guild.error.claim.already-claimed",
				"&cThis area has already been claimed by "
						+ FancyLocaleArg.GUILD.getVar(0));
		
		GUILD_ERROR_CLAIM_ALREADYOWNED = new NoxGuildLocale(
				"guild.error.claim.already-owned",
				"&cThis area already belongs to the guild of "
						+ FancyLocaleArg.GUILD.getVar(0));
		
		// Kingdom
		
		KINGDOM_CREATED_BROADCAST = new NoxGuildLocale(
				"kingdom.created.broadcast", FancyLocaleArg.PLAYER_INFO.getVar(0)
						+ " &bCreated the kingdom of "
						+ FancyLocaleArg.KINGDOM.getVar(1));
		
		KINGDOM_INVITED_GUILD = new NoxGuildLocale("kingdom.invited.guild",
				FancyLocaleArg.GUILD.getVar(0)
						+ " &bHas been invited to join the Kingdom!");
		
		KINGDOM_GUILD_KICKED = new NoxGuildLocale("kingdom.guild.kicked",
				FancyLocaleArg.GUILD.getVar(0) + " &bWas kicked from the kingdom");
		
		KINGDOM_ERROR_NO_KINGDOM = new NoxGuildLocale(
				"kingdom.error.no-kingdom",
				"&cYou don't belong to a Kingdom");
		
		KINGDOM_ERROR_NO_KINGDOM_NAMED = new NoxGuildLocale(
				"kingdom.error.no-kingdom-named",
				"&cYou don't belong to a Kingdom by the name of \"&b%0%&c\"");
		
		KINGDOM_ERROR_NOT_FOUND = new NoxGuildLocale(
				"kingdom.error.not-found", "&cCould not find a Kingdom");
		
		KINGDOM_ERROR_NOT_FOUND_NAMED = new NoxGuildLocale(
				"kingdom.error.not-found-named",
				"&cCould not find a Kingdom by the name of &b%0%");
		
		KINGDOM_ERROR_ALREADY_CREATED = new NoxGuildLocale(
				"kingdom.error.already-created",
				"&cA Kingdom already exists with a similar name!");
		
		KINGDOM_ERROR_INVALID_NAMEFASHION = new NoxGuildLocale(
				"kingdom.error.invalid-name",
				"&cA Kingdom name may only contains letters A - Z");
		
		// Player
		
		PLAYER_CREATED_GUILD = new NoxGuildLocale("guild.created",
				"&byou created the guild of " + FancyLocaleArg.GUILD.getVar(0));
		
		PLAYER_CREATED_KINGDOM = new NoxGuildLocale("kingdom.created",
				"&bYou created the Kingdom " + FancyLocaleArg.KINGDOM.getVar(0));
		
		PLAYER_INVITED_GUILD = new NoxGuildLocale(
				"player.invited.guild",
				"&bYou have been invited to join the guild of "
						+ FancyLocaleArg.GUILD.getVar(0));
		
		PLAYER_KICKED_GUILD = new NoxGuildLocale("player.kicked.guild",
				"&bYou were kicked from the guild of "
						+ FancyLocaleArg.GUILD.getVar(0));
		
		PLAYER_ERROR_JOINGUILD_CURRENTLYENEMY = new NoxGuildLocale(
				"player.error.join-guild.currently-enemy",
				"&cYou are currently an enemy of "
						+ FancyLocaleArg.GUILD.getVar(0));
		
		PLAYER_MOVED_GUILD = new NoxGuildLocale("player.moved.guild",
				"&3[&a*&3] " + FancyLocaleArg.GUILD.getVar(0) + " &3~ "
						+ FancyLocaleArg.PLOT_INFO.getVar(1));
		
		PLAYER_MOVED_PLOT = new NoxGuildLocale("player.moved.plot",
				"&3[&a*&3] " + FancyLocaleArg.PLOT_INFO.getVar(0));
		
		PLAYER_MOVED_UNOWNED = new NoxGuildLocale("player.moved.unowned",
				"&3[&a*&3] &8Unowned");
		
		PLAYER_ERROR_JOINGUILD_ALREADYJOINED = new NoxGuildLocale(
				"player.error.join-guild.already-joined",
				"&cYou are already apart of that guild!");
		
		PLAYER_ERROR_JOINGUILD_NOTINVITEDOROPEN = new NoxGuildLocale(
				"player.error.join-guild.notopen-orinvited",
				"&cYou are not currently invited to that guild, nor is it open");
		
		PLAYER_ERROR_CREATEGUILD_ALREADYOWNER = new NoxGuildLocale(
				"player.error.create-guild.already-owner",
				"&cYou cannot create a guild if you are already a guild owner");
		
		PLAYER_ERROR_NO_GUILD = new NoxGuildLocale("guild.no-guild",
				"&cYou don't belong to a guild");
		
		// Command
		
		COMMAND_ACCEPTDENY_DENY_NOPENDING = new NoxGuildLocale(
				"command.acceptdeny_deny_nopending",
				"&cYou don't have anything to deny right now");
		
		COMMAND_ACCEPTDENY_ACCEPT_NOPENDING = new NoxGuildLocale(
				"command.acceptdeny_accept_nopending",
				"&cYou don't have anything to accept right new");
		
		// Chat
		
		CHAT_CHANNEL_GUILD_TAG = new NoxGuildLocale(
				"chat.channel.tag.guild", "&b[" + FancyLocaleArg.GUILD.getVar(0)
						+ "&b]");
		
		CHAT_CHANNEL_KINGDOM_TAG = new NoxGuildLocale(
				"chat.channel.tag.guild", "&b[" + FancyLocaleArg.KINGDOM.getVar(0)
						+ "&b]");
		CHAT_HEADER_START = new NoxGuildLocale("chat.header-start",
				"&b[======~*~====={&cNox&2Guilds&b}=====~*~======]");
		
		CHAT_HEADER_START_SPECIFIC = new NoxGuildLocale(
				"chat.header-other",
				"&b[=====~*~===={&cNox&2Guilds &b- &e%0% &b}====~*~=====]");
		
		CHAT_HEADER_END = new NoxGuildLocale("chat.header-end",
				"&b[======&c~*~&b======&c~#*#~&b======&c~*~&b======]");
		
		CHAT_CHANNEL_SERVER_TAG = new NoxGuildLocale(
				"chat.channel.tag.guild", "&b[&aNoxGuilds&b] ");
		
		// Error
		
		ERROR_GENERIC = new NoxGuildLocale("error.generic",
				"&cError: &b%0%");
		
		ERROR_NULL = new NoxGuildLocale("error.null",
				"&cUh Oh...null: &b%0%");
		
		ERROR_EXCEPTION = new NoxGuildLocale("error.exception",
				"&cException: &b%0%");
		
		ERROR_NO_PERMISSION = new NoxGuildLocale(
				"error.failed-permission",
				"&cYou don't have permission for that");
		
		ERROR_NO_PERMISSION_VERBOSE = new NoxGuildLocale(
				"error.failed-permission-verbose",
				"&cYou don't have permission for that: &b%0%");
		
	}
	
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	// Instance Fields
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	// Constructors
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	
	public NoxGuildLocale(String path, String def) {
		super(path, def);
		
	}
	
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	// Static Methods
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	// Instance Methods
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	
	public FancyMessage get(FancyMessage starter, Object... args) {
		if (args.length > 0)
			return MessageUtil.parseFancyArguments(starter, MessageUtil
					.parseColor(getDefault()), args);
		
		return new FancyMessage(MessageUtil.parseColor(getDefault()));
	}
	
	public FancyMessage get(Object... args) {
		return get(new FancyMessage(""), args);
	}
	
	public void send(CommandSender receiver, Object... args) {
		get(args).send(receiver);
	}
	
}
