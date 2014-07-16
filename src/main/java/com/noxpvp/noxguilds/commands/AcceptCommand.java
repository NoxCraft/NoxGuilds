package com.noxpvp.noxguilds.commands;

import org.bukkit.command.CommandSender;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;

import com.bergerkiller.bukkit.common.utils.StringUtil;
import com.noxpvp.noxguilds.NoxGuilds;
import com.noxpvp.noxguilds.chat.BaseChannel;
import com.noxpvp.noxguilds.event.CommandEvent;
import  com.noxpvp.noxguilds.listeners.NoxCommandListener;
import com.noxpvp.noxguilds.locale.NoxGuildFancyLocale;

public abstract class AcceptCommand extends NoxCommandListener<NoxGuilds> {
	
	//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	//Static fields
	//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	
	//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	//Instance Fields
	//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	
	private boolean valid = false;
	private long stamp = 0;
	private long time = 1000 * 30;
	
	protected String what;
	protected BaseChannel requesting;
	protected BaseChannel answering;

	//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	//Constructors
	//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

	public AcceptCommand(String whatToAccept, BaseChannel requesting, BaseChannel answering) {
		super(NoxGuilds.getInstance());
	}

	//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	//Static Methods
	//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	
	//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	//Instance Methods
	//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

	@EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = true)
	public boolean onCommand(CommandEvent event) {
		
		if (!isValid()) {
			unregister();
			return false;
		}
		
		if (!event.getCmd().getName().equalsIgnoreCase("accept")) {
			return false;
		}
		
		if (!StringUtil.join(" ", event.getArgs()).equalsIgnoreCase(what)) {
			return false;
		}
		
		return true;
	}

	private void update() {
		if (isExpired())
			valid = false;
	}
	
    public boolean isValid() {
    	update();
    	return valid;
    }
    
    public boolean isExpired() {
    	return (stamp + time) > System.currentTimeMillis(); 
    }
	
	public void send(CommandSender reciever) {
		register();
		valid = true;
		this.stamp = System.currentTimeMillis();
		
		NoxGuildFancyLocale.COMMAND_ACCEPT_REQUEST.send(reciever, what);		
	}
	
	public abstract void onAccept();
	
	public abstract void onDeny();
	
	public abstract void onExpire();
	
}
