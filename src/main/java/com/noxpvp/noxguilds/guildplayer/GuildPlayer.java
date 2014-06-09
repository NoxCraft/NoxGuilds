package com.noxpvp.noxguilds.guildplayer;

import java.util.Map;

import org.bukkit.entity.Player;

public class GuildPlayer extends BaseGuildPlayer {
	
	public GuildPlayer(Map<String, Object> data) {
		super(data);
	}
	
	public GuildPlayer(Player player) {
		super(player);
	}
	
	public void load() {
		
	}
	
	public void save() {
		
	}
	
	@Override
	public Map<String, Object> serialize() {
		return super.serialize();
	}
	
}
