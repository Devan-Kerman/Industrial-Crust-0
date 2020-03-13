package net.devtech.industrialcrust.items.plates;

import net.devtech.industrialcrust.items.AbstractNamedItem;
import org.bukkit.ChatColor;
import org.bukkit.Material;

public class IronPlate extends AbstractNamedItem {
	@Override
	protected Material type() {
		return Material.IRON_PLATE;
	}

	@Override
	protected String name() {
		return ChatColor.GRAY+"Iron Plate";
	}
}
