package net.devtech.industrialcrust.items.materials.dust;

import net.devtech.industrialcrust.items.AbstractNamedItem;
import org.bukkit.ChatColor;
import org.bukkit.Material;

public class IronDust extends AbstractNamedItem {
	@Override protected Material type() {
		return Material.CLAY;
	}

	@Override protected String name() {
		return ChatColor.DARK_GRAY+"Iron Dust";
	}
}
