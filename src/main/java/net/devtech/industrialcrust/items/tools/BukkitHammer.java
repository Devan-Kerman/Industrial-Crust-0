package net.devtech.industrialcrust.items.tools;

import net.devtech.industrialcrust.items.AbstractNamedItem;
import org.bukkit.ChatColor;
import org.bukkit.Material;

public class BukkitHammer extends AbstractNamedItem {
	@Override
	protected Material type() {
		return Material.IRON_SPADE;
	}

	@Override
	protected String name() {
		return ChatColor.GRAY + "Bukkit Hammer";
	}
}
