package net.devtech.industrialcrust.blocks.ores;

import net.devtech.asyncore.world.server.ServerAccess;
import net.devtech.yajslib.persistent.PersistentRegistry;
import org.bukkit.ChatColor;

public class LeadOreBlock extends AbstractOreBlock {
	protected LeadOreBlock(PersistentRegistry registry, ServerAccess<Object> access) {
		super(registry, access, ChatColor.GRAY+"Lead Ore");
	}

	@Override
	protected String getUrl() {
		return
		"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNTE1MDZhNjI3MDc2ZTk5YzllZTRkODhkNzI5YzQ2ODcxZTMxNTdhZWI3ZjExNWVmY2I3ZjM0MjIxNjMyNTUifX19";
	}
}
