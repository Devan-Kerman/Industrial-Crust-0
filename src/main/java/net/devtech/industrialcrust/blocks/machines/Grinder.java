package net.devtech.industrialcrust.blocks.machines;

import net.devtech.asyncore.AsynCore;
import net.devtech.asyncore.blocks.events.TickEvent;
import net.devtech.asyncore.blocks.world.events.LocalEvent;
import net.devtech.asyncore.gui.components.AFilteredInputInventoryComponent;
import net.devtech.asyncore.gui.components.AOutputInventoryComponent;
import net.devtech.asyncore.items.CustomItem;
import net.devtech.asyncore.items.CustomItemFactory;
import net.devtech.asyncore.util.Size2i;
import net.devtech.asyncore.util.inv.ArrayInvWrapper;
import net.devtech.asyncore.util.inv.InvWrapper;
import net.devtech.asyncore.util.inv.Inventories;
import net.devtech.asyncore.util.persistents.BukkitPersistent;
import net.devtech.asyncore.world.server.ServerAccess;
import net.devtech.industrialcrust.gui.ABackgroundPanel;
import net.devtech.industrialcrust.items.materials.dust.*;
import net.devtech.industrialcrust.items.materials.ingots.*;
import net.devtech.industrialcrust.util.BukkitSerializers;
import net.devtech.industrialcrust.util.SkullUtil;
import net.devtech.yajslib.annotations.Reader;
import net.devtech.yajslib.annotations.Writer;
import net.devtech.yajslib.io.PersistentInput;
import net.devtech.yajslib.io.PersistentInputStream;
import net.devtech.yajslib.io.PersistentOutput;
import net.devtech.yajslib.io.PersistentOutputStream;
import net.devtech.yajslib.persistent.PersistentRegistry;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.function.Function;

import static net.devtech.asyncore.AsynCore.persistentRegistry;
import static net.devtech.asyncore.util.inv.Inventories.*;

public class Grinder extends IC0Machine {
	protected static final List<Function<ItemStack, ItemStack>> RECIPES = new ArrayList<>();

	static {
		registerC2CR(BronzeIngot.class, BronzeDust.class);
		registerC2CR(CopperIngot.class, CopperDust.class);
		registerC2CR(LeadIngot.class, LeadDust.class);
		registerC2CR(SilverIngot.class, SilverDust.class);
		registerC2CR(TinIngot.class, TinDust.class);
		registerM2CR(Material.CLAY, ClayDust.class);
		registerM2CR(Material.COAL, CoalDust.class);
		registerM2CR(Material.DIAMOND, DiamondDust.class);
		registerM2CR(Material.GOLD_INGOT, GoldDust.class);
		registerM2CR(Material.IRON_INGOT, IronDust.class);
		registerM2CR(Material.INK_SACK, LapisDust.class);
	}


	private static void registerC2CR(Class<?> input, Class<?> output) {
		RECIPES.add(i -> {
			CustomItem item = CustomItemFactory.from(persistentRegistry, i);
			if (item != null && input.isAssignableFrom(item.getClass())) {
				return CustomItemFactory.wrap(persistentRegistry, (CustomItem) persistentRegistry.blank(output));
			}
			return null;
		});
	}

	private static void registerM2CR(Material input, Class<?> output) {
		RECIPES.add(i -> {
			if (i.getType() == input) {
				return CustomItemFactory.wrap(persistentRegistry, (CustomItem) persistentRegistry.blank(output));
			}
			return null;
		});
	}

	protected ItemStack[] inputs = new ItemStack[9];
	protected ItemStack[] outputs = new ItemStack[9];

	public Grinder(PersistentRegistry registry, ServerAccess<Object> access) {
		super(registry, access);
	}

	public static ItemStack grind(ItemStack stack) {
		for (Function<ItemStack, ItemStack> recipe : RECIPES) {
			ItemStack apply = recipe.apply(stack);
			if (apply != null)
				return apply;
		}
		return null;
	}

	@LocalEvent
	private void tick(TickEvent event) {
		if (this.progress < 1) {
			// virtual smelt
			boolean proc = processOne(new ArrayInvWrapper(Inventories.clone(this.inputs)), new ArrayInvWrapper(Inventories.clone(this.outputs)), Grinder::grind);
			if (proc) {
				this.progress += .1;
			} else {
				this.progress = 0; // there is no output
			}
		} else {
			// smelt item
			processOne(new ArrayInvWrapper(this.inputs), new ArrayInvWrapper(this.outputs), Grinder::grind);
			this.progress = 0;
		}
		AsynCore.guiManager.redraw(this.display);
	}

	@Override protected void addComponents(ABackgroundPanel panel) {
		// input
		panel.addComponent(new Point(0, 0), new AFilteredInputInventoryComponent(new ArrayInvWrapper(this.inputs), new Size2i(3, 3), i -> grind(i) != null));
		panel.addComponent(new Point(4, 0), new AOutputInventoryComponent(new ArrayInvWrapper(this.outputs), new Size2i(3, 3)));
	}

	@Override protected String getName() {
		return ChatColor.GRAY + "Macerator";
	}

	@Override protected ItemStack getHead() {
		return SkullUtil.itemFromName("MushTurf");
	}

	@Override protected void place(Block block) {
		System.out.println("Place");
		block.setType(Material.GLASS);
	}

	@Override protected int tier() {
		return 1;
	}

	@Override public ItemStack createBaseStack() {
		ItemStack stack = new ItemStack(Material.IRON_BLOCK);
		ItemMeta meta = stack.getItemMeta();
		meta.setDisplayName(ChatColor.GRAY + "Grinder");
		stack.setItemMeta(meta);
		return stack;
	}

	public static boolean processOne(@NotNull InvWrapper export, @NotNull InvWrapper input, Function<ItemStack, ItemStack> converter) {
		for (int i = 0; i < export.size(); ++i) {
			ItemStack content = export.getStack(i);
			if (content != null) {
				ItemStack stack = content.clone();
				stack.setAmount(1);
				ItemStack transform = converter.apply(stack);
				if (transform == null)
					return false; // no merge
				if (canAddStack(transform, input)) {
					if (getAmount(addStack(transform, input)) == 0) {
						remove(stack, export);
						return true;
					}

					return false;
				}
			}
		}

		return false;
	}

	@Reader (3489348732873889389L)
	private void read(PersistentInput input) throws Exception {
		//this.inputs = BukkitSerializers.loadItems((PersistentInputStream) input);
		//this.outputs = BukkitSerializers.loadItems((PersistentInputStream) input);
		this.progress = input.readDouble();
		this.power = input.readInt();
		this.uuid = input.readUUID();
		System.out.println("progress" + this.uuid);
		System.out.println(input.readLong() + " ==d==d=awd=a=wd===");
		this.setLocation((Location) input.readPersistent());
	}

	@Writer (3489348732873889389L)
	private void write(PersistentOutput output) throws Exception {
		//BukkitSerializers.saveItems(this.inputs, (PersistentOutputStream) output);
		//BukkitSerializers.saveItems(this.outputs, (PersistentOutputStream) output);
		output.writeDouble(this.progress);
		output.writeInt(this.power);
		output.writeUUID(UUID.randomUUID());
		System.out.println(this.getLocation() + " =====================================");
		output.writeLong(438932898932L);
		output.writePersistent(this.getLocation());
	}

}
