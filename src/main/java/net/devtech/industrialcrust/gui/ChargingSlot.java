package net.devtech.industrialcrust.gui;

import net.devtech.asyncore.gui.components.AInventoryComponent;
import net.devtech.asyncore.util.Size2i;
import net.devtech.asyncore.util.inv.InvWrapper;

public class ChargingSlot extends AInventoryComponent {
	public ChargingSlot(InvWrapper sync) {
		super(sync, new Size2i(1, 1));
	}
}
