package net.devtech.industrialcrust.blocks.machines.cables;

import net.devtech.asyncore.world.server.ServerAccess;
import net.devtech.industrialcrust.blocks.AbstractBlockItem;
import net.devtech.industrialcrust.blocks.power.Conductor;
import net.devtech.yajslib.annotations.Reader;
import net.devtech.yajslib.annotations.Writer;
import net.devtech.yajslib.io.PersistentInput;
import net.devtech.yajslib.io.PersistentOutput;
import net.devtech.yajslib.persistent.PersistentRegistry;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import java.io.IOException;

public class CopperCable extends AbstractBlockItem implements Conductor {
	private boolean recursiveLock;
	private int power;

	protected CopperCable(PersistentRegistry registry, ServerAccess<Object> access) {
		super(registry, access);
	}

	@Override
	public boolean getRecursiveLock() {
		return this.recursiveLock;
	}

	@Override
	public void setRecursiveLock(boolean lock) {
		this.recursiveLock = lock;
	}

	@Override
	public void setPower(int power) {
		this.power = 0;
	}

	@Override
	public int getPower() {
		return this.power;
	}

	@Override
	public int getMaxCurrent() {
		return 128;
	}

	@Override
	protected Material type() {
		return Material.ACACIA_FENCE;
	}

	@Override
	protected String name() {
		return ChatColor.GOLD + "Copper Cable";
	}

	@Writer(54564634563487548L)
	private void write(PersistentOutput out) throws IOException {
		out.writePersistent(this.getLocation());
		out.writeBoolean(this.recursiveLock);
		out.writeInt(this.power);
	}

	@Reader(54564634563487548L)
	private void read(PersistentInput in) throws IOException {
		this.setLocation((Location) in.readPersistent());
		this.recursiveLock = in.readBoolean();
		this.power = in.readInt();
	}
}
