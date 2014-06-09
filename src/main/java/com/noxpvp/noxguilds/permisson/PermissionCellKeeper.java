package com.noxpvp.noxguilds.permisson;

public interface PermissionCellKeeper<T extends PermissionCell<?, ?>> {
	
	/**
	 * Gets the {@link PermissionCell} that this holder contains
	 * 
	 * @return
	 */
	public T getPermissions();
	
}
