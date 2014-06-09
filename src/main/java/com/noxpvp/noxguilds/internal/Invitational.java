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
package com.noxpvp.noxguilds.internal;

import java.util.List;

/**
 * @author ConnorStone
 * 
 */
public interface Invitational<T> {
	
	/**
	 * Gets a list of invites
	 * 
	 * @return
	 */
	public List<T> getInvites();
	
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
	 * Uninvites an invited object, forever ruining its self-confidence
	 * 
	 * @param object
	 */
	public void uninvite(T object);
	
}
