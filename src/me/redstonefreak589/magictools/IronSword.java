package me.redstonefreak589.magictools;

import java.util.Random;

import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class IronSword implements Listener{

	public Main plugin;
	boolean useAbility = false;
	boolean cancelAbility = false;
	
	public IronSword(Main plugin){
		this.plugin = plugin;
	}
	
	private boolean useXp(){
		if(plugin.getConfig().getBoolean("useXP")){
			return true;
		}
		return false;
	}
	
	private boolean useRedstone(){
		if(plugin.getConfig().getBoolean("useRedstone")){
			return true;
		}
		return false;
	}
	
	public int generateRandomNumberInRange(int max, int min){
		Random r = new Random();
		int i1 = r.nextInt((max - min)+1) + min;
		return i1;
	}
	
	@EventHandler
	public void onPlayerRightClick(PlayerInteractEvent event){
		Player player = (Player) event.getPlayer();
		if(event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK){
			if (event.getPlayer().getInventory().getItemInMainHand().getType() == Material.IRON_SWORD){
				if(!(event.getPlayer().getInventory().getItemInMainHand().getItemMeta().hasDisplayName())) return;
				if(player.hasPermission("magictools.ironsword")){
					if(plugin.cooldown1.get(event.getPlayer().getName()) == null) plugin.cooldown1.put(event.getPlayer().getName(), 0);
					if (plugin.cooldown1.get(event.getPlayer().getName()) <= 0) {
						if(!(event.getPlayer().getInventory().getItemInMainHand().getItemMeta().hasDisplayName())) return;
						if(event.getPlayer().getInventory().getItemInMainHand().getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.YELLOW + "Sword of The Goddess")){	
							if(event.getPlayer().getHealth() < 20){
								if(useXp() || useRedstone()){
									if(useRedstone() && useXp()){
										int redstoneToUse = plugin.getConfig().getInt("RedstoneToUse");
										ItemStack rs = new ItemStack(Material.REDSTONE, redstoneToUse);
										if((player.getLevel() >= plugin.getConfig().getInt("XPLevelsToUse") && player.getInventory().containsAtLeast(rs, plugin.getConfig().getInt("RedstoneToUse"))) || player.getGameMode().equals(GameMode.CREATIVE)){
											if(player.getInventory().containsAtLeast(rs, plugin.getConfig().getInt("RedstoneToUse"))){
												
												ItemStack rs1 = new ItemStack(Material.REDSTONE, plugin.getConfig().getInt("RedstoneToUse"));
												player.getInventory().removeItem(rs1);
												
											}else{
												if(!player.getGameMode().equals(GameMode.CREATIVE)){player.sendMessage(ChatColor.AQUA + "[MagicTools] " + ChatColor.RED + "You must have at least " + ChatColor.GRAY + plugin.getConfig().getInt("RedstoneToUse") + ChatColor.RED + " pieces of redstone to use this ability!");}
												cancelAbility = true;
											}
												
											
											int playerLevel = player.getLevel();
											if(player.getLevel() >= plugin.getConfig().getInt("XPLevelsToUse") && !(cancelAbility)){
												int newLevel = (playerLevel - plugin.getConfig().getInt("XPLevelsToUse"));
												player.setLevel(newLevel);
												useAbility = true;
											}else{
												if(!player.getGameMode().equals(GameMode.CREATIVE)){player.sendMessage(ChatColor.AQUA + "[MagicTools] " + ChatColor.RED + "You must have at least " + ChatColor.GRAY + plugin.getConfig().getInt("XPLevelsToUse") + ChatColor.RED + " levels to use this ability!");}
												if(cancelAbility){cancelAbility = false;}												
											}
											
											if(useAbility == true || player.getGameMode().equals(GameMode.CREATIVE)){
												double playerHealth = player.getHealth();
												double newPlayerHealth = playerHealth + 6;
												if(player.getHealth() >= 14){
													player.setHealth(20);
												}else{
													player.setHealth(newPlayerHealth);
												}
												//player.setHealth(newPlayerHealth);
												player.sendMessage(ChatColor.AQUA + "[MagicTools] " + ChatColor.BLUE + "You healed yourself with the power of the Sword of The Goddess.");
												plugin.cooldown1.put(event.getPlayer().getName(), 3);
												useAbility = false;
											}
										}else{
											if(!player.getGameMode().equals(GameMode.CREATIVE)){player.sendMessage(ChatColor.AQUA + "[MagicTools] " + ChatColor.RED + "You must have at least " + ChatColor.GRAY + plugin.getConfig().getInt("XPLevelsToUse") + ChatColor.RED + " XP levels and " + ChatColor.GRAY + plugin.getConfig().getInt("RedstoneToUse") + ChatColor.RED + " pieces of redstone to use this ability!");}
										}
									}else if(useRedstone() || useXp()){
										if(useXp()){
											int playerLevel = player.getLevel();
											if(player.getLevel() >= plugin.getConfig().getInt("XPLevelsToUse")){
												int newLevel = (playerLevel - plugin.getConfig().getInt("XPLevelsToUse"));
												player.setLevel(newLevel);
												double playerHealth = player.getHealth();
												double newPlayerHealth = playerHealth + 6;
												if(player.getHealth() >= 14){
													player.setHealth(20);
												}else{
													player.setHealth(newPlayerHealth);
												}
												//player.setHealth(newPlayerHealth);
												player.sendMessage(ChatColor.AQUA + "[MagicTools] " + ChatColor.BLUE + "You healed yourself with the power of the Sword of The Goddess.");
												plugin.cooldown1.put(event.getPlayer().getName(), 3);

											}else{
												if(!player.getGameMode().equals(GameMode.CREATIVE)){player.sendMessage(ChatColor.AQUA + "[MagicTools] " + ChatColor.RED + "You must have at least " + ChatColor.GRAY + plugin.getConfig().getInt("XPLevelsToUse") + ChatColor.RED + " levels to use this ability!");}
											}
										}else if(useRedstone()){
											ItemStack rs = new ItemStack(Material.REDSTONE);
											if(player.getInventory().containsAtLeast(rs, plugin.getConfig().getInt("RedstoneToUse"))){
												ItemStack rs1 = new ItemStack(Material.REDSTONE, plugin.getConfig().getInt("RedstoneToUse"));
												player.getInventory().removeItem(rs1);
												
												double playerHealth = player.getHealth();
												double newPlayerHealth = playerHealth + 6;
												if(player.getHealth() >= 14){
													player.setHealth(20);
												}else{
													player.setHealth(newPlayerHealth);
												}
												//player.setHealth(newPlayerHealth);
												player.sendMessage(ChatColor.AQUA + "[MagicTools] " + ChatColor.BLUE + "You healed yourself with the power of the Sword of The Goddess.");
												plugin.cooldown1.put(event.getPlayer().getName(), 3);
											}else{
												if(!player.getGameMode().equals(GameMode.CREATIVE)){player.sendMessage(ChatColor.AQUA + "[MagicTools] " + ChatColor.RED + "You must have at least " + ChatColor.GRAY + plugin.getConfig().getInt("RedstoneToUse") + ChatColor.RED + " pieces of redstone to use this ability!");}
											}
										}
									}
								}else{
									double playerHealth = player.getHealth();
									double newPlayerHealth = playerHealth + 6;
									if(player.getHealth() >= 14){
										player.setHealth(20);
									}else{
										player.setHealth(newPlayerHealth);
									}
									//player.setHealth(newPlayerHealth);
									player.sendMessage(ChatColor.AQUA + "[MagicTools] " + ChatColor.BLUE + "You healed yourself with the power of the Sword of The Goddess.");
									plugin.cooldown1.put(event.getPlayer().getName(), 3);
								}
							}else{
								player.sendMessage(ChatColor.AQUA + "[MagicTools] " + ChatColor.BLUE + "You are already at full health! You can't heal anymore!");
							}
						}
					}else{
						player.sendMessage(ChatColor.AQUA + "[MagicTools] " + ChatColor.BLUE + "You must wait " + plugin.cooldown1.get(event.getPlayer().getName()) + " seconds before using this ability!");
					}
				}else{
					player.sendMessage(ChatColor.AQUA + "[MagicTools] " + ChatColor.BLUE + "You don't have permission to use the iron sword ability.");
				}
			}
		}
	}
}
