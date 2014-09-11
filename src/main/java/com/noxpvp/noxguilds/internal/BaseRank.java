package com.noxpvp.noxguilds.internal;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.bukkit.entity.Player;

import com.noxpvp.noxguilds.guildplayer.GuildPlayer;
import com.noxpvp.noxguilds.permisson.PermissionCell;
import com.noxpvp.noxguilds.permisson.PermissionCellKeeper;

public abstract class BaseRank<T extends PermissionCell<?, ?>> implements IRank, PermissionCellKeeper<T> {
	
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	// Static Fields
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	
	public static final String	DEFAULT_RANK_NAME	= "Unnamed Rank";
	
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	// Instance Fields
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	
	private UUID				id;
	private List<UUID>			currentMembers;
	private String				rankName;
	
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	// Constructors
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	
	// Deserialize
	@SuppressWarnings("unchecked")
	public BaseRank(Map<String, Object> data) {
		Object getter;
		if ((getter = data.get("id")) != null && getter instanceof String) {
			this.id = UUID.fromString((String) getter);
		} else {
			this.id = UUID.randomUUID();
		}
		
		if ((getter = data.get("member-ids")) != null && getter instanceof List) {
			this.currentMembers = (List<UUID>) getter;
		} else {
			this.currentMembers = new ArrayList<UUID>();
		}
		
		if ((getter = data.get("name")) != null && getter instanceof String) {
			this.rankName = checkRankName((String) getter);
		} else {
			this.rankName = DEFAULT_RANK_NAME;
		}
		
	}
	
	public BaseRank(UUID id, String name) {
		this.id = id;
		this.rankName = checkRankName(name);
		this.currentMembers = new ArrayList<UUID>();
		
	}
	
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	// Instance Methods
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	
	public boolean addMember(GuildPlayer newMember) {
		return addMember(newMember.getPlayer());
	}
	
	public boolean addMember(Player newMember) {
		return addMember(newMember.getUniqueId());
	}
	
	public boolean addMember(UUID newMember) {
		if (!currentMembers.contains(newMember))
			return currentMembers.add(newMember);
		
		return false;
	}
	
	public UUID getID() {
		return id;
	}
	
	public List<UUID> getMembers() {
		return Collections.unmodifiableList(currentMembers);
	}
	
	public String getName() {
		return rankName;
	};
	
	public boolean hasMember(GuildPlayer player) {
		return hasMember(player.getPlayer());
	}
	
	public boolean hasMember(Player player) {
		return hasMember(player.getUniqueId());
	}
	
	public boolean hasMember(UUID playerID) {
		return currentMembers.contains(playerID);
	}
	
	public boolean removeMember(GuildPlayer curMember) {
		return removeMember(curMember.getPlayer());
	}
	
	public boolean removeMember(Player curMember) {
		return removeMember(curMember.getUniqueId());
	}
	
	public boolean removeMember(UUID curMember) {
		if (currentMembers.contains(curMember))
			return currentMembers.remove(curMember);
		
		return false;
	}
	
	public Map<String, Object> serialize() {
		final Map<String, Object> data = new HashMap<String, Object>();
		
		data.put("id", id.toString());
		data.put("member-ids", currentMembers);
		data.put("name", rankName);
		
		if (getPermissions().getPerms().size() > 0) {
			data.put("permissions", getPermissions());
		}
		
		return data;
	}
	
	public boolean setName(String newName) {
		rankName = checkRankName(DEFAULT_RANK_NAME);
		return newName.equals(rankName);
	}
	
	private String checkRankName(String newName) {
		if (newName.length() > 1 && newName.length() < 17)
			return newName;
		
		return rankName != null ? rankName : DEFAULT_RANK_NAME;
	}
	
}
