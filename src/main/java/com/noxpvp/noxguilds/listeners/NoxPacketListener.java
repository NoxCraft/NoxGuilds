package com.noxpvp.noxguilds.listeners;

import com.bergerkiller.bukkit.common.protocol.PacketListener;
import com.bergerkiller.bukkit.common.protocol.PacketType;
import com.bergerkiller.bukkit.common.utils.PacketUtil;
import com.noxpvp.noxguilds.NoxGuilds;

public abstract class NoxPacketListener implements PacketListener {
	
	private final PacketType[]	types;
	
	public NoxPacketListener(PacketType... types) {
		this.types = types;
		
	}
	
	public void register() {
		PacketUtil.addPacketListener(NoxGuilds.getInstance(), this, types);
	}
	
	public void unRegister() {
		PacketUtil.removePacketListener(this);
	}
	
}
