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
package com.noxpvp.noxguilds.runners;

import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;

import com.noxpvp.noxguilds.NoxGuilds;

/**
 * @author ConnorStone
 * 
 */
public abstract class NoxRunnable extends BukkitRunnable {
	
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	// Static fields
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	// Instance Fields
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	
	protected int	id;
	protected int	tickTime;
	protected int	period;
	
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	// Constructors
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	
	public NoxRunnable() {
		this(0, 0);
	}
	
	public NoxRunnable(int tickTime, int period) {
		this.tickTime = tickTime;
		this.period = period;
	}
	
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	// Static Methods
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	// Instance Methods
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	
	public void safeCancel() {
		try {
			Bukkit.getScheduler().cancelTask(id);
			return;
		} catch (final Exception e) {
			e.printStackTrace();
		}
	}
	
	public NoxRunnable start() {
		return start(0);
	}
	
	public NoxRunnable start(int delay) {
		id = period > 0 ? runTaskTimer(NoxGuilds.getInstance(), delay, period).getTaskId() : runTaskLater(
			NoxGuilds.getInstance(), delay).getTaskId();
		
		if (tickTime > 0) {
			new BukkitRunnable() {
				
				public void run() {
					NoxRunnable.this.stop();
				}
			}.runTaskLater(NoxGuilds.getInstance(), tickTime);
		}
		
		return this;
	}
	
	public void stop() {
		safeCancel();
	}
	
}
