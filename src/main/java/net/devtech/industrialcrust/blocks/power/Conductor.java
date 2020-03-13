package net.devtech.industrialcrust.blocks.power;

import net.devtech.asyncore.blocks.BlockDataAccess;
import net.devtech.asyncore.blocks.events.TickEvent;
import net.devtech.asyncore.blocks.world.events.LocalEvent;
import net.devtech.industrialcrust.util.Positions;
import org.bukkit.Location;
import java.util.function.IntUnaryOperator;
import java.util.function.Predicate;
import java.util.function.ToIntBiFunction;

/**
 * an electrical conductor
 */
public interface Conductor extends EnergyDrain, EnergySink, BlockDataAccess, EnergyHolder {
	boolean getRecursiveLock();

	void setRecursiveLock(boolean lock);

	@Override
	default int suck(int power) {
		return this.compute(power, o -> o instanceof EnergyDrain, EnergyDrain::suck, b -> Math.max(power - b, 0));
	}

	@Override
	default int add(int power) {
		return this.compute(power, o -> o instanceof EnergySink, EnergySink::add, b -> b);
	}

	default <T> int compute(int power, Predicate<Object> type, ToIntBiFunction<T, Integer> execute,
	                        IntUnaryOperator operator) {
		if (!this.getRecursiveLock()) {
			this.setRecursiveLock(true);
			int remaining = power;
			for (Location location : Positions.touching(this.getLocation())) {
				Object object = this.getAccess().get(location);
				if (type.test(object)) {
					int sucked = execute.applyAsInt((T) object, power);
					remaining -= sucked;
				}
				// debug : if less than one, there is power loss
				if (remaining <= 0) // exit if we've got all the power we need
					break;
			}
			this.setRecursiveLock(false);
			this.setPower(this.getPower() + Math.max(power - remaining, 0));

			return operator.applyAsInt(remaining);
		}
		return 0;
	}

	/**
	 * for conductors, this value should be reset at the beginning of each tick to 0
	 *
	 * @param power the amount of power transfered through the line at that tick
	 */
	@Override
	void setPower(int power);

	/**
	 * for conductors this value should be reset at the beginning of each tick, and
	 */
	@Override
	int getPower();

	@Override // unneeded for capacitor
	default int getMaxPower() {
		return 0;
	}

	@LocalEvent
	default void tickPower(TickEvent event) {
		if (this.getPower() > this.getMaxCurrent()) // if we're attempting to transfer more power than we can
			this.explode(this.getPower() - this.getMaxCurrent());
		this.setPower(0);
	}

	default void explode(int over) {
		Location location = this.getLocation();
		location.getWorld().createExplosion(location, Math.min(over / 10f, 6));
	}
}
