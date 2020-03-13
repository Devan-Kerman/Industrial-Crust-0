package net.devtech.industrialcrust.items.upgrades;

import net.devtech.industrialcrust.items.AbstractNamedItem;
import org.bukkit.ChatColor;
import org.bukkit.Material;

public class OverclockerUpgrade extends AbstractNamedItem implements UpgradeItem{
	@Override
	protected Material type() {
		return Material.DIAMOND;
	}

	@Override
	protected String name() {
		return ChatColor.BLUE + "Overclocker Upgrade";
	}
}
