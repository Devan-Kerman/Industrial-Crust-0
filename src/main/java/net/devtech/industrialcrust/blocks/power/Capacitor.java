package net.devtech.industrialcrust.blocks.power;

import net.devtech.asyncore.blocks.BlockDataAccess;
import org.bukkit.block.BlockFace;

/**
 * a capacitor for power, accepts and deposits power
 */
public interface Capacitor extends EnergyDrain, BlockDataAccess, EnergyHolder, EnergyConsumer {
	@Override
	default int suck(int power) {
		if (this.getPower() < power) {
			int old = this.getPower();
			this.setPower(0);
			return old;
		} else {
			this.setPower(this.getPower() - power);
			return power;
		}
	}
}
