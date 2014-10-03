package com.noxpvp.noxguilds.gui.internal;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import com.bergerkiller.bukkit.common.utils.LogicUtil;
import com.comphenix.packetwrapper.WrapperPlayServerOpenSignEntity;
import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.PacketEvent;
import com.noxpvp.noxguilds.listeners.NoxPacketListener;

public abstract class TextPrompt extends NoxPacketListener {
	
	private final WrapperPlayServerOpenSignEntity packet;
	private final Player p;
	
	public TextPrompt(Player p) {
	
		super(PacketType.Play.Client.UPDATE_SIGN);
		
		this.p = p;
		final Location pLoc = p.getLocation();
		packet = new WrapperPlayServerOpenSignEntity();
		packet.setLocation(pLoc);
	}
	
	@Override
	public void onPacketReceiving(PacketEvent event) {
	
		unRegister();
		final String[] ret = event.getPacket().getStringArrays().read(0);
		
		if (LogicUtil.nullOrEmpty(ret)) {
			onReturn(null);
		}
		
		onReturn(ret);
	}
	
	@Override
	public void onPacketSending(PacketEvent event) {
	
		super.onPacketSending(event);
	}
	
	public abstract void onReturn(String[] lines);
	
	public void show() {
	
		register();
		packet.sendPacket(p);
	}
	
}
