package net.devtech.industrialcrust.items.upgrades;

import net.devtech.industrialcrust.items.AbstractNamedItem;
import org.bukkit.ChatColor;
import org.bukkit.Material;

public class EnergyStorageUpgrade extends AbstractNamedItem implements UpgradeItem{
	@Override
	protected Material type() {
		return Material.REDSTONE;
	}

	@Override
	protected String name() {
		return ChatColor.RED + "Storage Upgrade";
	}
}
