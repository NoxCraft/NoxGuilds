package com.noxpvp.noxguilds;

import com.bergerkiller.bukkit.common.config.FileConfiguration;
import com.bergerkiller.bukkit.common.utils.StringUtil;

public class Settings {
	
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	// Static fields
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	
	// Nodes start
	private static final String	ECNMY								= "economy",
																	PRICE = "price",
			REFUND = "refund",
			NEW = "new",
			CLM = "claim",
			TRTRY = "territory",
			UCLM = "unclaim",
			GLD = "guild",
			KGDM = "kingdom",
			TAXES = "taxes",
			SRV = "server",
			DFLT = "default",
			ISPRCNT = "is-percent-based",
			PERM = "perimission";
	
	private static final String	ECONOMY_USE_ECONOMY					= ECNMY
																			+ ".economy-enabled";
	private static final String	ECONOMY_USE_SERVER_ACCOUNT			= ECNMY
																			+ "use-server-account";
	private static final String	ECONOMY_SERVER_ACCOUNT				= ECNMY
																			+ ".server-account";
	private static final String	ECONOMY_PRICE_KINGDOM				= StringUtil
																			.join(".",
																					ECNMY,
																					PRICE,
																					NEW,
																					KGDM);
	private static final String	ECONOMY_PRICE_GUILD					= StringUtil
																			.join(".",
																					ECNMY,
																					PRICE,
																					NEW,
																					GLD);
	private static final String	ECONOMY_PRICE_CLAIM_TERRITORY		= StringUtil
																			.join(".",
																					ECNMY,
																					PRICE,
																					CLM,
																					TRTRY);
	private static final String	ECONOMY_REFUND_UNCLAIM_TERRITORY	= StringUtil
																			.join(".",
																					ECNMY,
																					REFUND,
																					UCLM,
																					TRTRY);
	
	private static final String	ECONOMY_TAXES_SERVER_PERCENTBASED	= StringUtil
																			.join(".",
																					ECNMY,
																					TAXES,
																					SRV,
																					ISPRCNT);
	private static final String	ECONOMY_TAXES_KINGDOM				= StringUtil
																			.join(".",
																					ECNMY,
																					TAXES,
																					SRV,
																					KGDM);
	private static final String	ECONOMY_TAXES_GUILD					= StringUtil
																			.join(".",
																					ECNMY,
																					TAXES,
																					SRV,
																					GLD);
	
	private static final String	NEWWORLD_USING_GUILDS				= StringUtil
																			.join(".",
																					"new-world",
																					"using-noxguilds");
	
	private static final String	SERVER_MAX_KINGDOMS					= StringUtil
																			.join(".",
																					SRV,
																					"max",
																					KGDM);
	private static final String	SERVER_MAX_GULIDS					= StringUtil
																			.join(".",
																					SRV,
																					"max",
																					GLD);
	
	private static final String	KINGDOM_DEFAULT_OPEN				= StringUtil
																			.join(".",
																					KGDM,
																					DFLT,
																					"open");
	private static final String	KINGDOM_DEFAULT_TAG					= StringUtil
																			.join(".",
																					KGDM,
																					DFLT,
																					"tag");
	private static final String	KINGDOM_DEFAULT_TAXES				= StringUtil
																			.join(".",
																					ECNMY,
																					TAXES,
																					DFLT,
																					KGDM);
	private static final String	KINGDOM_DEFAULT_TAXES_PERCENTBASED	= StringUtil
																			.join(".",
																					ECNMY,
																					TAXES,
																					DFLT,
																					KGDM,
																					ISPRCNT);
	private static final String	KINGDOM_DEFAULT_FRIENDLYFIRE		= StringUtil
																			.join(".",
																					KGDM,
																					DFLT,
																					"friendlyfire");
	
	private static final String	GUILD_DEFAULT_OPEN					= StringUtil
																			.join(".",
																					GLD,
																					DFLT,
																					"open");
	private static final String	GUILD_DEFAULT_TAG					= StringUtil
																			.join(".",
																					GLD,
																					DFLT,
																					"tag");
	private static final String	GUILD_DEFAULT_TAXES					= StringUtil
																			.join(".",
																					GLD,
																					DFLT,
																					TAXES);
	private static final String	GUILD_DEFAULT_TAXES_PERCENTBASED	= StringUtil
																			.join(".",
																					GLD,
																					DFLT,
																					TAXES
																							+ ISPRCNT);
	private static final String	GUILD_DEFAULT_FRIENDLYFIRE			= StringUtil
																			.join(".",
																					GLD,
																					DFLT,
																					"friendlyfire");
	
	// Nodes end
	
	// Settings
	
	// New world settings
	public static Boolean		usingNoxGuilds;
	
	// Economy
	public static boolean		economyEnabled;
	public static boolean		economyUseServerAccount;
	public static String		economyServerAccount;
	public static double		economyPriceKingdom;
	public static double		economyPriceGuild;
	public static double		economyPriceClaimTerritory;
	public static double		economyRefundUnclaimTerritory;
	
	// Taxes
	public static double		serverTaxesTime;
	public static boolean		serverTaxesPercent;
	public static double		serverUpkeepKingdom;
	public static double		serverUpkeepGuild;
	
	// Kingdoms
	public static int			serverMaxKingdoms;
	public static String		defaultKingdomTag;
	public static Boolean		defaultKingdomOpen;
	public static double		defaultTaxesKingdom;
	public static Boolean		defaultKingdomFriendlyFire;
	public static Boolean		defaultTaxesKingdomPercent;
	
	// Guilds
	public static int			serverMaxGuilds;
	public static String		defaultGuildTag;
	public static double		defaultGuildTaxes;
	public static Boolean		defaultGuildTaxesPercent;
	public static Boolean		defaultGuildOpen;
	public static Boolean		defaultGuildFriendlyFire;
	public static int			territoryAllowanceRatio;
	public static int			territoryAllowanceMax;
	public static int			minDistanceBetweenGuilds;
	public static int			maxDistanceBetweenGuilds;
	
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	// Static Methods
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	
	public static void reload() {
		setup();
	}
	
	public static void setup() {
		final FileConfiguration config = NoxGuilds.getInstance()
				.getMainConfig();
		
		// Main
		config.setHeader
				(
				" _   _             _____       _ _     _     "
						+ "\n| \\ | |           |  __ \\     (_) |   | |    "
						+ "\n|  \\| | _____  __ | |  \\/_   _ _| | __| |___ "
						+ "\n| . ` |/ _ \\ \\/ / | | __| | | | | |/ _` / __|"
						+ "\n| |\\  | (_) >  <  | |_\\ \\ |_| | | | (_| \\__ \\"
						+ "\n\\_| \\_/\\___/_/\\_\\ \\____/\\__,_|_|_|\\__,_|___/"
						+ "\nThe configuration is split into 4 sections: Server, Economy, Kingdoms and Guilds"
						+ "\nPlease read headers of each option before changing and have fun!"
				);
		
		// TODO HEADERS
		
		// New world
		usingNoxGuilds = config.get(NEWWORLD_USING_GUILDS, Boolean.class,
				true);
		
		// Economy
		economyEnabled = config.get(ECONOMY_USE_ECONOMY, Boolean.class,
				true);
		economyUseServerAccount = config.get(ECONOMY_USE_SERVER_ACCOUNT,
				Boolean.class, true);
		economyServerAccount = config.get(ECONOMY_SERVER_ACCOUNT,
				String.class, "NoxGuildsBank");
		economyPriceKingdom = config.get(ECONOMY_PRICE_KINGDOM,
				Double.class, 50000.0);
		economyPriceGuild = config.get(ECONOMY_PRICE_GUILD, Double.class,
				10000.0);
		economyPriceClaimTerritory = config.get(
				ECONOMY_PRICE_CLAIM_TERRITORY, Double.class, 100.0);
		economyRefundUnclaimTerritory = config.get(
				ECONOMY_REFUND_UNCLAIM_TERRITORY, Double.class, 75.0);
		
		// Taxes - server
		serverTaxesPercent = config.get(ECONOMY_TAXES_SERVER_PERCENTBASED,
				Boolean.class, false);
		serverUpkeepKingdom = config.get(ECONOMY_TAXES_KINGDOM,
				Double.class, 2500.0);
		serverUpkeepGuild = config.get(ECONOMY_TAXES_GUILD, Double.class,
				1000.0);
		
		// Taxes - gameplay
		defaultTaxesKingdom = config.get(KINGDOM_DEFAULT_TAXES,
				Double.class, 500.0);
		defaultTaxesKingdomPercent = config.get(
				KINGDOM_DEFAULT_TAXES_PERCENTBASED, boolean.class, false);
		defaultGuildTaxes = config.get(GUILD_DEFAULT_TAXES, Double.class,
				250.0);
		defaultGuildTaxesPercent = config.get(
				GUILD_DEFAULT_TAXES_PERCENTBASED, boolean.class, false);
		
		// Kingdom
		serverMaxKingdoms = config.get(SERVER_MAX_KINGDOMS, Integer.class,
				10);
		defaultKingdomOpen = config.get(KINGDOM_DEFAULT_OPEN,
				Boolean.class, false);
		defaultKingdomTag = config
				.get(KINGDOM_DEFAULT_TAG, String.class,
						"&6Use &a/Kingdom Set Tag [message] &6to set your Kingdom tag");
		defaultKingdomFriendlyFire = config.get(
				KINGDOM_DEFAULT_FRIENDLYFIRE, Boolean.class, true);
		
		// Guild
		serverMaxGuilds = config
				.get(SERVER_MAX_GULIDS, Integer.class, 100);
		defaultGuildOpen = config.get(GUILD_DEFAULT_OPEN, Boolean.class,
				false);
		defaultGuildTag = config
				.get(GUILD_DEFAULT_TAG, String.class,
						"&6Use &a/Guild Set Tag [message] &6to set your Guild tag");
		defaultGuildFriendlyFire = config.get(GUILD_DEFAULT_FRIENDLYFIRE,
				Boolean.class, true);
		
		config.save();
	}
	
}
