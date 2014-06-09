package com.noxpvp.noxguilds.territory;

import java.util.Map;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.World;
import org.bukkit.inventory.ItemStack;

import com.noxpvp.noxguilds.access.PlayerAccessLevel;
import com.noxpvp.noxguilds.access.PlayerPermissionType;
import com.noxpvp.noxguilds.gui.internal.ItemRepresentable;
import com.noxpvp.noxguilds.manager.GuildPlayerManager;
import com.noxpvp.noxguilds.permisson.PermissionCell;

public class TerritoryBlock implements ITerritoryBlock, ItemRepresentable {
	
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	// Static Fields
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	
	// Serializers start
	private static final String	NODE_LOCATION	= "location";
	private static final String	NODE_OWNER_ID	= "owner-id";
	private static final String	NODE_PRICE	  = "price";
	// Serializers end
	
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	// Instance Fields
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	
	private TerritoryCoord	    coord;
	private UUID	            owner;
	private double	            salePrice;
	
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	// Constructors
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	
	// Deserialize
	public TerritoryBlock(Map<String, Object> data) {
		Object getter;
		if ((getter = data.get(NODE_LOCATION)) != null
		        && getter instanceof Location) {
			coord = new TerritoryCoord((Location) getter);
		}
		
		if ((getter = data.get(NODE_OWNER_ID)) != null
		        && getter instanceof String) {
			owner = UUID.fromString((String) getter);
		}
		
		if ((getter = data.get(NODE_PRICE)) != null
		        && getter instanceof Number) {
			salePrice = (Double) getter;
		}
		
	}
	
	public TerritoryBlock(TerritoryCoord coord) {
		this.coord = coord;
		salePrice = -1.0;
	}
	
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	// Instance Methods
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	
	// Main Data
	public TerritoryCoord getCoord() {
		return coord;
	}
	
	public ItemStack getIdentifiableItem() {
		return null;
	}
	
	public Location getLocation() {
		return coord.getLocation();
	}
	
	public UUID getOwner() {
		return owner;
	}
	
	public PermissionCell<PlayerAccessLevel, PlayerPermissionType> getPermissions() {
		return GuildPlayerManager.getInstance().getPlayer(owner)
		        .getPermissions();
	}
	
	public OfflinePlayer getPlayerOwner() {
		return Bukkit.getOfflinePlayer(owner);
	}
	
	public double getPrice() {
		return salePrice > 0 ? salePrice : 0;
	}
	
	public World getWorld() {
		return coord.getWorld();
	}
	
	public boolean hasOwner() {
		return owner != null;
	}
	
	public boolean isForSale() {
		return salePrice > 0 && salePrice != -1.0;
	}
	
	public boolean isOwner(UUID id) {
		return id.equals(owner);
	}
	
	public void setOwner(UUID newOwnerID) {
		owner = newOwnerID;
	}
	
	public void setPrice(double price) {
		salePrice = Math.max(price, 0);
	}
	
}
