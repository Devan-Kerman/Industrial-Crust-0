package net.devtech.industrialcrust.items.materials.plates;

import net.devtech.industrialcrust.items.AbstractNamedItem;
import net.devtech.industrialcrust.items.interfaces.CanConsume;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.event.player.PlayerItemConsumeEvent;

public class BronzePlate extends AbstractNamedItem implements CanConsume {
	@Override
	protected Material type() {
		return Material.PUMPKIN_PIE;
	}

	@Override
	protected String name() {
		return ChatColor.GOLD+"Bronze Plate";
	}

	@Override
	public void consume(PlayerItemConsumeEvent event) {
		event.setCancelled(true);
	}
}
