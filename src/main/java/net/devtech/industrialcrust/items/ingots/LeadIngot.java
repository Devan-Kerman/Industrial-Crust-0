package net.devtech.industrialcrust.items.ingots;

import net.devtech.industrialcrust.items.AbstractNamedItem;
import org.bukkit.ChatColor;
import org.bukkit.Material;

public class LeadIngot extends AbstractNamedItem {
	@Override
	protected Material type() {
		return Material.INK_SACK;
	}

	@Override
	protected int meta() {
		return 6;
	}

	@Override
	protected String name() {
		return ChatColor.DARK_GRAY+"Lead Ingot";
	}
}
