package com.noxpvp.noxguilds.guild;

import java.util.List;
import java.util.UUID;

import org.bukkit.inventory.ItemStack;

import com.noxpvp.noxguilds.guildplayer.GuildPlayer;
import com.noxpvp.noxguilds.kingdom.Kingdom;

public interface IGuild {
	
	// Main Data
	
	/**
	 * Gets the unique id for this guild
	 * 
	 * @return {@link UUID} unique id
	 */
	public UUID getID();
	
	/**
	 * Gets the kingdoms this guilds belongs to, if any
	 * 
	 * @return
	 */
	public List<Kingdom> getKingdoms();
	
	/**
	 * Gets the list of {@link UUID} of players in this guild
	 * 
	 * @return {@link List} uuid list
	 */
	public List<UUID> getMembers();
	
	/**
	 * Gets the tax set for members is this guild by the mayor
	 * 
	 * @return tax
	 */
	public double getMemberTax();
	
	/**
	 * Returns the player changable name for this guild
	 * 
	 * @return {@link String} Name
	 */
	public String getName();
	
	/**
	 * Return a list of {@link GuildPlayer} in this guild who are online
	 * 
	 * @return
	 */
	public List<GuildPlayer> getOnlineMembers();
	
	/**
	 * Gets the single GuildPlayer owner of this guild
	 * 
	 * @return {@link GuildPlayer} Owner
	 */
	public GuildPlayer getOwner();
	
	/**
	 * Checks if this guild belongs to any kingdom
	 * 
	 * @return true if it does, otherwise false
	 */
	public boolean hasKingdom();
	
	/**
	 * Gets if the given {@link GuildPlayer} is a member of this guild
	 * 
	 * @param player
	 * @return true if the given player is a member of this guild,
	 *         otherwise false
	 */
	public boolean hasMember(GuildPlayer player);
	
	/**
	 * Gets if friendly fire is turned on for this guild
	 * 
	 * @return
	 */
	public boolean isFriendlyFire();
	
	/**
	 * Gets if this guild is open; anyone can join with out being invited
	 * first
	 * 
	 * @return open
	 */
	public boolean isOpen();
	
	// Members
	
	/**
	 * Gets if the tax in this guild is percent based
	 * 
	 * @return is percent based
	 */
	public boolean isTaxPercentBased();
	
	/**
	 * Set a new item badge to use when displaying this guild
	 * 
	 * @param badge
	 */
	public void setItemBadge(ItemStack badge);
	
	/**
	 * Sets a new name for this guild
	 * 
	 * @param newName
	 */
	public void setName(String newName);
	
}
