package net.devtech.industrialcrust.items.materials.ingots;

import net.devtech.industrialcrust.items.AbstractNamedItem;
import org.bukkit.ChatColor;
import org.bukkit.Material;

public class CopperIngot extends AbstractNamedItem {
	@Override
	protected Material type() {
		return Material.CLAY_BRICK;
	}

	@Override
	protected String name() {
		return ChatColor.GOLD+"Copper Ingot";
	}
}
