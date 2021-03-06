package me.redstonefreak589.magictools;

import java.util.Random;
import java.util.Set;

import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class WoodSword implements Listener{

	public Main plugin;
	boolean useAbility = false;
	boolean cancelAbility = false;
	
	public WoodSword(Main plugin){
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
			if(event.getPlayer().getInventory().getItemInMainHand().getType() == Material.WOOD_SWORD){
				if(!(event.getPlayer().getInventory().getItemInMainHand().getItemMeta().hasDisplayName())) return;
				if(player.hasPermission("magictools.woodsword")){
					if(plugin.cooldown1.get(event.getPlayer().getName()) == null) plugin.cooldown1.put(event.getPlayer().getName(), 0);
					if (plugin.cooldown1.get(event.getPlayer().getName()) <= 0) {
						if(!(event.getPlayer().getInventory().getItemInMainHand().getItemMeta().hasDisplayName())) return;
						if(event.getPlayer().getInventory().getItemInMainHand().getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.GREEN + "Sword of Repelling")){
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
											Block block = (Block) player.getTargetBlock((Set<Material>) null, 50).getRelative(BlockFace.UP);
											Location bl = (Location) block.getLocation();
											if(plugin.getWorldGuard().canBuild(player, bl) || player.isOp()){
												block.setType(Material.WATER);
												useAbility = false;
											}else{
												player.sendMessage(ChatColor.AQUA + "[MagicTools] " + ChatColor.BLUE + "You need to be able to build in that region to do any actions inside it!");
												useAbility = false;
											}
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
											Block block = (Block) player.getTargetBlock((Set<Material>) null, 50).getRelative(BlockFace.UP);
											Location bl = (Location) block.getLocation();
											if(plugin.getWorldGuard().canBuild(player, bl) || player.isOp()){
												block.setType(Material.WATER);
											}else{
												player.sendMessage(ChatColor.AQUA + "[MagicTools] " + ChatColor.BLUE + "You need to be able to build in that region to do any actions inside it!");
											}
											plugin.cooldown1.put(event.getPlayer().getName(), 3);

										}else{
											if(!player.getGameMode().equals(GameMode.CREATIVE)){player.sendMessage(ChatColor.AQUA + "[MagicTools] " + ChatColor.RED + "You must have at least " + ChatColor.GRAY + plugin.getConfig().getInt("XPLevelsToUse") + ChatColor.RED + " levels to use this ability!");}
										}
									}else if(useRedstone()){
										ItemStack rs = new ItemStack(Material.REDSTONE);
										if(player.getInventory().containsAtLeast(rs, plugin.getConfig().getInt("RedstoneToUse"))){
											ItemStack rs1 = new ItemStack(Material.REDSTONE, plugin.getConfig().getInt("RedstoneToUse"));
											player.getInventory().removeItem(rs1);
											
											Block block = (Block) player.getTargetBlock((Set<Material>) null, 50).getRelative(BlockFace.UP);
											Location bl = (Location) block.getLocation();
											if(plugin.getWorldGuard().canBuild(player, bl) || player.isOp()){
												block.setType(Material.WATER);
											}else{
												player.sendMessage(ChatColor.AQUA + "[MagicTools] " + ChatColor.BLUE + "You need to be able to build in that region to do any actions inside it!");
											}
											plugin.cooldown1.put(event.getPlayer().getName(), 3);
										}else{
											if(!player.getGameMode().equals(GameMode.CREATIVE)){player.sendMessage(ChatColor.AQUA + "[MagicTools] " + ChatColor.RED + "You must have at least " + ChatColor.GRAY + plugin.getConfig().getInt("RedstoneToUse") + ChatColor.RED + " pieces of redstone to use this ability!");}
										}
									}
								}
							}else{
								Block block = (Block) player.getTargetBlock((Set<Material>) null, 50).getRelative(BlockFace.UP);
								Location bl = (Location) block.getLocation();
								if(plugin.getWorldGuard().canBuild(player, bl) || player.isOp()){
									block.setType(Material.WATER);
								}else{
									player.sendMessage(ChatColor.AQUA + "[MagicTools] " + ChatColor.BLUE + "You need to be able to build in that region to do any actions inside it!");
								}
								plugin.cooldown1.put(event.getPlayer().getName(), 3);
							}
						}
					}else{
						player.sendMessage(ChatColor.AQUA + "[MagicTools] " + ChatColor.BLUE + "You must wait " + plugin.cooldown1.get(event.getPlayer().getName()) + " seconds before using this ability!");
					}
				}else{
					player.sendMessage(ChatColor.AQUA + "[MagicTools] " + ChatColor.BLUE + "You don't have permission to use the wood sword ability. The admin may have disabled it to prevent world destruction.");
				}
			}
		}
	}
}
