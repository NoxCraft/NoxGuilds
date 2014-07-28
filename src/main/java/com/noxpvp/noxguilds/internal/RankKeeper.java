package com.noxpvp.noxguilds.internal;

import java.util.Set;
import java.util.UUID;

public interface RankKeeper<T extends BaseRank<?>> {
	
	public boolean addRank(T rank);
	
	public Set<T> getRanks();
	
	public boolean hasRank(T rank);
	
	public boolean hasRank(UUID rankID);
	
	public boolean removeRank(T rank);
	
}
