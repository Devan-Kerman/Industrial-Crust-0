package net.devtech.industrialcrust.blocks.machines.batteries;

import net.devtech.asyncore.AsynCore;
import net.devtech.asyncore.blocks.events.TickEvent;
import net.devtech.asyncore.blocks.world.events.LocalEvent;
import net.devtech.asyncore.gui.components.AButton;
import net.devtech.asyncore.gui.components.AHorizontalStatusBar;
import net.devtech.asyncore.gui.components.APanel;
import net.devtech.asyncore.gui.graphics.InventoryGraphics;
import net.devtech.asyncore.items.CustomItemFactory;
import net.devtech.asyncore.util.Size2i;
import net.devtech.asyncore.util.inv.ArrayInvWrapper;
import net.devtech.asyncore.world.server.ServerAccess;
import net.devtech.industrialcrust.blocks.AbstractBlockItem;
import net.devtech.industrialcrust.blocks.power.EnergyConsumer;
import net.devtech.industrialcrust.blocks.power.EnergyProducer;
import net.devtech.industrialcrust.gui.ChargingSlot;
import net.devtech.industrialcrust.items.interfaces.ItemPowerHolder;
import net.devtech.yajslib.annotations.Reader;
import net.devtech.yajslib.annotations.Writer;
import net.devtech.yajslib.io.PersistentInput;
import net.devtech.yajslib.io.PersistentOutput;
import net.devtech.yajslib.persistent.PersistentRegistry;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import java.awt.Point;
import java.io.IOException;

public class BatteryBocs extends AbstractBlockItem implements EnergyConsumer, EnergyProducer {
	private static final ItemStack DUMP_POWER = new ItemStack(Material.WOOL, 1, (short) 0, (byte) 14);
	private static final ItemStack CHARGE = new ItemStack(Material.WOOL, 1, (short) 0, (byte) 5);
	private static final ItemStack FILLER = new ItemStack(Material.IRON_INGOT);

	static {
		ItemMeta meta = DUMP_POWER.getItemMeta();
		meta.setDisplayName(ChatColor.RED + "Dump Power");
		DUMP_POWER.setItemMeta(meta);

		meta = CHARGE.getItemMeta();
		meta.setDisplayName(ChatColor.RED + "Charge Item");
		CHARGE.setItemMeta(meta);
	}

	private ItemStack[] charger = {null};
	private Inventory inventory = Bukkit.createInventory(null, 18, "Battery Box");
	private int power;

	protected BatteryBocs(PersistentRegistry registry, ServerAccess<Object> access) {
		super(registry, access);
	}

	@LocalEvent
	private void gui(PlayerInteractEvent event) {
		if (event.getAction() == Action.RIGHT_CLICK_BLOCK && !event.getPlayer().isSneaking()) {
			event.setCancelled(true);
			APanel panel = new APanel(new Size2i(9, 2)) {
				@Override
				public void draw(InventoryGraphics inventory) {
					super.draw(inventory);
					// dump power slot
					inventory.setItem(FILLER, 1, 0);
					inventory.setItem(FILLER, 2, 0);
					inventory.setItem(CHARGE, 3, 0);
					// charging slot
					inventory.setItem(CHARGE, 5, 0);
					inventory.setItem(FILLER, 6, 0);
					inventory.setItem(FILLER, 7, 0);
					inventory.setItem(FILLER, 8, 0);
				}
			};
			panel.addComponent(new Point(0, 0), new AButton(DUMP_POWER, () -> this.power = 0));
			panel.addComponent(new Point(4, 0), new ChargingSlot(new ArrayInvWrapper(this.charger)));
			panel.addComponent(new Point(0, 1), new AHorizontalStatusBar(new ItemStack(Material.EXP_BOTTLE),
			                                                        new ItemStack(Material.GLASS_BOTTLE), null,
			                                                        () -> this.power / ((double) this
			                                                                                     .getMaxPower()), 9));
			AsynCore.guiManager.openGui(event.getPlayer(), panel, this.inventory);
		}
	}

	@LocalEvent
	private void refresh(TickEvent event) {
		AsynCore.guiManager.resync(this.inventory);
		if (this.power > 0) {
			ItemStack current = this.charger[0];
			if (current != null) {
				CustomItemFactory.modify(current, AsynCore.persistentRegistry, i -> {
					if (i instanceof ItemPowerHolder) {
						ItemPowerHolder holder = (ItemPowerHolder) i;
						holder.addPower(this.suck(holder.powerToFill()));
					}
				});
			}
		}
		AsynCore.guiManager.redraw(this.inventory);
	}

	private boolean lock;

	@Override
	public void tickEnergy() {
		this.lock = true;
		EnergyConsumer.super.tickEnergy();
		this.lock = false;
	}

	@Override
	public int suck(int power) {
		if (this.lock)
			return 0;
		else
			return EnergyProducer.super.suck(power);
	}

	@Override
	protected Material type() {
		return Material.WORKBENCH;
	}

	@Override
	protected String name() {
		return ChatColor.GRAY + "Battery Box Power: " + this.power;
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
		return 4096;
	}

	@Override
	public int getMaxCurrent() {
		return 128;
	}

	@Writer (3435434544343433L)
	private void write(PersistentOutput out) throws IOException {
		out.writePersistent(this.getLocation());
		out.writeInt(this.power);
	}

	@Reader (3435434544343433L)
	private void read(PersistentInput in) throws IOException {
		this.setLocation((Location) in.readPersistent());
		this.power = in.readInt();
	}

}
