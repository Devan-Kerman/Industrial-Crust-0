package net.devtech.industrialcrust.blocks.ores;

import net.devtech.asyncore.blocks.AbstractBlock;
import net.devtech.asyncore.blocks.events.PlaceEvent;
import net.devtech.asyncore.blocks.world.events.LocalEvent;
import net.devtech.asyncore.items.blocks.BlockItem;
import net.devtech.asyncore.world.server.ServerAccess;
import net.devtech.industrialcrust.util.SkullCreator;
import net.devtech.yajslib.persistent.PersistentRegistry;
import org.bukkit.ChatColor;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Skull;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public abstract class AbstractOreBlock extends AbstractBlock implements BlockItem {
	private final String name;
	protected AbstractOreBlock(PersistentRegistry registry, ServerAccess<Object> access, String name) {
		super(registry, access);
		this.name = name;
	}

	@Override
	public ItemStack createBaseStack() {
		ItemStack skull = SkullCreator
		                  .itemFromBase64(this.getUrl());
		ItemMeta meta = skull.getItemMeta();
		meta.setDisplayName(this.name);
		meta.addEnchant(Enchantment.DURABILITY, 10, true);
		skull.setItemMeta(meta);
		return skull;
	}

	@LocalEvent
	public final void place(PlaceEvent event) {
		SkullCreator.blockWithBase64(event
		                             .getBlock(),
		                             this.getUrl());
	}

	protected abstract String getUrl();
}
