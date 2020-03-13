package net.devtech.industrialcrust.items.materials;

import net.devtech.asyncore.items.CanInteractWith;
import net.devtech.asyncore.items.CustomItem;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class Resin implements CustomItem, CanInteractWith {
	@Override
	public ItemStack createBaseStack() {
		ItemStack stack = new ItemStack(Material.FIREBALL);
		ItemMeta meta = stack.getItemMeta();
		meta.setDisplayName(ChatColor.GOLD+"Sticky Resin");
		stack.setItemMeta(meta);
		return stack;
	}

	@Override
	public void interact(PlayerInteractEvent event) { // prevent people from placing
		event.setCancelled(true);
	}
}
