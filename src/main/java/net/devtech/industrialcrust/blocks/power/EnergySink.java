package net.devtech.industrialcrust.blocks.power;

/**
 * represents a block that can have energy pumped into it
 */
public interface EnergySink {
	/**
	 * attempt to add an amount of power to the container
	 * @param power the power
	 * @return the leftover power
	 */
	int add(int power);
}
