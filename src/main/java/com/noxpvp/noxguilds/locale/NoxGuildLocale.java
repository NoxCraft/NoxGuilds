package com.noxpvp.noxguilds.locale;

public class NoxGuildLocale extends NoxLocale {
	
	// Chat
	public static final NoxGuildLocale	CHAT_HEADER_START;
	public static final NoxGuildLocale	CHAT_HEADER_START_SPECIFIC;
	public static final NoxGuildLocale	CHAT_HEADER_END;
	public static final NoxGuildLocale	CHAT_CHANNEL_GUILD_TAG;
	public static final NoxGuildLocale	CHAT_CHANNEL_KINGDOM_TAG;
	public static final NoxGuildLocale	CHAT_CHANNEL_SERVER_TAG;
	
	// Money
	public static final NoxGuildLocale	TRANSFER_SUCCESS;
	public static final NoxGuildLocale	TRANSFER_FAILED;
	
	// Guild
	public static final NoxGuildLocale	GUILD_CREATED;
	public static final NoxGuildLocale	GUILD_ERROR_NO_GUILD;
	public static final NoxGuildLocale	GUILD_ERROR_NO_GUILD_NAMED;
	public static final NoxGuildLocale	GUILD_ERROR_NOT_FOUND;
	public static final NoxGuildLocale	GUILD_ERROR_NOT_FOUND_NAMED;
	public static final NoxGuildLocale	GUILD_ERROR_ALREADY_CREATED;
	public static final NoxGuildLocale	GUILD_ERROR_INVALID_NAMEFASHION;
	
	// Kingdom
	public static final NoxGuildLocale	KINGDOM_CREATED;
	public static final NoxGuildLocale	KINGDOM_ERROR_NO_KINGDOM;
	public static final NoxGuildLocale	KINGDOM_ERROR_NO_KINGDOM_NAMED;
	public static final NoxGuildLocale	KINGDOM_ERROR_NOT_FOUND;
	public static final NoxGuildLocale	KINGDOM_ERROR_NOT_FOUND_NAMED;
	public static final NoxGuildLocale	KINGDOM_ERROR_ALREADY_CREATED;
	public static final NoxGuildLocale	KINGDOM_ERROR_INVALID_NAMEFASHION;
	
	// Player
	public static final NoxGuildLocale	PLAYER_ERROR_JOINGUILD_CURRENTLYENEMY;
	public static final NoxGuildLocale	PLAYER_ERROR_JOINGUILD_NOTINVITEDOROPEN;
	public static final NoxGuildLocale	PLAYER_ERROR_JOINGUILD_ALREADYJOINED;
	public static final NoxGuildLocale	PLAYER_ERROR_CREATEGUILD_ALREADYOWNER;
	
	// Invitational
	public static final NoxGuildLocale	INVITE_INVITED_GUILD;
	public static final NoxGuildLocale	INVITE_INVITED_KINGDOM;
	
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
		        "&6Transfer Success: &b%0%");
		
		// Guild
		GUILD_CREATED = new NoxGuildLocale("guild.created",
		        "&6You created the guild &e%0%");
		GUILD_ERROR_NO_GUILD = new NoxGuildLocale("guild.no-guild",
		        "&cYou don't belong to a guild");
		GUILD_ERROR_NO_GUILD_NAMED = new NoxGuildLocale(
		        "guild.no-guild-named",
		        "&cYou don't belong to a guild by the name of \"&b%0%&c\"");
		GUILD_ERROR_NOT_FOUND = new NoxGuildLocale(
		        "guild.error.not-found", "&cCould not find a guild");
		GUILD_ERROR_NOT_FOUND_NAMED = new NoxGuildLocale(
		        "guild.not-found-named",
		        "&cCould not find a guild by the name of &b%0%");
		GUILD_ERROR_ALREADY_CREATED = new NoxGuildLocale(
		        "guild.already-created",
		        "&cA Guild already exists with a similar name!");
		GUILD_ERROR_INVALID_NAMEFASHION = new NoxGuildLocale(
		        "guild.invalid-name",
		        "&cA guild name may only contains letters A - Z");
		
		// Kingdom
		KINGDOM_CREATED = new NoxGuildLocale("kingdom.created",
		        "&6You created the Kingdom &b%0%");
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
		PLAYER_ERROR_JOINGUILD_ALREADYJOINED = new NoxGuildLocale(
		        "player.error.join-guild.already-joined",
		        "&cYou are already apart of that guild!");
		PLAYER_ERROR_JOINGUILD_CURRENTLYENEMY = new NoxGuildLocale(
		        "player.error.join-guild.currently-enemy",
		        "&cYou are currently an enemy of the *guild* guild");
		PLAYER_ERROR_JOINGUILD_NOTINVITEDOROPEN = new NoxGuildLocale(
		        "player.error.join-guild.notopen-orinvited",
		        "&cYou are not currently invited to that guild, nor is it open");
		PLAYER_ERROR_CREATEGUILD_ALREADYOWNER = new NoxGuildLocale(
		        "player.error.create-guild.already-owner",
		        "&cYou cannot create a guild if you are already a guild owner");
		
		// Chat
		CHAT_HEADER_START = new NoxGuildLocale("chat.header-start",
		        "&6[======~*~====={&cNox&2Guilds&6}=====~*~======]");
		CHAT_HEADER_START_SPECIFIC = new NoxGuildLocale(
		        "chat.header-other",
		        "&6[=====~*~===={&cNox&2Guilds &6- &e%0% &6}====~*~=====]");
		CHAT_HEADER_END = new NoxGuildLocale("chat.header-end",
		        "&6[======&c~*~&6======&c~#*#~&6======&c~*~&6======]");
		CHAT_CHANNEL_GUILD_TAG = new NoxGuildLocale(
		        "chat.channel.tag.guild", "&6[&2Guild&6]");
		CHAT_CHANNEL_KINGDOM_TAG = new NoxGuildLocale(
		        "chat.channel.tag.guild", "&6[&dKingdom&6]");
		CHAT_CHANNEL_SERVER_TAG = new NoxGuildLocale(
		        "chat.channel.tag.guild", "&6[&cNoxGuilds&6]");
		
		// Invitational
		INVITE_INVITED_GUILD = new NoxGuildLocale(
		        "invitation.invited.guild",
		        "&6You've been invited to join the Guild of ");
		INVITE_INVITED_KINGDOM = new NoxGuildLocale(
		        "invitation.invited.kingdom",
		        "&6You've been invited to join the Kingdom of ");
		
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
	
	public NoxGuildLocale(String path, String def) {
		super(path, def);
	}
}
