package com.noxpvp.noxguilds.guild;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import com.bergerkiller.bukkit.common.ModuleLogger;
import com.noxpvp.noxguilds.NoxGuilds;
import com.noxpvp.noxguilds.Settings;
import com.noxpvp.noxguilds.chat.GuildChannel;
import com.noxpvp.noxguilds.guildplayer.GuildPlayer;
import com.noxpvp.noxguilds.kingdom.Kingdom;
import com.noxpvp.noxguilds.manager.GuildManager;
import com.noxpvp.noxguilds.manager.KingdomManager;
import com.noxpvp.noxguilds.permisson.GuildPermissionCell;
import com.noxpvp.noxguilds.util.ItemBuilder;

public class Guild extends BaseGuild {
	
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	// Static fields
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	
	private static final ItemStack	genericGuildItem		= new ItemStack(Material.IRON_SWORD);
	private static ModuleLogger		guildLogger				= new ModuleLogger(NoxGuilds.getInstance(), "Guilds");
	
	// Serializers start
	private static final String		NODE_PERMS				= "permissions";
	private static final String		NODE_ITEM_IDENTIFIER	= "guild-item";
	private static final String		NODE_OPEN				= "isopen";
	private static final String		NODE_TAXES				= "taxes";
	private static final String		NODE_TAXESISPERCENT		= "is-tax-percent";
	private static final String		NODE_FRIENDLYFIRE		= "friendly-fire";
	// Serializers end
	
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	// Instance Fields
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	
	private GuildPermissionCell		perms;
	private ItemStack				guildItem;
	private boolean					open;
	private double					taxes;
	private boolean					isTaxPercent;
	private boolean					friendlyFire;
	private GuildChannel			channel;
	
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	// Constructors
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	
	// Deserialize
	public Guild(Map<String, Object> data) {
		super(data);
		
		Object getter;
		
		if ((getter = data.get(NODE_PERMS)) != null && getter instanceof GuildPermissionCell) {
			perms = (GuildPermissionCell) getter;
		} else {
			perms = new GuildPermissionCell();
		}
		
		if ((getter = data.get(NODE_ITEM_IDENTIFIER)) != null && getter instanceof ItemStack) {
			guildItem = (ItemStack) getter;
		} else {
			guildItem = genericGuildItem.clone();
		}
		
		if ((getter = data.get(NODE_OPEN)) != null && getter instanceof Boolean) {
			open = (Boolean) getter;
		} else {
			open = Settings.defaultGuildOpen;
		}
		
		if ((getter = data.get(NODE_TAXES)) != null && getter instanceof Number) {
			taxes = (Double) getter;
		} else {
			taxes = Settings.defaultGuildTaxes;
		}
		
		if ((getter = data.get(NODE_TAXESISPERCENT)) != null && getter instanceof Boolean) {
			isTaxPercent = (Boolean) getter;
		} else {
			isTaxPercent = Settings.defaultGuildTaxesPercent;
		}
		
		if ((getter = data.get(NODE_FRIENDLYFIRE)) != null && getter instanceof Boolean) {
			friendlyFire = (Boolean) getter;
		} else {
			friendlyFire = Settings.defaultGuildFriendlyFire;
		}
		
	}
	
	public Guild(String name, GuildPlayer owner) {
		super(name, owner);
		
		perms = new GuildPermissionCell();
		guildItem = genericGuildItem.clone();
		open = Settings.defaultGuildOpen;
		taxes = Settings.defaultGuildTaxes;
		isTaxPercent = Settings.defaultGuildTaxesPercent;
		friendlyFire = Settings.defaultGuildFriendlyFire;
		
		instantiateNonSaves();
		GuildManager.getInstance().loadObject(this);
	}
	
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	// Static Methods
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	// Instance Methods
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	
	public static void log(Level lv, String msg) {
		guildLogger.log(lv, msg);
	}
	
	public GuildChannel getChatChannel() {
		return channel;
	}
	
	public ItemStack getIdentifiableItem() {
		guildItem = new ItemBuilder(guildItem)
			.setName(ChatColor.GREEN + getName())
			.setLore(ChatColor.GOLD + "Owner: " + ChatColor.AQUA + getOwner().getOffline().getName(),
				ChatColor.GOLD + "Tag: " + ChatColor.AQUA + getTag()).build();
		
		return guildItem.clone();
	}
	
	public Set<Kingdom> getKingdoms() {
		final Set<Kingdom> ret = new HashSet<Kingdom>();
		final KingdomManager km = KingdomManager.getInstance();
		for (final Kingdom k : km.getLoadedMap().values())
			if (k.getGuildIDs().contains(getID())) {
				ret.add(k);
			}
		
		return ret;
	}
	
	public ItemStack getKingdomsItem() {
		final List<String> lore = new ArrayList<String>();
		for (final Kingdom k : getKingdoms()) {
			lore.add(ChatColor.LIGHT_PURPLE + k.getName());
		}
		
		return new ItemBuilder(Material.BEACON, 1)
			.setName(ChatColor.GOLD + "Kingdoms: " + ChatColor.AQUA + getKingdoms().size()).setLore(lore).build();
	}
	
	public double getMemberTax() {
		return taxes;
	}
	
	public GuildPermissionCell getPermissions() {
		return perms;
	}
	
	@Override
	public boolean hasKingdom() {
		final KingdomManager km = KingdomManager.getInstance();
		for (final Kingdom k : km.getLoadedMap().values())
			if (k.getGuildIDs().contains(getID()))
				return true;
		
		return false;
	}
	
	public boolean isFriendlyFire() {
		return friendlyFire;
	}
	
	public boolean isOpen() {
		return open;
	}
	
	public boolean isTaxPercentBased() {
		return isTaxPercent;
	}
	
	@Override
	public Map<String, Object> serialize() {
		final Map<String, Object> data = super.serialize();
		
		data.put(NODE_OPEN, open);
		data.put(NODE_FRIENDLYFIRE, friendlyFire);
		data.put(NODE_ITEM_IDENTIFIER, guildItem);
		data.put(NODE_TAXES, taxes);
		data.put(NODE_TAXESISPERCENT, isTaxPercent);
		
		if (perms.getPerms().size() > 0) {
			data.put(NODE_PERMS, perms);
		}
		
		return data;
	}
	
	public void setItemBadge(ItemStack badge) {
		// Don't leak
		final ItemStack copy = new ItemStack(badge);
		
		// copy meta
		copy.setItemMeta(guildItem.getItemMeta());
		
		// set as new
		guildItem = copy;
	}
	
	public void setOpen(boolean open) {
		this.open = open;
	}
	
	private void instantiateNonSaves() {
		channel = new GuildChannel(this);
	}
}