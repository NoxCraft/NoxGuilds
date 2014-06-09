package com.noxpvp.noxguilds.guildplayer;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.bukkit.OfflinePlayer;

import com.noxpvp.noxguilds.gui.internal.CoreBox;
import com.noxpvp.noxguilds.guild.Guild;

public interface IGuildPlayer {
	
	public void deleteCoreBox();
	
	public CoreBox getCoreBox();
	
	public String getFormatedName();
	
	public List<Guild> getGuilds();
	
	public Object getOption(String key);
	
	public Map<String, Object> getOptions();
	
	public OfflinePlayer getPlayer();
	
	public GulidPlayerStats getStats();
	
	public UUID getUID();
	
	public boolean hasCoreBox();
	
	public boolean hasCoreBox(CoreBox box);
	
	public boolean hasGuild();
	
	public void setCoreBox(CoreBox box);
	
	public void setOption(String key, Object value);
}
