package me.redstonefreak589.magictools;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class Cooldown extends BukkitRunnable {
	public Main plugin;

	public Cooldown(Main plugin) {
		this.plugin = plugin;
	}

	// This is what happens when you call in this task
	public void run() {
		for (Player player : Bukkit.getServer().getOnlinePlayers()) {
			if(plugin.cooldown1.containsKey(player.getName())){
			plugin.cooldown1.put(player.getName(),
					plugin.cooldown1.get(player.getName()) - 1);
			}
		}
	}
}
