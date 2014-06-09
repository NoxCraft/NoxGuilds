package com.noxpvp.noxguilds.guild;

import java.util.Map;
import java.util.UUID;

import com.noxpvp.noxguilds.manager.GuildManager;
import com.noxpvp.noxguilds.territory.Area;
import com.noxpvp.noxguilds.territory.Zone;

public class GuildZone extends Zone {
	
	private Guild	guild;
	
	public GuildZone(Map<String, Object> data) {
		super(data);
		
		Object getter;
		if ((getter = data.get("guild")) != null
		        && getter instanceof String) {
			guild = GuildManager.getInstance().get(
			        UUID.fromString((String) getter));
		}
	}
	
	public GuildZone(String name, Area region, Guild guild) {
		super(name, region);
		
		this.guild = guild;
	}
	
	public Guild getGuild() {
		return guild;
	}
	
	@Override
	public Map<String, Object> serialize() {
		final Map<String, Object> data = super.serialize();
		
		data.put("guild-id", guild.getID());
		
		return data;
	}
	
}
