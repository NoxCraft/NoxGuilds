package com.noxpvp.noxguilds.gui.internal;

import java.util.List;
import java.util.UUID;

import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.entity.Player;

import com.noxpvp.noxguilds.guildplayer.GuildPlayer;

public interface IRank extends ConfigurationSerializable {
	
	/**
	 * Adds a guild player from the owning guild to this rank
	 * 
	 * @param newMember
	 * @return true if successful, otherwise false
	 */
	public boolean addMember(GuildPlayer newMember);
	
	/**
	 * Adds a player from the owning guild to this rank
	 * 
	 * @param newMember
	 * @return true if successful, otherwise false
	 */
	public boolean addMember(Player newMember);
	
	/**
	 * Adds a UUID from the owning guild to this rank
	 * 
	 * @param newMember
	 * @return true if successful, otherwise false
	 */
	public boolean addMember(UUID newMember);
	
	/**
	 * Return a list of guild players currently apart of this rank
	 * 
	 * @return
	 */
	public List<UUID> getMembers();
	
	/**
	 * Gets the name of this rank
	 * 
	 * @return
	 */
	public String getName();
	
	/**
	 * Checks if the given guild player is apart of this rank currently
	 * 
	 * @param player
	 * @return true if they are, otherwise false
	 */
	public boolean hasMember(GuildPlayer player);
	
	/**
	 * Checks if the given player is apart of this rank
	 * 
	 * @param player
	 * @return true if they are, otherwise false
	 */
	public boolean hasMember(Player player);
	
	/**
	 * Checks if the given uid is listed in this ranks members
	 * 
	 * @param playerID
	 * @return true if they are, otherwise false
	 */
	public boolean hasMember(UUID playerID);
	
	/**
	 * Removes a guild player from this rank, if they are currently apart
	 * of it
	 * 
	 * @param curMember
	 * @return true if successful, otherwise false
	 */
	public boolean removeMember(GuildPlayer curMember);
	
	/**
	 * Removes a player from this rank, if they are currently apart of it
	 * 
	 * @param curMember
	 * @return true if successful, otherwise false
	 */
	public boolean removeMember(Player curMember);
	
	/**
	 * Removes a UUID from this rank, if they are currently apart of it
	 * 
	 * @param curMember
	 * @return true if successful, otherwise false
	 */
	public boolean removeMember(UUID curMember);
	
	/**
	 * Sets the name of this rank, must be for than 1 char long and less
	 * than 17
	 * 
	 * @param newName
	 * @return true If name was successfully set, otherwise false
	 */
	public boolean setName(String newName);
}
