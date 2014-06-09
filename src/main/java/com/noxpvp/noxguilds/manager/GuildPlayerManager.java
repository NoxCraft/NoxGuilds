package com.noxpvp.noxguilds.manager;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import com.noxpvp.noxguilds.NoxGuilds;
import com.noxpvp.noxguilds.guildplayer.GuildPlayer;
import com.noxpvp.noxguilds.internal.NoxPlugin;

public class GuildPlayerManager extends BaseManager<GuildPlayer> {
	
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	// Static Fields
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	
	private static GuildPlayerManager	instance;
	
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	// Instance Fields
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	
	private NoxPlugin	              plugin;
	
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	// Constructors
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	
	public GuildPlayerManager() {
		this(NoxGuilds.getInstance());
	}
	
	public GuildPlayerManager(NoxPlugin plugin) {
		super(GuildPlayer.class, "guildplayers");
		
		GuildPlayerManager.instance = this;
		this.plugin = plugin;
	}
	
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	// Static Methods
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	
	public static GuildPlayerManager getInstance() {
		if (instance != null)
			return instance;
		else {
			setup();
			return instance;
		}
	}
	
	public static void setup() {
		instance = new GuildPlayerManager();
		instance.load();
	}
	
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	// Instance Methods
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	
	public GuildPlayer getFromPlayer(Player player) {
		GuildPlayer p = get(player.getUniqueId());
		if (p == null) {
			p = new GuildPlayer(player);
		}
		
		return p;
	}
	
	public GuildPlayer getPlayer(UUID arg) {
		return get(arg);
	}
	
	public NoxPlugin getPlugin() {
		return plugin != null ? plugin
		        : (plugin = NoxGuilds.getInstance());
	}
	
	public void load() {
		for (final Player p : Bukkit.getOnlinePlayers()) {
			GuildPlayer gp;
			if ((gp = get(p.getUniqueId())) != null && !isLoaded(gp)) {
				load(gp);
			}
		}
	}
	
	public void load(Player player) {
		GuildPlayer getter;
		if ((getter = get(player.getUniqueId())) == null) {
			loadObject(new GuildPlayer(player));
		} else {
			load(getter);
		}
	}
	
	public void unloadAndSave(Player player) {
		unloadAndSave(getIfLoaded(player.getUniqueId()));
	}
	
}
