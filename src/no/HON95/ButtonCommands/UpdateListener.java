package no.HON95.ButtonCommands;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;


public final class UpdateListener {

	private static final String PAGE = "http://hon95.koding.com/Bukkit/Version";

	private final JavaPlugin PLUGIN;
	private final String CV;
	private volatile String LV = "Checking...";
	private volatile boolean D = true;

	UpdateListener(final JavaPlugin plugin) {
		if (plugin == null)
			throw new IllegalArgumentException();

		PLUGIN = plugin;
		CV = plugin.getDescription().getVersion().trim();
	}

	@SuppressWarnings("deprecation")
	public void update() {
		
		Bukkit.getScheduler().scheduleAsyncDelayedTask(PLUGIN, new Runnable() {
			
			@Override
			public void run() {

				String lv = null;
				D = true;

				try {
					BufferedReader in = new BufferedReader(new InputStreamReader(new URL(PAGE).openStream()));
					while (true) {
						String li;
						String[] lp;
						li = in.readLine();
						if (li == null)
							break;
						if (!li.startsWith(PLUGIN.getName()))
							continue;
						lp = li.split(": ");
						if (lp.length == 2) {
							if (lp[0].trim().equalsIgnoreCase(PLUGIN.getName())) {
								lv = lp[1].trim();
								break;
							}
						}
					}
					in.close();
				} catch (Exception ex) {
					PLUGIN.getLogger().warning("Failed to check for updates: " + ex.getMessage());
					LV = "Unknown";
					return;
				}

				if (lv == null) {
					PLUGIN.getLogger().warning("Failed to check for updates: Didn't find plugin on file!");
					LV = "Unknown";
					return;
				}

				if (!CV.equalsIgnoreCase(lv)) {
					PLUGIN.getLogger().warning("Please update from " + CV + " to " + lv);
					D = false;
				}

				LV = lv;
			}
		});
	}

	public String getCurrentVersion() {
		return CV;
	}

	public String getLastVersion() {
		return LV;
	}

	public boolean isUpToDate() {
		return D;
	}
}
