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

import org.bukkit.configuration.serialization.ConfigurationSerializable;

import com.bergerkiller.bukkit.common.Common;
import com.bergerkiller.bukkit.common.PluginBase;
import com.bergerkiller.bukkit.common.config.ConfigurationNode;
import com.bergerkiller.bukkit.common.internal.PermissionHandler;
import com.noxpvp.noxguilds.MasterReloader;
import com.noxpvp.noxguilds.NoxGuilds;
import com.noxpvp.noxguilds.permisson.NoxPermission;

public abstract class NoxPlugin extends PluginBase {
	
	public void addPermission(NoxPermission perm) {
		NoxGuilds.getInstance().addPermission(perm);
	}
	
	public void addPermissions(NoxPermission... perms) {
		NoxGuilds.getInstance().addPermissions(perms);
	}
	
	@Override
	public String getLocale(String path, String... args) {
		return getNoxGuilds().getLocale(path, args);
	}
	
	/**
	 * @return Core Master Reloader
	 */
	public final MasterReloader getMasterReloader() {
		return MasterReloader.getInstance();
	}
	
	@Override
	public int getMinimumLibVersion() {
		return Common.VERSION;
	}
	
	public abstract NoxGuilds getNoxGuilds();
	
	/**
	 * Gets a localization configuration node
	 * 
	 * @param path
	 *            of the node to get
	 * @return Localization configuration node
	 */
	public ConfigurationNode getNoxLocalizationNode(String path) {
		return getNoxGuilds().getLocalizationNode(path);
	}
	
	public abstract PermissionHandler getPermissionHandler();
	
	public abstract Class<? extends ConfigurationSerializable>[] getSerialiables();
	
	public final void register(Reloader reloader) {
		getMasterReloader().addModule(reloader);
	}
}
