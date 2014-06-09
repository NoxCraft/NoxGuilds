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

package com.noxpvp.noxguilds.permisson;

import org.bukkit.permissions.PermissionDefault;

import com.noxpvp.noxguilds.internal.NoxPlugin;

public class NoxPermission implements INoxPermission {
	
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	// Instance Fields
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	
	private final NoxPermission[]	children;
	private final PermissionDefault	defaultPermission;
	private final String	        description;
	private final String	        name;
	private String	                node;
	private final String[]	        parents;
	private final NoxPlugin	        plugin;
	
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	// Constructors
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	
	public NoxPermission(NoxPlugin plugin, String node,
	        String description, PermissionDefault defaults) {
		this(plugin, node, description, defaults, new NoxPermission[0]);
	}
	
	public NoxPermission(NoxPlugin plugin, String node,
	        String description, PermissionDefault defaults,
	        NoxPermission... children) {
		this.plugin = plugin;
		if (!node.startsWith("noxguilds.")) {
			this.node = "nox." + node;
		} else {
			this.node = node;
		}
		
		name = this.node;
		this.children = children;
		parents = new String[1];
		this.description = description;
		defaultPermission = defaults;
		parents[0] = this.node.substring(0, this.node.lastIndexOf('.'))
		        + "*";
	}
	
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	// Instance Methods
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	
	public NoxPermission[] getChildren() {
		return children;
	}
	
	public PermissionDefault getDefault() {
		return defaultPermission;
	}
	
	public String getDescription() {
		return description;
	}
	
	public String getName() {
		return name;
	}
	
	public String[] getParentNodes() {
		return parents;
	}
	
	public NoxPlugin getPlugin() {
		return plugin;
	}
}
