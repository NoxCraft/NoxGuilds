package com.noxpvp.noxguilds.locale;

import org.bukkit.command.CommandSender;

import com.bergerkiller.bukkit.common.localization.LocalizationEnum;
import com.noxpvp.noxguilds.util.MessageUtil;

public class NoxLocale extends LocalizationEnum {
	
	public NoxLocale(String name, String def) {
		super(name, def);
		
	}
	
	@Override
	public String get(String... args) {
		if (args.length > 0)
			return MessageUtil.parseArguments(MessageUtil
					.parseColor(getDefault()), args);
		
		return MessageUtil.parseColor(getDefault());
	}
	
	public NoxLocale send(CommandSender receiver) {
		MessageUtil.sendMessage(receiver, getDefault());
		
		return this;
	}
	
	public NoxLocale send(CommandSender receiver, String... args) {
		MessageUtil.sendMessage(receiver, get(args));
		
		return this;
	}
	
}
