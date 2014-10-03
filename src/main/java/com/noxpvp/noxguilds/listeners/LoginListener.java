package com.noxpvp.noxguilds.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerLoginEvent;

import com.noxpvp.noxguilds.NoxGuilds;
import com.noxpvp.noxguilds.manager.GuildPlayerManager;

public class LoginListener extends NoxListener<NoxGuilds> {
	
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	// Static fields
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	// Instance Fields
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	// Constructors
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	
	public LoginListener(NoxGuilds plugin) {
	
		super(plugin);
		
	}
	
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	// Instance Methods
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	
	@EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = true)
	public void onLogin(PlayerLoginEvent event) {
	
		GuildPlayerManager.getInstance().load(event.getPlayer());
	}
}
