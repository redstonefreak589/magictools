package me.redstonefreak589.magictools;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Random;
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.BlockState;
import org.bukkit.block.Chest;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.world.ChunkPopulateEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.BookMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;

import com.sk89q.worldguard.bukkit.WorldGuardPlugin;

public class Main extends JavaPlugin implements Listener {
	public final Logger logger = Logger.getLogger("Minecraft");
	public static Main plugin;
	public Cooldown cooldown;
	HashMap<String, Integer> cooldown1;
	public ItemStack book = new ItemStack(Material.WRITTEN_BOOK);
	public ItemStack oldBook = new ItemStack(Material.WRITTEN_BOOK);
	public final DiamondSword mds = new DiamondSword(this);
	public final GoldSword mgs = new GoldSword(this);
	public final IronSword mis = new IronSword(this);
	public final WoodSword mws = new WoodSword(this);
	public final StoneSword mss = new StoneSword(this);
	public ItemStack diamondSword = new ItemStack(Material.DIAMOND_SWORD);
	public ItemStack ironSword = new ItemStack(Material.IRON_SWORD);
	public ItemStack woodSword = new ItemStack(Material.WOOD_SWORD);
	public ItemStack goldSword = new ItemStack(Material.GOLD_SWORD);
	public ItemStack stoneSword = new ItemStack(Material.STONE_SWORD);

	@SuppressWarnings("unused")
	@Override
	public void onEnable() {
		getConfig().options().copyDefaults(true);
		getConfig().options().header("How to use the config: \nuseXP - If you want to subtract levels from the player when they use an ability \nuseRedstone - If you want to subtract redstone from the player when they use an ability \nXPLevelsToUse - How many levels you want to take away from the player \nRedstoneToUse - How much redstone you want to take away from the player");
		getConfig().options().copyHeader(true);
		saveConfig();
		getWorldGuard();
		PluginDescriptionFile pdfFile = this.getDescription();
		this.logger.info(pdfFile.getName() + " Version " + pdfFile.getVersion()
				+ " has been enabled!");
		BukkitTask Cooldown = new Cooldown(this).runTaskTimer(this, 20, 20);
		cooldown1 = new HashMap<String, Integer>();
		PluginManager manager = getServer().getPluginManager();
		manager.registerEvents(this, this);
		manager.registerEvents(mds, this);
		manager.registerEvents(mgs, this);
		manager.registerEvents(mis, this);
		manager.registerEvents(mws, this);
		manager.registerEvents(mss, this);
		createBookMeta();
		createSwords();
		final ShapedRecipe dSword = new ShapedRecipe(diamondSword);
		dSword.shape("RGR", "GDG", "RGR");
		dSword.setIngredient('R', Material.REDSTONE);
		dSword.setIngredient('G', Material.GOLD_INGOT);
		dSword.setIngredient('D', Material.DIAMOND_SWORD);
		getServer().addRecipe(dSword);
		final ShapedRecipe gSword = new ShapedRecipe(goldSword);
		gSword.shape("NRN", "NGN", "NNN");
		gSword.setIngredient('N', Material.QUARTZ);
		gSword.setIngredient('G', Material.GOLD_SWORD);
		gSword.setIngredient('R', Material.REDSTONE);
		getServer().addRecipe(gSword);
		final ShapedRecipe iSword = new ShapedRecipe(ironSword);
		iSword.shape("RDR","DID","RDR");
		iSword.setIngredient('R', Material.RED_ROSE);
		iSword.setIngredient('D', Material.YELLOW_FLOWER);
		iSword.setIngredient('I', Material.IRON_SWORD);
		getServer().addRecipe(iSword);
		final ShapedRecipe wSword = new ShapedRecipe(woodSword);
		wSword.shape(" B ","GWG"," G ");
		wSword.setIngredient('B', Material.WATER_BUCKET);
		wSword.setIngredient('W', Material.WOOD_SWORD);
		wSword.setIngredient('G', Material.DIRT);
		getServer().addRecipe(wSword);
		final ShapedRecipe sSword = new ShapedRecipe(stoneSword);
		sSword.shape("BRB","GSG","RRR");
		sSword.setIngredient('B', Material.BONE);
		sSword.setIngredient('R', Material.ROTTEN_FLESH);
		sSword.setIngredient('G', Material.SULPHUR);
		sSword.setIngredient('S', Material.STONE_SWORD);
		getServer().addRecipe(sSword);
	}

	@Override
	public void onDisable() {
		PluginDescriptionFile pdfFile = this.getDescription();
		this.logger.info(pdfFile.getName() + " has been disabled!");
	}
	
	public WorldGuardPlugin getWorldGuard(){
		Plugin wg = getServer().getPluginManager().getPlugin("WorldGuard");
		if(wg == null || !(wg instanceof WorldGuardPlugin)){
			logger.severe("WorldGuard not found! This plugin CANNOT function without WorldEdit and WorldGuard! Disabling...");
			Bukkit.getPluginManager().disablePlugin(this);
		}
		
		return (WorldGuardPlugin) wg;
	}
	
	public void createSwords(){
		ItemMeta ds = (ItemMeta) diamondSword.getItemMeta();
		ds.setDisplayName(ChatColor.DARK_RED + "Sword of Boom");
		diamondSword.setItemMeta(ds);
		ItemMeta is = (ItemMeta) ironSword.getItemMeta();
		is.setDisplayName(ChatColor.YELLOW + "Sword of The Goddess");
		ironSword.setItemMeta(is);
		ItemMeta ws = (ItemMeta) woodSword.getItemMeta();
		ws.setDisplayName(ChatColor.GREEN + "Sword of Repelling");
		woodSword.setItemMeta(ws);
		ItemMeta gs = (ItemMeta) goldSword.getItemMeta();
		gs.setDisplayName(ChatColor.DARK_PURPLE + "Sword of Electricity");
		goldSword.setItemMeta(gs);
		ItemMeta ss = (ItemMeta) stoneSword.getItemMeta();
		ss.setDisplayName(ChatColor.DARK_AQUA + "Sword of Mobs");
		stoneSword.setItemMeta(ss);
	}
	
	public void createBookMeta(){
		BookMeta bm = (BookMeta) book.getItemMeta();
		ArrayList<String> lore;
		bm.setAuthor("Unknown");
		bm.setDisplayName(ChatColor.AQUA + "A Magician's Guide To Crafting Magic Tools");
		bm.setPages(Arrays.asList(ChatColor.GOLD + "A Magician's Guide To Crafting Magical Tools" + ChatColor.BLACK + "\nYou have found a book written by the ancient magician's long ago that holds the recipes to secret, and magical items. The magicians created multiples of this book and scatered them all across the world.", 
				"Now it is up to you to keep the life of these magical tools going. Don't share this secret with anyone! In the wrong hands it could be disasterous.", 
				ChatColor.DARK_RED + "Sword of Boom\n\n" + ChatColor.RED + "R = Redstone\n" + ChatColor.BLUE + "D = Diamond Sword\n" + ChatColor.GOLD + "G = Gold" + ChatColor.BLACK + "\n\nRGR\nGDG\nRGR", 
				ChatColor.DARK_PURPLE + "Sword of Electricity\n\n" + ChatColor.GRAY + "N = Nether Quarts\n" + ChatColor.GOLD + "G = Gold Sword\n" + ChatColor.RED + "R = Redstone\n\n" + ChatColor.BLACK + "NRN\nNGN\nNNN",
				ChatColor.YELLOW + "Sword of The Goddess\n\n" + ChatColor.RED + "R = Rose (Poppy)\n" + ChatColor.YELLOW + "D = Dandelion\n" + ChatColor.GRAY + "I = Iron Sword\n\n" + ChatColor.BLACK + "RDR\nDID\nRDR",
				ChatColor.GREEN + "Sword of Repelling\n\n" + ChatColor.BLUE + "B = Water Bucket\n" + ChatColor.DARK_GRAY + "W = Wooden Sword\n" + ChatColor.GREEN + "D = Dirt\n\n" + ChatColor.BLACK + " B \nDWD\n D ",
				ChatColor.DARK_AQUA + "Sword of Mobs\n\n" + ChatColor.WHITE + "B = Bone\n" + ChatColor.DARK_GRAY + "G = Gunpowder\n" + ChatColor.RED + "R = Rotten Flesh\n" + ChatColor.GRAY + "S = Stone Sword\n\n" + ChatColor.BLACK + "BRB\nGSG\nRRR",
				ChatColor.GOLD + "There are still secret magicians somewhere out there! Every time there is a plugin update, the books generated in new chests will be different. You can do the command '/updatebook' to get a new copy. Keep in mind you need permissions to do this."));
		lore = new ArrayList<String>();
		lore.add("Magic Tools Book" + ChatColor.AQUA + " v3.0");
		bm.setLore(lore);
		book.setItemMeta(bm);
	}
	
	public int generateRandomNumberInRange(int max, int min){
		Random r = new Random();
		int i1 = r.nextInt((max - min)+1) + min;
		return i1;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd,
			String label, String[] args){
		Player player = (Player) sender;
		if(label.equalsIgnoreCase("givemeabook")){
			if(player.hasPermission("magictools.givebook")){
				player.getInventory().addItem(book);
			}else{
				player.sendMessage(ChatColor.AQUA + "[MagicTools] " + ChatColor.BLUE + "You need permission to use the command /givemeabook!");
			}
		}else if(label.equalsIgnoreCase("updatebook")){
			if(player.hasPermission("magictools.updatebook")){
				if(player.getInventory().getItemInMainHand().getType().equals(Material.WRITTEN_BOOK) && player.getInventory().getItemInMainHand().getItemMeta().getLore().toString().contains("Magic Tools Book")){
					player.getInventory().setItemInMainHand(null);
					player.getInventory().addItem(book);
					player.sendMessage(ChatColor.AQUA + "[MagicTools] " + ChatColor.BLUE + "Through some mysterious force, your old book has magically disappeared and a new one has formed!");
				}else{
					player.sendMessage(ChatColor.AQUA + "[MagicTools] " + ChatColor.BLUE + "You must hold your old book in your hand, and it must be a previous version!");
				}
			}else{
				player.sendMessage(ChatColor.AQUA + "[MagicTools] " + ChatColor.BLUE + "You need permission to use to command /updatebook!");
			}
		}else if(label.equalsIgnoreCase("reloadconfig")){
			if(player.hasPermission("magictools.relconfig")){
				reloadConfig();
				player.sendMessage(ChatColor.AQUA + "[MagicTools] " + ChatColor.BLUE + "Configuration file has been reloaded.");
			}else{
				player.sendMessage("You don't have permission!");
			}
		}
		return false;
	}

	@EventHandler
	public void onGen(ChunkPopulateEvent e) {
		PluginDescriptionFile pdfFile = this.getDescription();
		BlockState[] tileEnts = e.getChunk().getTileEntities();
		for (BlockState state : tileEnts) {
			if(state != null && state.getType().equals(Material.CHEST) || state.getType() == Material.CHEST){
				Chest chest = (Chest) state;
				chest.getBlockInventory().addItem(book);
				this.logger.info(pdfFile.getName() + " has located a chest and tried to generate a book. The location is below");
				this.logger.info("X: " + chest.getLocation().getBlockX() + ", Y: " + chest.getLocation().getBlockY() + ", Z: " + chest.getLocation().getBlockZ());
			}
		}
	}
}
