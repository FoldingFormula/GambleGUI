package me.Folding.GambleGUI;



import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;

import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Chest;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

import io.netty.util.internal.ThreadLocalRandom;
import net.md_5.bungee.api.ChatColor;

public class Main extends JavaPlugin implements Listener {
	
	public static Inventory noobChestViewInventory;
	public static Inventory ironChestViewInventory;
	public static Inventory diamondChestViewInventory;
	
	public static ItemStack[] noobChestItemStack;
	public static ItemStack[] ironChestItemStack;
	public static ItemStack[] diamondChestItemStack;

	@Override
	public void onEnable() {
			noobChestViewInventory = createNoobInventory();
			noobChestItemStack = getNoobItems();
			this.getServer().getPluginManager().registerEvents(this,this);
	}

	public void onDisable() {

	}

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		if(label.equalsIgnoreCase("NoobChest")) {
			if(!(sender instanceof Player)) {
				sender.sendMessage("No Console!");
				return true;
			}
			Player player = (Player) sender;
			dropNoobChest(player);
			
		}
		
		if(label.equalsIgnoreCase("noobchestkey")) {
			if(!(sender instanceof Player)) {
				sender.sendMessage("No Console!");
				return true;
			}
			Player player = (Player) sender;
			player.getInventory().addItem(getNoobChestKey());
			return true;
		}
			
		
		
		
		
		return false;
	}
 
	public ItemStack getNoobChestKey() {
		ItemStack item = new ItemStack(Material.HOPPER);
		 ItemMeta meta = item.getItemMeta();
		 meta.setDisplayName(ChatColor.DARK_GRAY +"Noob Crate Key!");
		 meta.addEnchant(Enchantment.DURABILITY, 1, true);
		 List<String> lore = new ArrayList<String>();
		 lore.add("Use this to open the noob crate!");
		 meta.setLore(lore);
		 meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		 item.setItemMeta(meta);
		 
		 
		 
		 
		return item;
		
	}
	
	
	private void dropNoobChest(Player player) {
		Location chest = null;
		if(player.getFacing()== BlockFace.NORTH)
			chest = player.getLocation().add(0,0,-1);
		if(player.getFacing()== BlockFace.SOUTH)
			chest = player.getLocation().add(0,0,1);
		if(player.getFacing()== BlockFace.EAST)
			chest = player.getLocation().add(1,0,0);
		if(player.getFacing()== BlockFace.WEST)
			chest = player.getLocation().add(-1,0,0);
		
		if(chest.getBlock().getType() != Material.AIR) {
			player.sendMessage(ChatColor.DARK_RED + "Cannot put chest here!");
			return;
		}
		
		Block block = chest.getBlock();
		block.setType(Material.CHEST); //set the air to chest
		Chest c = (Chest) block.getState(); //get chest 
		c.setCustomName("Noob Chest");
		c.update();
		

	}
	
	
	private ItemStack[] getNoobItems() {
		ItemStack[] items = new ItemStack[10];
				items[0] = new ItemStack(Material.COAL, 16);
				items[1] = new ItemStack(Material.LEATHER_HELMET) ;
				items[2] = new ItemStack(Material.LEATHER_CHESTPLATE);
				items[3] = new ItemStack(Material.LEATHER_LEGGINGS);
				items[4] = new ItemStack(Material.LEATHER_BOOTS);
				items[6] = new ItemStack(Material.COOKED_BEEF, 32);
				items[7] = new ItemStack(Material.COOKED_BEEF, 32);
				items[8] = new ItemStack(Material.COOKED_BEEF, 32);
				items[9] = new ItemStack(Material.COOKED_BEEF, 32);
				

		return items;
	}
		
		 
	private static Inventory createNoobInventory() {
		Inventory inv = Bukkit.createInventory(null, 9, ChatColor.GOLD + "Noob Crate Contents!");
		

		
		ItemStack item0 = new ItemStack(Material.COAL, 16);
		inv.setItem(0, item0);
		
		ItemStack item1 = new ItemStack(Material.LEATHER_HELMET);
		inv.setItem(1, item1);
		
		ItemStack item2 = new ItemStack(Material.LEATHER_CHESTPLATE);
		inv.setItem(2, item2);
		
		ItemStack item3 = new ItemStack(Material.LEATHER_LEGGINGS, 3);
		inv.setItem(3, item3);
		
		ItemStack item4 = new ItemStack(Material.LEATHER_BOOTS, 3);
		inv.setItem(4, item4);
		
		ItemStack item5 = new ItemStack(Material.LEATHER_HELMET, 3);
		inv.setItem(5, item5);
		
		ItemStack item6 = new ItemStack(Material.LEATHER_HELMET, 3);
		inv.setItem(6, item6);
		
		ItemStack item7 = new ItemStack(Material.LEATHER_HELMET, 3);
		inv.setItem(7, item7);
		
		ItemStack item8 = new ItemStack(Material.LEATHER_HELMET, 3);
		inv.setItem(8, item8);
		
		
		
		
		
		
		return inv;
		
		
	}
		
	/*
	 Correct: 
	 Block chestBlock = event.getClickedBlock();
   	 Chest chestState = (Chest) chestBlock.getState();
	 if(chestState.getCustomName().contains("Noob Chest"))
	 
	 
	 Incorrect:
	 
	 Chest chest = (Chest) event.getClickedBlock();
	 if(chest.getCustomName().contains("Noob Chest"))
	 
	 */
	
	
	
	    
	
	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent event) { //If right clicking crate: start gambling, if left clicking: view contents
		
		Player p = event.getPlayer(); //get player
		
		if(event.getClickedBlock() == null) {
			return;
		}
		
		if (event.getClickedBlock().getType() != Material.CHEST) {
			return;
		}
		
		if (!(event.getClickedBlock().getState() instanceof Chest)) {
			return;
		}
		
		event.setCancelled(true); //stop chest from being opened
		
		if (event.getAction() == Action.RIGHT_CLICK_BLOCK)  { //right clicking on chest 
			
			Block chestBlock = event.getClickedBlock();
			Chest chestState = (Chest) chestBlock.getState();
			
			if(chestState.getCustomName().contains("Noob Chest")) {
				p.sendMessage("registered right click on noobchest!");
				//-------------------------------------- COMMENTING THIS AREA OUT GETS RID OF POINTER EXCEPTION	  
					if(p.getInventory().getItemInMainHand() != null) 
						if(p.getInventory().getItemInMainHand().hasItemMeta())
							if(p.getInventory().getItemInMainHand().getItemMeta().getDisplayName().contains("Noob Crate Key!")) 
						   		if(p.getInventory().getItemInMainHand().getItemMeta().hasLore()) {
						   		
						   			p.sendMessage("registered right click on noobchest with key!");
						   			
						   			int randomItem = ThreadLocalRandom.current().nextInt(noobChestItemStack.length);
						   			p.getInventory().addItem(noobChestItemStack[randomItem]);
						   			
						   			
						   		} 
					   		
					//-------------------------------------- COMMENTING THIS AREA OUT GETS RID OF POINTER EXCEPTION	   	
					
				   		
			}
			return;
			
		}
		//CHEST NAME: "Noob Chest"....... INVENTORY NAME: "Noob Crate Contents!"
		if (event.getAction() == Action.LEFT_CLICK_BLOCK) {  // left clicking on chest
		
			
			Block chestBlock = event.getClickedBlock();
			Chest chestState = (Chest) chestBlock.getState();
			
			
			if(chestState.getCustomName().contains("Noob Chest")) {
				p.sendMessage("registered left click on noob chest!");
				p.openInventory(noobChestViewInventory); //view contents
				return;
			}
			
		}
		
		
		
	}
	
	
	
	
	@EventHandler //VIEWING CONTENTS
	public void onClick(InventoryClickEvent event) { //clicking in the noobchest
		if(!event.getView().getTitle().contains("Noob Crate Contents!")) { 
			return;
		}
		
		if(event.getCurrentItem() == null) {
			return;
		}
		if(event.getCurrentItem().getItemMeta() == null) {
			return;
		}
		//Player player = (Player) event.getWhoClicked();
		event.setCancelled(true); //doesnt give the item if clicked on
		
		if(event.getClickedInventory().getType() == InventoryType.PLAYER) //exit out if player clicks in own inventory
			return;


		}
	
}
	
	
	
