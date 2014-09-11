package com.noxpvp.noxguilds.land;

import java.util.Map;
import java.util.UUID;

import com.noxpvp.noxguilds.guild.Guild;
import com.noxpvp.noxguilds.guildplayer.GuildPlayer;
import com.noxpvp.noxguilds.manager.GuildManager;
import com.noxpvp.noxguilds.manager.GuildPlayerManager;
import com.noxpvp.noxguilds.manager.PlotManager;
import com.noxpvp.noxguilds.permisson.PermissionCellKeeper;
import com.noxpvp.noxguilds.permisson.PlayerPermissionCell;

public class GuildPlot extends TerritoryBlock implements PermissionCellKeeper<PlayerPermissionCell> {
	
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	// Static fields
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	
	// Serializers start
	private static final String			NODE_GUILD_OWNER	= "guild-owner-id";
	private static final String			NODE_PLAYER_OWNER	= "player-owner-id";
	private static final String			NODE_PERMISSIONS	= "player-perms";
	// Serializers end
	
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	// Instance Fields
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	
	private UUID						guildOwner;
	private UUID						playerOwner;
	private final PlayerPermissionCell	playerPerms;
	
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	// Constructors
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	
	public GuildPlot(Guild owner, TerritoryCoord location) {
		super(location);
		
		guildOwner = owner.getPersistentID();
		playerPerms = new PlayerPermissionCell();
		
		PlotManager.getInstance().loadObject(this);
	}
	
	public GuildPlot(Map<String, Object> data) {
		super(data);
		
		Object getter;
		
		if ((getter = data.get(NODE_GUILD_OWNER)) != null && getter instanceof String) {
			guildOwner = UUID.fromString((String) getter);
		} else {
			guildOwner = null;
		}
		
		if ((getter = data.get(NODE_PLAYER_OWNER)) != null && getter instanceof String) {
			playerOwner = UUID.fromString((String) getter);
		} else {
			playerOwner = null;
		}
		
		if ((getter = data.get(NODE_PERMISSIONS)) != null && getter instanceof PlayerPermissionCell) {
			playerPerms = (PlayerPermissionCell) getter;
		} else {
			playerPerms = new PlayerPermissionCell();
		}
		
	}
	
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	// Static Methods
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	// Instance Methods
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	
	public Guild getGuildOwner() {
		if (guildOwner != null && guildOwner instanceof UUID)
			return GuildManager.getInstance().get(guildOwner);
		else
			return null;
	}
	
	public UUID getGuildOwnerID() {
		return guildOwner;
	}
	
	public PlayerPermissionCell getPermissions() {
		return playerPerms;
	}
	
	public GuildPlayer getPlayerOwner() {
		if (playerOwner != null && playerOwner instanceof UUID)
			return GuildPlayerManager.getInstance().getPlayer(playerOwner);
		else
			return null;
	}
	
	public UUID getPlayerOwnerID() {
		return playerOwner;
	}
	
	@Override
	public Map<String, Object> serialize() {
		final Map<String, Object> data = super.serialize();
		
		if (guildOwner != null) {
			data.put(NODE_GUILD_OWNER, guildOwner.toString());
		}
		
		if (playerOwner != null) {
			data.put(NODE_PLAYER_OWNER, playerOwner.toString());
		}
		
		if (playerPerms.getPerms().size() > 0) {
			data.put(NODE_PERMISSIONS, playerPerms);
		}
		
		return data;
	}
}
