package net.devtech.industrialcrust.items.ingots;

import net.devtech.industrialcrust.items.AbstractNamedItem;
import org.bukkit.ChatColor;
import org.bukkit.Material;

public class RefinedIronIngot extends AbstractNamedItem {
	@Override
	protected Material type() {
		return Material.IRON_INGOT;
	}

	@Override
	protected String name() {
		return ChatColor.BLUE+"Refined Iron Ingot";
	}
}
