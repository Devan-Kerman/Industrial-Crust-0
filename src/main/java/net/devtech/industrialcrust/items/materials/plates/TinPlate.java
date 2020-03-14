package net.devtech.industrialcrust.items.materials.plates;

import net.devtech.industrialcrust.items.AbstractNamedItem;
import org.bukkit.ChatColor;
import org.bukkit.Material;

public class TinPlate extends AbstractNamedItem {
	@Override
	protected Material type() {
		return Material.PAPER;
	}

	@Override
	protected String name() {
		return ChatColor.GRAY+"Tin Plate";
	}
}
