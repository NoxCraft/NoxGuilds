package com.noxpvp.noxguilds;

import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.configuration.serialization.ConfigurationSerialization;
import org.bukkit.inventory.ItemStack;

import se.ranzdo.bukkit.methodcommand.CommandHandler;

import com.bergerkiller.bukkit.common.ModuleLogger;
import com.bergerkiller.bukkit.common.config.FileConfiguration;
import com.bergerkiller.bukkit.common.internal.PermissionHandler;
import com.noxpvp.noxguilds.chat.ChannelKeeper;
import com.noxpvp.noxguilds.chat.ServerChannel;
import com.noxpvp.noxguilds.commands.GuildCommands;
import com.noxpvp.noxguilds.economy.BankAccount;
import com.noxpvp.noxguilds.event.CommandEvent;
import com.noxpvp.noxguilds.gui.internal.ItemRepresentable;
import com.noxpvp.noxguilds.guild.Guild;
import com.noxpvp.noxguilds.guild.GuildRank;
import com.noxpvp.noxguilds.guildplayer.GuildPlayer;
import com.noxpvp.noxguilds.internal.NoxPlugin;
import com.noxpvp.noxguilds.kingdom.Kingdom;
import com.noxpvp.noxguilds.kingdom.KingdomRank;
import com.noxpvp.noxguilds.land.Area;
import com.noxpvp.noxguilds.land.Zone;
import com.noxpvp.noxguilds.listeners.LoginListener;
import com.noxpvp.noxguilds.listeners.LogoutListener;
import com.noxpvp.noxguilds.listeners.PlayerMoveListener;
import com.noxpvp.noxguilds.locale.NoxGuildLocale;
import com.noxpvp.noxguilds.manager.GuildManager;
import com.noxpvp.noxguilds.manager.GuildPlayerManager;
import com.noxpvp.noxguilds.manager.KingdomManager;
import com.noxpvp.noxguilds.manager.PlotManager;
import com.noxpvp.noxguilds.permisson.PermissionCell;

public class NoxGuilds extends NoxPlugin implements ItemRepresentable, ChannelKeeper<ServerChannel> {
	
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	// Static Fields
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	
	private static NoxGuilds	instance;
	
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	// Instance Fields
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	
	private PermissionHandler	permHandler;
	private CommandHandler		commandHandler;
	private ModuleLogger		pluginLogger;
	
	// Chat
	ServerChannel				globalChat;
	
	// Commands
	GuildCommands				guildCommands;
	
	// Listeners
	private LoginListener		loginListener;
	private LogoutListener		logoutListener;
	private PlayerMoveListener	moveListener;
	
	// Economy
	private BankAccount			serverAccount;
	
	public static NoxGuilds getInstance() {
		return instance;
	}
	
	@Override
	public boolean command(CommandSender arg0, String arg1, String[] arg2) {
		Bukkit.getPluginManager().callEvent(new CommandEvent(arg0, arg1, arg2));
		
		return true;
	}
	
	@Override
	public void disable() {
		getMainConfig().save();
		
		KingdomManager.getInstance().unloadAndSaveAll();
		GuildManager.getInstance().unloadAndSaveAll();
		GuildPlayerManager.getInstance().unloadAndSaveAll();
		PlotManager.getInstance().unloadAndSaveAll();
		
	}
	
	@Override
	public void enable() {
		instance = this;
		permHandler = new PermissionHandler();
		commandHandler = new CommandHandler(instance);
		globalChat = new ServerChannel();
		
		VaultAdapter.load();
		registerCommands();
		loadGuildsSettings();
		
		for (final Class<? extends ConfigurationSerializable> clazz : getSerialiables()) {
			ConfigurationSerialization.registerClass(clazz);
		}
		
		// Managers
		PlotManager.setup();
		GuildPlayerManager.setup();
		GuildManager.setup();
		KingdomManager.setup();
		
		setupListeners();
		
	}
	
	public ServerChannel getChatChannel() {
		return globalChat;
	}
	
	public CommandHandler getCommandHandler() {
		return commandHandler;
	}
	
	public ItemStack getIdentifiableItem() {
		return new ItemStack(Material.WORKBENCH);
	}
	
	public FileConfiguration getMainConfig() {
		final FileConfiguration config = new FileConfiguration(instance, "config.yml");
		
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
		return new Class[] { Area.class, Zone.class,
		
		GuildRank.class, KingdomRank.class,
		
		PermissionCell.class,
		
		GuildPlayer.class, Guild.class, Kingdom.class };
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
		loadLocales(NoxGuildLocale.class);
		
	}
	
	@Override
	public void log(Level l, String msg) {
		(pluginLogger != null ? pluginLogger : (pluginLogger = new ModuleLogger("NoxGuilds"))).log(l, msg);
	}
	
	public void registerCommands() {
		final CommandHandler ch = getCommandHandler();
		
		guildCommands = new GuildCommands();
		
		ch.registerCommands(guildCommands);
	}
	
	private void setupListeners() {
		loginListener = new LoginListener(instance);
		logoutListener = new LogoutListener(instance);
		moveListener = new PlayerMoveListener(instance);
		
		loginListener.register();
		logoutListener.register();
		moveListener.register();
	}
	
}
