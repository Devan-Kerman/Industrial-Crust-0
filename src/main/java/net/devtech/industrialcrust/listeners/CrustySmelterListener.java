package net.devtech.industrialcrust.listeners;

import net.devtech.asyncore.AsynCore;
import net.devtech.asyncore.items.CustomItem;
import net.devtech.asyncore.items.CustomItemFactory;
import net.devtech.industrialcrust.blocks.ores.CopperOreBlock;
import net.devtech.industrialcrust.blocks.ores.LeadOreBlock;
import net.devtech.industrialcrust.blocks.ores.TinOreBlock;
import net.devtech.industrialcrust.blocks.ores.UraniumOreBlock;
import net.devtech.industrialcrust.items.materials.ingots.CopperIngot;
import net.devtech.industrialcrust.items.materials.ingots.LeadIngot;
import net.devtech.industrialcrust.items.materials.ingots.TinIngot;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityCombustEvent;
import org.bukkit.inventory.ItemStack;
import java.util.HashMap;
import java.util.Map;

public class CrustySmelterListener implements Listener {
	public static final Map<Class<?>, Class<? extends CustomItem>> SMELTS = new HashMap<>();
	static {
		SMELTS.put(CopperOreBlock.class, CopperIngot.class);
		SMELTS.put(LeadOreBlock.class, LeadIngot.class);
		SMELTS.put(TinOreBlock.class, TinIngot.class);
	}

	@EventHandler
	private void smeltCustomItem(EntityCombustEvent event) {
		Entity entity = event.getEntity();
		if(entity instanceof Item) {
			Item item = (Item) entity;
			ItemStack stack = item.getItemStack();
			Object object = CustomItemFactory.from(stack, AsynCore.persistentRegistry);
			if(object != null) {
				Class<? extends CustomItem> type = SMELTS.get(object.getClass());
				if(type != null) {
					ItemStack newStack = CustomItemFactory.createNew(AsynCore.persistentRegistry, type);
					newStack.setAmount(stack.getAmount());
					item.getWorld().dropItemNaturally(item.getLocation(), newStack).setInvulnerable(true);
					item.remove();
				}
			}
		}
	}
}
