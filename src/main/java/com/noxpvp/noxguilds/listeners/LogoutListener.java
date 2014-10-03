package com.noxpvp.noxguilds.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerQuitEvent;

import com.noxpvp.noxguilds.NoxGuilds;
import com.noxpvp.noxguilds.manager.GuildPlayerManager;

public class LogoutListener extends NoxListener<NoxGuilds> {
	
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	// Constructors
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	
	public LogoutListener(NoxGuilds plugin) {
	
		super(plugin);
	}
	
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	// Instance Methods
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	
	@EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = true)
	public void onLogout(PlayerQuitEvent event) {
	
		final GuildPlayerManager gpm = GuildPlayerManager.getInstance();
		
		gpm.unloadAndSave(event.getPlayer());
	}
	
}
