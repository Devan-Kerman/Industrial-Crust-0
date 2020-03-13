package net.devtech.industrialcrust.items.tools;

import net.devtech.asyncore.AsynCore;
import net.devtech.asyncore.items.CanInteractWith;
import net.devtech.asyncore.items.CustomItem;
import net.devtech.asyncore.items.CustomItemFactory;
import net.devtech.industrialcrust.items.materials.Resin;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import java.util.Random;

public class TreeTap implements CustomItem, CanInteractWith {
	private static final Random RANDOM = new Random();
	@Override
	public ItemStack createBaseStack() {
		ItemStack stack = new ItemStack(Material.WOOD_HOE);
		ItemMeta meta = stack.getItemMeta();
		meta.setDisplayName(ChatColor.GRAY+"Tree Tap");
		meta.addEnchant(Enchantment.LOOT_BONUS_MOBS, 1, true);
		stack.setItemMeta(meta);
		return stack;
	}

	@Override
	public void interact(PlayerInteractEvent event) {
		Block block = event.getClickedBlock();
		if(block != null && (block.getType() == Material.LOG || block.getType() == Material.LOG_2)) { // sap
			if(RANDOM.nextDouble() < .3) {
				block.setType(Material.AIR);
				Location location = block.getLocation();
				location.getWorld().dropItemNaturally(location, CustomItemFactory.createNew(AsynCore.persistentRegistry, Resin.class));
			}
		}
	}
}
