package com.noxpvp.noxguilds.kingdom;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import com.bergerkiller.bukkit.common.ModuleLogger;
import com.noxpvp.noxguilds.NoxGuilds;
import com.noxpvp.noxguilds.Settings;
import com.noxpvp.noxguilds.chat.KingdomChannel;
import com.noxpvp.noxguilds.guild.Guild;
import com.noxpvp.noxguilds.permisson.KingdomPermissionCell;
import com.noxpvp.noxguilds.util.ItemBuilder;
import com.noxpvp.noxguilds.util.MessageUtil;

public class Kingdom extends BaseKingdom {
	
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	// Static fields
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	
	private static ModuleLogger		kingdomLogger			= new ModuleLogger(NoxGuilds.getInstance(), "Kingdoms");
	public static final ItemStack	genericKingdomItem		= new ItemStack(Material.BEACON);
	
	// Serializers start
	public static final String		NODE_KINGDOM_ITEM		= "kingdom-item";
	public static final String		NODE_PERMS				= "permissions";
	public static final String		NODE_OPEN				= "open";
	public static final String		NODE_TAXES				= "taxes";
	public static final String		NODE_TAXESPERCENTBASED	= "taxes-percent";
	public static final String		NODE_FRIENDLYFIRE		= "friendly-fire";
	
	// Serializers end
	
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	// Instance Fields
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	
	private KingdomPermissionCell	perms;
	private ItemStack				kingdomBadge;
	private boolean					open;
	private double					taxes;
	private boolean					isTaxPercent;
	private boolean					friendlyFire;
	private KingdomChannel			channel;
	
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	// Constructors
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	
	public Kingdom(Guild capital, String name) {
		super(capital.getID(), name);
		
		kingdomBadge = genericKingdomItem.clone();
		open = Settings.defaultKingdomOpen;
		taxes = Settings.defaultTaxesKingdom;
		isTaxPercent = Settings.defaultTaxesKingdomPercent;
		friendlyFire = Settings.defaultKingdomFriendlyFire;
		perms = new KingdomPermissionCell();
		
		instantiateNonPersistant();
	}
	
	// Deserialize
	public Kingdom(Map<String, Object> data) {
		super(data);
		
		Object getter;
		
		if ((getter = data.get(NODE_KINGDOM_ITEM)) != null && getter instanceof ItemStack) {
			kingdomBadge = (ItemStack) getter;
		} else {
			kingdomBadge = genericKingdomItem.clone();
		}
		
		if ((getter = data.get(NODE_PERMS)) != null && getter instanceof KingdomPermissionCell) {
			perms = (KingdomPermissionCell) getter;
		} else {
			perms = new KingdomPermissionCell();
		}
		
		if ((getter = data.get(NODE_OPEN)) != null && getter instanceof Boolean) {
			open = (Boolean) getter;
		} else {
			open = Settings.defaultKingdomOpen;
		}
		
		if ((getter = data.get(NODE_FRIENDLYFIRE)) != null && getter instanceof Boolean) {
			friendlyFire = (Boolean) getter;
		} else {
			friendlyFire = Settings.defaultKingdomFriendlyFire;
		}
		
		if ((getter = data.get(NODE_TAXES)) != null && getter instanceof Number) {
			taxes = (Double) getter;
		} else {
			taxes = Settings.defaultTaxesKingdom;
		}
		
		if ((getter = data.get(NODE_TAXESPERCENTBASED)) != null && getter instanceof Boolean) {
			isTaxPercent = (Boolean) getter;
		} else {
			isTaxPercent = Settings.defaultTaxesKingdomPercent;
		}
		
		instantiateNonPersistant();
	}
	
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	// Static Methods
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	// Instance Methods
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	
	public KingdomChannel getChatChannel() {
		return channel;
	}
	
	public ItemStack getIdentifiableItem() {
		final Set<String> lore = new HashSet<String>();
		lore.add(ChatColor.GOLD + "Owner: " + ChatColor.AQUA + getKing().getPlayer().getName());
		lore.add(ChatColor.GOLD + "Tag: ");
		lore.addAll(MessageUtil.convertStringForLore(ChatColor.AQUA + getTag()));
		
		kingdomBadge = new ItemBuilder(kingdomBadge).setName(ChatColor.GREEN + getName()).setLore(lore).build();
		
		return kingdomBadge.clone();
	}
	
	public KingdomPermissionCell getPermissions() {
		return perms;
	}
	
	public double getTaxes() {
		return taxes;
	}
	
	public boolean isFriendlyFire() {
		return friendlyFire;
	}
	
	public boolean isOpen() {
		return open;
	}
	
	public boolean isTaxesPercent() {
		return isTaxPercent;
	}
	
	@Override
	public Map<String, Object> serialize() {
		final Map<String, Object> data = super.serialize();
		
		data.put(NODE_OPEN, open);
		data.put(NODE_TAXES, taxes);
		data.put(NODE_TAXESPERCENTBASED, isTaxPercent);
		data.put(NODE_FRIENDLYFIRE, friendlyFire);
		data.put(NODE_KINGDOM_ITEM, kingdomBadge);
		
		if (perms.getPerms().size() > 0) {
			data.put(NODE_PERMS, perms);
		}
		
		return data;
	}
	
	public void setItemBadge(ItemStack badge) {
		// Don't leak
		final ItemStack copy = new ItemStack(badge);
		
		// copy meta
		copy.setItemMeta(kingdomBadge.getItemMeta());
		
		// set as new
		kingdomBadge = copy;
	}
	
	private void instantiateNonPersistant() {
		channel = new KingdomChannel(this);
	}
	
}
