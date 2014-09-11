package com.noxpvp.noxguilds.kingdom;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import org.bukkit.Bukkit;

import com.noxpvp.noxguilds.NoxGuilds;
import com.noxpvp.noxguilds.Settings;
import com.noxpvp.noxguilds.chat.ChannelKeeper;
import com.noxpvp.noxguilds.chat.FanceeMessage;
import com.noxpvp.noxguilds.chat.KingdomChannel;
import com.noxpvp.noxguilds.economy.Account;
import com.noxpvp.noxguilds.economy.BankAccount;
import com.noxpvp.noxguilds.gui.internal.ItemRepresentable;
import com.noxpvp.noxguilds.guild.Guild;
import com.noxpvp.noxguilds.guildplayer.GuildPlayer;
import com.noxpvp.noxguilds.internal.Membership;
import com.noxpvp.noxguilds.internal.Persistant;
import com.noxpvp.noxguilds.internal.RankKeeper;
import com.noxpvp.noxguilds.locale.NoxGuildLocale;
import com.noxpvp.noxguilds.manager.GuildManager;
import com.noxpvp.noxguilds.manager.KingdomManager;
import com.noxpvp.noxguilds.permisson.KingdomPermissionCell;
import com.noxpvp.noxguilds.permisson.PermissionCellKeeper;
import com.noxpvp.noxguilds.util.GuildUtil;

public abstract class BaseKingdom implements IKingdom, Persistant, RankKeeper<KingdomRank>,
	PermissionCellKeeper<KingdomPermissionCell>, Account, ChannelKeeper<KingdomChannel>, Membership<Guild>,
	ItemRepresentable {
	
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	// Static fields
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	
	// Serializers start
	public static final String	NODE_KINGDOM_ID			= "kingdom-id";
	public static final String	NODE_KINGDOM_NAME		= "kingdom-name";
	public static final String	NODE_KINGDOM_TAG		= "kingdom-tag";
	public static final String	NODE_KINGDOM_CAPITAL	= "capital-id";
	public static final String	NODE_KINGDOM_GUILDS		= "member-ids";
	public static final String	NODE_KINGDOM_ALLIES		= "kingdom-allies";
	public static final String	NODE_KINGDOM_ENEMIES	= "kingdom-enemies";
	public static final String	NODE_KINGDOM_RANKS		= "kingdom-ranks";
	// Serializers end
	
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	// Instance Fields
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	
	private UUID				id;
	private String				name;
	private UUID				capital;
	private String				tag;
	
	private Set<UUID>			guilds;
	private Set<UUID>			allies;
	private Set<UUID>			enemies;
	private Set<KingdomRank>	ranks;
	
	private BankAccount			account;
	private Set<Guild>			invitedGuilds;
	private Map<Guild, Integer>	lastInviteRunners;
	
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	// Constructors
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	
	// Deserialize
	@SuppressWarnings("unchecked")
	public BaseKingdom(Map<String, Object> data) {
		Object getter;
		
		if ((getter = data.get(NODE_KINGDOM_ID)) != null && getter instanceof String) {
			id = UUID.fromString((String) getter);
		} else
			return;
		
		if ((getter = data.get(NODE_KINGDOM_NAME)) != null && getter instanceof String) {
			name = (String) getter;
		} else
			throw new NullPointerException("Could not get kingdom name from save");
		
		if ((getter = data.get(NODE_KINGDOM_CAPITAL)) != null && getter instanceof String) {
			capital = UUID.fromString((String) getter);
		}
		
		if ((getter = data.get(NODE_KINGDOM_TAG)) != null && getter instanceof String) {
			tag = (String) getter;
		} else {
			tag = Settings.defaultKingdomTag;
		}
		
		if ((getter = data.get(NODE_KINGDOM_ALLIES)) != null && getter instanceof Set<?>) {
			final Set<UUID> ids = new HashSet<UUID>();
			for (final String id : (Set<String>) getter) {
				ids.add(UUID.fromString(id));
			}
			
			allies = new HashSet<UUID>(ids);
		} else {
			allies = new HashSet<UUID>();
		}
		
		if ((getter = data.get(NODE_KINGDOM_ENEMIES)) != null && getter instanceof Set<?>) {
			final Set<UUID> ids = new HashSet<UUID>();
			for (final String id : (Set<String>) getter) {
				ids.add(UUID.fromString(id));
			}
			
			enemies = new HashSet<UUID>(ids);
		} else {
			enemies = new HashSet<UUID>();
		}
		
		if ((getter = data.get(NODE_KINGDOM_GUILDS)) != null && getter instanceof Set<?>) {
			final Set<UUID> ids = new HashSet<UUID>();
			for (final String id : (Set<String>) getter) {
				ids.add(UUID.fromString(id));
			}
			
			guilds = new HashSet<UUID>(ids);
		} else {
			guilds = new HashSet<UUID>();
		}
		
		if ((getter = data.get(NODE_KINGDOM_RANKS)) != null && getter instanceof Set<?>) {
			ranks = (Set<KingdomRank>) getter;
		} else {
			ranks = new HashSet<KingdomRank>();
		}
		
		instantiateNonSaves();
	}
	
	public BaseKingdom(UUID capital, String name) {
		id = UUID.randomUUID();
		// this.name = checkKingdomName(name);
		this.name = name;// TODO name fashion checks
		this.capital = capital;
		tag = Settings.defaultKingdomTag;
		
		guilds = new HashSet<UUID>();
		guilds.add(capital);
		allies = new HashSet<UUID>();
		enemies = new HashSet<UUID>();
		ranks = new HashSet<KingdomRank>();
		
		instantiateNonSaves();
	}
	
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	// Instance Methods
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	
	public boolean addAlly(Kingdom ally) {
		return allies.add(ally.getID());
	}
	
	public boolean addEnemy(Kingdom enemy) {
		return enemies.add(enemy.getID());
	}
	
	public void addMember(Guild joining) {
		guilds.add(joining.getID());
		
	}
	
	public boolean addRank(KingdomRank rank) {
		return ranks.add(rank);
	}
	
	public boolean deposit(double amount) {
		return account.deposit(amount);
	}
	
	public String getAccountName() {
		return account.getAccountName();
	}
	
	public Set<Kingdom> getAllies() {
		final Set<Kingdom> ret = new HashSet<Kingdom>();
		final KingdomManager km = KingdomManager.getInstance();
		for (final UUID id : getAlliesIDs()) {
			ret.add(km.get(id));
		}
		
		return Collections.unmodifiableSet(ret);
	}
	
	public Set<UUID> getAlliesIDs() {
		return Collections.unmodifiableSet(allies);
	}
	
	public double getBalance() {
		return account.getBalance();
	}
	
	public Guild getCapital() {
		return GuildManager.getInstance().get(capital);
	}
	
	public Set<Kingdom> getEnemies() {
		final Set<Kingdom> ret = new HashSet<Kingdom>();
		final KingdomManager km = KingdomManager.getInstance();
		for (final UUID id : getEnemiesIDs()) {
			ret.add(km.get(id));
		}
		
		return Collections.unmodifiableSet(ret);
	}
	
	public Set<UUID> getEnemiesIDs() {
		return Collections.unmodifiableSet(enemies);
	}
	
	public Set<UUID> getGuildIDs() {
		return Collections.unmodifiableSet(guilds);
	}
	
	public Set<Guild> getGuilds() {
		final Set<Guild> ret = new HashSet<Guild>();
		final GuildManager gm = GuildManager.getInstance();
		for (final UUID id : getGuildIDs()) {
			ret.add(gm.get(id));
		}
		
		return Collections.unmodifiableSet(ret);
	}
	
	public UUID getID() {
		return id;
	}
	
	public Set<Guild> getInvites() {
		return Collections.unmodifiableSet(invitedGuilds);
	}
	
	public GuildPlayer getKing() {
		return getCapital().getOwner();
	}
	
	public Set<Guild> getMembers() {
		final Set<Guild> ret = new HashSet<Guild>();
		final GuildManager gm = GuildManager.getInstance();
		
		for (final UUID id : guilds) {
			ret.add(gm.get(id));
		}
		
		return Collections.unmodifiableSet(ret);
	}
	
	public String getName() {
		return name;
	}
	
	public UUID getPersistentID() {
		return getID();
	}
	
	public String getPersistentStringID() {
		return getPersistentID().toString();
	}
	
	public Set<KingdomRank> getRanks() {
		return Collections.unmodifiableSet(ranks);
	}
	
	public String getTag() {
		return tag;
	}
	
	public boolean hasMember(Guild member) {
		return guilds.contains(member.getID());
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
	
	public void invite(final Guild guild) {
		invitedGuilds.add(guild);
		guild.getChatChannel().broadcast(new FanceeMessage(NoxGuildLocale.KINGDOM_INVITED_GUILD.get(guild)));
		
		int i;
		if ((i = lastInviteRunners.get(guild)) != 0) {
			Bukkit.getScheduler().cancelTask(i);
		}
		
		lastInviteRunners.put(guild, Bukkit.getScheduler().runTaskLater(NoxGuilds.getInstance(), new Runnable() {
			
			public void run() {
				invitedGuilds.remove(guild);
				lastInviteRunners.remove(guild);
			}
			
		}, 20 * 30).getTaskId());
	}
	
	public boolean isInvited(Guild object) {
		return invitedGuilds.contains(object);
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
	
	public boolean removeMember(Guild kicked) {
		return guilds.remove(kicked.getID());
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
		data.put(NODE_KINGDOM_NAME, name);
		data.put(NODE_KINGDOM_CAPITAL, capital.toString());
		data.put(NODE_KINGDOM_TAG, tag);
		
		if (guilds.size() > 0) {
			data.put(NODE_KINGDOM_GUILDS, GuildUtil.getUUIDStrings(guilds));
		}
		
		if (allies.size() > 0) {
			data.put(NODE_KINGDOM_ALLIES, GuildUtil.getUUIDStrings(allies));
		}
		
		if (enemies.size() > 0) {
			data.put(NODE_KINGDOM_ENEMIES, GuildUtil.getUUIDStrings(enemies));
		}
		
		if (ranks.size() > 0) {
			data.put(NODE_KINGDOM_RANKS, ranks);
		}
		
		return data;
	}
	
	public void uninvite(Guild object) {
		int i;
		if ((i = lastInviteRunners.get(object)) != 0) {
			Bukkit.getScheduler().cancelTask(i);
		}
		
		invitedGuilds.remove(object);
	}
	
	private void instantiateNonSaves() {
		account = new BankAccount(id.toString());
		invitedGuilds = new HashSet<Guild>();
		lastInviteRunners = new HashMap<Guild, Integer>();
	}
	
}
