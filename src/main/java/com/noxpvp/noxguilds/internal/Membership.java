package com.noxpvp.noxguilds.internal;

import java.util.Set;

public interface Membership<T> {
	
	/**
	 * Adds a member to this membership
	 * 
	 * @param joining
	 */
	public void addMember(T joining);
	
	/**
	 * Gets a list of invites
	 * 
	 * @return
	 */
	public Set<T> getInvites();
	
	/**
	 * Gets all members in this membership
	 * 
	 * @return Set<T> all members
	 */
	public Set<T> getMembers();
	
	/**
	 * Checks if the given member is apart of this membership
	 * 
	 * @param member
	 * @return True if already a member, otherwise false
	 */
	public boolean hasMember(T member);
	
	/**
	 * Invites an object to join
	 * 
	 * @param object
	 */
	public void invite(T object);
	
	/**
	 * Gets if this object has been invited
	 * 
	 * @param invited
	 * @return
	 */
	public boolean isInvited(T invited);
	
	/**
	 * Removes the given member from this membership if it is apart
	 * 
	 * @param kicked
	 * @return If the operation made any changes
	 */
	public boolean removeMember(T kicked);
	
	/**
	 * Uninvites an invited object, forever ruining its self-confidence
	 * 
	 * @param object
	 */
	public void uninvite(T object);
	
}
