package com.noxpvp.noxguilds.gui.internal;

import java.util.List;
import java.util.UUID;

public interface RankKeeper<T extends BaseRank> {
	
	public boolean addRank(T rank);
	
	public List<T> getRanks();
	
	public boolean hasRank(T rank);
	
	public boolean hasRank(UUID rankID);
	
	public boolean removeRank(T rank);
	
}
