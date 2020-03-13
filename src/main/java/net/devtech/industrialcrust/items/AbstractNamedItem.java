package net.devtech.industrialcrust.items;

import net.devtech.asyncore.items.CustomItem;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public abstract class AbstractNamedItem implements CustomItem {
	@Override
	public ItemStack createBaseStack() {
		ItemStack stack;
		if (this.meta() == -1) { stack = new ItemStack(this.type()); } else {
			stack = new ItemStack(this.type(), 1, (short) 0, (byte) this.meta());
		}
		ItemMeta meta = stack.getItemMeta();
		meta.setDisplayName(this.name());
		meta.addEnchant(Enchantment.DURABILITY, 1, true);
		stack.setItemMeta(meta);
		return stack;
	}

	protected abstract Material type();

	protected int meta() {return -1;}

	protected abstract String name();
}
