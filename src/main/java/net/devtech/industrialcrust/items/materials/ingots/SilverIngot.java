package net.devtech.industrialcrust.items.materials.ingots;

import net.devtech.industrialcrust.items.AbstractNamedItem;
import org.bukkit.ChatColor;
import org.bukkit.Material;

public class SilverIngot extends AbstractNamedItem {
	@Override
	protected Material type() {
		return Material.IRON_INGOT;
	}

	@Override
	protected String name() {
		return ChatColor.WHITE+ "Silver Ingot";
	}
}
