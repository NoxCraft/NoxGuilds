package com.noxpvp.noxguilds.listeners;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerMoveEvent;

import com.noxpvp.noxguilds.NoxGuilds;
import com.noxpvp.noxguilds.event.PlayerChunkMoveEvent;
import com.noxpvp.noxguilds.guild.Guild;
import com.noxpvp.noxguilds.guildplayer.GuildPlayer;
import com.noxpvp.noxguilds.land.GuildPlot;
import com.noxpvp.noxguilds.land.TerritoryID;
import com.noxpvp.noxguilds.locale.NoxGuildLocale;
import com.noxpvp.noxguilds.manager.GuildPlayerManager;
import com.noxpvp.noxguilds.manager.PlotManager;

public class PlayerMoveListener extends NoxListener<NoxGuilds> {
	
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	// Static fields
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	
	private static final String	lastPlotKey	= "last-plot";
	
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	// Instance Fields
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	// Constructors
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	
	public PlayerMoveListener(NoxGuilds plugin) {
		super(plugin);
		
	}
	
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	// Static Methods
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	// Instance Methods
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	
	@EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
	public void onPlayerChunkMove(PlayerChunkMoveEvent event) {
		
		final GuildPlayer p = GuildPlayerManager.getInstance().getFromPlayer(event.getPlayer());
		final GuildPlot lastPlot = (GuildPlot) p.getOption(lastPlotKey);
		final GuildPlot newPlot = PlotManager.getInstance().getPlot(new TerritoryID(event.getToChunk()).getID());
		
		if (lastPlot == newPlot)
			return;
		
		if (newPlot == null) {
			NoxGuildLocale.PLAYER_MOVED_UNOWNED.send(event.getPlayer());
			
			p.setOption(lastPlotKey, newPlot);
			return;
		}
		
		if (lastPlot != null && lastPlot.getChunk().equals(newPlot.getChunk())) {
			p.setOption(lastPlotKey, newPlot);
			return;
		}
		
		if (lastPlot == null || lastPlot.getGuildOwnerID() != newPlot.getGuildOwnerID()) {
			Guild g = null;
			if ((g = newPlot.getGuildOwner()) != null) {
				NoxGuildLocale.PLAYER_MOVED_GUILD.send(event.getPlayer(), g, newPlot);
				
			} else {
				NoxGuildLocale.PLAYER_MOVED_UNOWNED.send(event.getPlayer());
			}
			
		} else if (lastPlot.getPlayerOwnerID() != newPlot.getPlayerOwnerID()) {
			NoxGuildLocale.PLAYER_MOVED_PLOT.send(event.getPlayer(), newPlot);
		}
		
		p.setOption(lastPlotKey, newPlot);
	}
	
	@EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
	public void onPlayerMove(PlayerMoveEvent event) {
		final Location from = event.getFrom();
		final Location to = event.getTo();
		
		if ((int) from.getX() == (int) to.getX() && (int) from.getZ() == (int) to.getZ()
			&& (int) from.getY() == (int) to.getZ())
			return;
		
		if (from.getChunk() != to.getChunk()) {
			Bukkit.getPluginManager().callEvent(new PlayerChunkMoveEvent(event.getPlayer(), from, to));
		}
		
	}
}
