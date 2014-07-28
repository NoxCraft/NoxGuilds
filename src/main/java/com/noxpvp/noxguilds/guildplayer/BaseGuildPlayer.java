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
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.bergerkiller.bukkit.common.utils.LogicUtil;
import com.noxpvp.noxguilds.VaultAdapter;
import com.noxpvp.noxguilds.economy.Account;
import com.noxpvp.noxguilds.economy.AccountBase;
import com.noxpvp.noxguilds.economy.PlayerAccount;
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
	private static final String			NODE_UID			= "uid";
	private static final String			NODE_PLAYER_PERMS	= "permissions";
	private static final String			NODE_PLAYER_STATS	= "stats";
	// Serializers end
	
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	// Instance Fields
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	
	private UUID						playerUUID;
	private PlayerPermissionCell		perms;
	private GulidPlayerStats			stats;
	private final PlayerAccount			account;
	private final Map<String, Object>	options;
	private WeakReference<CoreBox>		currentBox;
	
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
		options = new HashMap<String, Object>();
		currentBox = null;
		
	}
	
	// From player
	public BaseGuildPlayer(Player player) {
		playerUUID = player.getUniqueId();
		perms = new PlayerPermissionCell();
		stats = new GulidPlayerStats();
		
		account = new PlayerAccount(playerUUID);
		options = new HashMap<String, Object>();
		currentBox = null;
	}
	
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	// Instance Methods
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	
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
	
	public String getAccountName() {
		return account.getAccountName();
	}
	
	public double getBalance() {
		return account.getBalance();
	}
	
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
		final List<UUID> ret = new ArrayList<UUID>();
		
		for (final Guild g : GuildManager.getInstance().getLoadedMap().values())
			if (g.hasMember(playerUUID)) {
				ret.add(g.getID());
			}
		
		return ret;
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
	
	public OfflinePlayer getOffline() {
		return Bukkit.getOfflinePlayer(getUID());
	}
	
	public Object getOption(String key) {
		return options.get(key);
	}
	
	public Map<String, Object> getOptions() {
		return Collections.unmodifiableMap(options);
	}
	
	public PlayerPermissionCell getPermissions() {
		return perms;
	}
	
	public UUID getPersistentID() {
		return getUID();
	}
	
	public String getPersistentStringID() {
		return getPersistentID().toString();
	}
	
	public Player getPlayer() {
		return Bukkit.getPlayer(getUID());
	}
	
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
		return !LogicUtil.nullOrEmpty(getGuildsIDs());
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
	
	public Map<String, Object> serialize() {
		final Map<String, Object> data = new HashMap<String, Object>();
		
		if (playerUUID != null) {
			data.put(NODE_UID, playerUUID.toString());
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
