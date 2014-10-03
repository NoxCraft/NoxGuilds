package com.noxpvp.noxguilds.kingdom;

import java.util.Set;
import java.util.UUID;

import com.noxpvp.noxguilds.guild.Guild;
import com.noxpvp.noxguilds.guildplayer.GuildPlayer;

public interface IKingdom {
	
	/**
	 * Adds an ally to the list of allies in this kingdom
	 * 
	 * @param ally
	 * @return if any change was made
	 */
	public boolean addAlly(Kingdom ally);
	
	/**
	 * Adds an enemy to the list of enemies in this kingdom
	 * 
	 * @param enemy
	 * @return if any change was made
	 */
	public boolean addEnemy(Kingdom enemy);
	
	/**
	 * Gets a list of kingdoms this kingdom has declared an ally
	 * 
	 * @return list of ally kingdoms
	 */
	public Set<Kingdom> getAllies();
	
	/**
	 * Gets the capital guild in this kingdom
	 * 
	 * @return the capital guild
	 */
	public Guild getCapital();
	
	/**
	 * Gets a list of kingdoms this kingdom has declared an enemy
	 * 
	 * @return list of enemiese
	 */
	public Set<Kingdom> getEnemies();
	
	/**
	 * Gets a list of guilds currently in this kingdom
	 * 
	 * @return list of guilds
	 */
	public Set<Guild> getGuilds();
	
	/**
	 * Gets the unique id of this kingdom
	 * 
	 * @return the id
	 */
	public UUID getID();
	
	/**
	 * Gets the king of this kingdom
	 * 
	 * @return the king
	 */
	public GuildPlayer getKing();
	
	/**
	 * Gets the tag set for this kingdom
	 * 
	 * @return
	 */
	public String getTag();
	
	/**
	 * Returns the taxes set for this kingdom for its guilds
	 * 
	 * @return taxes
	 */
	public double getTaxes();
	
	/**
	 * Gets if this kingdom has friendly fire on
	 * 
	 * @return friendly fire
	 */
	public boolean isFriendlyFire();
	
	/**
	 * Gets if this kingdom is open and any guild may join without invitation
	 * 
	 * @return
	 */
	public boolean isOpen();
	
	/**
	 * Gets if this kingdoms taxes are percent based
	 * 
	 * @return if taxes are percent based
	 */
	public boolean isTaxesPercent();
	
	/**
	 * Removes the given kingdom as an ally to this kingdom if they are one currently
	 * 
	 * @param ally
	 * @return if any change was made
	 */
	public boolean removeAlly(Kingdom ally);
	
	/**
	 * Removes the given kingdom as an enemy to this kingdom if they are one currently
	 * 
	 * @param enemy
	 * @return if any change was made
	 */
	public boolean removeEnemy(Kingdom enemy);
	
}