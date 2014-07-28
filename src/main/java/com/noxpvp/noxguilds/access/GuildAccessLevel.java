/*
 * Copyright (c) 2014. NoxPVP.com
 *
 * All rights are reserved.
 *
 * You are not permitted to
 * 	Modify
 * 	Redistribute nor distribute
 * 	Sublicense
 *
 * You are required to keep this license header intact
 *
 * You are allowed to use this for non commercial purpose only. This does not allow any ad.fly type links.
 *
 * When using this you are required to
 * 	Display a visible link to noxpvp.com
 * 	For crediting purpose.
 *
 * For more information please refer to the license.md file in the root directory of repo.
 *
 * To use this software with any different license terms you must get prior explicit written permission from the copyright holders.
 */
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

/**
 * @author ConnorStone
 * 
 */
public enum GuildAccessLevel
		implements
		AccessLevel<GuildAccessLevel, Guild, GuildPlayer>,
		NoxEnum<GuildAccessLevel>,
		ItemRepresentable {
	
	OUTSIDER(
			null,
			"Any player not accociated with this guild or guilds of the same nation"),
	ALLY(
			OUTSIDER,
			"Players in guilds that have been declared allies through a common kingdom"),
	MEMBER(ALLY, "Players in this guild");
	
	private GuildAccessLevel	parent;
	private String				desc;
	
	private GuildAccessLevel(GuildAccessLevel parent, String description) {
		this.parent = parent;
		desc = description;
	}
	
	public static GuildAccessLevel getAccessLevel(Guild owner,
			GuildPlayer p) {
		GuildAccessLevel last = null;
		
		for (final GuildAccessLevel level : values()) {
			if (level.filter(owner, p))
				if (last == null || level.isChildOf(last)) {
					last = level;
				}
		}
		
		return last;
	}
	
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	// Static fields
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	// Instance Fields
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	// Constructors
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	// Static Methods
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	
	public boolean filter(Guild owner, GuildPlayer player) {
		switch (this) {
			case MEMBER:
				return owner.hasMember(player);
			case ALLY:
				return GuildUtil.isAllyOf(owner, player);
			case OUTSIDER:
				return !GuildUtil.isAllyOf(owner, player);
			default:
				return false;
		}
		
	}
	
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	// Instance Methods
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	
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
	
	public GuildAccessLevel getParent() {
		return parent;
	}
	
	public boolean isChildOf(GuildAccessLevel parent) {
		return NoxEnumUtil.isChildOf(this, parent);
	}
	
	public boolean isParentOf(GuildAccessLevel child) {
		return NoxEnumUtil.isParentOf(this, child);
	}
	
}
