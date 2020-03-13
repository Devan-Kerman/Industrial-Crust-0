package net.devtech.industrialcrust.items.plates;

import net.devtech.asyncore.items.CanInteractWith;
import net.devtech.industrialcrust.items.AbstractNamedItem;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.event.player.PlayerInteractEvent;

public class TinPlate extends AbstractNamedItem {
	@Override
	protected Material type() {
		return Material.PAPER;
	}

	@Override
	protected String name() {
		return ChatColor.GRAY+"Tin Plate";
	}
}
