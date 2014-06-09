package com.noxpvp.noxguilds.guild;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.logging.Level;

import mkremins.fanciful.FancyMessage;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.bergerkiller.bukkit.common.ModuleLogger;
import com.bergerkiller.bukkit.common.utils.LogicUtil;
import com.noxpvp.noxguilds.Account;
import com.noxpvp.noxguilds.BankAccount;
import com.noxpvp.noxguilds.NoxGuilds;
import com.noxpvp.noxguilds.Settings;
import com.noxpvp.noxguilds.VaultAdapter;
import com.noxpvp.noxguilds.chat.ChannelKeeper;
import com.noxpvp.noxguilds.chat.FanceeMessage;
import com.noxpvp.noxguilds.chat.GuildChannel;
import com.noxpvp.noxguilds.gui.internal.ItemRepresentable;
import com.noxpvp.noxguilds.gui.internal.RankKeeper;
import com.noxpvp.noxguilds.guildplayer.GuildPlayer;
import com.noxpvp.noxguilds.internal.Invitational;
import com.noxpvp.noxguilds.internal.Persistant;
import com.noxpvp.noxguilds.internal.Result;
import com.noxpvp.noxguilds.kingdom.Kingdom;
import com.noxpvp.noxguilds.locale.NoxGuildLocale;
import com.noxpvp.noxguilds.manager.GuildPlayerManager;
import com.noxpvp.noxguilds.manager.KingdomManager;
import com.noxpvp.noxguilds.permisson.GuildPermissionCell;
import com.noxpvp.noxguilds.permisson.PermissionCellKeeper;
import com.noxpvp.noxguilds.territory.TerritoryBlock;
import com.noxpvp.noxguilds.territory.TerritoryCoord;
import com.noxpvp.noxguilds.territory.TerritoryKeeper;
import com.noxpvp.noxguilds.territory.ZoneKeeper;
import com.noxpvp.noxguilds.util.ItemBuilder;
import com.noxpvp.noxguilds.util.NoxItemUtil;

public abstract class BaseGuild implements IGuild, Account, Persistant,
        Invitational<GuildPlayer>,
        PermissionCellKeeper<GuildPermissionCell>, RankKeeper<GuildRank>,
        ZoneKeeper, TerritoryKeeper, ChannelKeeper<GuildChannel>,
        ItemRepresentable {
	
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	// Static fields
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	
	public static final String	      DEFAULT_GUILD_NAME	= "Default Guild Name";
	public static final ItemStack	  genericGuildItem	   = new ItemStack(
	                                                               Material.IRON_SWORD);
	private static ModuleLogger	      guildLogger	       = new ModuleLogger(
	                                                               NoxGuilds
	                                                                       .getInstance(),
	                                                               "Guilds");
	
	// Serializers Start
	private static final String	      NODE_ID	           = "id";
	private static final String	      NODE_GUILD_NAME	   = "name";
	private static final String	      NODE_OWNERID	       = "ownerid";
	private static final String	      NODE_MEMBERS	       = "member-ids";
	private static final String	      NODE_TERRITORY	   = "territory-locs";
	private static final String	      NODE_RANKS	       = "ranks";
	private static final String	      NODE_ZONES	       = "zones";
	private static final String	      NODE_PERMS	       = "permissions";
	private static final String	      NODE_ITEM_IDENTIFIER	= "guild-item";
	
	// options
	private static final String	      NODE_OPEN	           = "isopen";
	private static final String	      NODE_TAG	           = "tag";
	private static final String	      NODE_TAXES	       = "taxes";
	private static final String	      NODE_TAXESISPERCENT	= "is-tax-percent";
	private static final String	      NODE_FRIENDLYFIRE	   = "friendly-fire";
	// Serializers end
	
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	// Instance Fields
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	
	private final UUID	              guildID;
	private String	                  guildName;
	private UUID	                  ownerID;
	private List<UUID>	              members;
	private List<TerritoryBlock>	  territory;
	private List<GuildRank>	          ranks;
	private List<GuildZone>	          zones;
	private GuildPermissionCell	      perms;
	private ItemStack	              guildItem;
	
	// options
	private boolean	                  open;
	private String	                  guildTag;
	private double	                  taxes;
	private boolean	                  isTaxPercent;
	private boolean	                  friendlyFire;
	
	// Other
	private BankAccount	              account;
	private List<GuildPlayer>	      invitedPlayers;
	private Map<GuildPlayer, Integer>	lastInviteRunners;
	private GuildChannel	          channel;
	
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	// Constructors
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	
	@SuppressWarnings("unchecked")
	public BaseGuild(Map<String, Object> data) {
		Object getter;
		if ((getter = data.get(NODE_ID)) != null && getter instanceof String) {
			guildID = UUID.fromString((String) getter);
		} else {
			guildID = UUID.randomUUID();
			log(Level.SEVERE, "Could not load UUID of guild from save");
		}
		
		if ((getter = data.get(NODE_GUILD_NAME)) != null && getter instanceof String) {
			guildName = (String) getter;
		} else {
			guildName = DEFAULT_GUILD_NAME;
		}
		
		if ((getter = data.get(NODE_OWNERID)) != null && getter instanceof String) {
			ownerID = UUID.fromString((String) getter);
		}
		
		if ((getter = data.get(NODE_TAG)) != null && getter instanceof String) {
			guildTag = (String) getter;
		} else {
			guildTag = Settings.defaultGuildTag;
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
		
		if ((getter = data.get(NODE_TAXESISPERCENT)) != null
		        && getter instanceof Boolean) {
			isTaxPercent = (Boolean) getter;
		} else {
			isTaxPercent = Settings.defaultGuildTaxesPercent;
		}
		
		if ((getter = data.get(NODE_FRIENDLYFIRE)) != null
		        && getter instanceof Boolean) {
			friendlyFire = (Boolean) getter;
		} else {
			friendlyFire = Settings.defaultGuildFriendlyFire;
		}
		
		if ((getter = data.get(NODE_MEMBERS)) != null && getter instanceof List<?>) {
			members = new ArrayList<UUID>();
			for (final String id : (List<String>) getter) {
				members.add(UUID.fromString(id));
			}
		} else {
			members = new ArrayList<UUID>();
		}
		
		if ((getter = data.get(NODE_TERRITORY)) != null && getter instanceof List) {
			territory = (List<TerritoryBlock>) getter;
		} else {
			territory = new ArrayList<TerritoryBlock>();
		}
		
		if ((getter = data.get(NODE_RANKS)) != null && getter instanceof List) {
			ranks = (List<GuildRank>) getter;
		} else {
			ranks = new ArrayList<GuildRank>();
		}
		
		if ((getter = data.get(NODE_ZONES)) != null && getter instanceof List) {
			zones = (List<GuildZone>) getter;
		} else {
			zones = new ArrayList<GuildZone>();
		}
		
		if ((getter = data.get(NODE_PERMS)) != null
		        && getter instanceof GuildPermissionCell) {
			perms = (GuildPermissionCell) getter;
		} else {
			perms = new GuildPermissionCell();
		}
		
		if ((getter = data.get(NODE_ITEM_IDENTIFIER)) != null
		        && getter instanceof ItemStack) {
			guildItem = (ItemStack) getter;
		} else {
			guildItem = genericGuildItem.clone();
		}
		
		instantiateNonSaves();
	}
	
	// Deserialize
	public BaseGuild(String name, UUID owner) {
		guildID = UUID.randomUUID();
		guildName = name;
		ownerID = owner;
		members = new ArrayList<UUID>();
		territory = new ArrayList<TerritoryBlock>();
		ranks = new ArrayList<GuildRank>();
		zones = new ArrayList<GuildZone>();
		perms = new GuildPermissionCell();
		guildItem = genericGuildItem.clone();
		
		// options
		open = Settings.defaultGuildOpen;
		guildTag = Settings.defaultGuildTag;
		taxes = Settings.defaultGuildTaxes;
		isTaxPercent = Settings.defaultGuildTaxesPercent;
		friendlyFire = Settings.defaultGuildFriendlyFire;
		
		instantiateNonSaves();
		
		members.add(owner);
	}
	
	public static void log(Level lv, String msg) {
		guildLogger.log(lv, msg);
	}
	
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	// Static Methods
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	
	public boolean addRank(GuildRank rank) {
		if (!ranks.contains(rank))
			return ranks.add(rank);
		
		return false;
	}
	
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	// Instance Methods
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	
	public Result addTerritory(TerritoryBlock block) {
		if (!territory.contains(block))
			if (territory.add(block))
				return new Result(true, "");
			else
				return new Result(false, "");
		
		return new Result(false, "");
	}
	
	public boolean addZone(GuildZone zone) {
		if (!zones.contains(zone))
			return zones.add(zone);
		
		return false;
	}
	
	public Result buyTerritory(GuildPlayer buyer, TerritoryBlock block) {
		if (!hasTerritory(block) || !block.isForSale() || block.hasOwner())
			return new Result(false, "");
		
		if (!buyer.canPay(block.getPrice()))
			return new Result(false, "");
		
		if (buyer.withdrawTo(account, block.getPrice()).isResult()) {
			block.setOwner(buyer.getUID());
			return new Result(true, "");
		}
		
		return new Result(false, "");
	}
	
	public boolean deposit(double amount) {
		return account.deposit(amount);
	}
	
	// Bank account
	public BankAccount getAccount() {
		return account;
	}
	
	public ItemStack getAccountItem() {
		return NoxItemUtil.addLore(getAccount().getIdentifiableItem(), 0,
		        ChatColor.GOLD + "Tax: " + ChatColor.AQUA + (isTaxPercent ?
		                taxes + "%" : VaultAdapter.economy.format(taxes)));
	}
	
	public String getAccountName() {
		return account.getAccountName();
	}
	
	public double getBalance() {
		return account.getBalance();
	}
	
	// Channel keeper
	public GuildChannel getChatChannel() {
		return channel;
	}
	
	public UUID getID() {
		return guildID;
	}
	
	// Menu item
	public ItemStack getIdentifiableItem() {
		
		guildItem = new ItemBuilder(guildItem)
		        .setName(ChatColor.GREEN + guildName)
		        .setLore(
		                ChatColor.GOLD + "Owner: " + ChatColor.AQUA
		                        + getOwner().getPlayer().getName(),
		                ChatColor.GOLD + "Tag: " + ChatColor.AQUA
		                        + getTag())
		        .build();
		
		return guildItem.clone();
	}
	
	public List<GuildPlayer> getInvites() {
		return Collections.unmodifiableList(invitedPlayers);
	}
	
	public List<Kingdom> getKingdoms() {
		final List<Kingdom> ret = new ArrayList<Kingdom>();
		final KingdomManager km = KingdomManager.getInstance();
		for (final Kingdom k : km.getLoadeds().values())
			if (k.getGuildIDs().contains(guildID)) {
				ret.add(k);
			}
		
		return ret;
	}
	
	// Kingdoms item
	public ItemStack getKingdomsItem() {
		final List<String> lore = new ArrayList<String>();
		for (final Kingdom k : getKingdoms()) {
			lore.add(ChatColor.LIGHT_PURPLE + k.getName());
		}
		
		return new ItemBuilder(Material.BEACON, 1)
		        .setName(
		                ChatColor.GOLD + "Kingdoms: " + ChatColor.AQUA
		                        + getKingdoms().size())
		        .setLore(lore)
		        .build();
	}
	
	public List<UUID> getMembers() {
		return Collections.unmodifiableList(members);
	}
	
	// Members
	public ItemStack getMembersItem() {
		final List<String> members = new ArrayList<String>();
		int i = 0;
		
		for (final UUID id : getMembers()) {
			if (i >= 10) {
				members.add(ChatColor.GOLD + "...");
				break;
			}
			
			members.add(ChatColor.AQUA + Bukkit.getPlayer(id).getName());
			i++;
		}
		
		return new ItemBuilder(Material.SKULL_ITEM, getMembers().size(),
		        (short) 3)
		        .setName(
		                ChatColor.GOLD + "Members: " + ChatColor.AQUA
		                        + getMembers().size())
		        .setLore(members)
		        .build();
	}
	
	public double getMemberTax() {
		return taxes;
	}
	
	public String getName() {
		return guildName;
	}
	
	public List<GuildPlayer> getOnlineMembers() {
		final List<GuildPlayer> ret = new ArrayList<GuildPlayer>();
		
		final GuildPlayerManager gpm = GuildPlayerManager.getInstance();
		Player next;
		for (final UUID memberID : members)
			if ((next = Bukkit.getPlayer(memberID)) != null
			        && next.isOnline()) {
				ret.add(gpm.getFromPlayer(next));
			}
		
		return ret;
	}
	
	public List<Player> getOnlinePlayers() {
		final List<Player> ret = new ArrayList<Player>();
		
		for (final UUID pId : members) {
			Player p;
			if ((p = Bukkit.getPlayer(pId)) != null && p.isOnline()) {
				ret.add(p);
			}
		}
		
		return ret;
	}
	
	public GuildPlayer getOwner() {
		return GuildPlayerManager.getInstance().getFromPlayer(
		        Bukkit.getPlayer(ownerID));
	}
	
	// Permissions
	public GuildPermissionCell getPermissions() {
		return perms;
	}
	
	// Persistent
	public UUID getPersistentID() {
		return getID();
	}
	
	// Rank keeper
	public List<GuildRank> getRanks() {
		return Collections.unmodifiableList(ranks);
	}
	
	public List<GuildRank> getRanksFor(GuildPlayer p) {
		final List<GuildRank> ret = new ArrayList<GuildRank>();
		for (final GuildRank r : getRanks())
			if (r.hasMember(p)) {
				ret.add(r);
			}
		
		return ret;
	}
	
	public String getTag() {
		return guildTag;
	}
	
	public List<TerritoryBlock> getTerritory() {
		return Collections.unmodifiableList(territory);
	}
	
	// Territory keeper
	public ItemStack getTerritoryItem() {
		return new ItemBuilder(Material.GRASS, getTerritory().size())
		        .setName(ChatColor.GOLD + "Territory")
		        .setLore(
		                ChatColor.GOLD + "Claimed Land: " + ChatColor.AQUA
		                        + getTerritory().size() + " / " + "0")
		        .build();
	}
	
	// Zone keeper
	public List<GuildZone> getZones() {
		return zones;
	}
	
	public boolean hasKingdom() {
		final KingdomManager km = KingdomManager.getInstance();
		for (final Kingdom k : km.getLoadeds().values())
			if (k.getGuildIDs().contains(guildID))
				return true;
		
		return false;
	}
	
	public boolean hasMember(GuildPlayer player) {
		return getMembers().contains(player.getUID());
	}
	
	public boolean hasRank(GuildRank rank) {
		return ranks.contains(rank);
	}
	
	public boolean hasRank(UUID rankID) {
		for (final GuildRank r : ranks)
			if (r.getID().equals(rankID))
				return true;
		
		return false;
	}
	
	public boolean hasTerritory(Location loc) {
		return hasTerritory(new TerritoryCoord(loc));
	}
	
	public boolean hasTerritory(TerritoryBlock block) {
		return territory.contains(block);
	}
	
	public boolean hasTerritory(TerritoryCoord coord) {
		return hasTerritory(new TerritoryBlock(coord));
	}
	
	public boolean hasZone(GuildZone zone) {
		return zones.contains(zone);
	}
	
	public boolean hasZone(UUID zoneID) {
		for (final GuildZone z : zones)
			if (z.getID().equals(zoneID))
				return true;
		
		return false;
	}
	
	// Invitational
	public void invite(final GuildPlayer player) {
		invitedPlayers.add(player);
		getChatChannel().broadcast(
		        new FanceeMessage(
		                new FancyMessage()
		                        .text(NoxGuildLocale.INVITE_INVITED_GUILD
		                                .get())
		                        .then(getName())
		                        .color(ChatColor.GREEN)
		                        .itemTooltip(getIdentifiableItem())));
		
		int i;
		if ((i = lastInviteRunners.get(player)) != 0) {
			Bukkit.getScheduler().cancelTask(i);
		}
		
		lastInviteRunners.put(player, Bukkit.getScheduler().runTaskLater(
		        NoxGuilds.getInstance(), new Runnable() {
			        
			        public void run() {
				        invitedPlayers.remove(player);
				        lastInviteRunners.remove(player);
			        }
			        
		        }, 20 * 30).getTaskId());
	}
	
	public boolean isFriendlyFire() {
		return friendlyFire;
	}
	
	public boolean isInvited(GuildPlayer object) {
		return invitedPlayers.contains(object);
	}
	
	public boolean isOpen() {
		return open;
	}
	
	public boolean isTaxPercentBased() {
		return isTaxPercent;
	}
	
	public void load() {
		// Nothing not done with deserialization yet
		return;
	}
	
	public boolean pay(double amount) {
		return account.pay(amount);
	}
	
	public boolean removeRank(GuildRank rank) {
		return ranks.remove(rank);
	}
	
	public Result removeTerritory(TerritoryBlock block) {
		if (territory.contains(block))
			if (territory.remove(block))
				return new Result(true, "");
			else
				return new Result(false, "");
		
		return new Result(false, "");
	}
	
	public boolean removeZone(GuildZone zone) {
		return zones.remove(zone);
	}
	
	public void save() {
		// Nothing not done with serialization yet
		return;
	}
	
	// Serialize
	public Map<String, Object> serialize() {
		final Map<String, Object> data = new HashMap<String, Object>();
		
		data.put(NODE_ID, guildID.toString());
		data.put(NODE_GUILD_NAME, guildName);
		data.put(NODE_OWNERID, ownerID.toString());
		data.put(NODE_TAG, guildTag);
		data.put(NODE_FRIENDLYFIRE, friendlyFire);
		data.put(NODE_OPEN, open);
		data.put(NODE_TAXES, taxes);
		data.put(NODE_TAXESISPERCENT, isTaxPercent);
		data.put(NODE_ITEM_IDENTIFIER, new ItemStack(guildItem.getType(),
		        guildItem.getAmount()));
		
		final List<String> memberList = new ArrayList<String>();
		for (final UUID id : members) {
			memberList.add(id.toString());
		}
		if (memberList.size() > 0) {
			data.put(NODE_MEMBERS, memberList);
		}
		
		if (ranks.size() > 0) {
			data.put(NODE_RANKS, ranks);
		}
		
		if (territory.size() > 0) {
			data.put(NODE_TERRITORY, territory);
		}
		
		if (perms.getPerms().size() > 0) {
			data.put(NODE_PERMS, perms);
		}
		
		if (zones.size() > 0) {
			data.put(NODE_ZONES, zones);
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
	
	public void setName(String newName) {
		guildName = checkGuildName(newName);
	};
	
	public void setOpen(boolean open) {
		this.open = open;
	}
	
	public void setTag(String tag) {
		guildTag = tag.length() <= 128 ? tag : getTag();
	}
	
	public void uninvite(GuildPlayer object) {
		int i;
		if ((i = lastInviteRunners.get(object)) != 0) {
			Bukkit.getScheduler().cancelTask(i);
		}
		
		invitedPlayers.remove(object);
	}
	
	// Main Data
	private String checkGuildName(String newName) {
		if (newName.length() > 1 && newName.length() < 17)
			return newName;
		
		return !LogicUtil.nullOrEmpty(guildName) ? guildName
		        : DEFAULT_GUILD_NAME;
	}
	
	private void instantiateNonSaves() {
		account = new BankAccount(guildID.toString());
		invitedPlayers = new ArrayList<GuildPlayer>();
		lastInviteRunners = new HashMap<GuildPlayer, Integer>();
		channel = new GuildChannel(this);
	}
}