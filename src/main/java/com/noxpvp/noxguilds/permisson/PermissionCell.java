package com.noxpvp.noxguilds.permisson;

import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.bukkit.configuration.serialization.ConfigurationSerializable;

import com.bergerkiller.bukkit.common.utils.LogicUtil;
import com.noxpvp.noxguilds.access.AccessLevel;
import com.noxpvp.noxguilds.access.IPermissionType;
import com.noxpvp.noxguilds.gui.internal.ItemRepresentable;

public abstract class PermissionCell<A extends AccessLevel<?, ?, ?>, PT extends Enum<?> & IPermissionType<PT>>
        implements ConfigurationSerializable, ItemRepresentable {
	
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	// Static Fields
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	
	private static final String	     NODE_PERMS_KEY	= "permissions";
	
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	// Instance Fields
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	
	private Map<A, Map<PT, Boolean>>	cell;
	
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	// Constructors
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	
	public PermissionCell() {
		this.cell = new HashMap<A, Map<PT, Boolean>>();
	}
	
	// Deserialize
	@SuppressWarnings("unchecked")
	public PermissionCell(Map<String, Object> serMap) {
		Object getter;
		if ((getter = serMap.containsKey(NODE_PERMS_KEY)) != null
		        && getter instanceof Map) {
			this.cell = (HashMap<A, Map<PT, Boolean>>) serMap
			        .get(NODE_PERMS_KEY);
		} else {
			this.cell = new HashMap<A, Map<PT, Boolean>>();
		}
	}
	
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	// Instance Methods
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	
	/**
	 * Clears all set perms for all access levels in the cell
	 * 
	 */
	public void clearPerms() {
		cell.clear();
	}
	
	/**
	 * Clears all perms for a given access level
	 * 
	 * @param level
	 */
	public void clearPerms(A level) {
		if (cell.containsKey(level)) {
			cell.remove(level);
		}
	}
	
	/**
	 * Gets the boolean perm set for the given type in this permission cell
	 * 
	 * @param type
	 * @return
	 */
	public boolean get(A level, PT type) {
		final Map<PT, Boolean> map = cell.get(level);
		if (LogicUtil.nullOrEmpty(map))
			return false;
		
		final Iterator<PT> perms = map.keySet().iterator();
		
		// Place holders
		boolean parentValue = false;
		PT parentType = null;
		
		// Iterate through current set perms
		while (perms.hasNext()) {
			final PT cur = perms.next();
			
			// If equal, return set value
			if (cur.equals(type))
				return map.get(cur);
			
			// Else if the given type is a member of the current type, and
			// more specific than another found parent, set the value of
			// parent
			else if (parentType == null || type.isChildOf(cur) ? cur
			        .isChildOf(parentType) : false) {
				parentValue = map.get(cur);
				parentType = cur;
			}
		}
		
		// Fall back to closest found parent if any was found, otherwise
		// always false
		return parentType != null ? parentValue : false;
	}
	
	public Map<A, Map<PT, Boolean>> getPerms() {
		return Collections.unmodifiableMap(cell);
	}
	
	/**
	 * Checks if a specific value has been set in the permission cell
	 * 
	 * @param level
	 * @param type
	 * @return True if it is, otherwise false
	 */
	public boolean isSet(A level, PT type) {
		return cell.get(level).get(type) != null;
	}
	
	/**
	 * Removes a specific permission from the cell
	 * 
	 * @param level
	 * @param type
	 */
	public void removePerm(A level, PT type) {
		Map<PT, Boolean> getter = null;
		
		if ((getter = cell.get(level)) != null) {
			getter.remove(type);
		}
		
	}
	
	public Map<String, Object> serialize() {
		final Map<String, Object> serMap = new HashMap<String, Object>();
		
		serMap.put(NODE_PERMS_KEY, cell);
		
		return serMap;
	}
	
	public void set(A level, PT type, boolean value) {
		Map<PT, Boolean> levelGet = null;
		
		if ((levelGet = cell.get(level)) != null) {
			levelGet.put(type, value);
		} else {
			final Map<PT, Boolean> newMap = new HashMap<PT, Boolean>();
			newMap.put(type, value);
			
			cell.put(level, newMap);
		}
		
	}
	
}
