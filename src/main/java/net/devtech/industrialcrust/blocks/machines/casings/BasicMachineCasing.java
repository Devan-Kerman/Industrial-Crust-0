package net.devtech.industrialcrust.blocks.machines.casings;

import net.devtech.asyncore.world.server.ServerAccess;
import net.devtech.industrialcrust.blocks.AbstractBlockItem;
import net.devtech.yajslib.persistent.PersistentRegistry;
import org.bukkit.ChatColor;
import org.bukkit.Material;

public class BasicMachineCasing extends AbstractBlockItem {
	protected BasicMachineCasing(PersistentRegistry registry, ServerAccess<Object> access) {
		super(registry, access);
	}

	@Override
	protected Material type() {
		return Material.IRON_BLOCK;
	}

	@Override
	protected String name() {
		return ChatColor.GRAY+"Basic Machine Casing";
	}
}
