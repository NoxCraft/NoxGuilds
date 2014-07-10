package com.noxpvp.noxguilds.listeners;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.PacketAdapter;
import com.noxpvp.noxguilds.NoxGuilds;

public abstract class NoxPacketListener extends PacketAdapter {
	
	public NoxPacketListener(PacketType... types) {
		super(NoxGuilds.getInstance(), types);
	}
	
	public void register() {
		ProtocolLibrary.getProtocolManager().addPacketListener(this);
	}
	
	public void unRegister() {
		ProtocolLibrary.getProtocolManager().removePacketListener(this);
	}
	
}
