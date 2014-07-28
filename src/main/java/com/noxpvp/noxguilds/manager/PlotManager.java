package com.noxpvp.noxguilds.manager;

import com.noxpvp.noxguilds.NoxGuilds;
import com.noxpvp.noxguilds.internal.NoxPlugin;
import com.noxpvp.noxguilds.land.GuildPlot;
import com.noxpvp.noxguilds.land.TerritoryID;

public class PlotManager extends BaseManager<GuildPlot> {
	
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	// Static fields
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	
	public static final String	savePath	= "plots";
	public static PlotManager	instance;
	
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	// Instance Fields
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	
	public NoxGuilds			plugin;
	
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	// Constructors
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	
	public PlotManager() {
		this(NoxGuilds.getInstance());
	}
	
	public PlotManager(NoxGuilds plugin) {
		super(GuildPlot.class, savePath);
		
		this.plugin = plugin;
		PlotManager.instance = this;
	}
	
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	// Static Methods
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	
	public static PlotManager getInstance() {
		if (instance == null) {
			setup();
		}
		
		return instance;
	}
	
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	// Instance Methods
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	
	public static void setup() {
		instance = new PlotManager();
		instance.load();
	}
	
	public GuildPlot getPlot(String id) {
		return get(id);
	}
	
	public GuildPlot getPlot(TerritoryID id) {
		return getPlot(id.getID());
	}
	
	public NoxPlugin getPlugin() {
		return plugin != null ? plugin : (plugin = NoxGuilds.getInstance());
	}
	
	public void load() {
		return;
	}
	
}
