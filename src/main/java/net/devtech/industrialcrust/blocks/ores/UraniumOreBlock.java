package net.devtech.industrialcrust.blocks.ores;

import net.devtech.asyncore.world.server.ServerAccess;
import net.devtech.yajslib.persistent.PersistentRegistry;
import org.bukkit.ChatColor;

public class UraniumOreBlock extends AbstractOreBlock {
	protected UraniumOreBlock(PersistentRegistry registry, ServerAccess<Object> access) {
		super(registry, access, ChatColor.DARK_GREEN+"Uranium Ore");
	}

	@Override
	protected String getUrl() {
		return "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZWYxZDA2MmU4N2RlNDU2NmRjMzEzMmNiYzZkNmU3ZTM4NjkzZTE2ZGM3ZmUxMDVkYzE1NWRlNzA5M2M3YzIzIn19fQ==";
	}
}
