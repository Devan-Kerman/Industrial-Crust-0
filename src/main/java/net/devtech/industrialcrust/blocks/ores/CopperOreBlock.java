package net.devtech.industrialcrust.blocks.ores;

import net.devtech.asyncore.world.server.ServerAccess;
import net.devtech.yajslib.persistent.PersistentRegistry;
import org.bukkit.ChatColor;

public class CopperOreBlock extends AbstractOreBlock {
	public CopperOreBlock(PersistentRegistry registry, ServerAccess<Object> access) {
		super(registry, access, ChatColor.GOLD+"Copper Ore");
	}

	@Override
	protected String getUrl() {
		return
		"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZjBjNzliMThmOGEwYmQ2ZGM4ZmE4MGU0NjRiMmYyZWViM2E4NzdmY2VlYjZhMjc2Mjg0ZTE4YWRjM2NhNmE2In19fQ==";
	}
}
