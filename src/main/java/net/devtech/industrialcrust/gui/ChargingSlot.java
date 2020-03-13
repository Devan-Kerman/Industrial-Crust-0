package net.devtech.industrialcrust.gui;

import net.devtech.asyncore.AsynCore;
import net.devtech.asyncore.gui.components.AFilteredInputInventoryComponent;
import net.devtech.asyncore.items.CustomItemFactory;
import net.devtech.asyncore.util.Size2i;
import net.devtech.asyncore.util.inv.InvWrapper;
import net.devtech.industrialcrust.items.interfaces.ItemPowerHolder;

public class ChargingSlot extends AFilteredInputInventoryComponent {
	public ChargingSlot(InvWrapper sync) {
		super(sync, new Size2i(1, 1), i -> {
			Object object = CustomItemFactory.from(i, AsynCore.persistentRegistry);
			return object instanceof ItemPowerHolder;
		});
	}
}
