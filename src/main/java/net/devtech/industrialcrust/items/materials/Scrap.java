package net.devtech.industrialcrust.items.materials;

import net.devtech.asyncore.items.CustomItem;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class Scrap implements CustomItem {
	@Override
	public ItemStack createBaseStack() {
		ItemStack stack = new ItemStack(Material.INK_SACK, 1, (short) 0, (byte) 14);
		ItemMeta meta = stack.getItemMeta();
		meta.setDisplayName(ChatColor.GOLD+"Scrap");
		stack.setItemMeta(meta);
		return stack;
	}
}
