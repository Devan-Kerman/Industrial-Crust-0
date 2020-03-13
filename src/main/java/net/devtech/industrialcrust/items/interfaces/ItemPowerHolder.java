package net.devtech.industrialcrust.items.interfaces;

public interface ItemPowerHolder {
	int getMaxPower();
	int getPower();
	void setPower(int power);

	default void addPower(int power) {
		this.setPower(this.getPower() + power);
	}

	/**
	 * this method finds the amount of power needed
	 * to fill the item with power
	 * @return power required to fill the item up
	 */
	default int powerToFill() {
		return this.getMaxPower() - this.getPower();
	}
}
