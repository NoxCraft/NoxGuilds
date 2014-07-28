package com.noxpvp.noxguilds.land;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.inventory.ItemStack;

import com.noxpvp.noxguilds.manager.PlotManager;
import com.noxpvp.noxguilds.manager.ZoneManager;
import com.noxpvp.noxguilds.util.GuildUtil;
import com.noxpvp.noxguilds.util.ItemBuilder;

public abstract class LandOwner implements ConfigurationSerializable {
	
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	// Static fields
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	
	private static final String	SERIALIZE_PLOTS	= "plots";
	private static final String	SERIALIZE_ZONES	= "zones";
	
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	// Instance Fields
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	
	private Set<String>			plots;
	private Set<UUID>			zones;
	
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	// Constructors
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	
	public LandOwner() {
		plots = new HashSet<String>();
		zones = new HashSet<UUID>();
		
	}
	
	public LandOwner(Map<String, Object> data) {
		Object getter;
		if ((getter = data.get(SERIALIZE_PLOTS)) != null
				&& getter instanceof Collection) {
			plots = new HashSet<String>((Collection<String>) getter);
		} else {
			plots = new HashSet<String>();
		}
		
		if ((getter = data.get(SERIALIZE_ZONES)) != null
				&& getter instanceof Collection) {
			zones = new HashSet<UUID>(GuildUtil
					.UUIDSFromStrings((Collection<String>) getter));
		} else {
			zones = new HashSet<UUID>();
		}
		
	}
	
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	// Static Methods
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	// Instance Methods
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	
	public boolean addPlot(GuildPlot plot) {
		if (hasPlot(plot))
			return false;
		
		return plots.add(plot.getPersistentStringID());
	}
	
	public boolean addZone(Zone zone) {
		if (hasZone(zone))
			return false;
		
		return zones.add(zone.getPersistentID());
	}
	
	public boolean canClaimTerritory() {
		return plots.size() < getMaxPlots();
	}
	
	public ItemStack getLandItem() {
		return new ItemBuilder(Material.GRASS)
				.setName(ChatColor.GREEN + "Territory")
				.build();
	}
	
	public abstract int getMaxPlots();
	
	public Set<GuildPlot> getPlots() {
		final Set<GuildPlot> ret = new HashSet<GuildPlot>();
		
		final PlotManager pm = PlotManager.getInstance();
		for (final String plot : plots) {
			ret.add(pm.getPlot(plot));
		}
		
		return ret;
	}
	
	public Set<Zone> getZones() {
		final Set<Zone> ret = new HashSet<Zone>();
		
		final ZoneManager zm = ZoneManager.getInstance();
		for (final UUID zone : zones) {
			ret.add(zm.getZone(zone));
		}
		
		return ret;
	}
	
	public boolean hasPlot(GuildPlot plot) {
		return plots.contains(plot.getPersistentStringID());
	}
	
	public boolean hasTerritory(TerritoryBlock block) {
		return plots.contains(block.getPersistentStringID());
	}
	
	public boolean hasZone(Zone zone) {
		return zones.contains(zone.getPersistentID());
	}
	
	public boolean removePlot(GuildPlot plot) {
		return plots.remove(plot.getPersistentStringID());
	}
	
	public boolean removeTerritory(TerritoryBlock block) {
		return plots.remove(block.getPersistentStringID());
	}
	
	public boolean removeZone(Zone zone) {
		return zones.remove(zone.getPersistentID());
	}
	
	public Map<String, Object> serialize() {
		final Map<String, Object> data = new HashMap<String, Object>();
		
		if (plots.size() > 0) {
			data.put(SERIALIZE_PLOTS, plots);
		}
		
		if (zones.size() > 0) {
			data.put(SERIALIZE_ZONES, GuildUtil.getUUIDStrings(zones));
		}
		
		return data;
	}
}
