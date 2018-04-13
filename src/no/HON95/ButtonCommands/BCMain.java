package no.HON95.ButtonCommands;

import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;


/**
 * ButtonCommands plugin for Bukkit.
 * 
 * @author HON95
 * @version 1.9
 * 
 */

public final class BCMain extends JavaPlugin {

	final PlayerInteractListener PIL = new PlayerInteractListener(this);
	final RedstoneListener REL = new RedstoneListener(this);
	final BlockBreakListener BBL = new BlockBreakListener(this);
	final SignChangeListener SCL = new SignChangeListener(this);
	final Commands CMD = new Commands(this);
	final ConfigClass CNF = new ConfigClass(this);

	boolean enable = false;
	boolean updateCheck = false;

	@Override
	public void onEnable() {

		CNF.load();

		if (!enable) {
			getLogger().warning("Plugin will be disabled!");
			getPluginLoader().disablePlugin(this);
			return;
		}

		PluginManager pm = Bukkit.getPluginManager();
		pm.registerEvents(PIL, this);
		pm.registerEvents(REL, this);
		pm.registerEvents(BBL, this);
		pm.registerEvents(SCL, this);

		getCommand("buttoncommands").setExecutor(CMD);
		getCommand("get").setExecutor(CMD);
		getCommand("invclear").setExecutor(CMD);
		getCommand("iteminfo").setExecutor(CMD);
		getCommand("chat").setExecutor(CMD);
		getCommand("creative").setExecutor(CMD);
		getCommand("survival").setExecutor(CMD);

		getServer().getScheduler().scheduleSyncRepeatingTask(this, new Runnable() {
			public void run() {
				REL.triggeredBlocks.clear();
			}
		}, 0, 1);

		if (updateCheck)
			new UpdateListener(this).update();
	}
}
