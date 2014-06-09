package com.noxpvp.noxguilds.territory;

import java.util.List;
import java.util.UUID;

import com.noxpvp.noxguilds.guild.GuildZone;

public interface ZoneKeeper {
	
	public boolean addZone(GuildZone zone);
	
	public List<GuildZone> getZones();
	
	public boolean hasZone(GuildZone zone);
	
	public boolean hasZone(UUID zoneID);
	
	public boolean removeZone(GuildZone zone);
	
}
