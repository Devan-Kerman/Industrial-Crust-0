package net.devtech.industrialcrust.items.materials.dust;

import net.devtech.industrialcrust.items.AbstractNamedItem;
import org.bukkit.ChatColor;
import org.bukkit.Material;

public class CoalDust extends AbstractNamedItem {
	@Override protected Material type() {
		return Material.SULPHUR;
	}

	@Override protected String name() {
		return ChatColor.DARK_GRAY + "Coal Dust";
	}
}
