package com.noxpvp.noxguilds.manager;

import java.util.UUID;

import com.noxpvp.noxguilds.NoxGuilds;
import com.noxpvp.noxguilds.internal.NoxPlugin;
import com.noxpvp.noxguilds.land.Zone;

public class ZoneManager extends BaseManager<Zone> {
	
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	// Static fields
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	
	private static final String	savePath	= "zones";
	private static ZoneManager	instance;
	
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	// Instance Fields
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	
	private final NoxGuilds		plugin;
	
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	// Constructors
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	
	public ZoneManager() {
		this(NoxGuilds.getInstance());
	}
	
	public ZoneManager(NoxGuilds plugin) {
		super(Zone.class, savePath);
		
		this.plugin = plugin;
		ZoneManager.instance = this;
	}
	
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	// Static Methods
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	
	public static ZoneManager getInstance() {
		if (instance == null) {
			instance = new ZoneManager();
		}
		
		return instance;
	}
	
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	// Instance Methods
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	
	public NoxPlugin getPlugin() {
		return plugin;
	}
	
	public Zone getZone(UUID zone) {
		return get(zone);
	}
	
	public void load() {
		return;
	}
}
