package com.noxpvp.noxguilds.util;

import com.bergerkiller.bukkit.common.MessageBuilder;
import com.noxpvp.noxguilds.locale.NoxGuildLocale;

public class NoxMessageBuilder extends MessageBuilder {
	
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	// Constructors
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	
	public NoxMessageBuilder() {
		this(true);
	}
	
	public NoxMessageBuilder(boolean startWithHeader) {
		super();
		
		if (startWithHeader) {
			append(NoxGuildLocale.CHAT_HEADER_START.get()).newLine();
		}
		
	}
	
	public NoxMessageBuilder(String context) {
		super();
		
		append(NoxGuildLocale.CHAT_HEADER_START_SPECIFIC.get(context)).newLine();
	}
	
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	// Instance Methods
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	
}
