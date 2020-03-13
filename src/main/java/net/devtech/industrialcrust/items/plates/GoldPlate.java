package net.devtech.industrialcrust.items.plates;

import net.devtech.industrialcrust.items.AbstractNamedItem;
import org.bukkit.ChatColor;
import org.bukkit.Material;

public class GoldPlate extends AbstractNamedItem {
	@Override
	protected Material type() {
		return Material.GOLD_PLATE;
	}

	@Override
	protected String name() {
		return ChatColor.GOLD+"Gold Plate";
	}
}
