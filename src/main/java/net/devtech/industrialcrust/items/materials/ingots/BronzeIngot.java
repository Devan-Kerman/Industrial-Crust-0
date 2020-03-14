package net.devtech.industrialcrust.items.materials.ingots;

import net.devtech.industrialcrust.items.AbstractNamedItem;
import org.bukkit.ChatColor;
import org.bukkit.Material;

public class BronzeIngot extends AbstractNamedItem {
	@Override
	protected Material type() {
		return Material.GOLD_INGOT;
	}

	@Override
	protected String name() {
		return ChatColor.GOLD+"Bronze Ingot";
	}
}
