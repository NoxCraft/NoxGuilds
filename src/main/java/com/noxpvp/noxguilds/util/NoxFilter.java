package com.noxpvp.noxguilds.util;

public abstract class NoxFilter<T> {
	
	/**
	 * Returns whether the implemented filter filtered this object
	 * 
	 * @param object
	 * @return filter result
	 */
	public abstract boolean isFiltered(T object);
	
}
