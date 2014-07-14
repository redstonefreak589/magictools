package me.redstonefreak589.magictools;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

@SuppressWarnings("unused")
public class RightClickManager implements Listener{
	
	public Main plugin;
	
	public RightClickManager(Main plugin) {
		this.plugin = plugin;
	}
	
	@SuppressWarnings({ "deprecation" })
	@EventHandler
	public void onPlayerRightClick(PlayerInteractEvent event){
		Player player = (Player) event.getPlayer();
		if(event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK){
			if(event.getPlayer().getInventory().getItemInHand().getType() == Material.DIAMOND_SWORD/*&& event.getPlayer().getInventory().getItemInHand().getItemMeta().getDisplayName().equals(ChatColor.DARK_RED + "Sword of Boom") && !(event.getPlayer().getInventory().getItemInHand().getItemMeta().getDisplayName().equalsIgnoreCase("Diamond Sword"))*/){
				if(!(event.getPlayer().getInventory().getItemInHand().getItemMeta().hasDisplayName())) return;
				if(player.hasPermission("magictools.diamondsword")){
					if(plugin.cooldown1.get(event.getPlayer().getName()) == null) plugin.cooldown1.put(event.getPlayer().getName(), 0);
					if (plugin.cooldown1.get(event.getPlayer().getName()) <= 0) {
						if(!(player.getHealth() > 0 && player.getHealth() < 6)){
							Block block = player.getTargetBlock(null, 50);
							Location location = block.getLocation();
							World world = player.getWorld();
							world.createExplosion(location, 5);
							player.sendMessage(ChatColor.AQUA + "[MagicTools] " + ChatColor.BLUE + "You create an explosion with the power of the Sword of Boom.");
							plugin.cooldown1.put(event.getPlayer().getName(), 3);
						}else{
							player.sendMessage(ChatColor.AQUA + "[MagicTools] " + ChatColor.BLUE + "Magic only works if you are above 3 hearts of health. If you are below that, you will be to exhausted to cast the spell!");
						}
					}else{
						player.sendMessage(ChatColor.AQUA + "[MagicTools] " + ChatColor.BLUE + "You must wait " + plugin.cooldown1.get(event.getPlayer().getName()) + " seconds before using this ability!");
					}
				}else{
					player.sendMessage(ChatColor.AQUA + "[MagicTools] " + ChatColor.BLUE + "You don't have permission to use the diamond sword ability. Because it is an explosion, the owner might have disabled it to prevent world destruction.");
				}
			}else if (event.getPlayer().getInventory().getItemInHand().getType() == Material.GOLD_SWORD){
				if(player.hasPermission("magictools.goldsword")){
					if(plugin.cooldown1.get(event.getPlayer().getName()) == null) plugin.cooldown1.put(event.getPlayer().getName(), 0);
					if (plugin.cooldown1.get(event.getPlayer().getName()) <= 0) {
						if(!(event.getPlayer().getInventory().getItemInHand().getItemMeta().hasDisplayName())) return;
						if(event.getPlayer().getInventory().getItemInHand().getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.DARK_PURPLE + "Sword of Electricity")){
							if(!(player.getHealth() > 0 && player.getHealth() < 6)){
								Block block = player.getTargetBlock(null, 50);
								Location location = block.getLocation();
								World world = player.getWorld();
								world.strikeLightning(location);
								player.sendMessage(ChatColor.AQUA + "[MagicTools] " + ChatColor.BLUE + "You strike lightning with the power of the Sword of Electricity.");
								plugin.cooldown1.put(event.getPlayer().getName(), 3);
							}else{
								player.sendMessage(ChatColor.AQUA + "[MagicTools] " + ChatColor.BLUE + "Magic only works if you are above 3 hearts of health. If you are below that, you will be to exhausted to cast the spell!");
							}
						}
					}else{
						player.sendMessage(ChatColor.AQUA + "[MagicTools] " + ChatColor.BLUE + "You must wait " + plugin.cooldown1.get(event.getPlayer().getName()) + " seconds before using this ability!");
					}
				}else{
					player.sendMessage(ChatColor.AQUA + "[MagicTools] " + ChatColor.BLUE + "You don't have permission to use the gold sword ability. Because it is a lightning strike, the owner might have disabled it to prevent world destruction.");
				}
			}else if (event.getPlayer().getInventory().getItemInHand().getType() == Material.IRON_SWORD){
				if(player.hasPermission("magictools.ironsword")){
					if(plugin.cooldown1.get(event.getPlayer().getName()) == null) plugin.cooldown1.put(event.getPlayer().getName(), 0);
					if (plugin.cooldown1.get(event.getPlayer().getName()) <= 0) {
						if(!(event.getPlayer().getInventory().getItemInHand().getItemMeta().hasDisplayName())) return;
						if(event.getPlayer().getInventory().getItemInHand().getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.YELLOW + "Sword of The Goddess")){	
							if(event.getPlayer().getHealth() < 20){
								double playerHealth = player.getHealth();
								double newPlayerHealth = playerHealth + 6;
								player.setHealth(newPlayerHealth);
								player.sendMessage(ChatColor.AQUA + "[MagicTools] " + ChatColor.BLUE + "You healed yourself with the power of the Sword of The Goddess.");
								plugin.cooldown1.put(event.getPlayer().getName(), 3);
							}
						}
					}else{
						player.sendMessage(ChatColor.AQUA + "[MagicTools] " + ChatColor.BLUE + "You must wait " + plugin.cooldown1.get(event.getPlayer().getName()) + " seconds before using this ability!");
					}
				}else{
					player.sendMessage(ChatColor.AQUA + "[MagicTools] " + ChatColor.BLUE + "You don't have permission to use the iron sword ability.");
				}
			}else if(event.getPlayer().getItemInHand().getType() == Material.WOOD_SWORD){
				if(player.hasPermission("magictools.woodsword")){
					if(plugin.cooldown1.get(event.getPlayer().getName()) == null) plugin.cooldown1.put(event.getPlayer().getName(), 0);
					if (plugin.cooldown1.get(event.getPlayer().getName()) <= 0) {
						if(!(event.getPlayer().getInventory().getItemInHand().getItemMeta().hasDisplayName())) return;
						if(event.getPlayer().getInventory().getItemInHand().getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.GREEN + "Sword of Repelling")){
							Block block = (Block) player.getTargetBlock(null, 50).getRelative(BlockFace.UP);
							block.setType(Material.WATER);
							/*Location bl = (Location) block.getLocation();
							String world = (String) event.getPlayer().getWorld().getName();
							for(int i = 0; i == 3; i++){
								plugin.getServer().getWorld(world).spawnEntity(bl, EntityType.SKELETON);
							}*/
							plugin.cooldown1.put(event.getPlayer().getName(), 3);
						}
					}else{
						player.sendMessage(ChatColor.AQUA + "[MagicTools] " + ChatColor.BLUE + "You must wait " + plugin.cooldown1.get(event.getPlayer().getName()) + " seconds before using this ability!");
					}
				}else{
					player.sendMessage(ChatColor.AQUA + "[MagicTools] " + ChatColor.BLUE + "You don't have permission to use the wood sword ability. The admin may have disabled it to prevent world destruction.");
				}
			}else{
				return;
			}
		}
	}
}
