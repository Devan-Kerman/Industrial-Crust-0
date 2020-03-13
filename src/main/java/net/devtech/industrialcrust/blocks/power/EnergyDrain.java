package net.devtech.industrialcrust.blocks.power;

/**
 * represents an object who's power can be drained from
 */
public interface EnergyDrain {
	/**
	 * drain an amount of power from this block
	 * returns the amount of actually drained power
	 * @param power the requested amount of power to drain, may be negative
	 * @return never return a number larger than power, or a negative number
	 */
	int suck(int power);
}
