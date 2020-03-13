package net.devtech.industrialcrust.blocks.power;

import net.devtech.asyncore.blocks.BlockDataAccess;
import net.devtech.asyncore.blocks.events.TickEvent;
import net.devtech.asyncore.blocks.world.events.LocalEvent;
import net.devtech.industrialcrust.util.Positions;
import org.bukkit.Location;
import org.bukkit.block.BlockFace;

public interface EnergyConsumer extends BlockDataAccess, EnergyHolder {
	@LocalEvent
	default void tick(TickEvent event) {
		this.tick();
	}

	default void tick() {
		int toFill = Math.min(this.getMaxPower()-this.getPower(), this.getMaxCurrent());
		for (BlockFace face : Positions.getFaces()) {
			Location location = this.getLocation().add(face.getModX(), face.getModY(), face.getModZ());
			Object object = this.getAccess().get(location);
			if(object instanceof EnergyDrain) {
				EnergyDrain drain = (EnergyDrain) object;
				int drained = drain.suck(toFill);
				this.setPower(this.getPower() + drained);
				toFill-=drained;
				// debug: if less than 0, loss of power
				if(toFill <= 0)
					break;
			}
		}
	}
}
