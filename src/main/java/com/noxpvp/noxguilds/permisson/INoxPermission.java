package com.noxpvp.noxguilds.permisson;

import org.bukkit.permissions.PermissionDefault;

public interface INoxPermission {
	
	public PermissionDefault getDefault();
	
	public String getDescription();
	
	public String getName();
	
}
