package net.devtech.industrialcrust.blocks.machines;

import net.devtech.asyncore.AsynCore;
import net.devtech.asyncore.blocks.AbstractBlock;
import net.devtech.asyncore.blocks.events.DestroyEvent;
import net.devtech.asyncore.blocks.events.PlaceEvent;
import net.devtech.asyncore.blocks.events.TickEvent;
import net.devtech.asyncore.blocks.world.events.LocalEvent;
import net.devtech.asyncore.gui.components.AFilteredInputInventoryComponent;
import net.devtech.asyncore.gui.components.AHorizontalStatusBar;
import net.devtech.asyncore.items.CustomItemFactory;
import net.devtech.asyncore.items.blocks.BlockItem;
import net.devtech.asyncore.util.Size2i;
import net.devtech.asyncore.util.inv.ArrayInvWrapper;
import net.devtech.asyncore.world.server.ServerAccess;
import net.devtech.industrialcrust.blocks.power.EnergyConsumer;
import net.devtech.industrialcrust.gui.ABackgroundPanel;
import net.devtech.industrialcrust.gui.AEnergyBar;
import net.devtech.industrialcrust.items.upgrades.EnergyStorageUpgrade;
import net.devtech.industrialcrust.items.upgrades.OverclockerUpgrade;
import net.devtech.industrialcrust.items.upgrades.TransformerUpgrade;
import net.devtech.industrialcrust.items.upgrades.UpgradeItem;
import net.devtech.industrialcrust.util.SkullUtil;
import net.devtech.yajslib.io.PersistentInput;
import net.devtech.yajslib.io.PersistentOutput;
import net.devtech.yajslib.persistent.PersistentRegistry;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import java.awt.Point;
import java.io.IOException;
import java.util.UUID;
import java.util.function.Predicate;

public abstract class IC0Machine extends AbstractBlock implements EnergyConsumer, BlockItem {
	private static final ItemStack FILLED_ENERGY = new ItemStack(Material.EXP_BOTTLE);
	private static final ItemStack EMPTY_ENERGY = new ItemStack(Material.GLASS_BOTTLE);
	private static final ItemStack ICON = new ItemStack(Material.REDSTONE_BLOCK);
	private static final ItemStack PROGRESS_FILLED = new ItemStack(Material.STONE);
	private static final ItemStack PROGRESS_EMPTY = new ItemStack(Material.GLASS);

	static {
		ItemMeta meta = ICON.getItemMeta();
		meta.setDisplayName(ChatColor.YELLOW+"Energy");
		ICON.setItemMeta(meta);
	}

	public static final ItemStack BACKGROUND = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 0, (byte) 7);
	protected ItemStack[] upgrades = new ItemStack[4];
	protected Inventory display = Bukkit.createInventory(null, 6 * 9, this.getName());

	/**
	 * this should be updated if there is progress, this should also be serialized, but kinda sorta optional
	 */
	protected double progress;
	/**
	 * this should be serialized as well
	 */
	protected int power;

	/**
	 * the UUID of the armorstand
	 * must be serialized
	 */
	protected UUID uuid = new UUID(0, 0);

	protected abstract String getName();
	protected abstract ItemStack getHead();
	protected abstract void place(Block block);
	protected abstract int tier();

	protected IC0Machine(PersistentRegistry registry, ServerAccess<Object> access) {
		super(registry, access);
	}

	@LocalEvent(15)
	private void place(PlaceEvent event) {
		if(this.getHead() != null) {
			Location location = this.getLocation();
			location.add(.5, -1.25, .5);
			ArmorStand stand = (ArmorStand) location.getWorld().spawnEntity(location, EntityType.ARMOR_STAND);
			stand.setVisible(false);
			stand.setMarker(true);
			stand.setGravity(false);
			stand.setHelmet(this.getHead());
			this.uuid = stand.getUniqueId();
			Bukkit.getScheduler().runTaskLater(AsynCore.instance, () -> this.place(location.getBlock()), 1);
		}
	}

	@LocalEvent
	private void destroy(DestroyEvent event) {
		Bukkit.getEntity(this.uuid).remove();
	}

	@LocalEvent
	private void gui(PlayerInteractEvent event) {
		if (event.getAction() == Action.RIGHT_CLICK_BLOCK && !event.getPlayer().isSneaking()) {
			event.setCancelled(true);
			ABackgroundPanel panel = new ABackgroundPanel(new Size2i(9, 6), BACKGROUND);

			// upgrades
			panel.addComponent(new Point(8, 0), new AFilteredInputInventoryComponent(new ArrayInvWrapper(this.upgrades), new Size2i(1, 4), i -> {
				Object object = CustomItemFactory.from(i, AsynCore.persistentRegistry);
				return object instanceof UpgradeItem;
			}));
			// energy
			panel.addComponent(new Point(0, 4), new AEnergyBar(FILLED_ENERGY, EMPTY_ENERGY, ICON, this::getPower, this::getMaxPower, 9));
			panel.addComponent(new Point(0, 5), new AHorizontalStatusBar(PROGRESS_FILLED, PROGRESS_EMPTY, null, () -> this.progress, 9));
			this.addComponents(panel);
			AsynCore.guiManager.openGui(event.getPlayer(), panel, this.display);
		}
	}

	private boolean recursiveLock;
	@LocalEvent(value = 0)
	private void resync(TickEvent event) {
		AsynCore.guiManager.resync(this.display);

		// overclockers
		if(!this.recursiveLock) {
			this.recursiveLock = true;
			// infinity brain time
			int power = this.upgrades(o -> o instanceof OverclockerUpgrade);
			if (power != 0) {
				for (int i = 0; i < power; i++) { // speed with overclockers
					AsynCore.mainWorldAccess.invoke(event); // reinvoke event
				}
			}
			this.recursiveLock = false;
		}
	}

	/**
	 * add any display components to the panel, power and upgrades are already handled for you
	 * the upgrades take up the right most 4 slots, and energy take the bottom 9, the inventory is 6x9, but you only get 4x8 to work with
	 * @param panel the panel pre-draw
	 */
	protected abstract void addComponents(ABackgroundPanel panel);

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
		return 2048 * (this.tier() + this.upgrades(o -> o instanceof EnergyStorageUpgrade));
	}

	@Override
	public int getMaxCurrent() {
		return 128 * (this.tier() + this.upgrades(o -> o instanceof TransformerUpgrade));
	}

	private int upgrades(Predicate<Object> objectPredicate) {
		int powerTier = 0;
		for (ItemStack upgrade : this.upgrades) {
			if (upgrade != null) {
				Object object = CustomItemFactory.from(upgrade, AsynCore.persistentRegistry);
				if (objectPredicate.test(object)) {
					powerTier += upgrade.getAmount();
				}
			}
		}
		// upgrades
		return powerTier;
	}

}
