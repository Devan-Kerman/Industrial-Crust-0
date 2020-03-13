package net.devtech.industrialcrust.blocks.machines.generators;

import net.devtech.asyncore.blocks.events.TickEvent;
import net.devtech.asyncore.blocks.world.events.LocalEvent;
import net.devtech.asyncore.world.server.ServerAccess;
import net.devtech.industrialcrust.blocks.AbstractBlockItem;
import net.devtech.industrialcrust.blocks.power.EnergyProducer;
import net.devtech.yajslib.annotations.Reader;
import net.devtech.yajslib.annotations.Writer;
import net.devtech.yajslib.io.PersistentInput;
import net.devtech.yajslib.io.PersistentOutput;
import net.devtech.yajslib.persistent.PersistentRegistry;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import java.io.IOException;

public class CoalGenerator extends AbstractBlockItem implements EnergyProducer {
	private int power;

	protected CoalGenerator(PersistentRegistry registry, ServerAccess<Object> access) {
		super(registry, access);
	}

	@LocalEvent
	private void tick(TickEvent event) {
		this.power += 5; // todo gui fuel etc.
	}

	@Override
	protected Material type() {
		return Material.FURNACE;
	}

	@Override
	protected String name() {
		return ChatColor.BLACK + "Coal Generator " + this.power;
	}

	@Override
	public void setPower(int power) {
		this.power = power;
	}

	@Override
	public int getPower() {
		return this.power;
	}

	@Override
	public int getMaxPower() {
		return 128;
	}

	@Override
	public int getMaxCurrent() {
		return 128;
	}

	@Writer (895695095093409409L)
	private void write(PersistentOutput out) throws IOException {
		out.writePersistent(this.getLocation());
		out.writeInt(this.power);
	}

	@Reader (895695095093409409L)
	private void read(PersistentInput in) throws IOException {
		this.setLocation((Location) in.readPersistent());
		this.power = in.readInt();
	}
}
