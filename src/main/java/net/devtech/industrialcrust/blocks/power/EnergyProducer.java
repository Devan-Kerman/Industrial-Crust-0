package net.devtech.industrialcrust.blocks.power;

import net.devtech.asyncore.blocks.BlockDataAccess;

public interface EnergyProducer extends BlockDataAccess, EnergyDrain, EnergyHolder {
	@Override
	default int suck(int power) {
		int toTake = Math.min(power, this.getMaxCurrent());
		if (toTake > this.getPower()) {
			int old = this.getPower();
			this.setPower(0);
			return old;
		} else {
			this.setPower(this.getPower() - toTake);
			return toTake;
		}
	}
}
