package net.devtech.industrialcrust.blocks;

import net.devtech.asyncore.blocks.AbstractBlock;
import net.devtech.asyncore.blocks.events.PlaceEvent;
import net.devtech.asyncore.blocks.world.events.LocalEvent;
import net.devtech.asyncore.items.blocks.BlockItem;
import net.devtech.asyncore.world.server.ServerAccess;
import net.devtech.industrialcrust.hwla.HasHWWLAName;
import net.devtech.yajslib.persistent.PersistentRegistry;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

/**
 * a block who's item counterpart is the same type and name as the block itself
 */
public abstract class AbstractBlockItem extends AbstractBlock implements BlockItem, HasHWWLAName {
	protected AbstractBlockItem(PersistentRegistry registry, ServerAccess<Object> access) {
		super(registry, access);
	}

	@LocalEvent
	public final void place(PlaceEvent event) {
		event.getBlock().setType(this.type());
	}

	@Override
	public ItemStack createBaseStack() {
		ItemStack stack = new ItemStack(this.type(), 1, (short) 0, (byte) this.meta());
		ItemMeta meta = stack.getItemMeta();
		meta.setDisplayName(this.name());
		stack.setItemMeta(meta);
		return this.modify(stack);
	}

	/**
	 * make any last minute adjustments to the stack before it is finalized
	 *
	 * @param stack the adjusted stack
	 * @return the stack to return
	 * @see #createBaseStack()
	 */
	protected ItemStack modify(ItemStack stack) {
		return stack;
	}

	/**
	 * @return the type of the block
	 */
	protected abstract Material type();

	/**
	 * @return the metadata value of this object, if any
	 */
	protected int meta() {
		return -1;
	}

	/**
	 * the name of the block and the item
	 */
	protected abstract String name();

	@Override
	public String getName() {
		return this.name();
	}
}
