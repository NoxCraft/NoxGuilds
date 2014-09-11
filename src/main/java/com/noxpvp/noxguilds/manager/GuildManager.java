package com.noxpvp.noxguilds.manager;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.bergerkiller.bukkit.common.utils.LogicUtil;
import com.noxpvp.noxguilds.NoxGuilds;
import com.noxpvp.noxguilds.guild.Guild;
import com.noxpvp.noxguilds.internal.NoxPlugin;

public class GuildManager extends BaseManager<Guild> {
	
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	// Static Fields
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	
	private static final String	savePath	= "guilds";
	private static GuildManager	instance;
	
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	// Instance Fields
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	
	private NoxPlugin			plugin;
	
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	// Constructors
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	
	public GuildManager() {
		this(NoxGuilds.getInstance());
	}
	
	public GuildManager(NoxPlugin plugin) {
		super(Guild.class, savePath);
		
		GuildManager.instance = this;
	}
	
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	// Static Methods
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	
	public static GuildManager getInstance() {
		if (instance != null)
			return instance;
		else {
			setup();
			return instance;
		}
	}
	
	public static void setup() {
		instance = new GuildManager();
		instance.load();
	}
	
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	// Instance Methods
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	
	@Override
	public Guild get(UUID guildID) {
		return super.get(guildID);
	}
	
	public Guild getByName(String guildName) {
		for (final Guild g : getLoadedMap().values())
			if (g.getName().equalsIgnoreCase(guildName))
				return g;
		
		return null;
	}
	
	public NoxPlugin getPlugin() {
		return plugin != null ? plugin : (plugin = NoxGuilds.getInstance());
	}
	
	public boolean hasGuild(String name) {
		for (final Guild g : getLoadedMap().values())
			if (g.getName().equalsIgnoreCase(name))
				return true;
		
		return false;
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
