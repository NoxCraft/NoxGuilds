package com.noxpvp.noxguilds.locale;

import org.bukkit.command.CommandSender;

public interface INoxLocale {
	
	/**
	 * Gets this locale parsed from the args given, if any.
	 * 
	 * If no args are passed the returned String will be the same as {@link #getDef()}
	 * 
	 * @param args
	 * @return String the parsed locale
	 */
	public String get(String... args);
	
	/**
	 * Gets the default unparsed value for this locale
	 * 
	 * @return String default value
	 */
	public String getDef();
	
	/**
	 * Gets the name of this locale;
	 * 
	 * @return String name
	 */
	public String getName();
	
	/**
	 * Sends this locale to the given receiver with no arguments or parsing
	 * 
	 * @param the
	 *            receiver
	 * @return This instance
	 */
	public INoxLocale send(CommandSender receiver);
	
	/**
	 * Sends this locale to the given receiver parsed with the given arguments, if any
	 * 
	 * @param receiver
	 * @param args
	 * @return This instance
	 */
	public INoxLocale send(CommandSender receiver, String... args);
}
