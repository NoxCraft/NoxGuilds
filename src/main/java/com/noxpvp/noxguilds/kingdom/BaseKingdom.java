package com.noxpvp.noxguilds.kingdom;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import mkremins.fanciful.FancyMessage;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import com.bergerkiller.bukkit.common.ModuleLogger;
import com.noxpvp.noxguilds.Account;
import com.noxpvp.noxguilds.BankAccount;
import com.noxpvp.noxguilds.NoxGuilds;
import com.noxpvp.noxguilds.Settings;
import com.noxpvp.noxguilds.chat.ChannelKeeper;
import com.noxpvp.noxguilds.chat.FanceeMessage;
import com.noxpvp.noxguilds.chat.KingdomChannel;
import com.noxpvp.noxguilds.gui.internal.ItemRepresentable;
import com.noxpvp.noxguilds.gui.internal.RankKeeper;
import com.noxpvp.noxguilds.guild.Guild;
import com.noxpvp.noxguilds.guildplayer.GuildPlayer;
import com.noxpvp.noxguilds.internal.Invitational;
import com.noxpvp.noxguilds.internal.Persistant;
import com.noxpvp.noxguilds.locale.NoxGuildLocale;
import com.noxpvp.noxguilds.manager.GuildManager;
import com.noxpvp.noxguilds.manager.GuildPlayerManager;
import com.noxpvp.noxguilds.manager.KingdomManager;
import com.noxpvp.noxguilds.permisson.KingdomPermissionCell;
import com.noxpvp.noxguilds.permisson.PermissionCellKeeper;
import com.noxpvp.noxguilds.util.ItemBuilder;
import com.noxpvp.noxguilds.util.MessageUtil;

public class BaseKingdom implements IKingdom, Persistant,
        RankKeeper<KingdomRank>,
        PermissionCellKeeper<KingdomPermissionCell>, Account,
        ChannelKeeper<KingdomChannel>,
        Invitational<Guild>, ItemRepresentable {
	
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	// Static fields
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	
	public static final String	  DEFAULT_KINGDOM_NAME	 = "default kingdom name";
	public static final ItemStack	genericKingdomItem	 = new ItemStack(
	                                                             Material.BEACON);
	private static ModuleLogger	  kingdomLogger	         = new ModuleLogger(
	                                                             NoxGuilds
	                                                                     .getInstance(),
	                                                             "Kingdoms");
	
	// Serializers start
	public static final String	  NODE_KINGDOM_ID	     = "uid";
	public static final String	  NODE_NAME	             = "name";
	public static final String	  NODE_KINGDOM_ITEM	     = "kingdom-item";
	public static final String	  NODE_CAPITAL	         = "capital-id";
	public static final String	  NODE_KING	             = "king-id";
	public static final String	  NODE_GUILDS	         = "guild-ids";
	public static final String	  NODE_ALLIES	         = "allies";
	public static final String	  NODE_ENEMIES	         = "enemies";
	public static final String	  NODE_RANKS	         = "ranks";
	public static final String	  NODE_PERMS	         = "permissions";
	public static final String	  NODE_OPTIONS	         = "options";
	public static final String	  NODE_OPEN	             = "open";
	public static final String	  NODE_TAG	             = "tag";
	public static final String	  NODE_TAXES	         = "taxes";
	public static final String	  NODE_TAXESPERCENTBASED	= "taxes-percent";
	public static final String	  NODE_FRIENDLYFIRE	     = "friendly-fire";
	// Serializers end
	
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	// Instance Fields
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	
	private UUID	              id;
	private String	              name;
	private ItemStack	          kingdomBadge;
	private UUID	              capital;
	private UUID	              king;
	private String	              tag;
	
	private boolean	              open;
	private double	              taxes;
	private boolean	              isTaxPercent;
	private boolean	              friendlyFire;
	private List<UUID>	          guilds;
	private List<UUID>	          allies;
	private List<UUID>	          enemies;
	private KingdomPermissionCell	perms;
	private List<KingdomRank>	  ranks;
	
	private BankAccount	          account;
	private KingdomChannel	      channel;
	private List<Guild>	          invitedGuilds;
	private Map<Guild, Integer>	  lastInviteRunners;
	
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	// Constructors
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	
	// Deserialize
	@SuppressWarnings("unchecked")
	public BaseKingdom(Map<String, Object> data) {
		Object getter;
		
		if ((getter = data.get(NODE_KINGDOM_ID)) != null
		        && getter instanceof String) {
			id = UUID.fromString((String) getter);
		} else
			return;
		
		if ((getter = data.get(NODE_NAME)) != null
		        && getter instanceof String) {
			name = (String) getter;
		} else {
			name = DEFAULT_KINGDOM_NAME;
		}
		
		if ((getter = data.get(NODE_KINGDOM_ITEM)) != null
		        && getter instanceof ItemStack) {
			kingdomBadge = (ItemStack) getter;
		} else {
			kingdomBadge = genericKingdomItem.clone();
		}
		
		if ((getter = data.get(NODE_CAPITAL)) != null
		        && getter instanceof String) {
			capital = UUID.fromString((String) getter);
		}
		
		if ((getter = data.get(NODE_KING)) != null
		        && getter instanceof String) {
			king = UUID.fromString((String) getter);
		}
		
		if ((getter = data.get(NODE_TAG)) != null
		        && getter instanceof String) {
			tag = (String) getter;
		} else {
			tag = Settings.defaultKingdomTag;
		}
		
		if ((getter = data.get(NODE_OPEN)) != null
		        && getter instanceof Boolean) {
			open = (Boolean) getter;
		} else {
			open = Settings.defaultKingdomOpen;
		}
		
		if ((getter = data.get(NODE_FRIENDLYFIRE)) != null
		        && getter instanceof Boolean) {
			friendlyFire = (Boolean) getter;
		} else {
			friendlyFire = Settings.defaultKingdomFriendlyFire;
		}
		
		if ((getter = data.get(NODE_TAXES)) != null
		        && getter instanceof Number) {
			taxes = (Double) getter;
		} else {
			taxes = Settings.defaultTaxesKingdom;
		}
		
		if ((getter = data.get(NODE_TAXESPERCENTBASED)) != null
		        && getter instanceof Boolean) {
			isTaxPercent = (Boolean) getter;
		} else {
			isTaxPercent = Settings.defaultTaxesKingdomPercent;
		}
		
		if ((getter = data.get(NODE_ALLIES)) != null
		        && getter instanceof List<?>) {
			final List<UUID> ids = new ArrayList<UUID>();
			for (final String id : (List<String>) getter) {
				ids.add(UUID.fromString(id));
			}
			
			allies = new ArrayList<UUID>(ids);
		} else {
			allies = new ArrayList<UUID>();
		}
		
		if ((getter = data.get(NODE_ENEMIES)) != null
		        && getter instanceof List<?>) {
			final List<UUID> ids = new ArrayList<UUID>();
			for (final String id : (List<String>) getter) {
				ids.add(UUID.fromString(id));
			}
			
			enemies = new ArrayList<UUID>(ids);
		} else {
			enemies = new ArrayList<UUID>();
		}
		
		if ((getter = data.get(NODE_GUILDS)) != null
		        && getter instanceof List<?>) {
			final List<UUID> ids = new ArrayList<UUID>();
			for (final String id : (List<String>) getter) {
				ids.add(UUID.fromString(id));
			}
			
			guilds = new ArrayList<UUID>(ids);
		} else {
			guilds = new ArrayList<UUID>();
		}
		
		if ((getter = data.get(NODE_RANKS)) != null
		        && getter instanceof List<?>) {
			ranks = (List<KingdomRank>) getter;
		} else {
			ranks = new ArrayList<KingdomRank>();
		}
		
		if ((getter = data.get(NODE_PERMS)) != null
		        && getter instanceof KingdomPermissionCell) {
			perms = (KingdomPermissionCell) getter;
		} else {
			perms = new KingdomPermissionCell();
		}
		
		instantiateNonSaves();
	}
	
	public BaseKingdom(UUID capital, UUID king, String name) {
		id = UUID.randomUUID();
		this.name = checkKingdomName(name);
		kingdomBadge = genericKingdomItem.clone();
		this.capital = capital;
		this.king = king;
		tag = Settings.defaultKingdomTag;
		
		open = Settings.defaultKingdomOpen;
		taxes = Settings.defaultTaxesKingdom;
		isTaxPercent = Settings.defaultTaxesKingdomPercent;
		friendlyFire = Settings.defaultKingdomFriendlyFire;
		guilds = new ArrayList<UUID>();
		guilds.add(capital);
		allies = new ArrayList<UUID>();
		enemies = new ArrayList<UUID>();
		perms = new KingdomPermissionCell();
		ranks = new ArrayList<KingdomRank>();
		
		instantiateNonSaves();
	}
	
	public boolean addAlly(Kingdom ally) {
		return allies.add(ally.getID());
	}
	
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	// Instance Methods
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	
	public boolean addEnemy(Kingdom enemy) {
		return enemies.add(enemy.getID());
	}
	
	public boolean addRank(KingdomRank rank) {
		return ranks.add(rank);
	}
	
	public boolean deposit(double amount) {
		return account.deposit(amount);
	}
	
	// Bank Account
	public String getAccountName() {
		return account.getAccountName();
	}
	
	public List<Kingdom> getAllies() {
		final List<Kingdom> ret = new ArrayList<Kingdom>();
		final KingdomManager km = KingdomManager.getInstance();
		for (final UUID id : getAlliesIDs()) {
			ret.add(km.get(id));
		}
		
		return Collections.unmodifiableList(ret);
	}
	
	public List<UUID> getAlliesIDs() {
		return Collections.unmodifiableList(allies);
	}
	
	public double getBalance() {
		return account.getBalance();
	}
	
	public Guild getCapital() {
		return GuildManager.getInstance().get(capital);
	}
	
	// Channel keeper
	public KingdomChannel getChatChannel() {
		return channel;
	}
	
	public List<Kingdom> getEnemies() {
		final List<Kingdom> ret = new ArrayList<Kingdom>();
		final KingdomManager km = KingdomManager.getInstance();
		for (final UUID id : getEnemiesIDs()) {
			ret.add(km.get(id));
		}
		
		return Collections.unmodifiableList(ret);
	}
	
	public List<UUID> getEnemiesIDs() {
		return Collections.unmodifiableList(enemies);
	}
	
	public List<UUID> getGuildIDs() {
		return Collections.unmodifiableList(guilds);
	}
	
	public List<Guild> getGuilds() {
		final List<Guild> ret = new ArrayList<Guild>();
		final GuildManager gm = GuildManager.getInstance();
		for (final UUID id : getGuildIDs()) {
			ret.add(gm.get(id));
		}
		
		return Collections.unmodifiableList(ret);
	}
	
	// Main Data
	public UUID getID() {
		return id;
	}
	
	// Menu item
	public ItemStack getIdentifiableItem() {
		final List<String> lore = new ArrayList<String>();
		lore.add(ChatColor.GOLD + "Owner: " + ChatColor.AQUA
		        + getKing().getPlayer().getName());
		lore.add(ChatColor.GOLD + "Tag: ");
		lore.addAll(MessageUtil.convertStringForLore(ChatColor.AQUA
		        + getTag()));
		
		kingdomBadge = new ItemBuilder(kingdomBadge)
		        .setName(ChatColor.GREEN + getName())
		        .setLore(lore)
		        .build();
		
		return kingdomBadge.clone();
	}
	
	public List<Guild> getInvites() {
		return Collections.unmodifiableList(invitedGuilds);
	}
	
	public GuildPlayer getKing() {
		return GuildPlayerManager.getInstance().getPlayer(king);
	}
	
	public String getName() {
		return name;
	}
	
	// Permission keeper
	public KingdomPermissionCell getPermissions() {
		return perms;
	}
	
	// Persistant
	public UUID getPersistentID() {
		return getID();
	}
	
	// Rank Keeper
	public List<KingdomRank> getRanks() {
		return Collections.unmodifiableList(ranks);
	}
	
	public String getTag() {
		return tag;
	}
	
	public double getTaxes() {
		return taxes;
	}
	
	public boolean hasRank(KingdomRank rank) {
		return ranks.contains(rank);
	}
	
	public boolean hasRank(UUID rankID) {
		for (final KingdomRank r : ranks)
			if (r.getID().equals(rankID))
				return true;
		
		return false;
	}
	
	// invitational
	public void invite(final Guild guild) {
		invitedGuilds.add(guild);
		guild.getChatChannel()
		        .broadcast(
		                new FanceeMessage(
		                        new FancyMessage()
		                                .text(NoxGuildLocale.INVITE_INVITED_KINGDOM
		                                        .get("To join the Kingdom of "))
		                                .then(getName())
		                                .itemTooltip(getIdentifiableItem())));
		
		int i;
		if ((i = lastInviteRunners.get(guild)) != 0) {
			Bukkit.getScheduler().cancelTask(i);
		}
		
		lastInviteRunners.put(guild, Bukkit.getScheduler().runTaskLater(
		        NoxGuilds.getInstance(), new Runnable() {
			        
			        public void run() {
				        invitedGuilds.remove(guild);
				        lastInviteRunners.remove(guild);
			        }
			        
		        }, 20 * 30).getTaskId());
	}
	
	public boolean isFriendlyFire() {
		return friendlyFire;
	}
	
	public boolean isInvited(Guild object) {
		return invitedGuilds.contains(object);
	}
	
	public boolean isOpen() {
		return open;
	}
	
	public boolean isTaxesPercent() {
		return isTaxPercent;
	}
	
	public void load() {
		return;
	}
	
	public boolean pay(double amount) {
		return account.pay(amount);
	}
	
	public boolean removeAlly(Kingdom ally) {
		return allies.remove(ally);
	}
	
	public boolean removeEnemy(Kingdom enemy) {
		return enemies.remove(enemy);
	}
	
	public boolean removeRank(KingdomRank rank) {
		return ranks.remove(rank);
	}
	
	public void save() {
		return;
	}
	
	// Serialize
	public Map<String, Object> serialize() {
		final Map<String, Object> data = new HashMap<String, Object>();
		
		data.put(NODE_KINGDOM_ID, id.toString());
		data.put(NODE_NAME, name);
		data.put(NODE_CAPITAL, capital.toString());
		data.put(NODE_KING, king.toString());
		data.put(NODE_TAG, tag);
		data.put(NODE_OPEN, open);
		data.put(NODE_TAXES, taxes);
		data.put(NODE_TAXESPERCENTBASED, isTaxPercent);
		
		if (guilds.size() > 0) {
			data.put(NODE_GUILDS, guilds);
		}
		
		if (allies.size() > 0) {
			data.put(NODE_ALLIES, allies);
		}
		
		if (enemies.size() > 0) {
			data.put(NODE_ENEMIES, enemies);
		}
		
		if (ranks.size() > 0) {
			data.put(NODE_RANKS, ranks);
		}
		
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
	
	public void uninvite(Guild object) {
		int i;
		if ((i = lastInviteRunners.get(object)) != 0) {
			Bukkit.getScheduler().cancelTask(i);
		}
		
		invitedGuilds.remove(object);
	}
	
	private String checkKingdomName(String newName) {
		if (newName.length() > 1 && newName.length() < 17)
			return newName;
		
		return name != null ? name : DEFAULT_KINGDOM_NAME;
	}
	
	private void instantiateNonSaves() {
		account = new BankAccount(id.toString());
		channel = new KingdomChannel(this);
		invitedGuilds = new ArrayList<Guild>();
		lastInviteRunners = new HashMap<Guild, Integer>();
	}
	
}
