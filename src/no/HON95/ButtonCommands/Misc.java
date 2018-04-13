package no.HON95.ButtonCommands;

import org.bukkit.Bukkit;
import org.bukkit.block.Block;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;


public final class Misc {

	public static String insertAll(String line, Player player, Block block) {

		line = insertServerVariables(line);
		line = insertBlockVariables(line, block);
		line = insertPlayerVariables(line, player);

		return line;
	}

	public static String insertAll(String line, Player player) {

		line = insertServerVariables(line);
		line = insertPlayerVariables(line, player);

		return line;
	}

	public static String insertAll(String line, CommandSender sender, Block block) {

		line = insertServerVariables(line);
		line = insertBlockVariables(line, block);
		line = line.replace("{N}", sender.getName());

		return line;
	}

	public static String insertServerVariables(String line) {

		line = line.replace("{SBV}", Bukkit.getServer().getBukkitVersion());
		line = line.replace("{SV}", Bukkit.getServer().getVersion());
		line = line.replace("{SIP}", (Bukkit.getServer().getIp() + "").replace("/", "").split(":")[0]);
		line = line.replace("{SIPP}", (Bukkit.getServer().getIp() + "").replace("/", ""));
		line = line.replace("{SP}", Bukkit.getServer().getPort() + "");
		line = line.replace("{SN}", Bukkit.getServer().getServerName());

		return line;
	}

	public static String insertBlockVariables(String line, Block block) {

		line = line.replace("{BW}", block.getWorld().getName());
		line = line.replace("{BX}", block.getX() + "");
		line = line.replace("{BY}", block.getY() + "");
		line = line.replace("{BZ}", block.getZ() + "");

		return line;
	}

	public static String insertPlayerVariables(String line, Player player) {

		line = line.replace("{N}", player.getName());
		line = line.replace("{PW}", player.getWorld().getName());
		line = line.replace("{DN}", player.getDisplayName());
		line = line.replace("{PX}", player.getLocation().getBlockX() + "");
		line = line.replace("{PY}", player.getLocation().getBlockY() + "");
		line = line.replace("{PZ}", player.getLocation().getBlockZ() + "");
		line = line.replace("{PIPP}", (player.getAddress() + "").replace("/", ""));
		line = line.replace("{PIP}", ((player.getAddress() + "").replace("/", "")).split(":")[0]);
		line = line.replace("{XP}", player.getExp() + "");
		line = line.replace("{GM}", player.getGameMode().name());
		line = line.replace("{ID}", player.getEntityId() + "");

		return line;
	}

	public static String[] concatCmd(String str) {
		if (!str.contains(" "))
			return new String[] { "", str };
		String[] strArr = str.split(" ", 2);
		if (strArr[0].equalsIgnoreCase("c") || strArr[0].equalsIgnoreCase("console")
				|| strArr[0].equalsIgnoreCase("a") || strArr[0].equalsIgnoreCase("alias"))
			return strArr;
		return new String[] { "", strArr[0] + " " + strArr[1] };
	}

	public static String[] concatCmd(String[] strArr) {
		StringBuilder cmdBuild = new StringBuilder(strArr[1]);
		for (int c = 2; c < strArr.length; c++)
			cmdBuild.append(' ').append(strArr[c]);
		String str = cmdBuild.toString().trim();
		if (str.contains(" "))
			return str.split(" ", 2);
		return new String[] { "", str };
	}
}
