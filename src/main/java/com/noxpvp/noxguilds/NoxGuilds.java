package com.noxpvp.noxguilds;

import java.util.logging.Level;

import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.configuration.serialization.ConfigurationSerialization;
import org.bukkit.inventory.ItemStack;

import se.ranzdo.bukkit.methodcommand.CommandHandler;

import com.bergerkiller.bukkit.common.ModuleLogger;
import com.bergerkiller.bukkit.common.config.FileConfiguration;
import com.bergerkiller.bukkit.common.internal.PermissionHandler;
import com.noxpvp.noxguilds.commands.GuildCommands;
import com.noxpvp.noxguilds.gui.internal.ItemRepresentable;
import com.noxpvp.noxguilds.guild.Guild;
import com.noxpvp.noxguilds.guild.GuildRank;
import com.noxpvp.noxguilds.guild.GuildZone;
import com.noxpvp.noxguilds.guildplayer.GuildPlayer;
import com.noxpvp.noxguilds.internal.NoxPlugin;
import com.noxpvp.noxguilds.kingdom.Kingdom;
import com.noxpvp.noxguilds.kingdom.KingdomRank;
import com.noxpvp.noxguilds.listeners.LoginListener;
import com.noxpvp.noxguilds.listeners.LogoutListener;
import com.noxpvp.noxguilds.locale.NoxGuildLocale;
import com.noxpvp.noxguilds.manager.GuildManager;
import com.noxpvp.noxguilds.manager.GuildPlayerManager;
import com.noxpvp.noxguilds.manager.KingdomManager;
import com.noxpvp.noxguilds.permisson.PermissionCell;
import com.noxpvp.noxguilds.territory.Area;

public class NoxGuilds extends NoxPlugin implements ItemRepresentable {
	
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	// Static Fields
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	
	private static NoxGuilds	instance;
	
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	// Instance Fields
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	
	private PermissionHandler	permHandler;
	private CommandHandler	  commandHandler;
	private ModuleLogger	  pluginLogger;
	
	// Commands
	GuildCommands	          guildCommands;
	
	// Listeners
	private LoginListener	  loginListener;
	private LogoutListener	  logoutListener;
	
	// Economy
	private BankAccount	      serverAccount;
	
	public static NoxGuilds getInstance() {
		return instance;
	}
	
	@Override
	public boolean command(CommandSender arg0, String arg1, String[] arg2) {
		return false;
	}
	
	@Override
	public void disable() {
		getMainConfig().save();
		
		GuildPlayerManager.getInstance().unloadAndSaveAll();
		GuildManager.getInstance().unloadAndSaveAll();
		KingdomManager.getInstance().unloadAndSaveAll();
		
	}
	
	@Override
	public void enable() {
		instance = this;
		permHandler = new PermissionHandler();
		commandHandler = new CommandHandler(instance);
		
		VaultAdapter.load();
		registerCommands();
		loadGuildsSettings();
		
		for (final Class<? extends ConfigurationSerializable> clazz : getSerialiables()) {
			ConfigurationSerialization.registerClass(clazz);
		}
		
		// Managers
		KingdomManager.setup();
		GuildManager.setup();
		GuildPlayerManager.setup();
		
		setupListeners();
		
	}
	
	public CommandHandler getCommandHandler() {
		return commandHandler;
	}
	
	public ItemStack getIdentifiableItem() {
		return new ItemStack(Material.WORKBENCH);
	}
	
	public FileConfiguration getMainConfig() {
		final FileConfiguration config = new FileConfiguration(instance,
		        "config.yml");
		
		if (config.exists()) {
			config.load();
		}
		
		return config;
	}
	
	@Override
	public NoxGuilds getNoxGuilds() {
		return getInstance();
	}
	
	@Override
	public PermissionHandler getPermissionHandler() {
		return permHandler;
		
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Class<? extends ConfigurationSerializable>[] getSerialiables() {
		return new Class[] {
		        Area.class,
		        GuildZone.class,
		        
		        GuildRank.class,
		        KingdomRank.class,
		        
		        PermissionCell.class,
		        
		        GuildPlayer.class,
		        Guild.class,
		        Kingdom.class };
	}
	
	public BankAccount getServerAcount() {
		return serverAccount;
	}
	
	public void loadGuildsSettings() {
		Settings.setup();
		serverAccount = new BankAccount(Settings.economyServerAccount);
	}
	
	@Override
	public void localization() {
		loadLocales(NoxGuildLocale.class);
		
	}
	
	@Override
	public void log(Level l, String msg) {
		(pluginLogger != null ? pluginLogger
		        : (pluginLogger = new ModuleLogger("NoxGuilds")))
		        .log(l, msg);
	}
	
	public void registerCommands() {
		final CommandHandler ch = getCommandHandler();
		
		guildCommands = new GuildCommands();
		
		ch.registerCommands(guildCommands);
	}
	
	private void setupListeners() {
		loginListener = new LoginListener(instance);
		logoutListener = new LogoutListener(instance);
		
		loginListener.register();
		logoutListener.register();
	}
	
}
