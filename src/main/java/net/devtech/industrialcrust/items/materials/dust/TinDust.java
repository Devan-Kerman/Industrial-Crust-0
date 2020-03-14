package net.devtech.industrialcrust.items.materials.dust;

import net.devtech.industrialcrust.items.AbstractNamedItem;
import org.bukkit.ChatColor;
import org.bukkit.Material;

public class TinDust extends AbstractNamedItem {
	@Override protected Material type() {
		return Material.SUGAR;
	}

	@Override protected String name() {
		return ChatColor.GRAY+"Tin Dust";
	}
}
