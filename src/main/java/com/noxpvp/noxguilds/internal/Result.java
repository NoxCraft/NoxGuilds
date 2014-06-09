package com.noxpvp.noxguilds.internal;

import org.bukkit.command.CommandSender;

import com.bergerkiller.bukkit.common.utils.LogicUtil;
import com.noxpvp.noxguilds.util.MessageUtil;

public class Result {
	
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	// Static fields
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	// Instance Fields
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	
	private final boolean	result;
	private final String[]	messages;
	
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	// Constructors
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	
	public Result(boolean result, String... messages) {
		this.result = result;
		this.messages = messages;
	}
	
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	// Instance Methods
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	
	public String[] getMessages() {
		return messages;
	}
	
	public boolean isResult() {
		return result;
	}
	
	public void send(CommandSender receiver) {
		if (LogicUtil.nullOrEmpty(messages))
			return;
		
		MessageUtil.sendMessages(receiver, messages);
	}
}
