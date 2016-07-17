package me.redstonefreak589.magictools;

import java.util.Random;
import java.util.Set;

import org.bukkit.ChatColor;
import org.bukkit.GameMode;
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
import org.bukkit.inventory.ItemStack;

public class RightClickManager implements Listener{
	
	public Main plugin;
	boolean useAbility = false;
	boolean cancelAbility = false;
	
	public RightClickManager(Main plugin) {
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
	
	@SuppressWarnings({ "deprecation" })
	@EventHandler
	public void onPlayerRightClick(PlayerInteractEvent event){
		Player player = (Player) event.getPlayer();
		if(event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK){
			if(event.getPlayer().getInventory().getItemInMainHand().getType() == Material.DIAMOND_SWORD/*&& event.getPlayer().getInventory().getItemInMainHand().getItemMeta().getDisplayName().equals(ChatColor.DARK_RED + "Sword of Boom") && !(event.getPlayer().getInventory().getItemInMainHand().getItemMeta().getDisplayName().equalsIgnoreCase("Diamond Sword"))*/){
				if(!(event.getPlayer().getInventory().getItemInMainHand().getItemMeta().hasDisplayName())) return;
				if(player.hasPermission("magictools.diamondsword")){
					if(plugin.cooldown1.get(event.getPlayer().getName()) == null) plugin.cooldown1.put(event.getPlayer().getName(), 0);
					if (plugin.cooldown1.get(event.getPlayer().getName()) <= 0) {
						if(!(player.getHealth() > 0 && player.getHealth() < 6)){
							/*
							Block block = player.getTargetBlock(null, 50);
							Location location = block.getLocation();
							World world = player.getWorld();
							if(plugin.getWorldGuard().canBuild(player,location)){
								world.createExplosion(location, 5);
								player.sendMessage(ChatColor.AQUA + "[MagicTools] " + ChatColor.BLUE + "You create an explosion with the power of the Sword of Boom.");
								plugin.cooldown1.put(event.getPlayer().getName(), 3);
								
							}else{
								player.sendMessage(ChatColor.AQUA + "[MagicTools] " + ChatColor.BLUE + "You need to be able to build in that region to do any actions inside it!");
							}
							*/
							
							if(useXp() || useRedstone()){
								if(useRedstone() && useXp()){
									ItemStack rs = new ItemStack(Material.REDSTONE);
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
											Block block = player.getTargetBlock((Set<Material>) null, 50);
											Location location = block.getLocation();
											World world = player.getWorld();
											if(plugin.getWorldGuard().canBuild(player,location)){
												world.createExplosion(location, 5);
												player.sendMessage(ChatColor.AQUA + "[MagicTools] " + ChatColor.BLUE + "You create an explosion with the power of the Sword of Boom.");
												plugin.cooldown1.put(event.getPlayer().getName(), 3);
												
											}else{
												player.sendMessage(ChatColor.AQUA + "[MagicTools] " + ChatColor.BLUE + "You need to be able to build in that region to do any actions inside it!");
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
											Block block = player.getTargetBlock((Set<Material>) null, 50);
											Location location = block.getLocation();
											World world = player.getWorld();
											if(plugin.getWorldGuard().canBuild(player,location)){
												world.createExplosion(location, 5);
												player.sendMessage(ChatColor.AQUA + "[MagicTools] " + ChatColor.BLUE + "You create an explosion with the power of the Sword of Boom.");
												plugin.cooldown1.put(event.getPlayer().getName(), 3);
												
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
											
											Block block = player.getTargetBlock((Set<Material>) null, 50);
											Location location = block.getLocation();
											World world = player.getWorld();
											if(plugin.getWorldGuard().canBuild(player,location)){
												world.createExplosion(location, 5);
												player.sendMessage(ChatColor.AQUA + "[MagicTools] " + ChatColor.BLUE + "You create an explosion with the power of the Sword of Boom.");
												plugin.cooldown1.put(event.getPlayer().getName(), 3);
												
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
								Block block = player.getTargetBlock((Set<Material>) null, 50);
								Location location = block.getLocation();
								World world = player.getWorld();
								if(plugin.getWorldGuard().canBuild(player,location)){
									world.createExplosion(location, 5);
									player.sendMessage(ChatColor.AQUA + "[MagicTools] " + ChatColor.BLUE + "You create an explosion with the power of the Sword of Boom.");
									plugin.cooldown1.put(event.getPlayer().getName(), 3);
									
								}else{
									player.sendMessage(ChatColor.AQUA + "[MagicTools] " + ChatColor.BLUE + "You need to be able to build in that region to do any actions inside it!");
								}
								plugin.cooldown1.put(event.getPlayer().getName(), 3);
							}
						}else{
							player.sendMessage(ChatColor.AQUA + "[MagicTools] " + ChatColor.BLUE + "Magic only works if you are above 3 hearts of health. If you are below that, you will be to exhausted to cast the spell!");
						}
					}else{
						player.sendMessage(ChatColor.AQUA + "[MagicTools] " + ChatColor.BLUE + "You must wait " + plugin.cooldown1.get(event.getPlayer().getName()) + " seconds before using this ability!");
					}
				}else{
					player.sendMessage(ChatColor.AQUA + "[MagicTools] " + ChatColor.BLUE + "You don't have permission to use the diamond sword ability. Because it is an explosion, the owner might have disabled it to prevent world destruction.");
				}
			}else if (event.getPlayer().getInventory().getItemInMainHand().getType() == Material.GOLD_SWORD){
				if(player.hasPermission("magictools.goldsword")){
					if(plugin.cooldown1.get(event.getPlayer().getName()) == null) plugin.cooldown1.put(event.getPlayer().getName(), 0);
					if (plugin.cooldown1.get(event.getPlayer().getName()) <= 0) {
						if(!(event.getPlayer().getInventory().getItemInMainHand().getItemMeta().hasDisplayName())) return;
						if(event.getPlayer().getInventory().getItemInMainHand().getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.DARK_PURPLE + "Sword of Electricity")){
							if(!(player.getHealth() > 0 && player.getHealth() < 6)){
								/*
								Block block = player.getTargetBlock(null, 50);
								Location location = block.getLocation();
								World world = player.getWorld();
								if(plugin.getWorldGuard().canBuild(player, location) || player.isOp()){
									world.strikeLightning(location);
									player.sendMessage(ChatColor.AQUA + "[MagicTools] " + ChatColor.BLUE + "You strike lightning with the power of the Sword of Electricity.");
									plugin.cooldown1.put(event.getPlayer().getName(), 3);
									
								}else{
									player.sendMessage(ChatColor.AQUA + "[MagicTools] " + ChatColor.BLUE + "You need to be able to build in that region to do any actions inside it!");
								}
								*/
								
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
												Block block = player.getTargetBlock((Set<Material>) null, 50);
												Location location = block.getLocation();
												World world = player.getWorld();
												if(plugin.getWorldGuard().canBuild(player, location) || player.isOp()){
													world.strikeLightning(location);
													player.sendMessage(ChatColor.AQUA + "[MagicTools] " + ChatColor.BLUE + "You strike lightning with the power of the Sword of Electricity.");
													plugin.cooldown1.put(event.getPlayer().getName(), 3);
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
												Block block = player.getTargetBlock((Set<Material>) null, 50);
												Location location = block.getLocation();
												World world = player.getWorld();
												if(plugin.getWorldGuard().canBuild(player, location) || player.isOp()){
													world.strikeLightning(location);
													player.sendMessage(ChatColor.AQUA + "[MagicTools] " + ChatColor.BLUE + "You strike lightning with the power of the Sword of Electricity.");
													plugin.cooldown1.put(event.getPlayer().getName(), 3);
													
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
												
												Block block = player.getTargetBlock((Set<Material>) null, 50);
												Location location = block.getLocation();
												World world = player.getWorld();
												if(plugin.getWorldGuard().canBuild(player, location) || player.isOp()){
													world.strikeLightning(location);
													player.sendMessage(ChatColor.AQUA + "[MagicTools] " + ChatColor.BLUE + "You strike lightning with the power of the Sword of Electricity.");
													plugin.cooldown1.put(event.getPlayer().getName(), 3);
													useAbility = false;
												}else{
													player.sendMessage(ChatColor.AQUA + "[MagicTools] " + ChatColor.BLUE + "You need to be able to build in that region to do any actions inside it!");
													useAbility = false;
												}
												plugin.cooldown1.put(event.getPlayer().getName(), 3);
											}else{
												if(!player.getGameMode().equals(GameMode.CREATIVE)){player.sendMessage(ChatColor.AQUA + "[MagicTools] " + ChatColor.RED + "You must have at least " + ChatColor.GRAY + plugin.getConfig().getInt("RedstoneToUse") + ChatColor.RED + " pieces of redstone to use this ability!");}
											}
										}
									}
								}else{
									Block block = player.getTargetBlock((Set<Material>) null, 50);
									Location location = block.getLocation();
									World world = player.getWorld();
									if(plugin.getWorldGuard().canBuild(player, location) || player.isOp()){
										world.strikeLightning(location);
										player.sendMessage(ChatColor.AQUA + "[MagicTools] " + ChatColor.BLUE + "You strike lightning with the power of the Sword of Electricity.");
										plugin.cooldown1.put(event.getPlayer().getName(), 3);
									}else{
										player.sendMessage(ChatColor.AQUA + "[MagicTools] " + ChatColor.BLUE + "You need to be able to build in that region to do any actions inside it!");
									}
								}
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
			}else if (event.getPlayer().getInventory().getItemInMainHand().getType() == Material.IRON_SWORD){
				if(player.hasPermission("magictools.ironsword")){
					if(plugin.cooldown1.get(event.getPlayer().getName()) == null) plugin.cooldown1.put(event.getPlayer().getName(), 0);
					if (plugin.cooldown1.get(event.getPlayer().getName()) <= 0) {
						if(!(event.getPlayer().getInventory().getItemInMainHand().getItemMeta().hasDisplayName())) return;
						if(event.getPlayer().getInventory().getItemInMainHand().getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.YELLOW + "Sword of The Goddess")){	
							if(event.getPlayer().getHealth() < 20){
								/*
								double playerHealth = player.getHealth();
								double newPlayerHealth = playerHealth + 6;
								player.setHealth(newPlayerHealth);
								player.sendMessage(ChatColor.AQUA + "[MagicTools] " + ChatColor.BLUE + "You healed yourself with the power of the Sword of The Goddess.");
								plugin.cooldown1.put(event.getPlayer().getName(), 3);
								*/
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
												player.setHealth(newPlayerHealth);
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
												player.setHealth(newPlayerHealth);
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
												player.setHealth(newPlayerHealth);
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
									player.setHealth(newPlayerHealth);
									player.sendMessage(ChatColor.AQUA + "[MagicTools] " + ChatColor.BLUE + "You healed yourself with the power of the Sword of The Goddess.");
									plugin.cooldown1.put(event.getPlayer().getName(), 3);
								}
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
						if(!(event.getPlayer().getInventory().getItemInMainHand().getItemMeta().hasDisplayName())) return;
						if(event.getPlayer().getInventory().getItemInMainHand().getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.GREEN + "Sword of Repelling")){
							/*Block block = (Block) player.getTargetBlock(null, 50).getRelative(BlockFace.UP);
							Location bl = (Location) block.getLocation();
							if(plugin.getWorldGuard().canBuild(player, bl) || player.isOp()){
								block.setType(Material.WATER);
							}else{
								player.sendMessage(ChatColor.AQUA + "[MagicTools] " + ChatColor.BLUE + "You need to be able to build in that region to do any actions inside it!");
							}
							*/
							
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
			}else if (event.getPlayer().getInventory().getItemInMainHand().getType() == Material.STONE_SWORD){
				if(player.hasPermission("magictools.stonesword")){
					if(plugin.cooldown1.get(event.getPlayer().getName()) == null) plugin.cooldown1.put(event.getPlayer().getName(), 0);
					if (plugin.cooldown1.get(event.getPlayer().getName()) <= 0) {
						if(!(event.getPlayer().getInventory().getItemInMainHand().getItemMeta().hasDisplayName())) return;
						if(event.getPlayer().getInventory().getItemInMainHand().getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.DARK_AQUA + "Sword of Mobs")){
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
											
											//Begin ability
											
											int generate = generateRandomNumberInRange(1,5);
											if(generate == 1){
												Block block = (Block) player.getTargetBlock((Set<Material>) null, 50).getRelative(BlockFace.UP);
												Location bl = (Location) block.getLocation();
												World world = event.getPlayer().getWorld();
												if(plugin.getWorldGuard().canBuild(player, bl) || player.isOp()){
													for(int i = 5; i == 5; i++){
														world.spawnEntity(bl, EntityType.SKELETON);
													}
												}else{
													player.sendMessage(ChatColor.AQUA + "[MagicTools] " + ChatColor.BLUE + "You need to be able to build in that region to do any actions inside it!");
												}
											}else if(generate == 2){
												Block block = (Block) player.getTargetBlock((Set<Material>) null, 50).getRelative(BlockFace.UP);
												Location bl = (Location) block.getLocation();
												World world = event.getPlayer().getWorld();
												if(plugin.getWorldGuard().canBuild(player, bl) || player.isOp()){
													for(int i = 5; i == 5; i++){
														world.spawnEntity(bl, EntityType.CREEPER);
													}
												}else{
													player.sendMessage(ChatColor.AQUA + "[MagicTools] " + ChatColor.BLUE + "You need to be able to build in that region to do any actions inside it!");
												}
											}else if(generate == 3){
												Block block = (Block) player.getTargetBlock((Set<Material>) null, 50).getRelative(BlockFace.UP);
												Location bl = (Location) block.getLocation();
												World world = event.getPlayer().getWorld();
												if(plugin.getWorldGuard().canBuild(player, bl) || player.isOp()){
													for(int i = 5; i == 5; i++){
														world.spawnEntity(bl, EntityType.ZOMBIE);
													}
												}else{
													player.sendMessage(ChatColor.AQUA + "[MagicTools] " + ChatColor.BLUE + "You need to be able to build in that region to do any actions inside it!");
												}
											}else if(generate == 4){
												Block block = (Block) player.getTargetBlock((Set<Material>) null, 50).getRelative(BlockFace.UP);
												Location bl = (Location) block.getLocation();
												World world = event.getPlayer().getWorld();
												if(plugin.getWorldGuard().canBuild(player, bl) || player.isOp()){
													for(int i = 5; i == 5; i++){
														world.spawnEntity(bl, EntityType.SPIDER);
													}
												}else{
													player.sendMessage(ChatColor.AQUA + "[MagicTools] " + ChatColor.BLUE + "You need to be able to build in that region to do any actions inside it!");
												}
											}else if(generate == 5){
												Block block = (Block) player.getTargetBlock((Set<Material>) null, 50).getRelative(BlockFace.UP);
												Location bl = (Location) block.getLocation();
												World world = event.getPlayer().getWorld();
												if(plugin.getWorldGuard().canBuild(player, bl) || player.isOp()){
													for(int i = 5; i == 5; i++){
														world.spawnEntity(bl, EntityType.CAVE_SPIDER);
													}
												}else{
													player.sendMessage(ChatColor.AQUA + "[MagicTools] " + ChatColor.BLUE + "You need to be able to build in that region to do any actions inside it!");
												}
											}
											
											plugin.cooldown1.put(event.getPlayer().getName(), 3);
											
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

											//Begin ability
											
											int generate = generateRandomNumberInRange(1,5);
											if(generate == 1){
												Block block = (Block) player.getTargetBlock((Set<Material>) null, 50).getRelative(BlockFace.UP);
												Location bl = (Location) block.getLocation();
												World world = event.getPlayer().getWorld();
												if(plugin.getWorldGuard().canBuild(player, bl) || player.isOp()){
													for(int i = 5; i == 5; i++){
														world.spawnEntity(bl, EntityType.SKELETON);
													}
												}else{
													player.sendMessage(ChatColor.AQUA + "[MagicTools] " + ChatColor.BLUE + "You need to be able to build in that region to do any actions inside it!");
												}
											}else if(generate == 2){
												Block block = (Block) player.getTargetBlock((Set<Material>) null, 50).getRelative(BlockFace.UP);
												Location bl = (Location) block.getLocation();
												World world = event.getPlayer().getWorld();
												if(plugin.getWorldGuard().canBuild(player, bl) || player.isOp()){
													for(int i = 5; i == 5; i++){
														world.spawnEntity(bl, EntityType.CREEPER);
													}
												}else{
													player.sendMessage(ChatColor.AQUA + "[MagicTools] " + ChatColor.BLUE + "You need to be able to build in that region to do any actions inside it!");
												}
											}else if(generate == 3){
												Block block = (Block) player.getTargetBlock((Set<Material>) null, 50).getRelative(BlockFace.UP);
												Location bl = (Location) block.getLocation();
												World world = event.getPlayer().getWorld();
												if(plugin.getWorldGuard().canBuild(player, bl) || player.isOp()){
													for(int i = 5; i == 5; i++){
														world.spawnEntity(bl, EntityType.ZOMBIE);
													}
												}else{
													player.sendMessage(ChatColor.AQUA + "[MagicTools] " + ChatColor.BLUE + "You need to be able to build in that region to do any actions inside it!");
												}
											}else if(generate == 4){
												Block block = (Block) player.getTargetBlock((Set<Material>) null, 50).getRelative(BlockFace.UP);
												Location bl = (Location) block.getLocation();
												World world = event.getPlayer().getWorld();
												if(plugin.getWorldGuard().canBuild(player, bl) || player.isOp()){
													for(int i = 5; i == 5; i++){
														world.spawnEntity(bl, EntityType.SPIDER);
													}
												}else{
													player.sendMessage(ChatColor.AQUA + "[MagicTools] " + ChatColor.BLUE + "You need to be able to build in that region to do any actions inside it!");
												}
											}else if(generate == 5){
												Block block = (Block) player.getTargetBlock((Set<Material>) null, 50).getRelative(BlockFace.UP);
												Location bl = (Location) block.getLocation();
												World world = event.getPlayer().getWorld();
												if(plugin.getWorldGuard().canBuild(player, bl) || player.isOp()){
													for(int i = 5; i == 5; i++){
														world.spawnEntity(bl, EntityType.CAVE_SPIDER);
													}
												}else{
													player.sendMessage(ChatColor.AQUA + "[MagicTools] " + ChatColor.BLUE + "You need to be able to build in that region to do any actions inside it!");
												}
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
											

											//Begin ability
											
											int generate = generateRandomNumberInRange(1,5);
											if(generate == 1){
												Block block = (Block) player.getTargetBlock((Set<Material>) null, 50).getRelative(BlockFace.UP);
												Location bl = (Location) block.getLocation();
												World world = event.getPlayer().getWorld();
												if(plugin.getWorldGuard().canBuild(player, bl) || player.isOp()){
													for(int i = 5; i == 5; i++){
														world.spawnEntity(bl, EntityType.SKELETON);
													}
												}else{
													player.sendMessage(ChatColor.AQUA + "[MagicTools] " + ChatColor.BLUE + "You need to be able to build in that region to do any actions inside it!");
												}
											}else if(generate == 2){
												Block block = (Block) player.getTargetBlock((Set<Material>) null, 50).getRelative(BlockFace.UP);
												Location bl = (Location) block.getLocation();
												World world = event.getPlayer().getWorld();
												if(plugin.getWorldGuard().canBuild(player, bl) || player.isOp()){
													for(int i = 5; i == 5; i++){
														world.spawnEntity(bl, EntityType.CREEPER);
													}
												}else{
													player.sendMessage(ChatColor.AQUA + "[MagicTools] " + ChatColor.BLUE + "You need to be able to build in that region to do any actions inside it!");
												}
											}else if(generate == 3){
												Block block = (Block) player.getTargetBlock((Set<Material>) null, 50).getRelative(BlockFace.UP);
												Location bl = (Location) block.getLocation();
												World world = event.getPlayer().getWorld();
												if(plugin.getWorldGuard().canBuild(player, bl) || player.isOp()){
													for(int i = 5; i == 5; i++){
														world.spawnEntity(bl, EntityType.ZOMBIE);
													}
												}else{
													player.sendMessage(ChatColor.AQUA + "[MagicTools] " + ChatColor.BLUE + "You need to be able to build in that region to do any actions inside it!");
												}
											}else if(generate == 4){
												Block block = (Block) player.getTargetBlock((Set<Material>) null, 50).getRelative(BlockFace.UP);
												Location bl = (Location) block.getLocation();
												World world = event.getPlayer().getWorld();
												if(plugin.getWorldGuard().canBuild(player, bl) || player.isOp()){
													for(int i = 5; i == 5; i++){
														world.spawnEntity(bl, EntityType.SPIDER);
													}
												}else{
													player.sendMessage(ChatColor.AQUA + "[MagicTools] " + ChatColor.BLUE + "You need to be able to build in that region to do any actions inside it!");
												}
											}else if(generate == 5){
												Block block = (Block) player.getTargetBlock((Set<Material>) null, 50).getRelative(BlockFace.UP);
												Location bl = (Location) block.getLocation();
												World world = event.getPlayer().getWorld();
												if(plugin.getWorldGuard().canBuild(player, bl) || player.isOp()){
													for(int i = 5; i == 5; i++){
														world.spawnEntity(bl, EntityType.CAVE_SPIDER);
													}
												}else{
													player.sendMessage(ChatColor.AQUA + "[MagicTools] " + ChatColor.BLUE + "You need to be able to build in that region to do any actions inside it!");
												}
											}
											
											plugin.cooldown1.put(event.getPlayer().getName(), 3);
										}else{
											if(!player.getGameMode().equals(GameMode.CREATIVE)){player.sendMessage(ChatColor.AQUA + "[MagicTools] " + ChatColor.RED + "You must have at least " + ChatColor.GRAY + plugin.getConfig().getInt("RedstoneToUse") + ChatColor.RED + " pieces of redstone to use this ability!");}
										}
									}
								}
							}else{

								//Begin ability
								
								int generate = generateRandomNumberInRange(1,5);
								if(generate == 1){
									Block block = (Block) player.getTargetBlock((Set<Material>) null, 50).getRelative(BlockFace.UP);
									Location bl = (Location) block.getLocation();
									World world = event.getPlayer().getWorld();
									if(plugin.getWorldGuard().canBuild(player, bl) || player.isOp()){
										for(int i = 5; i == 5; i++){
											world.spawnEntity(bl, EntityType.SKELETON);
										}
									}else{
										player.sendMessage(ChatColor.AQUA + "[MagicTools] " + ChatColor.BLUE + "You need to be able to build in that region to do any actions inside it!");
									}
								}else if(generate == 2){
									Block block = (Block) player.getTargetBlock((Set<Material>) null, 50).getRelative(BlockFace.UP);
									Location bl = (Location) block.getLocation();
									World world = event.getPlayer().getWorld();
									if(plugin.getWorldGuard().canBuild(player, bl) || player.isOp()){
										for(int i = 5; i == 5; i++){
											world.spawnEntity(bl, EntityType.CREEPER);
										}
									}else{
										player.sendMessage(ChatColor.AQUA + "[MagicTools] " + ChatColor.BLUE + "You need to be able to build in that region to do any actions inside it!");
									}
								}else if(generate == 3){
									Block block = (Block) player.getTargetBlock((Set<Material>) null, 50).getRelative(BlockFace.UP);
									Location bl = (Location) block.getLocation();
									World world = event.getPlayer().getWorld();
									if(plugin.getWorldGuard().canBuild(player, bl) || player.isOp()){
										for(int i = 5; i == 5; i++){
											world.spawnEntity(bl, EntityType.ZOMBIE);
										}
									}else{
										player.sendMessage(ChatColor.AQUA + "[MagicTools] " + ChatColor.BLUE + "You need to be able to build in that region to do any actions inside it!");
									}
								}else if(generate == 4){
									Block block = (Block) player.getTargetBlock((Set<Material>) null, 50).getRelative(BlockFace.UP);
									Location bl = (Location) block.getLocation();
									World world = event.getPlayer().getWorld();
									if(plugin.getWorldGuard().canBuild(player, bl) || player.isOp()){
										for(int i = 5; i == 5; i++){
											world.spawnEntity(bl, EntityType.SPIDER);
										}
									}else{
										player.sendMessage(ChatColor.AQUA + "[MagicTools] " + ChatColor.BLUE + "You need to be able to build in that region to do any actions inside it!");
									}
								}else if(generate == 5){
									Block block = (Block) player.getTargetBlock((Set<Material>) null, 50).getRelative(BlockFace.UP);
									Location bl = (Location) block.getLocation();
									World world = event.getPlayer().getWorld();
									if(plugin.getWorldGuard().canBuild(player, bl) || player.isOp()){
										for(int i = 5; i == 5; i++){
											world.spawnEntity(bl, EntityType.CAVE_SPIDER);
										}
									}else{
										player.sendMessage(ChatColor.AQUA + "[MagicTools] " + ChatColor.BLUE + "You need to be able to build in that region to do any actions inside it!");
									}
								}
								
								plugin.cooldown1.put(event.getPlayer().getName(), 3);
							}
						}
					}else{
						player.sendMessage(ChatColor.AQUA + "[MagicTools] " + ChatColor.BLUE + "You must wait " + plugin.cooldown1.get(event.getPlayer().getName()) + " seconds before using this ability!");
					}
				}else{
					player.sendMessage(ChatColor.AQUA + "[MagicTools] " + ChatColor.BLUE + "You don't have permission to use the stone sword ability. The admin may have disabled it to prevent world destruction.");
				}
			}else{
				return;
			}
		}
	}
}
