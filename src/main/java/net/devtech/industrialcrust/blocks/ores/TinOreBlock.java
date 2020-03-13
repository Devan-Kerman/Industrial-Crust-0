package net.devtech.industrialcrust.blocks.ores;

import net.devtech.asyncore.world.server.ServerAccess;
import net.devtech.yajslib.persistent.PersistentRegistry;
import org.bukkit.ChatColor;

public class TinOreBlock extends AbstractOreBlock {
	public TinOreBlock(PersistentRegistry registry, ServerAccess<Object> access) {
		super(registry, access, ChatColor.WHITE+"Tin Ore");
	}

	@Override
	protected String getUrl() {
		return "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZTQzOWFlNWE4ZDUzODNkNjY4Y2Q1NGJlYzhmOThjYzZjODI2NTk5YWRmNzlkNmJiNjlmNTFiMGU4YzQ1OGIifX19";
	}
}
