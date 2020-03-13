package net.devtech.industrialcrust.blocks.power;

import net.devtech.asyncore.blocks.BlockDataAccess;
import net.devtech.asyncore.blocks.events.TickEvent;
import net.devtech.asyncore.blocks.world.events.LocalEvent;
import net.devtech.industrialcrust.util.Positions;
import org.bukkit.Location;
import org.bukkit.block.BlockFace;

/**
 * an electrical conductor
 */
public interface Conductor extends EnergyDrain, BlockDataAccess, EnergyHolder {
	boolean getRecursiveLock();

	void setRecursiveLock(boolean lock);

	@Override
	default int suck(int power) {
		if (!this.getRecursiveLock()) {
			this.setRecursiveLock(true);
			int remaining = power;
			for (BlockFace current : Positions.getFaces()) {
				Object object = this.getAccess().get(this.getLocation()
				                                         .add(current.getModX(), current.getModY(),
				                                              current.getModZ()));
				if (object instanceof EnergyDrain) {
					int sucked = ((EnergyDrain) object).suck(power);
					remaining -= sucked;
				}
				// debug : if less than one, there is power loss
				if (remaining <= 0) // exit if we've got all the power we need
					break;
			}
			this.setRecursiveLock(false);
			this.setPower(this.getPower() + Math.max(power - remaining, 0));

			return Math.max(power - remaining, 0);
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
