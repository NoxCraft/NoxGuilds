package com.noxpvp.noxguilds.util;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

public class StringEncoder {
	
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	// Static fields
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	
	private static final String	entrySeparater		= ",";
	private static final String	keyValueSeparater	= ":";
	
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	// Instance Fields
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	
	Map<String, Object>			data;
	
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	// Constructors
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	
	public StringEncoder() {
		data = new HashMap<String, Object>();
	}
	
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	// Static Methods
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	
	public static Map<String, Object> decode(String encodedMap) {
		final Map<String, Object> ret = new HashMap<String, Object>();
		
		for (final String entry : encodedMap.split(entrySeparater)) {
			final String[] keyValueSet = entry.split(keyValueSeparater);
			
			if (keyValueSet == null || keyValueSet.length < 2) {
				continue;
			}
			
			ret.put(keyValueSet[0], keyValueSet[1]);
		}
		
		return ret;
	}
	
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	// Instance Methods
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	
	public String encode() {
		final StringBuilder ret = new StringBuilder();
		
		final Iterator<Entry<String, Object>> iterater = data.entrySet().iterator();
		
		while (iterater.hasNext()) {
			final Entry<String, Object> entry = iterater.next();
			
			ret.append(entry.getKey()).append(keyValueSeparater).append(
					entry.getValue());
			
			if (iterater.hasNext()) {
				ret.append(entrySeparater);
			}
		}
		
		return ret.toString();
	}
	
	public void put(String key, Object value) {
		data.put(key, value);
	}
	
	public boolean remove(String key) {
		return data.remove(key) != null;
	}
	
}
