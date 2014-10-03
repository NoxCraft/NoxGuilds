package com.noxpvp.noxguilds.guild;

import java.util.Map;
import java.util.UUID;

import com.noxpvp.noxguilds.internal.BaseRank;
import com.noxpvp.noxguilds.manager.GuildManager;
import com.noxpvp.noxguilds.permisson.GuildPermissionCell;

public class GuildRank extends BaseRank<GuildPermissionCell> {
	
	private Guild guild;
	private GuildPermissionCell perms;
	
	public GuildRank(Guild owner, String name) {
	
		super(UUID.randomUUID(), name);
		
		guild = owner;
		perms = new GuildPermissionCell();
	}
	
	public GuildRank(Map<String, Object> data) {
	
		super(data);
		
		Object getter;
		Guild temp = null;
		if ((getter = data.get("guild-id")) != null && getter instanceof String) {
			temp = GuildManager.getInstance().get(UUID.fromString((String) getter));
		}
		
		if (temp != null) {
			setGuild(temp);
		}
		
		if ((getter = data.get("permissions")) != null && getter instanceof GuildPermissionCell) {
			perms = (GuildPermissionCell) getter;
		} else {
			perms = new GuildPermissionCell();
		}
	}
	
	// Permission keeper
	public GuildPermissionCell getPermissions() {
	
		return perms;
	}
	
	@Override
	public Map<String, Object> serialize() {
	
		final Map<String, Object> data = super.serialize();
		
		data.put("guild-id", guild.getID().toString());
		
		return data;
	}
	
	private void setGuild(Guild g) {
	
		if (guild != null) {
			guild.removeRank(this);
		}
		
		guild = g;
		guild.addRank(this);
	}
	
}
