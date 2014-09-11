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
package com.noxpvp.noxguilds.util;

import org.apache.commons.lang.WordUtils;

import com.noxpvp.noxguilds.internal.NoxEnum;

/**
 * @author ConnorStone
 * 
 */
public class NoxEnumUtil {
	
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	// Static fields
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	// Instance Fields
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	// Constructors
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	// Static Methods
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	
	public static String getFriendlyName(Enum<?> e) {
		return WordUtils.capitalizeFully(e.name().replace("_", " ").toLowerCase());
	}
	
	public static boolean isChildOf(NoxEnum<?> child, NoxEnum<?> parent) {
		return isParentOf(parent, child);
	}
	
	public static boolean isParentOf(NoxEnum<?> parent, NoxEnum<?> child) {
		while (child.getParent() != null) {
			if (child.getParent().equals(parent))
				return true;
			
			child = (NoxEnum<?>) child.getParent();
		}
		
		return false;
	}
	
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	// Instance Methods
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
}
