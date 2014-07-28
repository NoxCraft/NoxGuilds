package com.noxpvp.noxguilds.guild;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.noxpvp.noxguilds.NoxGuilds;
import com.noxpvp.noxguilds.Settings;
import com.noxpvp.noxguilds.VaultAdapter;
import com.noxpvp.noxguilds.chat.ChannelKeeper;
import com.noxpvp.noxguilds.chat.FanceeMessage;
import com.noxpvp.noxguilds.chat.GuildChannel;
import com.noxpvp.noxguilds.economy.Account;
import com.noxpvp.noxguilds.economy.BankAccount;
import com.noxpvp.noxguilds.gui.internal.ItemRepresentable;
import com.noxpvp.noxguilds.guildplayer.GuildPlayer;
import com.noxpvp.noxguilds.internal.Membership;
import com.noxpvp.noxguilds.internal.Persistant;
import com.noxpvp.noxguilds.internal.Question;
import com.noxpvp.noxguilds.internal.QuestionOption;
import com.noxpvp.noxguilds.internal.RankKeeper;
import com.noxpvp.noxguilds.kingdom.Kingdom;
import com.noxpvp.noxguilds.land.LandOwner;
import com.noxpvp.noxguilds.locale.NoxGuildLocale;
import com.noxpvp.noxguilds.manager.GuildManager;
import com.noxpvp.noxguilds.manager.GuildPlayerManager;
import com.noxpvp.noxguilds.manager.KingdomManager;
import com.noxpvp.noxguilds.permisson.GuildPermissionCell;
import com.noxpvp.noxguilds.permisson.PermissionCellKeeper;
import com.noxpvp.noxguilds.util.ItemBuilder;
import com.noxpvp.noxguilds.util.NoxItemUtil;

public abstract class BaseGuild extends LandOwner implements IGuild, Account,
		Persistant,
		PermissionCellKeeper<GuildPermissionCell>,
		RankKeeper<GuildRank>, ItemRepresentable,
		ChannelKeeper<GuildChannel>, Membership<GuildPlayer> {
	
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	// Static fields
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	
	private static final String			DEFAULT_GUILD_TAG	= "";
	
	// Serializers Start
	private static final String			NODE_ID				= "guild-id";
	private static final String			NODE_GUILD_NAME		= "guild-name";
	private static final String			NODE_GUILD_TAG		= "guild-tag";
	private static final String			NODE_OWNERID		= "owner-id";
	private static final String			NODE_MEMBERS		= "guild-members";
	private static final String			NODE_RANKS			= "player-ranks";
	// Serializers end
	
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	// Instance Fields
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	
	private final UUID					guildID;
	private String						guildName;
	private String						guildTag;
	private final UUID					ownerID;
	private Set<UUID>					members;
	private Set<GuildRank>				ranks;
	
	// Other
	private BankAccount					account;
	private Set<GuildPlayer>			invitedPlayers;
	private Map<GuildPlayer, Integer>	lastInviteRunners;
	
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	// Constructors
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	
	@SuppressWarnings("unchecked")
	public BaseGuild(Map<String, Object> data) {
		super(data);
		
		Object getter;
		if ((getter = data.get(NODE_ID)) != null && getter instanceof String) {
			guildID = UUID.fromString((String) getter);
		} else {
			guildID = UUID.randomUUID();
			Guild.log(Level.SEVERE, "Could not load UUID of guild from save");
		}
		
		if ((getter = data.get(NODE_GUILD_NAME)) != null && getter instanceof String) {
			guildName = (String) getter;
		}
		
		if ((getter = data.get(NODE_OWNERID)) != null && getter instanceof String) {
			ownerID = UUID.fromString((String) getter);
		} else
			throw new NullPointerException("Could not retrieve owner from save!");
		
		if ((getter = data.get(NODE_GUILD_TAG)) != null && getter instanceof String) {
			guildTag = (String) getter;
		} else {
			guildTag = Settings.defaultGuildTag;
		}
		
		if ((getter = data.get(NODE_MEMBERS)) != null && getter instanceof List<?>) {
			members = new HashSet<UUID>();
			for (final String id : (List<String>) getter) {
				members.add(UUID.fromString(id));
			}
		} else {
			members = new HashSet<UUID>();
		}
		
		if ((getter = data.get(NODE_RANKS)) != null && getter instanceof Set) {
			ranks = (Set<GuildRank>) getter;
		} else {
			ranks = new HashSet<GuildRank>();
		}
		
		instantiateNonSaves();
	}
	
	// Deserialize
	public BaseGuild(String name, GuildPlayer owner) {
		super();
		
		guildID = UUID.randomUUID();
		guildName = name;
		guildTag = DEFAULT_GUILD_TAG;
		ownerID = owner.getUID();
		members = new HashSet<UUID>();
		ranks = new HashSet<GuildRank>();
		
		instantiateNonSaves();
		
		addMember(owner);
	}
	
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	// Static Methods
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	// Instance Methods
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	
	public void addMember(GuildPlayer player) {
		members.add(player.getUID());
	}
	
	public void addMember(UUID newMember) {
		members.add(newMember);
	}
	
	public boolean addRank(GuildRank rank) {
		if (!ranks.contains(rank))
			return ranks.add(rank);
		
		return false;
	}
	
	public boolean deposit(double amount) {
		return account.deposit(amount);
	}
	
	public BankAccount getAccount() {
		return account;
	}
	
	public ItemStack getAccountItem() {
		return NoxItemUtil.addLore(getAccount().getIdentifiableItem(), 0,
				ChatColor.GOLD
						+ "Tax: "
						+ ChatColor.AQUA
						+ (isTaxPercentBased() ?
								getMemberTax() + "%" : VaultAdapter.economy
										.format(getMemberTax())));
	}
	
	public String getAccountName() {
		return account.getAccountName();
	}
	
	public double getBalance() {
		return account.getBalance();
	}
	
	public UUID getID() {
		return guildID;
	}
	
	public Set<GuildPlayer> getInvites() {
		return Collections.unmodifiableSet(invitedPlayers);
	}
	
	@Override
	public int getMaxPlots() {
		return 0;// TODO
	}
	
	public Set<GuildPlayer> getMembers() {
		final Set<GuildPlayer> ret = new HashSet<GuildPlayer>();
		final GuildPlayerManager gpm = GuildPlayerManager.getInstance();
		
		for (final UUID id : members) {
			ret.add(gpm.getPlayer(id));
		}
		
		return ret;
	}
	
	public ItemStack getMembersItem() {
		final Set<String> members = new HashSet<String>();
		int i = 0;
		
		for (final UUID id : getMemberUIDs()) {
			if (i >= 10) {
				members.add(ChatColor.GOLD + "...");
				break;
			}
			
			members.add(ChatColor.AQUA + Bukkit.getOfflinePlayer(id).getName());
			i++;
		}
		
		return new ItemBuilder(Material.SKULL_ITEM, getMemberUIDs().size(),
				(short) 3)
				.setName(
						ChatColor.GOLD + "Members: " + ChatColor.AQUA
								+ getMemberUIDs().size())
				.setLore(members)
				.build();
	}
	
	public Set<UUID> getMemberUIDs() {
		return Collections.unmodifiableSet(members);
	}
	
	public String getName() {
		return guildName;
	}
	
	public Set<GuildPlayer> getOnlineMembers() {
		final Set<GuildPlayer> ret = new HashSet<GuildPlayer>();
		
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
		return GuildPlayerManager.getInstance().getPlayer(ownerID);
	}
	
	public UUID getPersistentID() {
		return getID();
	}
	
	public String getPersistentStringID() {
		return getPersistentID().toString();
	}
	
	public Set<GuildRank> getRanks() {
		return Collections.unmodifiableSet(ranks);
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
	
	public boolean hasKingdom() {
		final KingdomManager km = KingdomManager.getInstance();
		for (final Kingdom k : km.getLoadedMap().values())
			if (k.getGuildIDs().contains(guildID))
				return true;
		
		return false;
	}
	
	public boolean hasMember(GuildPlayer player) {
		return getMemberUIDs().contains(player.getUID());
	}
	
	public boolean hasMember(UUID player) {
		return getMemberUIDs().contains(player);
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
	
	public void invite(final GuildPlayer player) {
		getChatChannel().broadcast(
				new FanceeMessage(NoxGuildLocale.GUILD_INVITED_PLAYER
						.get(player)));
		
		final List<QuestionOption> options = new ArrayList<QuestionOption>();
		options.add(new QuestionOption("Join", "guild join " + getName()));
		
		new Question(NoxGuildLocale.PLAYER_INVITED_GUILD.get(this), options,
				player.getPlayer()).send();
		
		invitedPlayers.add(player);
		
		int i = 0;
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
	
	public boolean isInvited(GuildPlayer object) {
		return invitedPlayers.contains(object);
	}
	
	public void load() {
		// Nothing not done with deserialization yet
		return;
	}
	
	public boolean pay(double amount) {
		return account.pay(amount);
	}
	
	public boolean removeMember(GuildPlayer kicked) {
		return members.remove(kicked.getUID());
	}
	
	public boolean removeRank(GuildRank rank) {
		return ranks.remove(rank);
	}
	
	public void save() {
		// Nothing not done with serialization yet
		return;
	}
	
	// Serialize
	@Override
	public Map<String, Object> serialize() {
		final Map<String, Object> data = new HashMap<String, Object>();
		
		data.put(NODE_ID, guildID.toString());
		data.put(NODE_GUILD_NAME, guildName);
		data.put(NODE_OWNERID, ownerID.toString());
		data.put(NODE_GUILD_TAG, guildTag);
		
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
		
		return data;
	}
	
	public void setName(String newName) {
		if (newName == null || newName.length() < 3 || newName.length() > 20)
			return;
		
		for (final Guild g : GuildManager.getInstance().getLoadedMap().values())
			if (g.getName().equalsIgnoreCase(newName))
				return;
		
		guildName = newName;
	};
	
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
	
	private void instantiateNonSaves() {
		account = new BankAccount(guildID.toString());
		invitedPlayers = new HashSet<GuildPlayer>();
		lastInviteRunners = new HashMap<GuildPlayer, Integer>();
	}
}