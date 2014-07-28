package com.noxpvp.noxguilds.kingdom;

import java.util.Map;
import java.util.UUID;

import com.noxpvp.noxguilds.internal.BaseRank;
import com.noxpvp.noxguilds.manager.KingdomManager;
import com.noxpvp.noxguilds.permisson.KingdomPermissionCell;

public class KingdomRank extends BaseRank<KingdomPermissionCell> {
	
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	// Instance Fields
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	
	private Kingdom					owner;
	private KingdomPermissionCell	perms;
	
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	// Constructors
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	
	public KingdomRank(Kingdom owner, String name) {
		super(UUID.randomUUID(), name);
		
		this.owner = owner;
		perms = new KingdomPermissionCell();
	}
	
	// Deserialize
	public KingdomRank(Map<String, Object> data) {
		super(data);
		
		Object getter;
		Kingdom temp = null;
		
		if ((getter = data.get("kingdom-id")) != null
				&& getter instanceof String) {
			temp = KingdomManager.getInstance().get(
					UUID.fromString((String) getter));
		}
		
		if (temp != null) {
			owner = temp;
		}
		
		if ((getter = data.get("permissions")) != null
				&& getter instanceof KingdomPermissionCell) {
			perms = (KingdomPermissionCell) getter;
		} else {
			perms = new KingdomPermissionCell();
		}
		
	}
	
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	// Instance Methods
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	
	public Kingdom getOwner() {
		return owner;
	}
	
	// Permission keeper
	public KingdomPermissionCell getPermissions() {
		return perms;
	};
	
	@Override
	public Map<String, Object> serialize() {
		final Map<String, Object> data = super.serialize();
		
		data.put("kingdom-id", owner.getID().toString());
		if (perms.getPerms().size() > 0) {
			data.put("permissions", perms);
		}
		
		return data;
	}
	
}
