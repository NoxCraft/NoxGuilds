package com.noxpvp.noxguilds.access;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import com.noxpvp.noxguilds.gui.internal.ItemRepresentable;
import com.noxpvp.noxguilds.guild.Guild;
import com.noxpvp.noxguilds.internal.NoxEnum;
import com.noxpvp.noxguilds.kingdom.Kingdom;
import com.noxpvp.noxguilds.util.ItemBuilder;
import com.noxpvp.noxguilds.util.NoxEnumUtil;

public enum KingdomAccessLevel
        implements
        AccessLevel<KingdomAccessLevel, Kingdom, Guild>,
        NoxEnum<KingdomAccessLevel>,
        ItemRepresentable {
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	// Static fields
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	// Instance Fields
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	
	OUTSIDER(
	        null,
	        "All guilds outside of this kingdom and allied kingdoms"),
	KINGDOM_ALLY(
	        OUTSIDER,
	        "Any guild inside this kingdom or an allied kingdom"),
	KINNGDOM_ENEMY(
	        OUTSIDER,
	        "Any guild in a kingdom declared an enemy by this kingdom or an allied kingdom");
	
	private KingdomAccessLevel	parent;
	private String	           desc;
	
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	// Constructors
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	
	private KingdomAccessLevel(KingdomAccessLevel parent,
	        String description) {
		this.parent = parent;
		desc = description;
	}
	
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	// Static Methods
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	
	public static KingdomAccessLevel getAccessLevel(Kingdom owner,
	        Guild object) {
		KingdomAccessLevel last = null;
		
		for (final KingdomAccessLevel cur : values()) {
			if (cur.filter(owner, object)) {
				if (last == null || cur.isChildOf(last)) {
					last = cur;
				}
			}
		}
		
		return last;
	};
	
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	// Instance Methods
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	
	public boolean filter(Kingdom owner, Guild object) {
		return false;
	}
	
	public String getDescription() {
		return desc;
	}
	
	public ItemStack getIdentifiableItem() {
		return new ItemBuilder(Material.BOOKSHELF, 1)
		        .setName(
		                ChatColor.AQUA + NoxEnumUtil.getFriendlyName(this))
		        .setLore(ChatColor.GOLD + getDescription())
		        .build();
	}
	
	public KingdomAccessLevel getParent() {
		return parent;
	}
	
	public boolean isChildOf(KingdomAccessLevel parent) {
		return NoxEnumUtil.isChildOf(this, parent);
	}
	
	public boolean isParentOf(KingdomAccessLevel child) {
		return NoxEnumUtil.isParentOf(this, child);
	}
	
}
