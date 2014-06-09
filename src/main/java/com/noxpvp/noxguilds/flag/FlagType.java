package com.noxpvp.noxguilds.flag;

public enum FlagType {
	
	ALL(null),
	KINGDOM(ALL),
	KINGDOM_PVP(KINGDOM),
	ALLY_GUILD_SPAWN(KINGDOM),
	GUILD(ALL),
	GUILD_SPAWN(GUILD),
	GUILD_PVP(GUILD),
	EXPLOSIONS(GUILD),
	MOBSPAWNS(GUILD),
	TELEPORT(GUILD),
	FIRESPREAD(GUILD),
	ENDERMAN(GUILD),
	TRAMPLING(GUILD),
	PLAYER_TRAMPLING(TRAMPLING),
	MOB_TRAMPLING(TRAMPLING);
	
	public final FlagType	parent;
	
	private FlagType(FlagType parent) {
		this.parent = parent;
	}
	
	public FlagType getParent() {
		return parent;
	}
	
	public boolean hasParent() {
		return parent != null;
	}
	
	/**
	 * Returns true if the given type is a parent of this type
	 * 
	 * Special Cases: if the given type is equal according to
	 * {@link #equals(Object)} the return will be false
	 * 
	 * @param type
	 * @return True if the given type is a parent of this type
	 */
	public boolean isMemberOf(FlagType type) {
		if (type == null)
			return false;
		
		for (FlagType t = this; t != null; t = t.getParent()) {
			if (type.equals(t))
				return true;
		}
		
		return false;
	}
}
