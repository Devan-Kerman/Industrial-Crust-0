package net.devtech.industrialcrust.listeners;

import net.devtech.asyncore.AsynCore;
import net.devtech.asyncore.items.CustomItem;
import net.devtech.asyncore.items.CustomItemFactory;
import net.devtech.industrialcrust.blocks.ores.CopperOreBlock;
import net.devtech.industrialcrust.blocks.ores.LeadOreBlock;
import net.devtech.industrialcrust.blocks.ores.TinOreBlock;
import net.devtech.industrialcrust.blocks.ores.UraniumOreBlock;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import java.util.Random;

public class OreListener implements Listener {
	private static final Random RANDOM = new Random();

	@EventHandler
	private void haha(BlockBreakEvent event) {
		Block block = event.getBlock();
		if (block.getType() == Material.IRON_ORE && RANDOM.nextBoolean()) { // 50% of iron will drop custom ores
			event.setDropItems(false);
			block.getWorld().dropItemNaturally(block.getLocation(), CustomItemFactory
			                                                        .createNew(AsynCore.persistentRegistry, this.getOre()));
		}
	}

	private static final Class<? extends CustomItem>[] ORES = new Class[]{CopperOreBlock.class, LeadOreBlock.class,
	                                                                      TinOreBlock.class,
	                                                                      UraniumOreBlock.class
	};

	private Class<? extends CustomItem> getOre() {
		return ORES[RANDOM.nextInt(ORES.length)];
	}
}
