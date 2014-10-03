package com.noxpvp.noxguilds.event;

import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerMoveEvent;

public class PlayerChunkMoveEvent extends PlayerMoveEvent implements Cancellable {
	
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	// Static fields
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	
	public static final HandlerList handlers = new HandlerList();
	
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	// Instance Fields
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	
	private final Chunk from;
	private final Chunk to;
	private boolean cancelled;
	
	public PlayerChunkMoveEvent(Player player, Location from, Location to) {
	
		super(player, from, to);
		
		this.from = from.getChunk();
		this.to = to.getChunk();
	}
	
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	// Constructors
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	
	public static HandlerList getHandlerList() {
	
		return handlers;
	}
	
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	// Static Methods
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	
	public Chunk getFromChunk() {
	
		return from;
	}
	
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	// Instance Methods
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	
	@Override
	public HandlerList getHandlers() {
	
		return handlers;
	};
	
	public Chunk getToChunk() {
	
		return to;
	}
	
	@Override
	public boolean isCancelled() {
	
		return cancelled;
	}
	
	@Override
	public void setCancelled(boolean arg0) {
	
		cancelled = arg0;
	}
	
}
