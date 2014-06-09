package com.noxpvp.noxguilds.guildplayer;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.bergerkiller.bukkit.common.utils.LogicUtil;
import com.noxpvp.noxguilds.Account;
import com.noxpvp.noxguilds.AccountBase;
import com.noxpvp.noxguilds.PlayerAccount;
import com.noxpvp.noxguilds.VaultAdapter;
import com.noxpvp.noxguilds.gui.internal.CoreBox;
import com.noxpvp.noxguilds.gui.internal.ItemRepresentable;
import com.noxpvp.noxguilds.guild.Guild;
import com.noxpvp.noxguilds.internal.Persistant;
import com.noxpvp.noxguilds.internal.Result;
import com.noxpvp.noxguilds.manager.GuildManager;
import com.noxpvp.noxguilds.permisson.PermissionCellKeeper;
import com.noxpvp.noxguilds.permisson.PlayerPermissionCell;
import com.noxpvp.noxguilds.util.ItemBuilder;

public abstract class BaseGuildPlayer implements IGuildPlayer, Persistant,
        PermissionCellKeeper<PlayerPermissionCell>, Account,
        ItemRepresentable {
	
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	// Static Fields
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	
	// Serializers start
	private static final String	   NODE_UID	           = "uid";
	private static final String	   NODE_GUILD_LIST	   = "guilds";
	private static final String	   NODE_PLAYER_OPTIONS	= "options";
	private static final String	   NODE_PLAYER_PERMS	= "permissions";
	private static final String	   NODE_PLAYER_STATS	= "stats";
	// Serializers end
	
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	// Instance Fields
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	
	private UUID	               playerUUID;
	private final PlayerAccount	   account;
	private List<UUID>	           guilds;
	private PlayerPermissionCell	perms;
	private GulidPlayerStats	   stats;
	private WeakReference<CoreBox>	currentBox;
	private Map<String, Object>	   options;
	
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	// Constructors
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	
	// Deserialize
	@SuppressWarnings("unchecked")
	public BaseGuildPlayer(Map<String, Object> data) {
		Object getter;
		if ((getter = data.get(NODE_UID)) != null
		        && getter instanceof String) {
			playerUUID = UUID.fromString((String) getter);
		} else
			throw new IllegalStateException(
			        "Could not load player uid from data file");
		
		if ((getter = data.get(NODE_GUILD_LIST)) != null
		        && getter instanceof List<?>) {
			guilds = new ArrayList<UUID>();
			for (final String guildID : (List<String>) getter) {
				guilds.add(UUID.fromString(guildID));
			}
		} else {
			guilds = new ArrayList<UUID>();
		}
		
		if ((getter = data.get(NODE_PLAYER_OPTIONS)) != null
		        && getter instanceof Map<?, ?>) {
			options = (Map<String, Object>) getter;
		} else {
			options = new HashMap<String, Object>();
		}
		
		if ((getter = data.get(NODE_PLAYER_PERMS)) != null
		        && getter instanceof PlayerPermissionCell) {
			perms = (PlayerPermissionCell) getter;
		} else {
			perms = new PlayerPermissionCell();
		}
		
		if ((getter = data.get(NODE_PLAYER_STATS)) != null
		        && getter instanceof GulidPlayerStats) {
			stats = (GulidPlayerStats) getter;
		} else {
			stats = new GulidPlayerStats();
		}
		
		account = new PlayerAccount(playerUUID);
		currentBox = null;
		
	}
	
	// From player
	public BaseGuildPlayer(Player player) {
		playerUUID = player.getUniqueId();
		guilds = new ArrayList<UUID>();
		options = new HashMap<String, Object>();
		perms = new PlayerPermissionCell();
		stats = new GulidPlayerStats();
		
		account = new PlayerAccount(playerUUID);
		currentBox = null;
	}
	
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	// Instance Methods
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	
	// Item
	
	public boolean addToGuild(Guild guild) {
		return guilds.add(guild.getID());
	}
	
	public boolean canPay(double amount) {
		return account.canPay(amount);
	}
	
	public void deleteCoreBox() {
		if (hasCoreBox()) {
			getCoreBox().hide();
		}
		
		currentBox = null;
	}
	
	public boolean deposit(double amount) {
		return account.deposit(amount);
	}
	
	public Result depositFrom(AccountBase giver, double amount) {
		return account.depositFrom(giver, amount);
	}
	
	// Economy
	public String getAccountName() {
		return account.getAccountName();
	}
	
	public double getBalance() {
		return account.getBalance();
	}
	
	// GUI
	public CoreBox getCoreBox() {
		return currentBox != null && currentBox.get() != null ? currentBox
		        .get() : null;
	}
	
	public String getFormatedName() {
		return VaultAdapter.ChatUtil.getFormatedPlayerName(getPlayer());
	}
	
	public List<Guild> getGuilds() {
		final List<Guild> ret = new ArrayList<Guild>();
		final GuildManager gm = GuildManager.getInstance();
		for (final UUID id : getGuildsIDs()) {
			ret.add(gm.get(id));
		}
		
		return ret;
	}
	
	public List<UUID> getGuildsIDs() {
		return Collections.unmodifiableList(guilds);
	}
	
	public ItemStack getIdentifiableItem() {
		return new ItemBuilder(new ItemStack(Material.SKULL_ITEM, 1,
		        (short) 3))
		        .setName(getFormatedName())
		        .setLore(
		                ChatColor.GOLD + "Kills: " + ChatColor.AQUA
		                        + getStats().getKills(),
		                ChatColor.GOLD + "Deaths: " + ChatColor.AQUA
		                        + getStats().getDeaths(),
		                ChatColor.GOLD
		                        + "KD Ratio: "
		                        + ChatColor.AQUA
		                        + String.format("%.2f", getStats()
		                                .getKDRatio()),
		                ChatColor.GOLD + "Last login: " + ChatColor.AQUA
		                        + getStats().getLastLogin().toString(),
		                ChatColor.GOLD + "Join date: " + ChatColor.AQUA
		                        + getStats().getFirstCreated().toString()
		        )
		        .build();
	}
	
	public Object getOption(String key) {
		return options.get(key);
	}
	
	// Main Data
	public Map<String, Object> getOptions() {
		return Collections.unmodifiableMap(options);
	}
	
	// Permissions
	public PlayerPermissionCell getPermissions() {
		return perms;
	}
	
	// Persistent
	public UUID getPersistentID() {
		return getUID();
	}
	
	public Player getPlayer() {
		return Bukkit.getPlayer(getUID());
	}
	
	// Stats
	public GulidPlayerStats getStats() {
		return stats;
	}
	
	public UUID getUID() {
		return playerUUID;
	}
	
	public boolean hasCoreBox() {
		return getCoreBox() != null;
	}
	
	public boolean hasCoreBox(CoreBox box) {
		return hasCoreBox() && getCoreBox().equals(box);
	}
	
	public boolean hasGuild() {
		return !LogicUtil.nullOrEmpty(guilds);
	}
	
	public boolean isGuildMaster() {
		for (final Guild g : getGuilds())
			if (g.getOwner() == this)
				return true;
		
		return false;
	}
	
	public boolean pay(double amount) {
		return account.pay(amount);
	}
	
	public boolean removeFromGuild(Guild guild) {
		return guilds.remove(guild.getID());
	}
	
	// Serialize
	public Map<String, Object> serialize() {
		final Map<String, Object> data = new HashMap<String, Object>();
		
		if (playerUUID != null) {
			data.put(NODE_UID, playerUUID.toString());
		}
		
		if (!LogicUtil.nullOrEmpty(guilds)) {
			final List<String> guildIdStrings = new ArrayList<String>();
			for (final UUID id : guilds) {
				guildIdStrings.add(id.toString());
			}
			
			data.put(NODE_GUILD_LIST, guildIdStrings);
		}
		
		if (!LogicUtil.nullOrEmpty(options)) {
			data.put(NODE_PLAYER_OPTIONS, options);
		}
		
		return data;
	}
	
	public void setCoreBox(CoreBox box) {
		currentBox = new WeakReference<CoreBox>(box);
	}
	
	public void setOption(String key, Object value) {
		options.put(key, value);
	}
	
	public Result withdrawTo(AccountBase receiver, double amount) {
		return account.withdrawTo(receiver, amount);
	}
	
}
