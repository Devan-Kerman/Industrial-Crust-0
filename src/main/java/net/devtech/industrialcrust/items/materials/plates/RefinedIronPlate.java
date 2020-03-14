package net.devtech.industrialcrust.items.materials.plates;

import net.devtech.asyncore.items.CanInteractWith;
import net.devtech.industrialcrust.items.AbstractNamedItem;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.event.player.PlayerInteractEvent;

public class RefinedIronPlate extends AbstractNamedItem implements CanInteractWith {
	@Override
	protected Material type() {
		return Material.MAP;
	}

	@Override
	protected String name() {
		return ChatColor.BLUE+"Refined Iron Plate";
	}

	@Override
	public void interact(PlayerInteractEvent event) {
		event.setCancelled(true);
	}
}
