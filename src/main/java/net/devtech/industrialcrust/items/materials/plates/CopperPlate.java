package net.devtech.industrialcrust.items.materials.plates;

import net.devtech.industrialcrust.items.AbstractNamedItem;
import org.bukkit.ChatColor;
import org.bukkit.Material;

public class CopperPlate extends AbstractNamedItem {
	@Override
	protected Material type() {
		return Material.LEATHER;
	}

	@Override
	protected String name() {
		return ChatColor.GOLD+"Copper Plate";
	}
}
