package com.noxpvp.noxguilds.manager;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.bergerkiller.bukkit.common.utils.LogicUtil;
import com.noxpvp.noxguilds.NoxGuilds;
import com.noxpvp.noxguilds.internal.NoxPlugin;
import com.noxpvp.noxguilds.kingdom.Kingdom;

public class KingdomManager extends BaseManager<Kingdom> {
	
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	// Static Fields
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	
	private static KingdomManager	instance;
	
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	// Instance Fields
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	
	private NoxPlugin				plugin;
	
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	// Constructors
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	
	public KingdomManager() {
		this(NoxGuilds.getInstance());
	}
	
	public KingdomManager(NoxPlugin plugin) {
		super(Kingdom.class, "kingdoms");
		
		this.plugin = plugin;
		KingdomManager.instance = this;
	}
	
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	// Static Methods
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	
	public static KingdomManager getInstance() {
		if (instance == null) {
			setup();
		}
		
		return instance;
	}
	
	public static void setup() {
		instance = new KingdomManager();
		instance.load();
	}
	
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	// Instance Methods
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	
	@Override
	public Kingdom get(UUID kingdomUID) {
		return super.get(kingdomUID);
	}
	
	public NoxPlugin getPlugin() {
		return plugin != null ? plugin : (plugin = NoxGuilds.getInstance());
	}
	
	public void load() {
		final List<String> files = new ArrayList<String>();
		final File[] fileList = getFile().listFiles();
		
		if (LogicUtil.nullOrEmpty(fileList))
			return;
		
		for (final File f : fileList) {
			final String name = f.getName().replace(".yml", "");
			if (name.matches("(\\w{8})-?(\\w{4})-?(\\w{4})-?(\\w{4})-?(\\w{12})")) {
				files.add(name);
			}
			
		}
		
		for (final String uid : files) {
			load(UUID.fromString(uid));
		}
		
	}
}
