package net.devtech.industrialcrust.items.materials.dust;

import net.devtech.industrialcrust.items.AbstractNamedItem;
import org.bukkit.ChatColor;
import org.bukkit.Material;

public class GoldDust extends AbstractNamedItem {
	@Override protected Material type() {
		return Material.GLOWSTONE_DUST;
	}

	@Override protected String name() {
		return ChatColor.YELLOW+"Gold Dust";
	}
}
