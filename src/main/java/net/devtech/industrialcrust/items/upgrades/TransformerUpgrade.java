package net.devtech.industrialcrust.items.upgrades;

import net.devtech.industrialcrust.items.AbstractNamedItem;
import org.bukkit.ChatColor;
import org.bukkit.Material;

public class TransformerUpgrade extends AbstractNamedItem implements UpgradeItem{
	@Override
	protected Material type() {
		return Material.GOLD_INGOT;
	}

	@Override
	protected String name() {
		return ChatColor.YELLOW+"Transformer Upgrade";
	}
}
