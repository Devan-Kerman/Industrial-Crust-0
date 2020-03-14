package net.devtech.industrialcrust.items.materials.dust;

import net.devtech.industrialcrust.items.AbstractNamedItem;
import org.bukkit.ChatColor;
import org.bukkit.Material;

public class ClayDust extends AbstractNamedItem {
	@Override protected Material type() {
		return Material.INK_SACK;
	}

	@Override protected int meta() {
		return 7;
	}

	@Override protected String name() {
		return ChatColor.GRAY+"Clay Dust";
	}
}
