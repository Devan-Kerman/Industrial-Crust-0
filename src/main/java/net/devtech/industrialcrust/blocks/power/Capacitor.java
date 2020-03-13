package net.devtech.industrialcrust.blocks.power;

import net.devtech.asyncore.blocks.BlockDataAccess;

/**
 * a capacitor for power, accepts and deposits power
 */
public interface Capacitor extends EnergyDrain, EnergySink, BlockDataAccess, EnergyHolder {
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

	@Override
	default int add(int power) {
		if (this.getPower() + power > this.getMaxPower()) { // overflow
			this.setPower(this.getMaxPower()); // fill
			return this.getMaxPower() - (this.getPower() + power); // return overflow
		} else {
			this.setPower(this.getPower() + power);
			return 0;
		}
	}
}
