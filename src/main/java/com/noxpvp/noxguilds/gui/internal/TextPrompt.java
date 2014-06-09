package com.noxpvp.noxguilds.gui.internal;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import com.bergerkiller.bukkit.common.events.PacketReceiveEvent;
import com.bergerkiller.bukkit.common.events.PacketSendEvent;
import com.bergerkiller.bukkit.common.protocol.CommonPacket;
import com.bergerkiller.bukkit.common.protocol.PacketType;
import com.bergerkiller.bukkit.common.utils.LogicUtil;
import com.bergerkiller.bukkit.common.utils.PacketUtil;
import com.noxpvp.noxguilds.listeners.NoxPacketListener;

public abstract class TextPrompt extends NoxPacketListener {
	
	private final CommonPacket	packet;
	private final Player	   p;
	
	public TextPrompt(Player p) {
		super(PacketType.IN_UPDATE_SIGN);
		
		this.p = p;
		final Location pLoc = p.getLocation();
		packet = new CommonPacket(PacketType.OUT_UPDATE_SIGN);
		packet.write(PacketType.OUT_UPDATE_SIGN.x, (int) pLoc.getX());
		packet.write(PacketType.OUT_UPDATE_SIGN.y, (int) pLoc.getY());
		packet.write(PacketType.OUT_UPDATE_SIGN.z, (int) pLoc.getZ());
	}
	
	public void onPacketReceive(PacketReceiveEvent event) {
		unRegister();
		final String[] ret = event.getPacket().read(
		        PacketType.IN_UPDATE_SIGN.lines);
		
		if (LogicUtil.nullOrEmpty(ret)) {
			onReturn(null);
		}
		
		onReturn(ret);
	}
	
	public void onPacketSend(PacketSendEvent event) {
		return;
	}
	
	public abstract void onReturn(String[] lines);
	
	public void show() {
		register();
		PacketUtil.sendPacket(p, packet);
	}
	
}
