package net.devtech.industrialcrust.items.materials.dust;

import net.devtech.industrialcrust.items.AbstractNamedItem;
import org.bukkit.ChatColor;
import org.bukkit.Material;

public class BronzeDust extends AbstractNamedItem {
	@Override protected Material type() {
		return Material.BLAZE_POWDER;
	}

	@Override protected String name() {
		return ChatColor.GOLD+"Bronze Dust";
	}
}
