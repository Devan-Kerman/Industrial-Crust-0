package net.devtech.industrialcrust.items.materials.dust;

import net.devtech.industrialcrust.items.AbstractNamedItem;
import org.bukkit.ChatColor;
import org.bukkit.Material;

public class SiliconDioxide extends AbstractNamedItem {
	@Override protected Material type() {
		return Material.REDSTONE;
	}

	@Override protected String name() {
		return ChatColor.GOLD + "Silicon Dioxide";
	}
}
