package com.noxpvp.noxguilds.access;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import com.noxpvp.noxguilds.gui.internal.ItemRepresentable;
import com.noxpvp.noxguilds.guild.Guild;
import com.noxpvp.noxguilds.guildplayer.GuildPlayer;
import com.noxpvp.noxguilds.internal.NoxEnum;
import com.noxpvp.noxguilds.util.GuildUtil;
import com.noxpvp.noxguilds.util.ItemBuilder;
import com.noxpvp.noxguilds.util.NoxEnumUtil;

public enum PlayerAccessLevel
	implements
	AccessLevel<PlayerAccessLevel, GuildPlayer, GuildPlayer>,
	NoxEnum<PlayerAccessLevel>,
	ItemRepresentable {
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	// Static fields
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	// Instance Fields
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	
	OUTSIDER(null, "Any player not accociated with this player through guild/kingdom ally or friendship"),
	ALLY(OUTSIDER, "Players in the same guild or a guild allied through a nation"),
	FRIEND(ALLY, "Players who the player has added to their friends list");
	
	private PlayerAccessLevel parent;
	private String desc;
	
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	// Constructors
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	
	private PlayerAccessLevel(PlayerAccessLevel parent, String description) {
	
		this.parent = parent;
		desc = description;
	}
	
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	// Static Methods
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	
	public static PlayerAccessLevel getAccessLevel(GuildPlayer owner, GuildPlayer object) {
	
		PlayerAccessLevel last = null;
		
		for (final PlayerAccessLevel cur : values()) {
			if (cur.filter(owner, object)) {
				if (last == null || cur.isChildOf(last)) {
					last = cur;
				}
			}
		}
		
		return last;
	}
	
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	// Instance Methods
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	
	public boolean filter(GuildPlayer owner, GuildPlayer object) {
	
		switch (this) {
			case OUTSIDER:
				if (!owner.hasGuild())
					return true;
				
				for (final Guild g : owner.getGuilds())
					if (GuildUtil.isAllyOf(g, object))
						return false;
				
				return true;
			case ALLY:
				if (!owner.hasGuild())
					return false;
				
				for (final Guild g : owner.getGuilds())
					if (GuildUtil.isAllyOf(g, object))
						return true;
				
				return false;
			case FRIEND:
				// TODO friends
				return false;
			default:
				return false;
		}
	}
	
	public String getDescription() {
	
		return desc;
	}
	
	public ItemStack getIdentifiableItem() {
	
		return new ItemBuilder(Material.BOOKSHELF, 1).setName(ChatColor.AQUA + NoxEnumUtil.getFriendlyName(this))
			.setLore(ChatColor.GOLD + getDescription()).build();
	}
	
	public PlayerAccessLevel getParent() {
	
		return parent;
	};
	
	public boolean isChildOf(PlayerAccessLevel parent) {
	
		return NoxEnumUtil.isChildOf(this, parent);
	}
	
	public boolean isParentOf(PlayerAccessLevel child) {
	
		return NoxEnumUtil.isParentOf(this, child);
	}
	
}
