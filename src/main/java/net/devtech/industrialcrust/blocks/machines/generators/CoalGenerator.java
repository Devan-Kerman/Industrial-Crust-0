package net.devtech.industrialcrust.blocks.machines.generators;

import net.devtech.asyncore.AsynCore;
import net.devtech.asyncore.blocks.events.TickEvent;
import net.devtech.asyncore.blocks.world.events.LocalEvent;
import net.devtech.asyncore.gui.components.AFilteredInputInventoryComponent;
import net.devtech.asyncore.gui.components.AHorizontalStatusBar;
import net.devtech.asyncore.gui.components.APanel;
import net.devtech.asyncore.util.Size2i;
import net.devtech.asyncore.util.inv.ArrayInvWrapper;
import net.devtech.asyncore.util.inv.Inventories;
import net.devtech.asyncore.world.server.ServerAccess;
import net.devtech.industrialcrust.blocks.AbstractBlockItem;
import net.devtech.industrialcrust.blocks.power.EnergyProducer;
import net.devtech.industrialcrust.util.BukkitSerializers;
import net.devtech.industrialcrust.util.BurnTime;
import net.devtech.yajslib.annotations.Reader;
import net.devtech.yajslib.annotations.Writer;
import net.devtech.yajslib.io.PersistentInput;
import net.devtech.yajslib.io.PersistentInputStream;
import net.devtech.yajslib.io.PersistentOutput;
import net.devtech.yajslib.io.PersistentOutputStream;
import net.devtech.yajslib.persistent.PersistentRegistry;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import java.awt.Point;
import java.io.IOException;

public class CoalGenerator extends AbstractBlockItem implements EnergyProducer {
	private ItemStack[] burning = new ItemStack[9];
	private Inventory inventory = Bukkit.createInventory(null, 27, "Coal Generator");
	private int power;
	private int burnTime;
	private int maxTime;

	protected CoalGenerator(PersistentRegistry registry, ServerAccess<Object> access) {
		super(registry, access);
	}

	@LocalEvent
	private void tick(TickEvent event) {
		AsynCore.guiManager.resync(this.inventory);
		if (this.burnTime <= 0) {
			ItemStack[] next = {null};
			Inventories.mergeOne(this.burning, next);
			ItemStack burn = next[0];
			if (burn != null) {
				this.maxTime = BurnTime.getBurnTime(burn.getType());
				this.burnTime = this.maxTime;
				AsynCore.guiManager.redraw(this.inventory);
			}
		} else {
			this.burnTime--;
			this.power += 10;
			AsynCore.guiManager.redraw(this.inventory);
		}
	}

	@LocalEvent
	private void gui(PlayerInteractEvent event) {
		if (event.getAction() == Action.RIGHT_CLICK_BLOCK && !event.getPlayer().isSneaking()) {
			event.setCancelled(true);
			APanel panel = new APanel(new Size2i(3, 9));
			panel
			.addComponent(new Point(0, 0), new AFilteredInputInventoryComponent(new ArrayInvWrapper(this.burning),
			                                                                    new Size2i(9, 1), i -> i
			                                                                                           .getType()
			                                                                                           .isFuel()));
			panel
			.addComponent(new Point(0, 1), new AHorizontalStatusBar(new ItemStack(Material.LAVA_BUCKET),
			                                                        new ItemStack(Material.BUCKET), null,
			                                                        () -> this.burnTime / ((double) this.maxTime), 9));
			panel
			.addComponent(new Point(0, 2), new AHorizontalStatusBar(new ItemStack(Material.EXP_BOTTLE),
			                                                        new ItemStack(Material.GLASS_BOTTLE), null,
			                                                        () -> this.power / ((double) this
			                                                                                     .getMaxPower()), 9));
			AsynCore.guiManager.openGui(event.getPlayer(), panel, this.inventory);
		}
	}

	@Override
	protected Material type() {
		return Material.FURNACE;
	}

	@Override
	protected String name() {
		return ChatColor.DARK_GRAY + "Coal Generator " + this.power;
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
	private void write(PersistentOutput out) throws Exception {
		out.writePersistent(this.getLocation());
		out.writeInt(this.power);
		BukkitSerializers.saveItems(this.burning, (PersistentOutputStream) out);
		out.writeInt(this.burnTime);
		out.writeInt(this.maxTime);
	}

	@Reader (895695095093409409L)
	private void read(PersistentInput in) throws Exception {
		this.setLocation((Location) in.readPersistent());
		this.power = in.readInt();
		this.burning = BukkitSerializers.loadItems((PersistentInputStream) in);
		this.burnTime = in.readInt();
		this.maxTime = in.readInt();
	}
}
